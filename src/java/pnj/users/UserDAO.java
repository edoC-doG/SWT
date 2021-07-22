/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.users;

import pnj.roles.RoleDTO;
import pnj.status.StatusDTO;
import pnj.utils.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class UserDAO {

    private PreparedStatement pstm = null;
    private Connection cn = null;
    private ResultSet rs = null;

    public void close() throws SQLException {
        if (pstm != null) {
            pstm.close();
        }
        if (cn != null) {
            cn.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
    
    public UserDTO checkLogin(String userID, String password) throws NamingException, SQLException{
        UserDTO user = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select userID, username, password, r.roleID, roleName, statusName, s.statusID from Users u, Roles r, Status s where "
                        + "u.roleID = r.roleID and u.statusID = s.statusID and userID = ? and password = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, userID);
                pstm.setString(2, password);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    user = new UserDTO(rs.getString("userID"), rs.getString("userName"), rs.getString("password"), new RoleDTO(rs.getInt("roleID"), rs.getString("roleName")), new StatusDTO(rs.getInt("statusID"), rs.getString("statusName")));
                }
            }
        } finally {
            close();
        }
        return user;
    }
     public ArrayList<UserDTO> getAllUser() throws NamingException, SQLException{
        UserDTO user = null;
         ArrayList<UserDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select userID, username, password, r.roleID, roleName, statusName, s.statusID from Users u, Roles r, Status s where "
                        + "u.roleID = r.roleID and u.statusID = s.statusID and r.roleID = 1 ";
                pstm = cn.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    user = new UserDTO(rs.getString("userID"), rs.getString("userName"),
                            rs.getString("password"), new RoleDTO(rs.getInt("roleID"),
                                    rs.getString("roleName")), new StatusDTO(rs.getInt("statusID"),
                                            rs.getString("statusName")));
                    list.add(user);
                }
            }
        } finally {
            close();
        }
        return list;
    }
    
    
}
