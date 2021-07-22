/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.controller;

import pnj.codeDetails.CodeDetailDAO;
import pnj.codeDetails.CodeDetailDTO;
import pnj.codes.CodeDAO;
import pnj.codes.CodesDTO;
import pnj.status.StatusDTO;
import pnj.users.UserDAO;
import pnj.users.UserDTO;

import pnj.utils.MyContants;
import java.util.ArrayList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "AddDiscountServlet", urlPatterns = {"/AddDiscountServlet"})
public class AddDiscountServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        try {
            String userID = request.getParameter("userID");
            String codeID = request.getParameter("codeID");
            CodeDetailDTO codeDetail = new CodeDetailDTO(new CodesDTO(Integer.parseInt(codeID), 0, null), userID, new StatusDTO(MyContants.STATUS_NUMBER_ACTIVE, ""));
            CodeDetailDAO codeDetailDAO = new CodeDetailDAO();
            CodeDetailDTO codeCheck = codeDetailDAO.checkCodeDetail(codeDetail);
            CodeDAO codeDAO = new CodeDAO();
            ArrayList<CodesDTO> listCode = codeDAO.getListCode();
            UserDAO userDao = new UserDAO();
            ArrayList<UserDTO> listUser = userDao.getAllUser();

            if (codeCheck == null) {
                boolean addCode = codeDetailDAO.insertCodeDetail(codeDetail);
                request.setAttribute("mess", "add success");
                request.setAttribute("codeIDSelect", codeID);
                request.setAttribute("userID", userID);
            } else {
                request.setAttribute("mess", "userID " + userID + " had " + "codeID " + codeID);
                request.setAttribute("codeIDSelect", codeID);
                request.setAttribute("userID", userID);
            }
            request.setAttribute("listUser", listUser);
            request.setAttribute("listCode", listCode);
        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
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
