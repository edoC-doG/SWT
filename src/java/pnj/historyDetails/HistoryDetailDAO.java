/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.historyDetails;

import pnj.products.ProductDTO;
import pnj.categories.CategoriesDTO;
import pnj.codes.CodesDTO;
import pnj.histories.HistoryDTO;
import pnj.status.StatusDTO;
import pnj.users.UserDTO;
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
public class HistoryDetailDAO {

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

    public boolean insertHistortyDetail(HistoryDetailDTO historyDetail) throws NamingException, SQLException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Insert into HistoryDetails(IDcart,quantity,productID) "
                        + "Values(?,?,?)";
                pstm = cn.prepareStatement(sql);
                pstm.setInt(1, historyDetail.getCart().getIDcart());
                pstm.setInt(2, historyDetail.getQuantity());
                pstm.setString(3, historyDetail.getProduct().getproductID());
                flag = pstm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            close();
        }
        return flag;
    }

    public ArrayList<HistoryDetailDTO> getListHistoryDetail(String userID, String nameProduct, String dateOrder) throws SQLException, NamingException {
        ArrayList<HistoryDetailDTO> listHistory = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            String sqlCondition = "";
            if (!nameProduct.isEmpty()) {
                sqlCondition = "and b.nameProduct like ? ";
            }
            if (!dateOrder.isEmpty()) {
                sqlCondition = "and h.dateOrder = ? ";
            }
            if (!nameProduct.isEmpty() && !dateOrder.isEmpty()) {
                sqlCondition = "and b.title like ? and h.dateOrder = ? ";
            }
            if (cn != null) {
                String sql = "select h.IDcart, totalPrice, dateOrder, dateShip, isPayment,  h.codeID, "
                        + "h.userID, b.nameProduct, b.image, b.productID, b.price, hd.quantity,  b.statusID, s.statusName, c.categoryName, c.categoryID, h.codeID "
                        + "from Histories h, HistoryDetails hd, Products b, Users u, Status s, Categories c \n"
                        + "where h.IDcart = hd.IDcart "
                        + "and h.userID = u.userID "
                        + "and hd.productID = b.productID "
                        + "and b.statusID = s.statusID "
                        + "and b.categoryID = c.categoryID "
                        + "and h.userID = ? " + sqlCondition;
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, userID);
                if (!nameProduct.isEmpty()) {
                    pstm.setString(2, "%" + nameProduct + "%");
                }
                if (!dateOrder.isEmpty()) {
                    pstm.setString(2, dateOrder);
                }
                if (!nameProduct.isEmpty() && !dateOrder.isEmpty()) {
                    pstm.setString(2, "%" + nameProduct + "%");
                    pstm.setString(3, dateOrder);
                }
                rs = pstm.executeQuery();
                while (rs.next()) {
                    StatusDTO status = new StatusDTO(rs.getInt("statusID"), rs.getString("statusName"));
                    CategoriesDTO category = new CategoriesDTO(rs.getInt("categoryID"), rs.getString("categoryName"));
                    ProductDTO product = new ProductDTO(rs.getString("productID"), rs.getString("nameProduct"), rs.getString("image"),
                            "", 0,  status, category, rs.getFloat("price"), null);
                    UserDTO user = new UserDTO(rs.getString("userID"), "", "", null, null);
                    HistoryDTO history = new HistoryDTO(rs.getInt("IDcart"), rs.getFloat("totalPrice"),
                            rs.getDate("dateOrder"), rs.getDate("dateShip"), rs.getBoolean("isPayment"), user, new CodesDTO(rs.getInt("codeID"), 0, null));
                    HistoryDetailDTO historyDetail = new HistoryDetailDTO(history, product, rs.getInt("quantity"));
                    listHistory.add(historyDetail);
                }
            }
        } finally {
            close();
        }
        return listHistory;
    }
}
