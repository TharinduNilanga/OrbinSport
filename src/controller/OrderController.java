package controller;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT `orderId` FROM `Order` ORDER BY `orderId` DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "O-00" + tempId;
            } else if (tempId < 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }
    }
    public boolean placeOrder(Order order){
        Connection connection=null;
        try {
            connection= DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm =connection.
                    prepareStatement("INSERT INTO `Order` VALUES(?,?,?,?,?,?,?,?,?)");
            stm.setObject(1,order.getOrderId());
            stm.setObject(2,order.getCustomerId());
            stm.setObject(3,order.getpId());
            stm.setObject(4,order.getWarranty());
            stm.setObject(5,order.getOrderType());
            stm.setObject(6,order.getQty());
            stm.setObject(7,order.getDiscount());
            stm.setObject(8,order.getUnitPrice());
            stm.setObject(9,order.getOrderDate());


            if (stm.executeUpdate()>0){
                if (saveOrderDetails(order.getOrderId(),order.getOrderDetails())){
                    connection.commit();
                    return true;
                }else{
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;

    }
    public boolean saveOrderDetails(String OrderId, ArrayList<OrderDetails>orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetails temp:orderDetails){
            PreparedStatement preparedStatement=DbConnection.getInstance().getConnection()
                    .prepareStatement("INSERT INTO `OrderDetails` VALUES (?,?,?,?,?,?)");
            preparedStatement.setObject(1,OrderId);
            preparedStatement.setObject(2,temp.getpId());
            preparedStatement.setObject(3,temp.getOrderType());
            preparedStatement.setObject(4,temp.getQty());
            preparedStatement.setObject(5,temp.getDiscount());
            preparedStatement.setObject(6,temp.getUnitPrice());
            if (preparedStatement.executeUpdate()>0){
                if (updateQty(temp.getpId(),temp.getQty())){

                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
    public boolean updateQty(String productId,int Qty) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE Products SET qty=(qty-"+Qty+") WHERE productId='" + productId+ "'");
        return preparedStatement.executeUpdate()>0;
    }
    public List<String> getOrderIds() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order`").executeQuery();
        List<String> orderId=new ArrayList<>();
        while (set.next()){
            orderId.add(set.getString(1));
        }return orderId;
    }
    public Order getOrder(String orderId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE orderId =?");
        stm.setObject(1,orderId);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new Order(
                   rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getInt(7),
                    rst.getDouble(8),
                    rst.getString(9)
            );
        }else{
            return null;
        }
    }
    public List<Daily> getIncomeDaily() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("select date(orderDate),sum(o.unitPrice) from Products p,`order` o where p.productId =o.pId group by date(orderDate)").executeQuery();
        List<Daily> dailyArrayList=new ArrayList<>();
        while (set.next()){
            dailyArrayList.add(new Daily(
                    set.getString(1),
                    set.getDouble(2)
            ));

        }return  dailyArrayList;
    }

    public List<Daily> getIncomeMonthly() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("select month(orderDate),sum(o.unitPrice) from Products p,`order` o where p.productId =o.pId group by month (orderDate)").executeQuery();
        List<Daily> dailyArrayList=new ArrayList<>();
        while (set.next()){
            dailyArrayList.add(new Daily(
                    set.getString(1),
                    set.getDouble(2)
            ));

        }return  dailyArrayList;
    }
    public List<Daily> getIncomeAnnually() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("select year(orderDate),sum(o.unitPrice) from Products p,`order` o where p.productId =o.pId group by year(orderDate)").executeQuery();
        List<Daily> dailyArrayList=new ArrayList<>();
        while (set.next()){
            dailyArrayList.add(new Daily(
                    set.getString(1),
                    set.getDouble(2)
            ));

        }return  dailyArrayList;
    }
    public List<most> getMostMovable() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("select p.description, sum(o.qty) from Products p,`order` o  where p.productId =o.pId  group by p.description order by sum(o.qty) desc limit 5").executeQuery();
        List<most> mostArrayList=new ArrayList<>();
        while (set.next()){
            mostArrayList.add(new most(
                    set.getString(1),
                    set.getInt(2)
            ));

        }return  mostArrayList;
    }
    public List<most> getMostMovableCount() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("select p.description, count(o.qty) from Products p,`order` o  where p.productId =o.pId  group by p.description order by count(o.qty) desc limit 5").executeQuery();
        List<most> countArrayList=new ArrayList<>();
        while (set.next()){
            countArrayList.add(new most(
                    set.getString(1),
                    set.getInt(2)
            ));

        }return  countArrayList;
    }
    public static ArrayList<adminOrder> getOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `OrderDetails`").executeQuery();
        ArrayList<adminOrder> orderArrayList=new ArrayList<>();
        while (rst.next()){
            orderArrayList.add(new adminOrder(
                  rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            ));
        }
        return orderArrayList;
    }
    public static boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order` WHERE orderId='" + id + "'").executeUpdate() > 0) {
           deleteOrderDetails(id);
        } else {
            return false;
        }
        return true;
    }
    public static boolean deleteOrderDetails(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `OrderDetails` WHERE oId='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }


}
