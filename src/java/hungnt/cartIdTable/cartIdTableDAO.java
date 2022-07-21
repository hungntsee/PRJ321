/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.cartIdTable;

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
public class cartIdTableDAO implements Serializable {

    public boolean addCart(String customerName, String customerAddress) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        int result = 0;

        try {
            con = DBulti.makeConnection();
            if (con != null) {
                String sql = "Insert into cartIdTable(customerName,customerAddress) "
                            + "values(?,?)";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, customerName);
                stm.setString(2, customerAddress);
                
                result = stm.executeUpdate();
                if( result >0 ){
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
