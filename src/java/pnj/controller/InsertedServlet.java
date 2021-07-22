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
@WebServlet(name = "InsertedServlet", urlPatterns = {"/InsertedServlet"})
public class InsertedServlet extends HttpServlet {

    private String SUCCESS = "LoadProductAdminServlet";
    private String FAIL = "InsertServlet";

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

            ProductError error = new ProductError("", "", "", "", "", "", "", "", "", "", "", "", "", "");
            boolean flag = false;
            ProductDAO productDao = new ProductDAO();

            if (productID.isEmpty()) {
                flag = true;
                error.setProductIDEmpty("productID is empty");
            }
            if (!productID.isEmpty() && !productID.matches("[B][0-9]{3}")) {
                flag = true;
                error.setProductIDFormat("Product ID fortmat B{3} example B001");
            }
            if (!productID.isEmpty()) {
                try {
                    boolean checkDuplicate = productDao.checkDuplicateProduct(productID);
                    if (checkDuplicate) {
                        flag = true;
                        error.setProductIDDuplicate("ProductID duplicate " + productID);
                    }
                } catch (NamingException ex) {
                    log(ex.getMessage());
                } catch (SQLException ex) {
                    log(ex.getMessage());
                }
            }
            if (nameProduct.isEmpty()) {
                flag = true;
                error.setnameProductEmpty("Name is empty");
            }
            if (!nameProduct.isEmpty() && nameProduct.length() > 100) {
                flag = true;
                error.setnameProductLength("Name only 100 character");
            }
            if (image.isEmpty()) {
                flag = true;
                error.setImageEmpty("image is empty");
            }
            // thieu61 format
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
            if (flag) {
                request.setAttribute("productID", productID);
                request.setAttribute("nameProduct", nameProduct);
                request.setAttribute("desciption", description);
                request.setAttribute("price", price);
                request.setAttribute("quantity", quanity);
                request.setAttribute("categoryID", categoryID);
                request.setAttribute("error", error);
            } else {
                ProductDTO newProduct = new ProductDTO(productID, nameProduct, image, description, Integer.parseInt(quanity), null, null, Float.parseFloat(price), Date.valueOf(java.time.LocalDate.now()));
                StatusDAO statusDAO = new StatusDAO();
                for (StatusDTO status : statusDAO.getListStatus()) {
                    if (status.getStatusName().equals(MyContants.STATUS_ACTIVE)) {
                        newProduct.setStatus(status);
                        break;
                    }
                }
                CategoriesDAO categoryDAO = new CategoriesDAO();
                for (CategoriesDTO categoriesDTO : categoryDAO.getListCategory()) {
                    if (categoriesDTO.getCategoryID() == Integer.parseInt(categoryID)) {
                        newProduct.setCategory(categoriesDTO);
                        break;
                    }
                }
                boolean add = productDao.insertProduct(newProduct);
                if (add) {
                    url = SUCCESS;
                    request.setAttribute("addSuccess", "Add  success Product");
                } else {
                    request.setAttribute("addFail", "add fail");
                }

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
