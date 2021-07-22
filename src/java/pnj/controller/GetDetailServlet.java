/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.controller;

import pnj.products.ProductDAO;
import pnj.products.ProductDTO;
import pnj.categories.CategoriesDAO;
import pnj.categories.CategoriesDTO;
import pnj.status.StatusDAO;
import pnj.status.StatusDTO;
import pnj.users.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "GetDetailServlet", urlPatterns = {"/GetDetailServlet"})
public class GetDetailServlet extends HttpServlet {

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
        String url = "";
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();
            UserDTO admin = (UserDTO) session.getAttribute("account");
            if (admin == null) {
                url = "Login.jsp";
                request.setAttribute("errorLogin", "Please login before update product");
            } else {
                url = "DetailProduct.jsp";
                String productID = request.getParameter("productID");
                ProductDAO productDAO = new ProductDAO();
                ProductDTO productDetail = productDAO.getDetailProduct(productID);
                CategoriesDAO categoryDAO = new CategoriesDAO();
                ArrayList<CategoriesDTO> listCategory = categoryDAO.getListCategory();
                StatusDAO statusDAO = new StatusDAO();
                ArrayList<StatusDTO> listStatus = statusDAO.getListStatus();
                request.setAttribute("productDetail", productDetail);
                request.setAttribute("listCategory", listCategory);
                request.setAttribute("listStatus", listStatus);
            }

        } catch (SQLException ex) {
            log(ex.getMessage());
        } catch (NamingException ex) {
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