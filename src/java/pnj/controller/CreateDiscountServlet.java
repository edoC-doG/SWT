/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.controller;

import pnj.codes.CodeDAO;
import pnj.codes.CodeError;
import pnj.codes.CodesDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
@WebServlet(name = "CreateDiscountServlet", urlPatterns = {"/CreateDiscountServlet"})
public class CreateDiscountServlet extends HttpServlet {

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
            String codeID = request.getParameter("codeID");
            String codeValue = request.getParameter("codeValue");
            boolean flag = false;
            CodeDAO codeDAO = new CodeDAO();
            CodeError error = new CodeError("", "", "", "", "");
            if (codeID.isEmpty()) {
                flag = true;
                error.setCodeIDEmpty("code id is empty");
            }
            if (!codeID.isEmpty() && !codeID.matches("[0-9]+")) {
                flag = true;
                error.setCodeIDFormat("code id must number");
            }

            if (codeValue.isEmpty()) {
                flag = true;
                error.setCodeValueEmpty("code value is empty");
            }

            if (!codeValue.isEmpty() && !codeValue.matches("[0-9]+")) {
                flag = true;
                error.setCodeValueFormat("code value must number");
            }
            if (!codeID.isEmpty() && codeID.matches("[1-9]{1}")) {
                CodesDTO isDuplicate = codeDAO.checkCode(codeID);
                if (!codeID.isEmpty() && isDuplicate != null) {
                    flag = true;
                    error.setCodeIDduplicate("codeID " + codeID + " duplicate");
                }
            }

            if (flag) {
                request.setAttribute("codeID", codeID);
                request.setAttribute("codeValue", codeValue);
                request.setAttribute("error", error);
            } else {
                boolean add = codeDAO.insertCode(new CodesDTO(Integer.parseInt(codeID), Integer.parseInt(codeValue), Date.valueOf(java.time.LocalDate.now())));
                if (add) {
                    request.setAttribute("addSuccess", "insert success");
                }
            }
            ArrayList<CodesDTO> listCode = codeDAO.getListCode();
            request.setAttribute("listCode", listCode);
        } catch (SQLException ex) {
            log(ex.getMessage());
        } catch (NamingException ex) {
            log(ex.getMessage());
        } finally {
            request.getRequestDispatcher("AddDiscount.jsp").forward(request, response);
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
