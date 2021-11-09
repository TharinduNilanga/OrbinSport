package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;
import model.Repair;
import model.Return;
import model.adminOrder;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminLoginController {
    public TableView tblRepair;
    public TableColumn colRepairNo;
    public TableColumn colCustomerId;
    public TableColumn colRepairType;
    public TableColumn colEstimatedPrice;
    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colOrderType;
    public TableColumn colQTY;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public TableView tblReturn;
    public TableColumn colReturnNo;
    public TableColumn colUserId;
    public TableColumn colpId;
    public TableColumn colCost;
    public TableColumn coloId;
    public TableColumn colproductId;

    ObservableList<Repair> repairObservableList= FXCollections.observableArrayList();
    ObservableList<Return> returnObservableList= FXCollections.observableArrayList();
    ObservableList<adminOrder> orderDetailsObservableList= FXCollections.observableArrayList();
    public void initialize() {
        colproductId.setStyle("");
        try {
            getRepair(new RepairController().getAllRepair());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colRepairNo.setCellValueFactory(new PropertyValueFactory<>("repairNo"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        colEstimatedPrice.setCellValueFactory(new PropertyValueFactory<>("estimatedPrice"));

        try {
            getReturn(new ReturnController().getReturns());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colReturnNo.setCellValueFactory(new PropertyValueFactory<>("returnNo"));
        coloId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colpId.setCellValueFactory(new PropertyValueFactory<>("returnedItems"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        try {
            getOrderDetails(new OrderController().getOrderDetails());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colproductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colOrderType.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));





        }

    public void getRepair(ArrayList<Repair> repairArrayList){

        repairObservableList.clear();
        for (Repair temp:repairArrayList){
            repairObservableList.add(
                    new Repair(
                            temp.getRepairNo(),
                            temp.getCustomerId(),
                            temp.getRepairType(),
                            temp.getEstimatedPrice()
                    ));
        }
        tblRepair.setItems(repairObservableList);
    }
    public void getReturn(ArrayList<Return> returns){
        returnObservableList.clear();
        for (Return temp:returns){
            returnObservableList.add(new Return(temp.getReturnNo(),temp.getOrderId(),temp.getUserId(),temp.getReturnedItems(),temp.getCost()));
        }
        tblReturn.setItems(returnObservableList);
    }
    public void getOrderDetails(ArrayList<adminOrder> orderDetails){
        for (adminOrder temp:orderDetails){
            orderDetailsObservableList.add(
                    new adminOrder(
                            temp.getOrderId(),
                            temp.getProductId(),
                            temp.getOrderType(),
                            temp.getQTY(),
                            temp.getDiscount(),
                            temp.getUnitPrice()
                    )
            );
        }
        tblOrder.setItems(orderDetailsObservableList);
        System.out.println(tblOrder.getItems());

    }
    /*Notifications notificationsBuilder = Notifications.create()
            .title("Login Error")
            .text("Invalid User Name or Password..")
            .graphic(null)
            .hideAfter(Duration.seconds(2))
            .position(Pos.CENTER);
                  notificationsBuilder.showError();*/
}
