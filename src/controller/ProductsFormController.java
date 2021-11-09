package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Product;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.ProductTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProductsFormController {
    public Label lblProductId;
    public JFXTextField txtProductId;
    public JFXTextField txtDescription;
    public JFXTextField txtqty;
    public JFXTextField txtUnitPrice;
    public TableView tblProduct;
    public TableColumn colProductId;
    public TableColumn colDescription;
    public TableColumn colBrand;
    public TableColumn colqty;
    public TableColumn colUnitPrice;
    public JFXTextField txtProductIdNew;
    public JFXTextField txtDescriptionNew;
    public JFXTextField txtBrandNew;
    public JFXTextField txtqtyNew;
    public JFXTextField txtUnitPriceNew;
    public TextField txtSearch;
    public JFXComboBox cmbBrand;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnAddproduct;

    ObservableList<ProductTable> productTableObservableList= FXCollections.observableArrayList();
    public void initialize(){
        setProductId();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        try {
            getBrand((ArrayList<String>) new SupplierController().getBrands());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        btnAddproduct.setDisable(true);

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            getProduct(new ProductController().getAllProduct());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        txtProductId.setText(lblProductId.getText());


    }
    public void getBrand(ArrayList<String> brand){
        cmbBrand.getItems().addAll(brand);
    }
    public void AddProductOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Product product=new Product(
                lblProductId.getText(),
                txtDescription.getText(),
                cmbBrand.getValue().toString(),
                Integer.parseInt(txtqty.getText()),
                Double.parseDouble(txtUnitPrice.getText())
        );
        if (ProductController.saveProduct(product)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getProduct(new ProductController().getAllProduct());
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
    public void getProduct(ArrayList<Product> productArrayList){

        productTableObservableList.clear();
        for (Product temp:productArrayList){
            productTableObservableList.add(
                    new ProductTable(
                            temp.getProductId(),
                            temp.getDescription(),
                            temp.getBrand(),
                            temp.getQty(),
                            temp.getUnitPrice()));
        }
        tblProduct.setItems(productTableObservableList);
    }
    public void setProductId(){
        try {
            lblProductId.setText(new ProductController().getProductId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (new ProductController().deleteProduct(txtProductIdNew.getText())){
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
            getProduct(new ProductController().getAllProduct());
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

        Product product=new ProductController().getProduct(productIdNew);
        if (product==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(product);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);

        }
    }
    void setData(Product p){
        txtProductIdNew.setText(p.getProductId());
        txtDescriptionNew.setText(p.getDescription());
        txtBrandNew.setText(p.getBrand());
        txtqtyNew.setText(String.valueOf(p.getQty()));
        txtUnitPriceNew.setText(String.valueOf(p.getUnitPrice()));
    }

    public void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Product  product=new Product(
                txtProductIdNew.getText(),
                txtDescriptionNew.getText(),
                txtBrandNew.getText(),
                Integer.parseInt(txtqtyNew.getText()),
                Double.parseDouble(txtUnitPriceNew.getText())
        );
        if (new ProductController().updateProduct(product)){
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
            getProduct(new ProductController().getAllProduct());
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
        String textRegEx=("^[A-z]*?[(A-z)(,)]*$");
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
            List<Product>products= ProductController.searchProduct(value);
            ObservableList<ProductTable> tableData = FXCollections.observableArrayList();
            for (Product temp1 : products) {
                tblProduct.getSelectionModel().getSelectedItem();
                tableData.add(new ProductTable(
                        temp1.getProductId(),
                        temp1.getDescription(),
                        temp1.getBrand(),
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
            btnAddproduct.setDisable(false);
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void txtDesc(ActionEvent event) {
        validateDesc(txtDescription);
    }

    public void txtQTY(ActionEvent event) {
        validateQTY(txtqty);
        txtUnitPrice.requestFocus();
    }

    public void txtUnitPrice(ActionEvent event) {
        validateUnitePrice(txtUnitPrice);
    }

    public void desc(ActionEvent event) {
        validateDesc(txtDescriptionNew);
        txtBrandNew.requestFocus();
    }

    public void brand(ActionEvent event) {
        validateDesc(txtBrandNew);
        txtqtyNew.requestFocus();
    }

    public void Qty(ActionEvent event) {
        validateQTY(txtqtyNew);
        txtUnitPriceNew.requestFocus();
    }

    public void unitPrice(ActionEvent event) {
        validateUnitePrice(txtUnitPriceNew);

    }


   /* public void search(List<Product> products) throws SQLException, ClassNotFoundException {
        ObservableList<ProductTable> searchTable= FXCollections.observableArrayList();
        Product product= (Product) new ProductController().searchProduct(txtSearch.getText());
        for (Product temp:products){
            productTableObservableList.add(
                    new ProductTable(
                            temp.getProductId(),
                            temp.getDescription(),
                            temp.getBrand(),
                            temp.getQty(),
                            temp.getUnitPrice()));
        }
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tblProduct.setItems(searchTable);

    }*/


}
