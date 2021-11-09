package controller;

import db.DbConnection;
import model.Product;
import model.cash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashInHand {

    public static boolean saveProduct(cash cash) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO  CashInHand VALUES (?)");
        preparedStatement.setObject(1,cash.getCash());

        return preparedStatement.executeUpdate() > 0;

    }
  /*  public static ArrayList<cash> getCash() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM CashInHand").executeQuery();
        ArrayList<cash> cashArrayList=new ArrayList<>();
        while (rst.next()){
            cashArrayList.add(new cash(
              rst.getDouble(1)
            ));
        }
        return cashArrayList;
    }*/
  public cash getCash() throws SQLException, ClassNotFoundException {
      PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM CashInHand");
      ResultSet rst=stm.executeQuery();
      if(rst.next()){
          return new cash(
               rst.getDouble(1)
          );
      }else{
          return null;
      }
  }
    public static boolean deleteCash() throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement(" delete from cashInHand").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }
}

