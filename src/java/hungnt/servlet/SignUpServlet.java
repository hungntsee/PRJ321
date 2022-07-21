/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.servlet;

import hungnt.SignUpError.RegistryErrorObject;
import hungnt.user.userDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {
    private final String ERROR_SIGNUP_PAGE = "signupJ";
    private final String LOGIN_PAGE = "login";
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
        PrintWriter out = response.getWriter();
        
        ServletContext context = request.getServletContext();
        Map<String,String> roadMap = (Map<String,String>) context.getAttribute("ROAD_MAP");
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        boolean role = false;
       
        String url = roadMap.get(ERROR_SIGNUP_PAGE);
        
        RegistryErrorObject regErrObj = new RegistryErrorObject();
        try{
            boolean foundErr = false;
            if( username.trim().length() < 6 || username.trim().length() > 20 ){
                foundErr = true;
                regErrObj.setUsernameError("Username is require inputted from 6-20 chars !");
            }
            if( password.trim().length() < 6 || password.trim().length() > 20 ){
                foundErr = true;
                regErrObj.setPasswordError("Password is require inputted from 6-20 chars !");
            }else if( !confirm.equals(password) ){
                foundErr = true;
                regErrObj.setConfirmError("Confirm must match password !");
            }
            if(fullname.trim().length() < 2 || fullname.trim().length() > 50){
                foundErr = true;
                regErrObj.setFullnameError("Full name is require inputted from 2-50 chars !");
            }
            if(foundErr){
                request.setAttribute("CREATE_ERROR", regErrObj);
            }else{
                userDAO dao = new userDAO();
                if(dao.addRecord(username, password, fullname, role)){
                    url = roadMap.get(LOGIN_PAGE);
                }
            }
        } catch (ClassNotFoundException ex) {
            log("SignUpServlet - ClassNotFound error: " + ex.getMessage());
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            if(errMsg.contains("duplicate")){
               regErrObj.setUsernameError("Username is existed !");
               request.setAttribute("CREATE_ERROR", regErrObj);
            }
            log("SignUpServlet - SQL error: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SignUpServlet - Naming error: " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
