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
import pnj.codes.CodeDAO;
import pnj.codes.CodesDTO;
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

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoadProductServlet", urlPatterns = {"/LoadProductServlet"})
public class LoadProductServlet extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            ProductDAO productDAO = new ProductDAO();
            CategoriesDAO categoryDAO = new CategoriesDAO();
            String nameProduct = request.getParameter("nameProduct");
            String category = request.getParameter("category");
            String max = request.getParameter("max");
            String min = request.getParameter("min");
            if (nameProduct == null) {
                nameProduct = "";
            }
            if (min == null) {
                min = "";
            }
            if (max == null) {
                max = "";
            }
            if (category == null || category.equals("ALL")) {
                category = "";
            }
            ArrayList<CategoriesDTO> listCategories = categoryDAO.getListCategory();
            listCategories.add(new CategoriesDTO(listCategories.size() + 1, "ALL"));
            ArrayList<ProductDTO> listProduct = productDAO.getListProduct(nameProduct, category, min, max);
            if (listProduct.size() == 0) {
                request.setAttribute("mess", "NO PRODUCT SEARCH FOR USER");
            }else{
                request.setAttribute("listProduct", listProduct);
            }
            request.setAttribute("nameProduct", nameProduct);
            request.setAttribute("max", max);
            request.setAttribute("min", min);
            request.setAttribute("category", category);
            request.setAttribute("listCategory", listCategories);
        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
            log(ex.getMessage());
        } finally {
            request.getRequestDispatcher("Index.jsp").forward(request, response);
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
