package controller;

import db.DbConnection;
import model.Product;
import model.Repair;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairController {
    public String getUserId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT repairNo FROM Repairs ORDER BY repairNo DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "R-00" + tempId;
            } else if (tempId < 99) {
                return "R-0" + tempId;
            } else {
                return "R-" + tempId;
            }

        } else {
            return "R-001";
        }
    }
   public static boolean saveRepair(Repair r) throws SQLException, ClassNotFoundException {
       PreparedStatement preparedStatement =DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Repairs VALUES (?,?,?,?)");
       preparedStatement.setObject(1,r.getRepairNo());
       preparedStatement.setObject(2,r.getCustomerId());
       preparedStatement.setObject(3,r.getRepairType());
       preparedStatement.setObject(4,r.getEstimatedPrice());
       return preparedStatement.executeUpdate()>0;
   }
    public static ArrayList<Repair> getAllRepair() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Repairs").executeQuery();
        ArrayList<Repair> repairArrayList=new ArrayList<>();
        while (rst.next()){
            repairArrayList.add(new Repair(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return repairArrayList;
    }
    public static List<Repair> searchRepair(String value) throws SQLException, ClassNotFoundException {
        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM Repairs WHERE repairNo LIKE '%"+value+"%'");
        ResultSet set=preparedStatement.executeQuery();
        List<Repair> repairArrayList=new ArrayList<>();

        while (set.next()){
           repairArrayList.add(new Repair(
                    set.getString("repairNo"),
                    set.getString("customerId"),
                    set.getString("repairType"),
                    set.getDouble("price")
            ));
        }
        return repairArrayList;
    }
    public static boolean deleteRepair(String rNo) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Repairs WHERE repairNo='" + rNo + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Repair getRepair(String rNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Repairs WHERE repairNo=?");
        stm.setObject(1,rNo);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new Repair(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
        }else{
            return null;
        }
    }
    public boolean updateRepair(Repair r) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Repairs SET customerId=?,repairType=?,price=? WHERE repairNo=?");
        stm.setObject(1,r.getCustomerId());
        stm.setObject(2,r.getRepairType());
        stm.setObject(3,r.getEstimatedPrice());
        stm.setObject(4,r.getRepairNo());
        return stm.executeUpdate()>0;
    }

}
