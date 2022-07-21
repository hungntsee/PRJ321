/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.servlet;

import hungnt.cart.cartObject;
import hungnt.cartIdTable.cartIdTableDAO;
import hungnt.itemDetaillsTable.itemDetailsDAO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProcessConfirmCheckOutServlet", urlPatterns = {"/ProcessConfirmCheckOutServlet"})
public class ProcessConfirmCheckOutServlet extends HttpServlet {
    private final String VIEW_CART = "viewcart";
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
        
        String customerName = request.getParameter("customerName");
        String customerAdrress = request.getParameter("customerAddress");
        String url = roadMap.get(VIEW_CART);
        try{
            if( customerName.isEmpty() || customerAdrress.isEmpty()){
                request.setAttribute("CONFIRM_ERROR", "Customer Name or Address cannot be empty");
                request.setAttribute("CONFIRM", "confirm");
            }else{
                ////////////////////////////////////////////////////////////////
                HttpSession session  = request.getSession(false);
                if(session != null){
                    //Check if cart is exsited
                    cartObject cart = (cartObject) session.getAttribute("CART");
                    Map<String, Integer> items = cart.getItems();
                    //add cart details
                    cartIdTableDAO cartDAO = new cartIdTableDAO();
                    if( cartDAO.addCart(customerName, customerAdrress) ){
                        itemDetailsDAO itemDAO = new itemDetailsDAO();
                        for (String title : items.keySet()) {
                            itemDAO.addItem(title, items.get(title));
                        }
                        session.removeAttribute("CART");
                        request.setAttribute("CHECK_OUT_SUCCESS_MSG", "SUCCESS");
                    }
                }
                ////////////////////////////////////////////////////////////////
            }
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
            log("ProcessConfirmCheckOutServlet - ClassNotFound error: " + ex.getMessage());
        } catch (SQLException ex) {
            //ex.printStackTrace();
            log("ProcessConfirmCheckOutServlet - SQL error: " + ex.getMessage());
        } catch (NamingException ex) {
            //ex.printStackTrace();
            log("ProcessConfirmCheckOutServlet - Naming error: " + ex.getMessage());
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
