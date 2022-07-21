/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.user;

import hungnt.ulti.DBulti;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class userDAO implements Serializable{
    userDTO dto;

    public userDTO getDTO() {
        return dto;
    }
    
    public void checkLogin(String username, String password) throws ClassNotFoundException,SQLException,NamingException{
        Connection con = null;
        
        PreparedStatement stm = null;
        
        ResultSet rs = null;
        try{
            con = DBulti.makeConnection();
            if(con != null){
                String sql = "Select username, password, lastname, isAdmin "
                        + "From userTable "
                        + "Where username=? and password=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                
                rs = stm.executeQuery();
                if(rs.next()){
                    dto = new userDTO(rs.getString("username"), rs.getString("password"), rs.getString("lastname"), rs.getBoolean("isAdmin"));
                }
            }
        }finally{
            if(rs != null){
                rs.close();
            }
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
    
    List<userDTO> list;

    public List<userDTO> getList() {
        return list;
    }
    
    public void searchFullName(String searchValue) throws ClassNotFoundException, SQLException,NamingException{
        Connection con = null;
        
        PreparedStatement stm = null;
        
        ResultSet rs = null;
        
        try{
            con = DBulti.makeConnection();
            if(con != null){
                String sql = "Select username, password, lastname, isAdmin "
                        + "From userTable "
                        + "Where lastname like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                
                rs = stm.executeQuery();
                while(rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                    userDTO dto = new userDTO(username, password, lastname, role);
                    if(this.list == null){
                        this.list = new ArrayList<>();
                    }
                    
                    this.list.add(dto);
                }
            }
        }finally{
            if(rs != null){
                rs.close();
            }
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
    
    public boolean deleteRecord(String username) throws ClassNotFoundException, SQLException,NamingException{
        Connection con = null;
        
        PreparedStatement stm = null;
        
        int result = 0;
        
        try{
            con = DBulti.makeConnection();
            if(con != null){
                String sql = "Delete "
                        + "From userTable "
                        + "Where username=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                
                result = stm.executeUpdate();
                if(result > 0){
                    return true;
                }
            }
        }finally{
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
        return false;
    }
    
    public boolean updateRecord(String username, String password, boolean role) throws ClassNotFoundException,SQLException,NamingException{
        Connection con = null;
        
        PreparedStatement stm = null;
        
        int result = 0;
        
        try{
            con = DBulti.makeConnection();
            if(con != null){
                String sql = "Update userTable "
                        + "Set password=?, isAdmin=? "
                        + "Where username=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, role);
                stm.setString(3, username);
                
                result = stm.executeUpdate();
                if(result > 0){
                    return true;
                }
            }
        }finally{
            if(stm != null){
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }
        return false;
    }

    public boolean addRecord(String username, String password, String fullname, boolean role) throws ClassNotFoundException, SQLException,NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        int result = 0;
        
        try{
            con = DBulti.makeConnection();
            if( con != null){
                String sql = " Insert into userTable(username,password,lastname,isAdmin) "
                                + "values(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullname);
                stm.setBoolean(4, role);
                
                result = stm.executeUpdate();
                if( result > 0 ){
                    return true;
                }
            }
        }finally{
            if( stm != null ){
                stm.close();
            }
            if( con != null){
                con.close();
            }
        }
        return false;
    }
}
