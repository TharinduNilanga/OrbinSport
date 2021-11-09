package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import view.tm.ProductTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SystemReportController {
    public TableView tblDaily;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableView tblMonthly;
    public TableColumn colMonth;
    public TableColumn colMTotal;
    public TableView tblAnnually;
    public TableColumn colYear;
    public TableColumn colYTotal;
    public TableView tblMostMovable;
    public TableColumn colPName;
    public TableColumn colsum;
    public TableView tblCount;
    public TableColumn colCPName;
    public TableColumn colCount;
    public TextField btnAddCashInHand;
    public Label lblCashInHand;
    public TextField txtAddCashInHand;
    public Label lblProfit;
    public TableView tblProfit;
    public TableColumn colIncome;

    ObservableList<Daily> dailyObservableList= FXCollections.observableArrayList();
    ObservableList<Daily> monthlyObservableList= FXCollections.observableArrayList();
    ObservableList<Daily> annuallyObservableList= FXCollections.observableArrayList();
    ObservableList<most> mostObservableList= FXCollections.observableArrayList();
    ObservableList<most> countObservableList= FXCollections.observableArrayList();
    ObservableList<income> icomeObservableList= FXCollections.observableArrayList();
    public void initialize() {
        try {
            getDailyIncome((ArrayList<Daily>) new OrderController().getIncomeDaily());
            getMonthlyIncome((ArrayList<Daily>) new OrderController().getIncomeMonthly());
            getAnnuallyIncome((ArrayList<Daily>) new OrderController().getIncomeAnnually());
            getMostMovable((ArrayList<most>) new OrderController().getMostMovable());
            getCount((ArrayList<most>) new OrderController().getMostMovableCount());
            getIncome((ArrayList<income>) new Income().getProfit());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

       colMonth.setCellValueFactory(new PropertyValueFactory<>("date"));
       colMTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

      colYear.setCellValueFactory(new PropertyValueFactory<>("date"));
        colYTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colPName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colsum.setCellValueFactory(new PropertyValueFactory<>("qty"));

        colCPName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("qty"));

        colIncome.setCellValueFactory(new PropertyValueFactory<>("cost"));

        try {
            getCash();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    public void getDailyIncome(ArrayList<Daily> dailyArrayList){
        for (Daily temp:dailyArrayList){
            dailyObservableList.add(new Daily(
                    temp.getDate(),
                    temp.getTotal()
            ));
        }
        tblDaily.setItems(dailyObservableList);
    }

    public void getMonthlyIncome(ArrayList<Daily> monthlyArrayList){
        for (Daily temp:monthlyArrayList){
            monthlyObservableList.add(new Daily(
                    temp.getDate(),
                    temp.getTotal()
            ));
        }
        tblMonthly.setItems(monthlyObservableList);
    }
    public void getAnnuallyIncome(ArrayList<Daily> arrayList){
        for (Daily temp:arrayList){
            annuallyObservableList.add(new Daily(
                    temp.getDate(),
                    temp.getTotal()
            ));
        }
        tblAnnually.setItems(annuallyObservableList);
    }
    public void getMostMovable(ArrayList<most> arrayList){
        for (most temp:arrayList){
            mostObservableList.add(new most(
                    temp.getDescription(),
                    temp.getQty()
            ));
        }
        tblMostMovable.setItems(mostObservableList);
    }
    public void getCount(ArrayList<most> arrayList){
        for (most temp:arrayList){
            countObservableList.add(new most(
                    temp.getDescription(),
                    temp.getQty()
            ));
        }
        tblCount.setItems(countObservableList);
    }

    public void OkOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        cash c=new cash(Double.parseDouble(txtAddCashInHand.getText()));

        if (CashInHand.saveProduct(c)){
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
        getCash();
    }


public void getCash() throws SQLException, ClassNotFoundException {
        cash cash=new CashInHand().getCash();
        if (cash==null){
        } else {
            setData(cash);
        }
    }
    void setData(cash c){
    lblCashInHand.setText(c.getCash()+"/=");
        }

    public void RemoveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (CashInHand.deleteCash()){
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            getCash();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void profit(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/incomeCalculated.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

  /*  ObservableList<cash> cashObservableList=FXCollections.observableArrayList();
    public void getCash(ArrayList<cash> cashArrayList){

        cashObservableList.clear();
        for (cash temp:cashArrayList){
            cashObservableList.add(
                    new cash(
                          temp.getCash()));
        }

    }*/
/*  public void getIncome(ArrayList<income> arrayList) throws SQLException, ClassNotFoundException {
      income income=new  Income().getIncomeFinishedRepair();
      income income1=new Income().getIncomeOrder();
      income income2=new Income().getReturnCost();


  }*/
}
