package controller;

import db.DbConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    public String getProductId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT productId FROM Products ORDER BY productId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "P-00" + tempId;
            } else if (tempId < 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }

    public static boolean saveProduct(Product product) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Products VALUES (?,?,?,?,?)");
        preparedStatement.setObject(1, product.getProductId());
        preparedStatement.setObject(2, product.getDescription());
        preparedStatement.setObject(3, product.getBrand());
        preparedStatement.setObject(4, product.getQty());
        preparedStatement.setObject(5, product.getUnitPrice());
        return preparedStatement.executeUpdate() > 0;

    }
    public static ArrayList<Product>getAllProduct() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Products").executeQuery();
        ArrayList<Product> productArrayList=new ArrayList<>();
        while (rst.next()){
          productArrayList.add(new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            ));
        }
        return productArrayList;
    }

    public static boolean deleteProduct(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Products WHERE ProductId='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Product getProduct(String productId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Products WHERE productId=?");
        stm.setObject(1,productId);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
        }else{
            return null;
        }
    }
    public boolean updateProduct(Product p) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Products SET description=?,brand=?,qty=?,unitPrice=? WHERE productId=?");
        stm.setObject(1,p.getDescription());
        stm.setObject(2,p.getBrand());
        stm.setObject(3,p.getQty());
        stm.setObject(4,p.getUnitPrice());
        stm.setObject(5,p.getProductId());
        return stm.executeUpdate()>0;
    }
    public static List<Product> searchProduct(String value) throws SQLException, ClassNotFoundException {
        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM Products WHERE description LIKE '%"+value+"%'");
        ResultSet set=preparedStatement.executeQuery();
        List<Product>productList=new ArrayList<>();

        while (set.next()){
            productList.add(new Product(
                    set.getString("productId"),
                    set.getString("description"),
                    set.getString("brand"),
                    set.getInt("qty"),
                    set.getDouble("unitPrice")
            ));
        }
        return productList;
    }
    public List<String> getProductIds() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Products").executeQuery();
        List<String> ids=new ArrayList<>();
        while (set.next()){
            ids.add(set.getString(1));
        }return ids;
    }
    public ArrayList<Product>  getLessNotification() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Products WHERE qty<=5");
        ArrayList<Product> productArrayList=new ArrayList<>();
        ResultSet set=preparedStatement.executeQuery();
        while (set.next()){
            productArrayList.add(new Product(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getInt(4),
                    set.getDouble(5)
            ));
        }
        return productArrayList;
    }

}



