package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Customer;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddCustomerFormController {
    public AnchorPane customer;
    public JFXTextField txtCustomerID;
    public JFXComboBox cmbUserID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtNIC;
    public JFXTextField txtPhoneNo;
    public JFXButton btnAddCustomer;
    public Label lblCustomerId;
    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colNic;
    public TableColumn colPhoneNo;
    public JFXTextField txtCustomerIdNew;
    public JFXTextField txtUserIdNew;
    public JFXTextField txtNameNew;
    public JFXTextField txtNICNew;
    public JFXTextField txtPhoneNoNew;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public TextField txtSearch;

    ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();
    public void initialize(){
        setCustomerId();
        txtCustomerID.setText(lblCustomerId.getText());
        try {
            getUserIds((ArrayList<String>) new UserController().getUserIds());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        try {
            getCustomer(new CustomerController().getAllCustomer());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        btnAddCustomer.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
    public void AddCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Customer customer=new Customer(
                lblCustomerId.getText(),
                cmbUserID.getValue().toString(),
                txtCustomerName.getText(),
                txtNIC.getText(),
                txtPhoneNo.getText()
                );
        if (CustomerController.saveCustomer(customer)){
           success();
            getCustomer(new CustomerController().getAllCustomer());
            setCustomerId();
            txtCustomerID.setText(lblCustomerId.getText());
            txtCustomerName.clear();
            txtNIC.clear();
            txtPhoneNo.clear();

        }else{
           Fail();
        }
        btnAddCustomer.setDisable(true);

    }
    public void setCustomerId(){
        try {
            lblCustomerId.setText(new CustomerController().getCustomerId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void getUserIds(ArrayList<String> userIds){
        cmbUserID.getItems().addAll(userIds);
    }
    public void getCustomer(ArrayList<Customer> customerArrayList){
        customerObservableList.clear();
        for (Customer tem:customerArrayList){
            customerObservableList.add(new Customer(
                    tem.getCustomerId(),
                    tem.getUserId(),
                    tem.getName(),
                    tem.getNic(),
                    tem.getPhoneNo()
            ));
        }
        tblCustomer.setItems(customerObservableList);
    }

    public void customerIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId=txtCustomerIdNew.getText();
        Customer customer=new CustomerController().getCustomer(customerId);
        if (customer==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(customer);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    void setData(Customer customer){
        txtCustomerIdNew.setText(customer.getCustomerId());
        txtUserIdNew.setText(customer.getUserId());
        txtNameNew.setText(customer.getName());
        txtNICNew.setText(customer.getNic());
        txtPhoneNoNew.setText(customer.getPhoneNo());
    }

    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

         if (new CustomerController().deleteCustomer(txtCustomerIdNew.getText())){
             successDelete();
             setCustomerId();
             getCustomer(new CustomerController().getAllCustomer());
             txtCustomerID.setText(lblCustomerId.getText());
             txtPhoneNoNew.clear();
             txtNICNew.clear();
             txtNameNew.clear();
             txtUserIdNew.clear();
             txtCustomerIdNew.setText("C-00");
         }else{
            Fail();

         }
        }

    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Customer customer=new Customer(
                txtCustomerIdNew.getText(),
                txtUserIdNew.getText(),
                txtNameNew.getText(),
                txtNICNew.getText(),
               txtPhoneNoNew.getText()
        );
        if (new CustomerController().updateCustomer(customer)){
            success();
            setCustomerId();
            getCustomer(new CustomerController().getAllCustomer());
            txtCustomerID.setText(lblCustomerId.getText());
            txtPhoneNoNew.clear();
            txtNICNew.clear();
            txtNameNew.clear();
            txtUserIdNew.clear();
            txtCustomerIdNew.setText("C-00");

        }else{
           Fail();
        }
    }

    public void CustomerSearchOnAction(KeyEvent keyEvent) {
        search(txtSearch.getText());
        validateSearch(txtSearch);
    }
    private void search(String value){
        try {
            List<Customer> customerList= CustomerController.searchCustomer(value);
            ObservableList<Customer> tableData = FXCollections.observableArrayList();
            for (Customer temp1 : customerList) {
                tblCustomer.getSelectionModel().getSelectedItem();
                tableData.add(new Customer(
                        temp1.getCustomerId(),
                        temp1.getUserId(),
                        temp1.getName(),
                        temp1.getNic(),
                        temp1.getPhoneNo()

                ));
            }
           tblCustomer.getItems().setAll(tableData);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void validateName(TextField textField){
        String textRegEx=("^[A-z]*|[A-z]*(\\s)[A-z]*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void validateSearch(TextField textField){
        String textRegEx=("^[A-z]*|[A-z]*(\\s)[A-z]*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.setStyle("-fx-border-color: green");
        }else{
            textField.setStyle("-fx-border-color: red");
        }
    }
    public void validateNIC(TextField textField){
        String textRegEx=("^[0-9]*(V)?$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void validatePHN(TextField textField){
        String textRegEx=("^[0-9]{0,3}(-)[0-9]{7}|[0-9]{0,10}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void validateId(TextField textField){
        String textRegEx=("^[A-z](-)[0-9]{3}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }

    public void txtName(ActionEvent event) {
        validateName(txtCustomerName);
        txtNIC.requestFocus();
    }

    public void txtNIC(ActionEvent event) {
        validateNIC(txtNIC);
        txtPhoneNo.requestFocus();
    }

    public void txtPhoneNo(ActionEvent event) {
        btnAddCustomer.setDisable(false);
        validatePHN(txtPhoneNo);
    }

    public void userIdOnAction(ActionEvent event) {
        validateId(txtUserIdNew);
        txtNameNew.requestFocus();
    }

    public void nameOnAction(ActionEvent event) {
        validateName(txtNameNew);
        txtNICNew.requestFocus();
    }

    public void NICOnAction(ActionEvent event) {
        validateNIC(txtNICNew);
        txtPhoneNoNew.requestFocus();
    }

    public void phoneNoOnAction(ActionEvent event) {
        validatePHN(txtPhoneNoNew);
    }

    public void success(){
        String title = "Successful ";
        String message = "Added";
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.millis(8000));
    }
    public void successDelete(){
        String title = "Successful ";
        String message = "Delete";
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.NOTICE);
        notification.showAndDismiss(Duration.millis(8000));
    }
    public void Fail(){
        String title = "Fail ";
        String message = "TryAgain";
        TrayNotification notification = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.millis(8000));
    }
}
