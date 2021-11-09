package controller;

import db.DbConnection;
import model.Customer;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT cId FROM Customer ORDER BY cID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "C-00" + tempId;
            } else if (tempId < 99) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }

        } else {
            return "C-001";
        }
    }
    public static boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?)");
        preparedStatement.setObject(1,customer.getCustomerId());
        preparedStatement.setObject(2,customer.getUserId());
        preparedStatement.setObject(3,customer.getName());
        preparedStatement.setObject(4,customer.getNic());
        preparedStatement.setObject(5,customer.getPhoneNo());
        return preparedStatement.executeUpdate()>0;
    }
    public static ArrayList<Customer>getAllCustomer() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        ArrayList<Customer> customerArrayList=new ArrayList<>();
        while (rst.next()){
            customerArrayList.add(new Customer(
                  rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return  customerArrayList;
    }
    public static boolean deleteCustomer(String cId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE cId='" + cId + "'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    public Customer getCustomer (String cId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection().prepareStatement("SELECT *FROM Customer WHERE cId=?");
        preparedStatement.setObject(1,cId);
        ResultSet set=preparedStatement.executeQuery();
        if (set.next()){
            return new Customer(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5)
            );
        }else{
            return null;
        }
    }
    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET  userId=?,`name`=?,NIC=?,phoneNO=? WHERE cId=?");
        preparedStatement.setObject(1,c.getUserId());
        preparedStatement.setObject(2,c.getName());
        preparedStatement.setObject(3,c.getNic());
        preparedStatement.setObject(4,c.getPhoneNo());
        preparedStatement.setObject(5,c.getCustomerId());
        return preparedStatement.executeUpdate()>0;
    }
    public static List<Customer> searchCustomer(String value) throws SQLException, ClassNotFoundException {
        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM Customer WHERE `name` LIKE '%"+value+"%'");
        ResultSet set=preparedStatement.executeQuery();
        List<Customer> customerList=new ArrayList<>();

        while (set.next()){
            customerList.add(new Customer(
                    set.getString("cId"),
                    set.getString("userId"),
                    set.getString("name"),
                    set.getString("nic"),
                    set.getString("phoneNo")
            ));
        }
        return customerList;
    }

    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}
