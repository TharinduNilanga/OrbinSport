package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Order;
import model.Product;
import model.Return;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ReturnFormController {
    public JFXTextField txtReturnNo;
    public JFXComboBox cmbOrderId;
    public JFXComboBox cmbUserId;
    public JFXTextField txtReturnProductId;
    public JFXTextField txtCost;
    public JFXTextField txtCustomerId;
    public JFXTextField txtProductId;
    public JFXTextField txtWarranty;
    public JFXTextField txtOrderType;
    public JFXTextField txtqty;
    public JFXTextField txtdiscount;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtOrderDate;
    public JFXTextField txtDescription;
    public JFXTextField txtBrand;
    public JFXTextField txtUnitPriceInfo;
    public JFXTextField txtProductIdInfo;
    public Label lblReturnId;
    public TableView tblReturn;
    public TableColumn colReturnNo;
    public TableColumn colOrderId;
    public TableColumn colUserId;
    public TableColumn colpId;
    public TableColumn colCost;
    public Label txtTotal;
    public Label txtBalance;
    public TextField txtAmount;
    public Button btnAdd;
    public Button btnOk;


    public void initialize(){
        setReturnNo();
        txtReturnNo.setText(lblReturnId.getText());
        btnAdd.setDisable(true);
        btnOk.setDisable(true);
        try {
            getUserIds((ArrayList<String>) new UserController().getUserIds());
            getOrderIds((ArrayList<String>) new OrderController().getOrderIds());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            getReturn(new ReturnController().getReturns());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colReturnNo.setCellValueFactory(new PropertyValueFactory<>("returnNo"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colpId.setCellValueFactory(new PropertyValueFactory<>("returnedItems"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }
    public void setReturnNo(){
        try {
            lblReturnId.setText(new ReturnController().getReturnNo());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void getUserIds(ArrayList<String> userIds){
        cmbUserId.getItems().addAll(userIds);
    }
    public void getOrderIds(ArrayList<String> orderIds){
        cmbOrderId.getItems().addAll(orderIds);
    }
    public void cmbOrderIdONAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String OId=cmbOrderId.getValue().toString();
        btnOk.setDisable(false);
        btnAdd.setDisable(false);
        Order order=new OrderController().getOrder(OId);
        if (order==null){new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(order);
        }
        String pId=txtProductId.getText();
        Product p=new ProductController().getProduct(pId);
        if (p==null){new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(p);
        }

    }

    void setData(Order order){
        txtCost.setText(String.valueOf(order.getUnitPrice()));
        txtCustomerId.setText(order.getCustomerId());
        txtProductId.setText(order.getpId());
        txtReturnProductId.setText(order.getpId());
        txtWarranty.setText(order.getWarranty());
        txtOrderType.setText(order.getOrderType());
        txtqty.setText(String.valueOf(order.getQty()));
        txtdiscount.setText(String.valueOf(order.getDiscount()));
        txtUnitPrice.setText(String.valueOf(order.getUnitPrice()));
        txtOrderDate.setText(order.getOrderDate());
        txtTotal.setText(String.valueOf(order.getUnitPrice()));
    }
    void setData(Product p){
         txtProductIdInfo.setText(p.getProductId());
        txtDescription.setText(p.getDescription());
        txtBrand.setText(p.getBrand());
        txtUnitPriceInfo.setText(String.valueOf(p.getUnitPrice()));
    }

    public void AddReturnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Return r=new Return(
                txtReturnNo.getText(),
                cmbOrderId.getValue().toString(),
                cmbUserId.getValue().toString(),
                txtReturnProductId.getText(),
                Double.parseDouble(txtCost.getText())
        );
        if (ReturnController.saveReturn(r)) {
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.FADE;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getReturn(new ReturnController().getReturns());
            setReturnNo();
            txtReturnNo.setText(lblReturnId.getText());
        }else{
            String title = "Fail ";
            String message = "TryAgain";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.FADE;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.WARNING);
            notification.showAndDismiss(Duration.millis(8000));
    }
       /* if (new OrderController().deleteOrder(cmbOrderId.getValue().toString())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.FADE;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));

        }else{
            String title = "Fail ";
            String message = "TryAgain";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.FADE;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.WARNING);
            notification.showAndDismiss(Duration.millis(8000));
        }*/
    }
    ObservableList<Return> returnObservableList= FXCollections.observableArrayList();
    public void getReturn(ArrayList<Return> returns){
        returnObservableList.clear();
        for (Return temp:returns){
            returnObservableList.add(new Return(temp.getReturnNo(),temp.getOrderId(),temp.getUserId(),temp.getReturnedItems(),temp.getCost()));
        }
        tblReturn.setItems(returnObservableList);
    }

    public void OkOnAction(ActionEvent event) {
        double total=Double.parseDouble(txtTotal.getText());
        double amount=Double.parseDouble(txtAmount.getText());
        double balance=(amount)-(total);

        txtBalance.setText("Rs "+balance+"/=");
    }

    public void txtAmount(KeyEvent keyEvent) {
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtAmount.getText();
        if (pattern.matcher(brand).matches()){
            txtAmount.setStyle("-fx-border-color: green");
        }else{
            txtAmount.setStyle("-fx-border-color: red");
        }
    }
}
