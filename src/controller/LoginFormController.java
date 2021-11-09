package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane root;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public ComboBox cmbRoll;


    public void initialize(){

    }
    public void loginOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();


        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM `User` WHERE userName=? AND password=md5(?) ");
        preparedStatement.setObject(1, userName);
        preparedStatement.setObject(2, password);
        ResultSet set = preparedStatement.executeQuery();

        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Admin WHERE userName=? AND password=md5(?) ");
        stm.setObject(1, userName);
        stm.setObject(2, password);

        ResultSet rst = stm.executeQuery();


        if (set.next()) {
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/DashBoard.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cashier ");
            primaryStage.centerOnScreen();
        } else if (rst.next()) {
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/AdminDashBoardForm.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);

            primaryStage.setTitle("Admin");
            primaryStage.centerOnScreen();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid Password/userName");
            Optional<ButtonType> buttonType = alert.showAndWait();
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();
        }

    }
}
