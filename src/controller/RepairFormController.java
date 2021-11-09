package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.FinishedRepair;
import model.Product;
import model.Repair;
import model.RepairProducts;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.ProductTable;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class RepairFormController {
    public JFXTextField txtRepairId;
    public JFXComboBox cmbCustomerId;
    public JFXComboBox cmbRepairType;
    public JFXTextField txtEstimatedPrice;
    public Label lblRepairId;
    public TableView tblRepair;
    public TableColumn colRepairNo;
    public TableColumn colCustomerId;
    public TableColumn colRepairType;
    public TableColumn colEstimatedPrice;
    public TextField txtSearch;
    public JFXTextField txtRepairNo;
    public JFXComboBox cmbProductUsed;
    public JFXTextField txtqty;
    public JFXTextField txtCustomerIdNew;
    public JFXTextField txtRepairTypeNew;
    public JFXTextField txtProductPrice;
    public JFXTextField txtCharges;
    public JFXTextField txtProductQTY;
    public Label lblTotal;
    public JFXButton btnAdd;
    public Label txtPayementTotal;
    public TableView tblFinishedRepair;
    public TableColumn colRepairNew;
    public TableColumn colCustomerIdNew;
    public TableColumn colRepairTypeNew;
    public TableColumn colProductsUsedNew;
    public TableColumn colTotalNew;
    public Label txtBalance;
    public TextField txtAmount;
    public TableColumn colFRepairTypeNew;
    public Button btnOk;
    public Button btnBillNew;
    public Button btnUpdate;
    public JFXButton btnAddRepair;
    public Button btnPrint;
    public JFXButton btnSet;

    ObservableList<Repair> repairObservableList= FXCollections.observableArrayList();
    ObservableList<FinishedRepair> finishedRepairObservableList= FXCollections.observableArrayList();
    public void initialize() {
        setRepairId();
        txtRepairId.setText(lblRepairId.getText());
        txtSearch.setText("R-00");
        txtRepairNo.setText("R-00");
        btnAdd.setDisable(true);
        btnOk.setDisable(true);
        btnUpdate.setDisable(true);
        btnAddRepair.setDisable(true);
        btnBillNew.setDisable(true);
        btnPrint.setDisable(true);


        cmbRepairType.getItems().addAll("Binding Bat","Replace Handle","Seasoning bat","Applying season Cover","Applying Gud For Badminton");
        try {
            getCustomerId((ArrayList<String>) new CustomerController().getCustomerIds());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            getRepair(new RepairController().getAllRepair());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            getRepairProductId((ArrayList<String>) new RepairProductController().getRepairProductIds());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colRepairNo.setCellValueFactory(new PropertyValueFactory<>("repairNo"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        colEstimatedPrice.setCellValueFactory(new PropertyValueFactory<>("estimatedPrice"));

        try {
            getFinishedRepair(new FinishedRepairController().getAllFinishedRepair());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colRepairNew.setCellValueFactory(new PropertyValueFactory<>("repairNo"));
        colCustomerIdNew.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colFRepairTypeNew.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        colProductsUsedNew.setCellValueFactory(new PropertyValueFactory<>("productUsed"));
        colTotalNew.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }
    public void setRepairId() {
        try {
            lblRepairId.setText(new RepairController().getUserId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    public void getCustomerId(ArrayList<String> cId){
        cmbCustomerId.getItems().addAll(cId);
    }

    public void AddOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Repair repair=new Repair(
                txtRepairId.getText(),
                cmbCustomerId.getValue().toString(),
                cmbRepairType.getValue().toString(),
                Double.parseDouble( txtEstimatedPrice.getText())
        );
        if (new RepairController().saveRepair(repair)) {
            String title = "Successful ";
            String message = "Saved";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            setRepairId();
            txtRepairId.setText(lblRepairId.getText());
            getRepair(new RepairController().getAllRepair());
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

    public void updateDeleteOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/UpdateDeleteRepair.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void searchOnAction(ActionEvent event) {


    }
    private void search(String value){
        try {
            List<Repair> repairs= new RepairController().searchRepair(value);
            ObservableList<Repair> tableData = FXCollections.observableArrayList();
            for (Repair temp1 : repairs) {
                tblRepair.getSelectionModel().getSelectedItem();
                tableData.add(new Repair(
                        temp1.getRepairNo(),
                        temp1.getCustomerId(),
                        temp1.getRepairType(),
                        temp1.getEstimatedPrice()

                ));
            }
            tblRepair.getItems().setAll(tableData);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void repairNoOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String repairNo=txtRepairNo.getText();

        Repair repair =new RepairController().getRepair(repairNo);
        if (repair==null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(repair);
         /*   btnUpdate.setDisable(false);
            btnDelete.setDisable(false);*/

        }
    }
    void setData(Repair r){
        txtRepairNo.setText(r.getRepairNo());
        txtCustomerIdNew.setText(r.getCustomerId());
        txtRepairTypeNew.setText(r.getRepairType());

    }
    public void getRepairProductId(ArrayList<String> rNo){
        cmbProductUsed.getItems().addAll(rNo);
    }
    public void cmbProductUsedOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String rProductId=cmbProductUsed.getValue().toString();
         RepairProducts repairProducts =new RepairProductController().getProduct(rProductId);
        if (repairProducts==null){new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(repairProducts);
        }

    }
    void setData(RepairProducts repairProducts){
        txtProductPrice.setText(String.valueOf(repairProducts.getUnitPrice()));
        txtProductQTY.setText(String.valueOf(repairProducts.getQty()));
    }

    public void AddFinishedRepairOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        FinishedRepair finishedRepair=new FinishedRepair(
                txtRepairNo.getText(),
                txtCustomerIdNew.getText(),
                txtRepairTypeNew.getText(),
                (String) cmbProductUsed.getValue(),
                Integer.parseInt(txtqty.getText()),
                Double.parseDouble(txtPayementTotal.getText())

        );
        if (new FinishedRepairController().saveFinishedRepair(finishedRepair)){
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getFinishedRepair(new FinishedRepairController().getAllFinishedRepair());

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


        if (new RepairController().deleteRepair(txtRepairNo.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
            notification.showAndDismiss(Duration.millis(8000));
            setRepairId();
            txtRepairId.setText(lblRepairId.getText());
            getRepair(new RepairController().getAllRepair());
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

    public void setOnAction(ActionEvent event) {
        int QTY=Integer.parseInt(txtqty.getText());
        int productQty=Integer.parseInt(txtProductQTY.getText());
        double productPrice=Double.parseDouble(txtProductPrice.getText());
        double nonValuatedPrice=(QTY)*(productPrice);
        double charges=Double.parseDouble(txtCharges.getText());
        double Total=(nonValuatedPrice)+(charges);
        if (QTY > productQty) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }else {
            lblTotal.setText(Total + "/=");
            txtPayementTotal.setText(String.valueOf(Total));
        }
        btnAdd.setDisable(false);
        btnBillNew.setDisable(false);
        btnOk.setDisable(false);
    }
    public void getFinishedRepair(ArrayList<FinishedRepair> finishedRepairs){

        finishedRepairObservableList.clear();
        for (FinishedRepair temp:finishedRepairs){
           finishedRepairObservableList.add(
                    new FinishedRepair(
                            temp.getRepairNo(),
                            temp.getCustomerId(),
                            temp.getRepairType(),
                            temp.getProductUsed(),
                            temp.getQty(),
                            temp.getTotalPrice()
                    ));
        }
        System.out.println(finishedRepairObservableList);
        tblFinishedRepair.setItems(finishedRepairObservableList);
    }

    public void OKOnAction(ActionEvent event) {
        double total=Double.parseDouble(txtPayementTotal.getText());
        double amount=Double.parseDouble(txtAmount.getText());
        double balance=(amount)-(total);

        txtBalance.setText("Rs "+balance+"/=");
    }

    public void txtAmountOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtAmount.getText();
        if (pattern.matcher(brand).matches()){
            txtAmount.setStyle("-fx-border-color: green");
        }else{
            txtAmount.setStyle("-fx-border-color: red");
        }
    }

    public void txtQTYOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtqty.getText();
        if (pattern.matcher(brand).matches()){
            txtqty.setStyle("-fx-border-color: green");
            txtCharges.requestFocus();
        }else{
            txtqty.setStyle("-fx-border-color: red");
        }
    }

    public void txtChargeOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtCharges.getText();
        if (pattern.matcher(brand).matches()){
            txtCharges.setStyle("-fx-border-color: green");
        }else{
            txtCharges.setStyle("-fx-border-color: red");
        }
    }

    public void txtSearchOnKeyReleased(KeyEvent keyEvent) {
        String textRegEx=("^[A-z]{1}-[0-9]{1,3}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtSearch.getText();
        if (pattern.matcher(brand).matches()){
            txtSearch.setStyle("-fx-border-color: green");
        }else{
            txtSearch.setStyle("-fx-border-color: red");
        }
        search(txtSearch.getText());

    }

    public void txtPriceOnAction(KeyEvent keyEvent) {
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtEstimatedPrice.getText();
        if (pattern.matcher(brand).matches()){
            txtEstimatedPrice.getParent().setStyle("-fx-border-color: green");
        }else{
            txtEstimatedPrice.getParent().setStyle("-fx-border-color: red");
        }
    }

    public void RepairBillOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/RepairBill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
      /*      for (int i = 0; i < obList.size(); i++) {
                System.out.println(obList.get(i).getOrderId());
            }

            String customerName=txtName.getText();
            String NIc=txtNIC.getText();
            double Total=Double.parseDouble(txtTotalNew.getText());
*/
            String  rNo=txtRepairId.getText();
            String cId=cmbCustomerId.getValue().toString();
            String rType=cmbRepairType.getValue().toString();
            double price=Double.parseDouble(txtEstimatedPrice.getText());
            HashMap map=new HashMap();
            map.put("RepairNumber",rNo);
            map.put("CustomerId",cId);
            map.put("RepairType",rType);
            map.put("EstimatedPrice",price);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map,new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void PrintFRepairBillOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/FinishedRepair.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
           String cId=txtCustomerIdNew.getText();
           String rNo=txtRepairNo.getText();
           String rTYpe= txtRepairTypeNew.getText();
          double productPrice=Double.parseDouble(txtProductPrice.getText());
          int pQty=Integer.parseInt(txtqty.getText());
          double charge=Double.parseDouble(txtCharges.getText());
          double total=Double.parseDouble(txtPayementTotal.getText());

            HashMap map=new HashMap();
            map.put("CustomerId",rNo);
            map.put("RepairNo",cId);
            map.put("RepairType",rTYpe);
            map.put("productPrice",productPrice);
            map.put("pQty",pQty);
            map.put("charges",charge);
            map.put("Total",total);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map,new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void estimatePrice(ActionEvent event) {
        btnPrint.setDisable(false);
        btnAddRepair.setDisable(false);
        btnUpdate.setDisable(false);

    }
}
