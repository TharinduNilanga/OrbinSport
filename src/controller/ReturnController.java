package controller;

import db.DbConnection;
import model.Return;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnController {
    public String getReturnNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT returnNo  FROM Returns ORDER BY returnNo  DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "Rt-00" + tempId;
            } else if (tempId < 99) {
                return "Rt-0" + tempId;
            } else {
                return "Rt-" + tempId;
            }

        } else {
            return "Rt-001";
        }
    }
    public static boolean saveReturn(Return r) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection().prepareStatement("INSERT  INTO Returns VALUES (?,?,?,?,?)");
        preparedStatement.setObject(1,r.getReturnNo());
        preparedStatement.setObject(2,r.getOrderId());
        preparedStatement.setObject(3,r.getUserId());
        preparedStatement.setObject(4,r.getReturnedItems());
        preparedStatement.setObject(5,r.getCost());
        return preparedStatement.executeUpdate()>0;

    }
    public static ArrayList<Return> getReturns() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Returns").executeQuery();
        ArrayList<Return> returnArrayList=new ArrayList<>();
        while (rst.next()){
            returnArrayList.add(new Return(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
        }
        return returnArrayList;
    }
}
