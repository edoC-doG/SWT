/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.controller;

import pnj.products.ProductDAO;
import pnj.products.ProductDTO;
import pnj.products.ProductError;
import pnj.categories.CategoriesDAO;
import pnj.categories.CategoriesDTO;
import pnj.status.StatusDAO;
import pnj.status.StatusDTO;
import pnj.utils.MyContants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
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
@WebServlet(name = "UpdatedServlet", urlPatterns = {"/UpdatedServlet"})
public class UpdatedServlet extends HttpServlet {

    private String FAIL = "DetailProduct.jsp";
    private String SUCCESS = "LoadProductAdminServlet";

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            String productID = request.getParameter("productID");
            String nameProduct = request.getParameter("nameProduct");       
            String image = request.getParameter("image");
            String description = request.getParameter("description");
            String price = request.getParameter("price");
            String quanity = request.getParameter("quantity");
            String categoryID = request.getParameter("categoryID");
            String date = request.getParameter("date");
            String status = request.getParameter("status");

            ProductError error = new ProductError("", "", "", "", "", "", "", "", "", "", "", "", "", "");
            boolean flag = false;
            ProductDAO productDao = new ProductDAO();

            if (nameProduct.isEmpty()) {
                flag = true;
                error.setnameProductEmpty("title is empty");
            }
            if (!nameProduct.isEmpty() && nameProduct.length() > 100) {
                flag = true;
                error.setnameProductLength("title only 100 character");
            }
            if (image.isEmpty()) {
                flag = true;
                error.setImageEmpty("image is empty");
            }
            if (description.isEmpty()) {
                flag = true;
                error.setDescriptionEmpty("description is empty");
            }
            if (!description.isEmpty() && description.length() > 100) {
                flag = true;
                error.setDescriptionLength("description only 100 character");
            }
            if (price.isEmpty()) {
                flag = true;
                error.setPriceEmpty("price is empty");
            }
            if (quanity.isEmpty()) {
                flag = true;
                error.setQuanityEmpty("quanlity is empty");
            }
            String mess = "";
            if (flag) {
                request.setAttribute("productID", productID);
                request.setAttribute("nameProduct", nameProduct);
                request.setAttribute("desciption", description);
                request.setAttribute("price", price);
                request.setAttribute("quantity", quanity);
                request.setAttribute("categoryID", categoryID);
                request.setAttribute("date", date);
                request.setAttribute("status", status);
                request.setAttribute("error", error);
            } else {
                ProductDTO updateProduct = new ProductDTO(productID, nameProduct, image, description, Integer.parseInt(quanity),
                        null, null, Float.parseFloat(price), Date.valueOf(date));
                StatusDAO statusDAO = new StatusDAO();
                for (StatusDTO s : statusDAO.getListStatus()) {
                    if (s.getStatusName().equals(status)) {
                        updateProduct.setStatus(s);
                        break;
                    }
                }

                CategoriesDAO categoryDAO = new CategoriesDAO();
                for (CategoriesDTO categoriesDTO : categoryDAO.getListCategory()) {
                    if (categoriesDTO.getCategoryID() == Integer.parseInt(categoryID)) {
                        updateProduct.setCategory(categoriesDTO);
                        break;
                    }
                }
                boolean isUpdate = productDao.updateProduct(updateProduct);

                if (isUpdate) {
                    url = SUCCESS;
                    mess = "update success " + productID;
                } else {
                    mess = "update fail" + productID;
                }

            }
            request.setAttribute("productID", productID);
            request.setAttribute("mess", mess);
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
