package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;
import model.Supplier;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.SupplierTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SupplierFormController {
    public JFXTextField txtSupplierId;
    public JFXTextField txtDescription;
    public JFXTextField txtCompany;
    public Label lblSupplierId;
    public JFXTextField txtbrand;
    public TableView tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colBrand;
    public TableColumn colDescription;
    public TableColumn colCompany;
    public JFXTextField txtSupplierIdNew;
    public JFXTextField txtBrandNew;
    public JFXTextField txtDescriptionNew;
    public JFXTextField txtCompanyNew;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnAdd;
    public Button btnAdd2;

    ObservableList<SupplierTable> supplierTableObservableList= FXCollections.observableArrayList();

    public void initialize(){
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        setSupplierId();
        txtSupplierId.setText(lblSupplierId.getText());

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        try {
            getAllSupplier(new SupplierController().getAllSupplier());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        btnAdd2.setDisable(true);

    }
    public void setSupplierId(){
        try {
            lblSupplierId.setText(new SupplierController().getSupplierId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addSupplierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Supplier supplier=new Supplier(
                lblSupplierId.getText(),
                txtbrand.getText(),
                txtDescription.getText(),
                txtCompany.getText()
        );
        if (SupplierController.saveSupplier(supplier)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            txtSupplierId.clear();
            txtbrand.clear();
            txtDescription.clear();
            txtCompany.clear();
            setSupplierId();
            txtSupplierId.setText(lblSupplierId.getText());
            getAllSupplier(new SupplierController().getAllSupplier());
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

    public void getAllSupplier(ArrayList<Supplier> suppliers){
        supplierTableObservableList.clear();
        for (Supplier temp:suppliers){
            supplierTableObservableList.add(new SupplierTable(
                    temp.getSupplierId(),
                    temp.getBrand(),
                    temp.getDescription(),
                    temp.getCompany()
            ));
        }
        tblSupplier.setItems(supplierTableObservableList);
    }


    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
         Supplier supplier=new Supplier(
                 txtSupplierIdNew.getText(),
                 txtBrandNew.getText(),
                 txtDescriptionNew.getText(),
                 txtCompanyNew.getText()
         );
         if (new SupplierController().updateSupplier(supplier)){
             String title = "Successful ";
             String message = "Updated";
             TrayNotification notification = new TrayNotification();
             AnimationType type = AnimationType.POPUP;

             notification.setAnimationType(type);
             notification.setTitle(title);
             notification.setMessage(message);
             notification.setNotificationType(NotificationType.SUCCESS);
             notification.showAndDismiss(Duration.millis(8000));
             getAllSupplier(new SupplierController().getAllSupplier());
             txtSupplierIdNew.clear();
             txtBrandNew.clear();
             txtDescriptionNew.clear();
             txtCompanyNew.clear();
             txtSupplierIdNew.setText("S-00");
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

    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (new SupplierController().deleteSupplier(txtSupplierIdNew.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            setSupplierId();
            txtSupplierId.setText(lblSupplierId.getText());
            getAllSupplier(new SupplierController().getAllSupplier());
        }else{

        }
    }

    public void SupplierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String sId = txtSupplierIdNew.getText();

        Supplier supplier = new SupplierController().getSupplier(sId);
        if (supplier == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(supplier);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);


        }
    }
    void setData(Supplier s){
        txtSupplierIdNew.setText(s.getSupplierId());
        txtBrandNew.setText(s.getBrand());
        txtDescriptionNew.setText(s.getDescription());
        txtCompanyNew.setText(s.getCompany());
    }

    public void validate(TextField textField){
        String textRegEx=("^[(A-z)(\\s)]*|[(A-z)(,)]*|[(A-z)(,)(\\s)]$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=textField.getText();
        if (pattern.matcher(brand).matches()){
            textField.getParent().setStyle("-fx-border-color: green");
            btnAdd2.setDisable(false);
        }else{
            textField.getParent().setStyle("-fx-border-color: red");
        }
    }
    public void txtBrand(ActionEvent event) {
        validate(txtbrand);
        txtDescription.requestFocus();
    }
    public void txtDesc(ActionEvent event) {
        validate(txtDescription);
        txtCompany.requestFocus();
    }
    public void txtCompany(ActionEvent event) {
     validate(txtCompany);
    }

    public void brandOnAction(ActionEvent event) {
        validate(txtBrandNew);
        txtDescriptionNew.requestFocus();
    }

    public void desc(ActionEvent event) {
        validate(txtDescriptionNew);
        txtCompanyNew.requestFocus();
    }

    public void com(ActionEvent event) {
        validate(txtCompanyNew);
    }
}
