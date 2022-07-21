/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.itemDetaillsTable;

import hungnt.ulti.DBulti;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class itemDetailsDAO implements Serializable{
    public boolean addItem(String title, int quantity) throws ClassNotFoundException,SQLException,NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        int result = 0;
        
        try{
            con = DBulti.makeConnection();
            if(con != null){
                String sql = "declare @cartid int\n"
                            +"select @cartid = (select top(1) cartID From cartIdTable order by cartID desc)\n"
                            +"insert into itemDetailsTable(name,quantity,cartID) "
                            + "values(?, ?, @cartid)";
                stm = con.prepareStatement(sql);
                stm.setString(1, title);
                stm.setInt(2, quantity);
                
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
}