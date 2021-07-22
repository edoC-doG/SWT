/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.controller;

import pnj.products.ProductDAO;
import pnj.products.ProductDTO;
import pnj.cart.CartDTO;
import pnj.codeDetails.CodeDetailDAO;
import pnj.codeDetails.CodeDetailDTO;
import pnj.codes.CodeDAO;
import pnj.codes.CodesDTO;
import pnj.histories.HistoryDAO;
import pnj.histories.HistoryDTO;
import pnj.historyDetails.HistoryDetailDAO;
import pnj.historyDetails.HistoryDetailDTO;
import pnj.payment.PaymentServices;
import pnj.status.StatusDTO;
import pnj.users.UserDTO;
import pnj.utils.MyContants;
import com.paypal.base.rest.PayPalRESTException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private String SUCCESSS = "LoadProductServlet";
    private String FAIL = "ViewListCartServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = FAIL;
        boolean isError = false;
        try {
            response.setContentType("text/html;charset=UTF-8");
            String code = request.getParameter("code");
            int codeValue = 0;
            String dateOrder = request.getParameter("dateOrder");
            String dateShip = request.getParameter("dateShip");
            String total = request.getParameter("total");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("account");
            String mess = "";
            HashMap<String, Integer> cart = (HashMap<String, Integer>) session.getAttribute("listCart");
            Set<String> listKeys = cart.keySet();
            ProductDAO productDao = new ProductDAO();
            boolean checkQuantity = false;
            String idProductOutOfStock = "";
            if (code == null) {
                code = "";
            }
            if (!dateShip.isEmpty()) {
                // kiem tra so luong trong kho trc
                for (String idProduct : listKeys) {
                    ProductDTO product = productDao.getDetailProduct(idProduct );
                    if (product.getQuantity() < cart.get(idProduct )) {
                        checkQuantity = true;
                       idProductOutOfStock = product.getproductID();
                        cart.remove(idProduct );
                        break;
                    }
                }
                if (checkQuantity) {
                    isError = true;
                    mess = "Sorry Product out of stock " + idProductOutOfStock;
                    request.setAttribute("orderFail", mess);
                } else {
                    if (code.equals("")) {
                        code = "0";
                    }
                    codeValue = Integer.parseInt(code);
                    CodeDAO codeDAO = new CodeDAO();
                    ArrayList<CodesDTO> listCode = codeDAO.getListCode();
                    CodesDTO codeDTO = null;
                    // tìm object code
                    for (CodesDTO codesDTO : listCode) {
                        if (codesDTO.getCodeValue() == codeValue) {
                            codeDTO = new CodesDTO(codesDTO.getCodeID(), codesDTO.getCodeValue(), codesDTO.getDate());
                            break;
                        }
                    }
                    // discount nè 
                    float totalAfterDiscount = Float.parseFloat(total) - (Float.parseFloat(total) * codeValue / 100);
                    // insert History
                    PaymentServices paymentService = new PaymentServices();
                     HistoryDTO history = null;
                    try {
                        history = new HistoryDTO(0, totalAfterDiscount,
                                Date.valueOf(dateOrder), Date.valueOf(dateShip), false, user, codeDTO);
                        String approveLink = paymentService.authorizePayment(history, cart, listKeys, codeValue);
                        url = approveLink;
                    } catch (PayPalRESTException ex) {
                        log(ex.getMessage());
                    }
                    session.setAttribute("history", history);
                }
            } else {
                isError = true;
                request.setAttribute("errorDateShip", "please choose DateShip");
            }

        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
            log(ex.getMessage());
        } finally {
            if (isError) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
