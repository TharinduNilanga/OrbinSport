package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBoardController {
    public Pane root;
    public Pane ui;
    public Label lblTime;
    public Label lblDate;
    public ImageView imageLoad;

    public void initialize() throws IOException {
        CurrentDateAndTime();
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Report.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        imageLoad.setVisible(false);
    }
    public void logOutOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene=new Scene(parent);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    public void addCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AddCustomerForm.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();

    }
    public void addOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AddOrderForm.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
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

    public void addRepairOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/RepairForm.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }

    public void addReturnOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ReturnForm.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }
    public void animationCogWheel() {
        imageLoad.setVisible(true);
        Timeline rotate = new Timeline();
        Timeline rotate1 = new Timeline();
        DoubleProperty r = imageLoad.rotateProperty();
        //  DoubleProperty r1 = childim.rotateProperty();

        rotate1.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(r, 360))
        );
//        rotate.getKeyFrames().addAll(
//                new KeyFrame(new Duration(0), new KeyValue(r1, 0)),
//                new KeyFrame(new Duration(1000), new KeyValue(r1, 180))
//        );
        rotate1.play();
        rotate.play();

    }

    public void ReportOnAction(ActionEvent event) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Report.fxml"));
        pane = fxmlLoader.load();
        ui.getChildren().setAll(pane);
        animationCogWheel();
    }
}
