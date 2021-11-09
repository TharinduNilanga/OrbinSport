package controller;

import db.DbConnection;
import model.Product;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController implements user {
    public String getUserId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT userId FROM `User` ORDER BY userId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "U-00" + tempId;
            } else if (tempId < 99) {
                return "U-0" + tempId;
            } else {
                return "U-" + tempId;
            }

        } else {
            return "U-001";
        }
    }

    @Override
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `User` VALUES (?,?,md5(?))");
        preparedStatement.setObject(1, user.getUserId());
        preparedStatement.setObject(2, user.getUserName());
        preparedStatement.setObject(3, user.getPassword());
        return preparedStatement.executeUpdate() > 0;

    }

    @Override
    public ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `User`").executeQuery();
        ArrayList<User> userArrayList = new ArrayList<>();
        while (rst.next()) {
            userArrayList.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return userArrayList;
    }

    @Override
    public List<String> getUserIds() throws SQLException, ClassNotFoundException {
        ResultSet set = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `User`").executeQuery();
        List<String> userId = new ArrayList<>();
        while (set.next()) {
            userId.add(set.getString(1));
        }
        return userId;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `User` WHERE userId='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
/*    public static boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `User` VALUES (?,?,md5(?))");
        preparedStatement.setObject(1,user.getUserId());
        preparedStatement.setObject(2,user.getUserName());
        preparedStatement.setObject(3,user.getPassword());
        return preparedStatement.executeUpdate()>0;

    }*/
  /*  public static ArrayList<User>getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `User`").executeQuery();
        ArrayList<User> userArrayList=new ArrayList<>();
        while (rst.next()){
            userArrayList.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return userArrayList;
    }
    public List<String> getUserIds() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `User`").executeQuery();
        List<String> userId=new ArrayList<>();
        while (set.next()){
            userId.add(set.getString(1));
        }return userId;
    }
    public static boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `User` WHERE userId='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }*/

    }
}
