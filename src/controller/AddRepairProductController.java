package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Product;
import model.RepairProducts;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.ProductTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddRepairProductController {
    public JFXTextField txtProductId;
    public JFXTextField txtDescription;
    public JFXTextField txtqty;
    public JFXTextField txtUnitPrice;
    public TableView tblProduct;
    public TableColumn colProductId;
    public TableColumn colDescription;
    public TableColumn colqty;
    public TableColumn colUnitPrice;
    public TextField txtSearch;
    public JFXTextField txtProductIdNew;
    public JFXTextField txtDescriptionNew;
    public JFXTextField txtqtyNew;
    public JFXTextField txtUnitPriceNew;
    public Button btnUpdate;
    public Button btnDelete;
    public Label lblProductId;
    public Button btnAdd;

    ObservableList<RepairProducts> productTableObservableList= FXCollections.observableArrayList();
    public void initialize(){
        setProductId();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            getProduct(new RepairProductController().getAllProduct());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        txtProductId.setText(lblProductId.getText());
        btnAdd.setDisable(true);

    }

    public void AddProductOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        RepairProducts product=new RepairProducts(
                lblProductId.getText(),
                txtDescription.getText(),
                Integer.parseInt(txtqty.getText()),
                Double.parseDouble(txtUnitPrice.getText())
        );
        if (RepairProductController.saveProduct(product)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getProduct(new RepairProductController().getAllProduct());
            setProductId();
            txtProductId.setText(lblProductId.getText());
            txtDescription.clear();
            txtqty.clear();
            txtUnitPrice.clear();

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
    public void getProduct(ArrayList<RepairProducts> productArrayList){

        productTableObservableList.clear();
        for (RepairProducts temp:productArrayList){
            productTableObservableList.add(
                    new RepairProducts(
                            temp.getProductId(),
                            temp.getDescription(),
                            temp.getQty(),
                            temp.getUnitPrice()));
        }
        tblProduct.setItems(productTableObservableList);
    }
    public void setProductId(){
        try {
            lblProductId.setText(new RepairProductController().getProductId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (new RepairProductController().deleteProduct(txtProductIdNew.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            setProductId();
            txtProductId.setText(lblProductId.getText());
            getProduct(new RepairProductController().getAllProduct());
            txtDescriptionNew.clear();
            txtqtyNew.clear();
            txtUnitPriceNew.clear();
            txtProductIdNew.clear();
            txtProductIdNew.setText("P-00");
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

    public void productIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String productIdNew=txtProductIdNew.getText();

        RepairProducts product=new RepairProductController().getProduct(productIdNew);
        if (product==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(product);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);

        }
    }
    void setData(RepairProducts p){
        txtProductIdNew.setText(p.getProductId());
        txtDescriptionNew.setText(p.getDescription());
        txtqtyNew.setText(String.valueOf(p.getQty()));
        txtUnitPriceNew.setText(String.valueOf(p.getUnitPrice()));
    }

    public void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        RepairProducts  product=new RepairProducts(
                txtProductIdNew.getText(),
                txtDescriptionNew.getText(),
                Integer.parseInt(txtqtyNew.getText()),
                Double.parseDouble(txtUnitPriceNew.getText())
        );
        if (new RepairProductController().updateProduct(product)){
            String title = "Successful ";
            String message = "Updated";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            setProductId();
            txtProductId.setText(lblProductId.getText());
            getProduct(new RepairProductController().getAllProduct());
            txtDescriptionNew.clear();
            txtqtyNew.clear();
            txtUnitPriceNew.clear();
            txtProductIdNew.clear();
            txtProductIdNew.setText("P-00");
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

    public void searchOnAction(KeyEvent event) {
        search(txtSearch.getText());
        String textRegEx=("^[(A-z)(\\s)]*|[(A-z)(,)]*|[(A-z)(,)(\\s)]$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtSearch.getText();
        if (pattern.matcher(brand).matches()){
            txtSearch.setStyle("-fx-border-color: green");
        }else{
            txtSearch.setStyle("-fx-border-color: red");
        }

    }
    private void search(String value){
        try {
            List<RepairProducts> products= RepairProductController.searchProduct(value);
            ObservableList<RepairProducts> tableData = FXCollections.observableArrayList();
            for (RepairProducts temp1 : products) {
                tblProduct.getSelectionModel().getSelectedItem();
                tableData.add(new RepairProducts(
                        temp1.getProductId(),
                        temp1.getDescription(),
                        temp1.getQty(),
                        temp1.getUnitPrice()

                ));
            }
            tblProduct.getItems().setAll(tableData);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void validateDesc(TextField textField){
        String textRegEx=("^[(A-z)(\\s)]*|[(A-z)(,)]*|[(A-z)(,)(\\s)]$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void validateQTY(TextField textField){
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void validateUnitePrice(TextField textField){
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
            btnAdd.setDisable(false);
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void txtdescOnAction(ActionEvent event) {
        validateDesc(txtDescription);
        txtqty.requestFocus();
    }

    public void txtQTYOnAction(ActionEvent event) {
        validateQTY(txtqty);
        txtUnitPrice.requestFocus();
    }

    public void txtUnitPriceOnAction(ActionEvent event) {
        validateUnitePrice(txtUnitPrice);
    }

    public void descOnAction(ActionEvent event) {
        validateDesc(txtDescriptionNew);
        txtqtyNew.requestFocus();
    }

    public void qtyOnAction(ActionEvent event) {
        validateQTY(txtqtyNew);
        txtUnitPriceNew.requestFocus();
    }

    public void priceOnAction(ActionEvent event) {
        validateUnitePrice(txtUnitPriceNew);
    }

    public void txtSerach(ActionEvent event) {
        String textRegEx=("^[(A-z)(\\s)]*|[(A-z)(,)]*|[(A-z)(,)(\\s)]$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtSearch.getText();
        if (pattern.matcher(brand).matches()){
            txtSearch.setStyle("-fx-border-color: green");
        }else{
            txtSearch.setStyle("-fx-border-color: red");
        }
    }
}
