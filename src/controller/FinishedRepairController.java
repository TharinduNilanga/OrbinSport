package controller;

import db.DbConnection;
import model.FinishedRepair;
import model.Product;
import model.Repair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinishedRepairController {
    public static boolean saveFinishedRepair(FinishedRepair finishedRepair) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO FinishedRepairs VALUES (?,?,?,?,?,?)");
        preparedStatement.setObject(1,finishedRepair.getRepairNo());
        preparedStatement.setObject(2,finishedRepair.getCustomerId());
        preparedStatement.setObject(3,finishedRepair.getRepairType());
        preparedStatement.setObject(4,finishedRepair.getProductUsed());
        preparedStatement.setObject(5,finishedRepair.getQty());
        preparedStatement.setObject(6,finishedRepair.getTotalPrice());
        if ( preparedStatement.executeUpdate() > 0){
            updateQty(finishedRepair.getProductUsed(),finishedRepair.getQty());
        }else{
            return  false;
        }
return true;
    }

    public static boolean updateQty(String repairPNo, int Qty) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection()
                .prepareStatement("UPDATE RepairProducts SET qty=(qty-"+Qty+") WHERE repairProductId ='" + repairPNo+ "'");
        return preparedStatement.executeUpdate()>0;
    }
    public static ArrayList<FinishedRepair> getAllFinishedRepair() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM FinishedRepairs").executeQuery();
        ArrayList<FinishedRepair> finishedRepairArrayList=new ArrayList<>();
        while (rst.next()){
            finishedRepairArrayList.add(new FinishedRepair(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDouble(6)

            ));
        }
        return finishedRepairArrayList;
    }
}
