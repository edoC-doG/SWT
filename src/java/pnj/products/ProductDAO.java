/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.products;

import pnj.categories.CategoriesDTO;
import pnj.status.StatusDTO;
import pnj.utils.DBHelper;
import pnj.utils.MyContants;
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
public class ProductDAO {

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

    public ProductDTO getDetailProduct(String productID) throws SQLException, NamingException {
        ProductDTO product = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select productID, nameProduct, image, description, price, c.categoryID, categoryName, quantity, statusName, s.statusID, date \n"
                        + "from Products b, Status s, Categories c where b.statusID = s.statusID "
                        + "and b.categoryID = c.categoryID "
                        + "and productID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, productID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    product = new ProductDTO(rs.getString("productID"), rs.getString("nameProduct"), rs.getString("image"),
                            rs.getString("description"), rs.getInt("quantity"), new StatusDTO(rs.getInt("statusID"),
                            rs.getString("statusName")), new CategoriesDTO(rs.getInt("categoryID"), rs.getString("categoryName")), rs.getFloat("price"), rs.getDate("date"));
                }
            }
        } finally {
            close();
        }
        return product;
    }
    public int checkQuantityProduct(String productID) throws NamingException, SQLException{
        int quantity = 0;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select quantity from Products where productID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, productID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        } finally {
            close();
        }
        return quantity;
    }
    public boolean updateProduct(ProductDTO product) throws SQLException, NamingException{
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Update Products SET nameProduct = ?, image = ?, description = ?, price = ?, "
                        + "categoryID = ?, statusID = ?, quantity = ?, date = ? where productID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1,  product.getnameProduct());
                pstm.setString(2,  product.getImage());
                pstm.setString(3,  product.getDescription());
                pstm.setFloat(4,  product.getPrice());             
                pstm.setInt(5,  product.getCategory().getCategoryID());
                pstm.setInt(6,  product.getStatus().getStatusID());
                pstm.setInt(7,  product.getQuantity());
                pstm.setDate(8,  product.getDate());
                pstm.setString(9,  product.getproductID());
                flag = pstm.executeUpdate() > 0 ? true : false;
            }
        }finally{
            close();
        }
        return flag;
    }

    public boolean insertProduct(ProductDTO product) throws NamingException, SQLException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "insert into Products values(?,?,?,?,?,?,?,?,?)";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, product.getproductID());
                pstm.setString(2, product.getnameProduct());
                pstm.setString(3, product.getImage());
                pstm.setString(4, product.getDescription());
                pstm.setFloat(5, product.getPrice());            
                pstm.setInt(6, product.getCategory().getCategoryID());
                pstm.setInt(7, product.getStatus().getStatusID());
                pstm.setInt(8, product.getQuantity());
                pstm.setDate(9, product.getDate());
                flag = pstm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            close();
        }
        return flag;
    }
    
    public boolean updateStatusProduct(String productID, int status) throws NamingException, SQLException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "update Products set statusID = ? where productID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setInt(1, status);
                pstm.setString(2, productID);
                flag = pstm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            close();
        }
        return flag;
    }
    
     public boolean updateQuantityProduct(String productID, int quantity) throws NamingException, SQLException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "update Products set quantity = ? where productID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setInt(1, quantity);
                pstm.setString(2, productID);
                flag = pstm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            close();
        }
        return flag;
    }

    public ArrayList<ProductDTO> getListProduct(String nameProduct, String categoryName, String min, String max) throws NamingException, SQLException {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sqlCondition = "";
        if (!categoryName.isEmpty()) {
            sqlCondition = "and c.categoryName = ? ";
        }
        if (!min.isEmpty() && !max.isEmpty()) {
            sqlCondition = "and price BETWEEN ? and ? ";
        }
        if (!categoryName.isEmpty() && !min.isEmpty() && !max.isEmpty()) {
            sqlCondition = "and categoryName = ? and price BETWEEN ? and ? ";
        }
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select productID, nameProduct, image, description, price, c.categoryID, categoryName, quantity, statusName, s.statusID, date \n"
                        + "from Products b, Status s, Categories c where b.statusID = s.statusID "
                        + "and b.categoryID = c.categoryID "
                        + "and statusName = ? and quantity > 0 "
                        + "and nameProduct like ? " + sqlCondition;
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, MyContants.STATUS_ACTIVE);
                pstm.setString(2, "%" + nameProduct + "%");
                if (!categoryName.isEmpty()) {
                    pstm.setString(3, categoryName);
                }
                if (!min.isEmpty() && !max.isEmpty()) {
                    pstm.setFloat(3, Float.parseFloat(min));
                    pstm.setFloat(4, Float.parseFloat(max));
                }
                if (!categoryName.isEmpty() && !min.isEmpty() && !max.isEmpty()) {
                    pstm.setString(3, categoryName);
                    pstm.setFloat(4, Float.parseFloat(min));
                    pstm.setFloat(5, Float.parseFloat(max));
                }
                rs = pstm.executeQuery();
                while (rs.next()) {
                    list.add(new ProductDTO(rs.getString("productID"), rs.getString("nameProduct"), rs.getString("image"),
                            rs.getString("description"), rs.getInt("quantity"), new StatusDTO(rs.getInt("statusID"),
                            rs.getString("statusName")), new CategoriesDTO(rs.getInt("categoryID"), rs.getString("categoryName")), rs.getFloat("price"), rs.getDate("date")));
                }
            }
        } finally {
            close();
        }
        return list;
    }

    public ArrayList<ProductDTO> getListProductAdmin() throws NamingException, SQLException {
        ArrayList<ProductDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select productID, nameProduct, image, description, price, c.categoryID, categoryName, quantity, statusName, s.statusID, date \n"
                        + "from Products b, Status s, Categories c where b.statusID = s.statusID "
                        + "and b.categoryID = c.categoryID ";
                pstm = cn.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    list.add(new ProductDTO(rs.getString("productID"), rs.getString("nameProduct"), rs.getString("image"),
                            rs.getString("description"), rs.getInt("quantity"), new StatusDTO(rs.getInt("statusID"),
                            rs.getString("statusName")), new CategoriesDTO(rs.getInt("categoryID"), rs.getString("categoryName")), rs.getFloat("price"), rs.getDate("date")));
                }
            }
        } finally {
            close();
        }
        return list;
    }

    public boolean checkDuplicateProduct(String productID) throws NamingException, SQLException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select productID from Products where productID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, productID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    flag = rs.getString("productID").isEmpty() ? false : true;
                }
            }

        } finally {
            close();
        }
        return flag;
    }
}
