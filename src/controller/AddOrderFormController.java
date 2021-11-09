package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Order;
import model.OrderDetails;
import model.Product;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.CartTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AddOrderFormController {
    public Label lblOrderId;
    public JFXComboBox cmbCustomerId;
    public JFXTextField txtUserId;
    public JFXTextField txtName;
    public JFXTextField txtNIC;
    public JFXTextField txtPhoneNo;
    public JFXComboBox cmbProductId;
    public JFXTextField txtDescription;
    public JFXTextField txtBrand;
    public JFXTextField txtQTY;
    public JFXTextField txtUnitPrice;
    public JFXComboBox cmbOrderType;
    public JFXTextField txtWarranty;
    public JFXTextField txtDiscount;
    public JFXTextField txtOrderQTY;

    public Label lblTotal;
    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colProductId;
    public TableColumn colWarranty;
    public TableColumn colOrderType;
    public TableColumn colQTY;
    public TableColumn colDiscount;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TextField txtAmount;
    public Label lblBalance;
    public TextField txtTotalNew;
    public Button btnAddToCart;
    public Button btnClear;
    public Button btnPlaceOrder;
    public Button btnOk;
    public Button btnBill;

    public void initialize() {
        setOrderId();
        btnAddToCart.setDisable(true);
        btnBill.setDisable(true);
        btnClear.setDisable(true);
        btnOk.setDisable(true);
        btnPlaceOrder.setDisable(true);
        try {
            getCustomerId((ArrayList<String>) new CustomerController().getCustomerIds());
            getProductId((ArrayList<String>) new ProductController().getProductIds());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        cmbOrderType.getItems().addAll("Wholesale","Retail");

       colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
       colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colWarranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));
        colOrderType.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }
    public void setOrderId(){
        try {
            lblOrderId.setText(new OrderController().getOrderId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void getCustomerId(ArrayList<String> cId){
        cmbCustomerId.getItems().addAll(cId);
    }
    public void getProductId(ArrayList<String> pId){
        cmbProductId.getItems().addAll(pId);
    }

    public void cmbCustomerIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String cId=cmbCustomerId.getValue().toString();
        Customer c=new CustomerController().getCustomer(cId);
        if (c==null){new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
    } else {
        setData(c);
        btnAddToCart.setDisable(false);
    }
    }
    void setData(Customer customer){

        txtUserId.setText(customer.getUserId());
        txtName.setText(customer.getName());
        txtNIC.setText(customer.getNic());
        txtPhoneNo.setText(customer.getPhoneNo());
    }

    public void cmbProductIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String pId=cmbProductId.getValue().toString();
        Product p=new ProductController().getProduct(pId);
        if (p==null){new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(p);
        }

    }
    void setData(Product p){

        txtDescription.setText(p.getDescription());
        txtBrand.setText(p.getBrand());
        txtQTY.setText(String.valueOf(p.getQty()));
        txtUnitPrice.setText(String.valueOf(p.getUnitPrice()));
    }
    ObservableList<CartTm> obList= FXCollections.observableArrayList();
    public void AddToCartOnAction(ActionEvent event) {
        String warranty = txtWarranty.getText();
        int QTy = Integer.parseInt(txtQTY.getText());
        int customerQty = Integer.parseInt(txtOrderQTY.getText());
        int discount = Integer.parseInt(txtDiscount.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double initialPrice = (customerQty) * (unitPrice);
        double discPrice = (initialPrice / 100) * (discount);
        double total = (initialPrice) - (discPrice);

        if (QTy < customerQty) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }
        CartTm tm = new CartTm(
                lblOrderId.getText(),
                (String) cmbCustomerId.getValue(),
                (String) cmbProductId.getValue(),
                warranty,
                (String) cmbOrderType.getValue(),
                customerQty,
                discount,
                unitPrice,
                total
        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {// new Add
            obList.add(tm);

        }else{
            CartTm temp=obList.get(rowNumber);
            CartTm newTm=new CartTm(
                    temp.getOrderId(),
                    temp.getCustomerId(),
                    temp.getProductId(),
                    temp.getWarranty(),
                    temp.getOrderType(),
                    temp.getQty()+customerQty,
                    temp.getDiscount(),
                    temp.getUnitPrice(),
                    temp.getTotal()+total
            );

            obList.remove(rowNumber);
            obList.add(newTm);


        }
        tblOrder.setItems(obList);
        System.out.println(obList);
        calculateCost();
        btnClear.setDisable(false);
        btnPlaceOrder.setDisable(false);
        btnOk.setDisable(false);
        btnBill.setDisable(false);


    }
    private int isExists(CartTm tm){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getProductId().equals(obList.get(i).getProductId())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl=0;
        for (CartTm tm:obList
        ) {
            ttl+=tm.getTotal();
        }
        lblTotal.setText(ttl+" /=");
        txtTotalNew.setText(String.valueOf(ttl));

    }
    int cartSelectedRowForRemove = -1;


    public void placeOrderOnAction(ActionEvent event) {

        ArrayList<OrderDetails> orderDetails= new ArrayList<>();
        double total=0;
        dateNow=dateNow();
        for (CartTm tempTm:obList
        ) {
            total+=tempTm.getTotal();
            orderDetails.add(new OrderDetails(tempTm.getOrderId(),tempTm.getProductId(),tempTm.getOrderType(),tempTm.getQty(),
                    tempTm.getDiscount(),tempTm.getTotal()));
        }

        Order order= new Order(
                lblOrderId.getText(),
                (String) cmbCustomerId.getValue(),
                (String) cmbProductId.getValue(),
                txtWarranty.getText(),
                (String) cmbOrderType.getValue(),
                Integer.parseInt(txtOrderQTY.getText()),
                Integer.parseInt(txtDiscount.getText()),
                total,
                dateNow,
                orderDetails
        );

        if (new OrderController().placeOrder(order)){
            obList.clear();
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));

            setOrderId();
            tblOrder.refresh();
            txtDiscount.clear();
            txtOrderQTY.clear();
            txtWarranty.clear();
            cmbOrderType.getSelectionModel().clearSelection();
            txtNIC.clear();
            txtName.clear();
            txtQTY.clear();
            txtUnitPrice.clear();
            txtBrand.clear();
            txtDescription.clear();
            txtPhoneNo.clear();
            txtUserId.clear();
            cmbProductId.getSelectionModel().clearSelection();
            cmbCustomerId.getSelectionModel().clearSelection();

        }else{
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
    public String dateNow;
    public String dateNow() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateNow=simpleDateFormat.format(date);
        String da=dateNow;
        return da;
    }

    public void OkOnAction(ActionEvent event) {
        double total=Double.parseDouble(txtTotalNew.getText());
        double payment=Double.parseDouble(txtAmount.getText());
        double balance=(payment)-(total);

        lblBalance.setText(balance+"/=");

    }

    public void clearOnAction(ActionEvent event) {

        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblOrder.refresh();
        }
    }

    public void txtWarranty(ActionEvent event) {
        String textRegEx=("^[1-9]{1}[A-z]*|[1-9]{1}[A-z]*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtWarranty.getText();
        if (pattern.matcher(brand).matches()){
            txtWarranty.setStyle("-fx-border-color: green");
        }else{
            txtWarranty.setStyle("-fx-border-color: red");
        }
    }

    public void txtdisOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtDiscount.getText();
        if (pattern.matcher(brand).matches()){
            txtDiscount.setStyle("-fx-border-color: green");
            btnAddToCart.setDisable(false);
        }else{
            txtDiscount.setStyle("-fx-border-color: red");
        }
    }

    public void txtQTYOnAction(ActionEvent event) {
        String textRegEx=("^[0-9]{1,5}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtOrderQTY.getText();
        if (pattern.matcher(brand).matches()){
            txtOrderQTY.setStyle("-fx-border-color: green");

            txtDiscount.requestFocus();
        }else{
            txtOrderQTY.setStyle("-fx-border-color: red");
        }
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

    public void PrintBillOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/Bill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            for (int i = 0; i < obList.size(); i++) {
                System.out.println(obList.get(i).getOrderId());
            }

            String customerName=txtName.getText();
            String NIc=txtNIC.getText();
            double Total=Double.parseDouble(txtTotalNew.getText());

            HashMap map=new HashMap();
            map.put("Customername",customerName);
            map.put("Nic",NIc);
            map.put("Total",Total);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(obList.toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }
}
