/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.controller;

import pnj.products.ProductDAO;
import pnj.products.ProductDTO;
import pnj.codes.CodeDAO;
import pnj.codes.CodesDTO;
import pnj.users.UserDTO;
import pnj.utils.MyContants;
import com.sun.java.swing.plaf.windows.WindowsBorders;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
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
@WebServlet(name = "ViewListCartServlet", urlPatterns = {"/ViewListCartServlet"})
public class ViewListCartServlet extends HttpServlet {

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
        ArrayList<ProductDTO> listProduct = new ArrayList<>();
        String url = "";
        try {
            response.setContentType("text/html;charset=UTF-8");
            ProductDAO productDAO = new ProductDAO();
            listProduct = productDAO.getListProduct("", "", "", "");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("account");
            if (user == null) {
                url = "LoadProductServlet";
                request.setAttribute("errorAddToCart", "Please login before view list cart");
                request.getRequestDispatcher("LoadProductServlet").forward(request, response);
            } else {
                CodeDAO codeDAO = new CodeDAO();
                ArrayList<CodesDTO> listCode = codeDAO.getListCodeByIDUser(user.getUserID(), MyContants.STATUS_NUMBER_ACTIVE);
                request.setAttribute("listCode", listCode);
                request.setAttribute("listProduct", listProduct);
                request.setAttribute("dateOrder", Date.valueOf(java.time.LocalDate.now()));
                url = "ViewListCart.jsp";
            }
        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
            log(ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
