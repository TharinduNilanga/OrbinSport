package controller;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Product;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import util.TableLoadEvent;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AdminDashBoardFormController  extends ReportController {
    public Pane ui;
    public Label lblTime;
    public Label lblDate;
    public Pane root;
    public Button btnProduct;
    public Button btnRepair;
    public Button btnSupplier;
    public Button btnAccount;
    public Button btnSystemReport;
    public ImageView imageLoad;
    public int countNotification;
    public static ArrayList<Label> labelArrayList=new ArrayList<>();
    public Label lblNotificationNew;

    public void initialize() throws IOException {
        CurrentDateAndTime();
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Report.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        imageLoad.setVisible(false);
        getLessQtyNotification();
    }

    public void logOutOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void CurrentDateAndTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblDate.setText(LocalDateTime.now().format(dateFormat));
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void addProductsOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ProductsForm.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }

    public void supplierOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SupplierForm.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }

    public void UserOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UserForm.fxml"));
        Parent parent = loader.load();
        ui.getChildren().setAll(parent);
        animationCogWheel();
    }

    public void SystemReportOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SystemReport.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }


    public void addRepairProductsOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AddRepairProduct.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }

    public void detailsOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AdminLogin.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }

    public void ReportOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Report.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }

    public void animationCogWheel() {
        imageLoad.setVisible(true);
        Timeline rotate = new Timeline();
        Timeline rotate1 = new Timeline();
        DoubleProperty r = imageLoad.rotateProperty();
        rotate1.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r, 360))
        );
        rotate1.play();
        rotate.play();
    }

    public void getLessQtyNotification() {

        ArrayList<Product> products = null;
        try {
            labelArrayList.clear();
            products = new ProductController().getLessNotification();
            for (int i = 0; i < products.size(); i++) {
                String title = "Alert-Listed Product is less than 5";
                String message = "check " + products.get(i). getDescription() + "Product in the stock";
                TrayNotification notification = new TrayNotification();
                AnimationType type = AnimationType.POPUP;

                notification.setAnimationType(type);
                notification.setTitle(title);
                notification.setMessage(message);
                notification.setNotificationType(NotificationType.WARNING);
                notification.showAndDismiss(Duration.millis(8000));
                countNotification++;
                lblNotificationNew.setText(String.valueOf(countNotification));

                Label label=new Label();
                label.setStyle("-fx-text-fill: white");
                label.setStyle("-fx-font-size: 55");
                label.setText(message);
                labelArrayList.add(label);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }


    public void bellNotification(MouseEvent mouseEvent) throws IOException {
        System.out.println("a");
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/QuantityNotification.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }
}
