package controller;

import db.DbConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    public String getAdminId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT adminId  FROM Admin ORDER BY adminId  DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "A-00" + tempId;
            } else if (tempId < 99) {
                return "A-0" + tempId;
            } else {
                return "A-" + tempId;
            }

        } else {
            return "A-001";
        }
    }
    public static boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Admin VALUES (?,?,md5(?))");
        preparedStatement.setObject(1,user.getUserId());
        preparedStatement.setObject(2,user.getUserName());
        preparedStatement.setObject(3,user.getPassword());
        return preparedStatement.executeUpdate()>0;

    }
    public static ArrayList<User> getAllAdmin() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Admin").executeQuery();
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
    public static boolean deleteAdmin(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Admin WHERE adminId='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
