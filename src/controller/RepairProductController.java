package controller;

import db.DbConnection;
import model.Product;
import model.RepairProducts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairProductController {

        public String getProductId() throws SQLException, ClassNotFoundException {
            ResultSet rst = DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "SELECT repairProductId FROM RepairProducts ORDER BY repairProductId DESC LIMIT 1"
                    ).executeQuery();
            if (rst.next()) {

                int tempId = Integer.
                        parseInt(rst.getString(1).split("-")[1]);
                tempId = tempId + 1;
                if (tempId < 10) {
                    return "RP-00" + tempId;
                } else if (tempId < 99) {
                    return "RP-0" + tempId;
                } else {
                    return "RP-" + tempId;
                }

            } else {
                return "RP-001";
            }
        }

        public static boolean saveProduct(RepairProducts product) throws SQLException, ClassNotFoundException {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO RepairProducts VALUES (?,?,?,?)");
            preparedStatement.setObject(1, product.getProductId());
            preparedStatement.setObject(2, product.getDescription());
            preparedStatement.setObject(3, product.getQty());
            preparedStatement.setObject(4, product.getUnitPrice());
            return preparedStatement.executeUpdate() > 0;

        }
        public static ArrayList<RepairProducts> getAllProduct() throws SQLException, ClassNotFoundException {
            ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM RepairProducts").executeQuery();
            ArrayList<RepairProducts> repairProductProductArrayList=new ArrayList<>();
            while (rst.next()){
                repairProductProductArrayList.add(new RepairProducts(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getDouble(4)
                ));
            }
            return repairProductProductArrayList ;
        }

        public static boolean deleteProduct(String repairProductId) throws SQLException, ClassNotFoundException {
            if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM RepairProducts WHERE RepairProductId='" + repairProductId + "'").executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        }
        public RepairProducts getProduct(String repairProductId) throws SQLException, ClassNotFoundException {
            PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM RepairProducts WHERE repairProductId=?");
            stm.setObject(1,repairProductId);
            ResultSet rst=stm.executeQuery();
            if(rst.next()){
                return new RepairProducts(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getDouble(4)
                );
            }else{
                return null;
            }
        }
        public boolean updateProduct(RepairProducts repairProducts) throws SQLException, ClassNotFoundException {
            PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE RepairProducts SET description=?,qty=?,unitPrice=? WHERE repairProductId=?");
            stm.setObject(1,repairProducts.getDescription());
            stm.setObject(2,repairProducts.getQty());
            stm.setObject(3,repairProducts.getUnitPrice());
            stm.setObject(4,repairProducts.getProductId());
            return stm.executeUpdate()>0;
        }
        public static List<RepairProducts> searchProduct(String value) throws SQLException, ClassNotFoundException {
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM RepairProducts WHERE description LIKE '%"+value+"%'");
            ResultSet set=preparedStatement.executeQuery();
            List<RepairProducts>productList=new ArrayList<>();

            while (set.next()){
                productList.add(new RepairProducts(
                        set.getString("RepairProductId"),
                        set.getString("description"),
                        set.getInt("qty"),
                        set.getDouble("unitPrice")
                ));
            }
            return productList;
        }
        public List<String> getRepairProductIds() throws SQLException, ClassNotFoundException {
            ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM RepairProducts").executeQuery();
            List<String> ids=new ArrayList<>();
            while (set.next()){
                ids.add(set.getString(1));
            }return ids;
        }
   /* public List<String> getProductIds() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Products").executeQuery();
        List<String> ids=new ArrayList<>();
        while (set.next()){
            ids.add(set.getString(1));
        }return ids;*/
    //}

    }






