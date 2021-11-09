package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Product;
import model.income;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class incomeCalculatedController extends SystemReportController{

    public Label lblincome;
    public Label lbldeductcost;
    public Label lblprofit;
    public TableView tblProfit;
    public TableColumn colIncome;
    ObservableList<income> icomeObservableList= FXCollections.observableArrayList();
    public void initialize(){
       colIncome.setStyle("-fx-alignment:CENTER;");
        try {
            income();
            incomeReturn();
            Profit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            getIncome((ArrayList<income>) new Income().getProfit());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        colIncome.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }
    public void income() throws SQLException, ClassNotFoundException {

        income income=new  Income().getCost();
            setData(income);
    }
    void setData(income i){
        lblincome.setText(String.valueOf(i.getCost()));
    }
    public void incomeReturn(){
        try {
            income income=new Income().getReturnCost();
            setR(income);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    void setR(income i){
        lbldeductcost.setText(String.valueOf(i.getCost()));
    }
    public void Profit(){
        double income=Double.parseDouble(lblincome.getText());
        double deductedCost=Double.parseDouble(lbldeductcost.getText());

        double profit=income-deductedCost;

        lblprofit.setText(String.valueOf(profit));
    }


    public void add(ActionEvent event) throws SQLException, ClassNotFoundException {
        getIncome((ArrayList<income>) new Income().getProfit());
        income income=new income(
                Double.parseDouble(lblprofit.getText())
        );
       if(Income.saveIncome(income)){
           String title = "Successful ";
           String message = "Added";
           TrayNotification notification = new TrayNotification();
           AnimationType type = AnimationType.POPUP;

           notification.setAnimationType(type);
           notification.setTitle(title);
           notification.setMessage(message);
           notification.setNotificationType(NotificationType.SUCCESS);
           notification.showAndDismiss(Duration.millis(8000));
       }else{
           String title = "Fail ";
           String message = "TryAgain";
           TrayNotification notification = new TrayNotification();
           AnimationType type = AnimationType.POPUP;
           notification.setAnimationType(type);
           notification.setTitle(title);
           notification.setMessage(message);
           notification.setNotificationType(NotificationType.WARNING);
           notification.showAndDismiss(Duration.millis(8000));
       }
    }
    public void getIncome(ArrayList<income> ArrayList){
        for (income temp:ArrayList){
            icomeObservableList.add(new income(
                    temp.getCost()
            ));
            tblProfit.setItems(icomeObservableList);
        }

    }

    public void reportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/Profit.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            double orderIncome=Double.parseDouble(lblincome.getText());
            double cost=Double.parseDouble(lbldeductcost.getText());
            double profit=Double.parseDouble(lblprofit.getText());
            HashMap map=new HashMap();
            map.put("Income",orderIncome);
            map.put("returnOrderCost",cost);
            map.put("calculatedIncome",profit);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map,new JREmptyDataSource(1) );
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
