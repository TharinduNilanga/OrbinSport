package controller;

import db.DbConnection;
import model.Daily;
import model.Product;
import model.income;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Income {
  /* *//* public List<income> getIncomeFinishedRepair() throws SQLException, ClassNotFoundException {
        ResultSet set= DbConnection.getInstance().getConnection().prepareStatement("select sum(totalPrice) from finishedRepairs").executeQuery();
        List<income>  ArrayList=new ArrayList<>();
        while (set.next()){
            ArrayList.add(new income(
                    set.getDouble(1)
            ));

        }return  ArrayList;
    }
    public List<income> getIncomeOrder() throws SQLException, ClassNotFoundException {
        ResultSet set = DbConnection.getInstance().getConnection().prepareStatement("select sum(unitPrice) from `order`").executeQuery();
        List<income> ArrayList = new ArrayList<>();
        while (set.next()) {
            ArrayList.add(new income(
                    set.getDouble(1)
            ));

        }
        return ArrayList;
    }
    public List<income> getReturnCost() throws SQLException, ClassNotFoundException {
        ResultSet set = DbConnection.getInstance().getConnection().prepareStatement("select sum(cost) from returns").executeQuery();
        List<income> ArrayList = new ArrayList<>();
        while (set.next()) {
            ArrayList.add(new income(
                    set.getDouble(1)
            ));

        }
        return ArrayList;
    }*//*
    public income getIncomeOrder()throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("select sum(cost) from returns");
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new income(
                    rst.getDouble(1)
            );
        }else{
            return null;
        }
    }
    public income getIncomeFinishedRepair() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("select sum(unitPrice) from `order`");
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new income(
                    rst.getDouble(1)
            );
        }else{
            return null;
        }
    }
    public income getReturnCost() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("select sum(cost) from returns");
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new income(
                    rst.getDouble(1)
            );
        }else{
            return null;
        }
    }*/
  public income getCost() throws SQLException, ClassNotFoundException {
      PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("select sum(o.unitPrice+f.totalPrice)as income from finishedRepairs f ,`order` o");
      ResultSet rst = stm.executeQuery();
      if (rst.next()) {
          return new income(
                  rst.getDouble(1)
          );
      } else {
          return null;
      }
  }
    public income getReturnCost() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("select sum(cost) from returns");
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new income(
                    rst.getDouble(1)
            );
        }else{
            return null;
        }
    }
    public static boolean saveIncome(income income) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("insert into income values(?)");
        preparedStatement.setObject(1,income.getCost());
        return preparedStatement.executeUpdate() > 0;
    }
    public List<income> getProfit() throws SQLException, ClassNotFoundException {
        ResultSet set = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Income").executeQuery();
        List<income> ArrayList = new ArrayList<>();
        while (set.next()) {
            ArrayList.add(new income(
                    set.getDouble(1)
            ));

        }
        return ArrayList;
    }

}
