package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.User;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import view.tm.userTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserFormController  {
    public Button btnAddUser;
    public Button btnAdd;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXTextField txtRoll;
    public TableView<userTable> tblUserTable;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colRoll;
    public TableColumn colUserId;
    public Label lblUserId;
    public Button btnAddUser1;
    public JFXTextField txtUserName1;
    public JFXTextField txtPassword1;
    public Button btnAdd1;
    public Label lblAdminId;
    public TableView tblUserTable2;
    public TableColumn colAdminId;
    public TableColumn colUserName2;
    public TableColumn colPassword2;
    public TextField txtIdDelete;


    ObservableList<userTable> userObservableList= FXCollections.observableArrayList();
    ObservableList<User> adminObservableList= FXCollections.observableArrayList();
    public void initialize() {

        btnAdd.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);

         setUserId();
         setAdminIds();
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

btnAdd1.setDisable(true);
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName2.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword2.setCellValueFactory(new PropertyValueFactory<>("password"));

        /*tblUserTable.getColumns().setAll(colUserName,colPassword,colRoll);
        tblUserTable.getItems().setAll(loadUser());*/
        try {
            getUser(new UserController().getAllUsers());
            getAdmin(new AdminController().getAllAdmin());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public  void setUserId(){
        try {
            lblUserId.setText(new UserController().getUserId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public  void setAdminIds(){
        try {
            lblAdminId.setText(new AdminController().getAdminId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void addUserOnAction(ActionEvent event) {
        btnAdd.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
    }

    public void AddOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        User user=new User(
                lblUserId.getText(),txtUserName.getText(),txtPassword.getText()
        );
        if (new UserController().saveUser(user)) {
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            getUser(new UserController().getAllUsers());
            setUserId();

        } else{
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
        txtUserName.clear();
        txtPassword.clear();

    }
    public void AddAdminOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        User user=new User(lblAdminId.getText(),txtUserName1.getText(),txtPassword1.getText());
        if (AdminController.saveUser(user)){
            getAdmin(new AdminController().getAllAdmin());
            String title = "Successful ";
            String message = "Added";
            TrayNotification notification = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            notification.setAnimationType(type);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.showAndDismiss(Duration.millis(8000));
            setAdminIds();
            getAdmin(new AdminController().getAllAdmin());
    } else{
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
    public void getUser(ArrayList<User> userArrayList){
        userObservableList.clear();
        for (User temp:userArrayList){
            userObservableList.add(new userTable(temp.getUserId(),temp.getUserName(),temp.getPassword()));
        }
        tblUserTable.setItems(userObservableList);
    }
    public void getAdmin(ArrayList<User> userArrayList){
        adminObservableList.clear();
        for (User temp:userArrayList){
            adminObservableList.add(new User(temp.getUserId(),temp.getUserName(),temp.getPassword()));
        }
        tblUserTable2.setItems(adminObservableList);
    }



    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if ((new UserController().deleteUser(txtIdDelete.getText()))||(new AdminController().deleteAdmin(txtIdDelete.getText()))){
            getUser(new UserController().getAllUsers());
            getAdmin(new AdminController().getAllAdmin());
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
    //validation
    public void txtUserOnAction(ActionEvent keyEvent) {
    String userNameRegEx=("^[A-z]*?[A-z]+[1-9]*$");

        Pattern userPattern=Pattern.compile(userNameRegEx);


        String typeUserName=txtUserName.getText();
        if (userPattern.matcher(typeUserName).matches()){
            txtUserName.getParent().setStyle("-fx-border-color: green");
            txtPassword.requestFocus();
        }else{
            txtUserName.getParent().setStyle("-fx-border-color: red");
        }

    }

    public void txtPasswordOnAction(ActionEvent event) {
        String password=("^[0-9]* ?[(0-9)(A-z)]* ?[(0-9)(*)]* ?[a-z]*$");

        Pattern passwordPattern=Pattern.compile(password);
        String typePassword=txtPassword.getText();
        if (passwordPattern.matcher(typePassword).matches()){
            txtPassword.getParent().setStyle("-fx-border-color: green");
        }else{
            txtPassword.getParent().setStyle("-fx-border-color: red");

        }
    }

    public void txtAdminOnAction(ActionEvent event) {
        String adminNameRegEx=("^[A-z]*?[A-z]+[1-9]*$");

        Pattern userPattern=Pattern.compile(adminNameRegEx);


        String typeAdminName=txtUserName1.getText();
        if (userPattern.matcher(typeAdminName).matches()){
            txtUserName1.getParent().setStyle("-fx-border-color: green");
            txtPassword1.requestFocus();
        }else{
            txtUserName1.getParent().setStyle("-fx-border-color: red");
        }
    }

    public void txtAdminPasswordOnAction(ActionEvent event) {
        String password=("^[0-9]* ?[(0-9)(A-z)]* ?[(0-9)(*)]* ?[a-z]*$");

        Pattern passwordPattern=Pattern.compile(password);
        String typePassword=txtPassword1.getText();
        if (passwordPattern.matcher(typePassword).matches()){
            txtPassword1.getParent().setStyle("-fx-border-color: green");btnAdd1.setDisable(false);
        }else{
            txtPassword1.getParent().setStyle("-fx-border-color: red");

        }
    }

    public void txtIdOnAction(ActionEvent event) {
        String id=("^[A-z]-[0-9]{3}$");

        Pattern idPattern=Pattern.compile(id);
        String typeId=txtIdDelete.getText();
        if (idPattern.matcher(typeId).matches()){
            txtIdDelete.setStyle("-fx-border-color: green");
        }else{
            txtIdDelete.setStyle("-fx-border-color: red");
        }
    }



   /* public void Refresh(ArrayList<User> userArrayList){
        userObservableList.clear();
        for (User temp:userArrayList){
            userObservableList.add(new userTable(temp.getUserName(),temp.getPassword(),temp.getRoll()));
        }
        tblUserTable.setItems(userObservableList);

    }*/
   /* public ObservableList<userTable> loadUser(){
        try {
            ArrayList<User> users=UserController.getAllUsers();
            ObservableList<userTable> tableObservableList=FXCollections.observableArrayList();
            for (User user:users){
                tableObservableList.addAll(new userTable(
                        user.getUserName(),
                        user.getPassword(),
                        user.getRoll()
                ));
            }
            return tableObservableList;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
     return null;
    }*/


}