package controller;

import db.DbConnection;
import model.Product;
import model.Supplier;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController {
    public String getSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT sId FROM Supplier ORDER BY sId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 10) {
                return "S-00" + tempId;
            } else if (tempId < 99) {
                return "S-0" + tempId;
            } else {
                return "S-" + tempId;
            }

        } else {
            return "S-001";
        }
    }

    public static boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Supplier VALUES (?,?,?,?)");
        preparedStatement.setObject(1,supplier.getSupplierId());
        preparedStatement.setObject(2,supplier.getBrand());
        preparedStatement.setObject(3,supplier.getDescription());
        preparedStatement.setObject(4,supplier.getCompany());

        return preparedStatement.executeUpdate()>0;

    }
    public List<String> getBrands() throws SQLException, ClassNotFoundException {
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier").executeQuery();
        List<String> brand=new ArrayList<>();
        while (set.next()){
            brand.add(set.getString(2));
        }return brand;
    }
    public static ArrayList<Supplier>getAllSupplier() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier").executeQuery();
        ArrayList<Supplier> supplierArrayList=new ArrayList<>();
        while (rst.next()){
            supplierArrayList.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return supplierArrayList;
    }
    public Supplier getSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE sId=?");
        statement.setObject(1,supplierId);
        ResultSet set=statement.executeQuery();
        if (set.next()){
            return new Supplier(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4)
            );
        }else{
            return null;
        }


    }
    public boolean updateSupplier(Supplier s) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Supplier SET brand=?,description=?,company=? WHERE sId=?");
       preparedStatement.setObject(1,s.getBrand());
       preparedStatement.setObject(2,s.getDescription());
       preparedStatement.setObject(3,s.getCompany());
       preparedStatement.setObject(4,s.getSupplierId());
       return preparedStatement.executeUpdate()>0;
    }
    public static boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Supplier WHERE sId='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
