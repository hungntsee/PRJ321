/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.servlet;

import hungnt.user.userDAO;
import hungnt.user.userDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {
    private final String LOGIN_PAGE = "login";
    private final String SEARCH_PAGE = "search";
    private final String LOAD_PRODUCT = "loadProductS";
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
        String url = LOGIN_PAGE;
        try{
          Cookie[] cookies = request.getCookies();
          if(cookies != null){
              Cookie cookie = cookies[cookies.length - 1];
              String username = cookie.getName();
              String password = cookie.getValue();
              
              userDAO dao = new userDAO();
              dao.checkLogin(username, password);
              userDTO dto = dao.getDTO();
              
              if(dto != null){
                  HttpSession session = request.getSession();
                  session.setAttribute("curUSER", dto);
                  if(dto.isRole()){
                      url = SEARCH_PAGE;
                  }else{
                      url = LOAD_PRODUCT;
                  }
              }else{
                  url = LOGIN_PAGE;
              }
          }
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
            log("StartUpServlet - ClassNotFound err: " + ex.getMessage());
        } catch (SQLException ex) {
            //ex.printStackTrace();
            log("StartUpServlet - ClassNotFound err: " + ex.getMessage());
        } catch (NamingException ex) {
            //ex.printStackTrace();
            log("StartUpServlet - ClassNotFound err: " + ex.getMessage());
        }finally{
            response.sendRedirect(url);
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
