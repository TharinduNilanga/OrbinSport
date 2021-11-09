package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Product;
import model.Repair;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateDeleteRepair{
    public JFXTextField txtRepairId;
    public JFXTextField txtCustomerId;
    public JFXTextField txtRepairType;
    public JFXTextField txtEstimatedPrice;


    public void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (new RepairController().deleteRepair(txtRepairId.getText())){
            String title = "Successful ";
            String message = "Delete";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.NOTICE);
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

    public void repairNoOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String repairNo=txtRepairId.getText();

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
     txtRepairId.setText(r.getRepairNo());
     txtCustomerId.setText(r.getCustomerId());
     txtRepairType.setText(r.getRepairType());
     txtEstimatedPrice.setText(String.valueOf(r.getEstimatedPrice()));
    }

    public void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Repair repair=new Repair(
                txtRepairId.getText(),
                txtCustomerId.getText(),
                txtRepairType.getText(),
                Double.parseDouble(txtEstimatedPrice.getText())
        );
        if (new RepairController().updateRepair(repair)){
            String title = "Successful ";
            String message = "Updated";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
           ;
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

    public void repairNoKeyReleasedOnAction(KeyEvent keyEvent) {
        String textRegEx=("^[A-z]{1}-[0-9]{1,3}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtRepairId.getText();
        if (pattern.matcher(brand).matches()){
            txtRepairId.getParent().setStyle("-fx-border-color: green");
        }else{
            txtRepairId.getParent().setStyle("-fx-border-color: red");
        }
    }

    public void txtcustId(ActionEvent event) {
        String textRegEx=("^[A-z]{1}-[0-9]{1,3}$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtCustomerId.getText();
        if (pattern.matcher(brand).matches()){
            txtCustomerId.getParent().setStyle("-fx-border-color: green");
        }else{
            txtCustomerId.getParent().setStyle("-fx-border-color: red");
        }

    }

    public void txtRepairType(ActionEvent event) {
        String textRegEx=("^[A-z]* [A-z]*");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtRepairType.getText();
        if (pattern.matcher(brand).matches()){
            txtRepairType.getParent().setStyle("-fx-border-color: green");
        }else{
            txtRepairType.getParent().setStyle("-fx-border-color: red");
        }
    }

    public void txtPrice(ActionEvent event) {
        String textRegEx=("^[0-9]*?[0-9]*(.00)*$");
        Pattern pattern=Pattern.compile(textRegEx);
        String brand=txtEstimatedPrice.getText();
        if (pattern.matcher(brand).matches()){
            txtEstimatedPrice.getParent().setStyle("-fx-border-color: green");
        }else{
            txtEstimatedPrice.getParent().setStyle("-fx-border-color: red");
        }
    }
}
