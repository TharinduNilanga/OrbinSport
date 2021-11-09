package controller;

import com.jfoenix.controls.JFXButton;
import com.sun.javaws.jnl.JavaFXAppDesc;
import com.sun.org.apache.bcel.internal.generic.LADD;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;

import static controller.AdminDashBoardFormController.labelArrayList;

public class QuantityNotificationController {
    public Pane notificationUI;
    public ArrayList<Label> labelNewArrayList = new ArrayList<>();
    public VBox vBox;
    public AnchorPane anchorPane;
    public VBox Nvbox;


    public void initialize(){
        setNotiData();
        setNotification();
      /*  for (int i = 0; i <labelNewArrayList.size() ; i++) {
            System.out.println(labelNewArrayList.get(i));
        }
*/
    }
    public void setNotiData() {
        for (javafx.scene.control.Label lbl : labelArrayList) {
            labelNewArrayList.add(lbl);
           /* System.out.println(lbl);*/
        }
    }


    public void setNotification() {
        for (int i = 0; i < labelNewArrayList.size(); i++) {

            if (i % 2 == 0) {
                labelNewArrayList.get(i).setStyle("-fx-background-color: linear-gradient(dimgrey,darkgray);-fx-font-size: 23;-fx-text-fill: white;-fx-translate-x:50;-fx-padding: 20;");
               // System.out.println(labelNewArrayList.get(i));
                setDataInNotificationsForm(labelNewArrayList, i);
            } else {
                labelNewArrayList.get(i).setStyle("-fx-background-color: linear-gradient(slategrey,dimgrey);-fx-font-size: 23;-fx-text-fill: white;-fx-translate-x: 50;-fx-padding: 20;");
                   setDataInNotificationsForm(labelNewArrayList, i);
            }
        }
    }

    private void setDataInNotificationsForm(ArrayList<Label> arrayList, int i) {
       // System.out.println(arrayList.get(i));
        arrayList.get(i).setPrefWidth(1250);
        arrayList.get(i).setPrefHeight(60);
        JFXButton button=new JFXButton("clear");
        button.setStyle("-fx-translate-x: 1250;-fx-pref-height: 74;-fx-pref-width: 78.0;-fx-background-color:darkslategrey;-fx-text-fill: white;-fx-font-size: 18;");
        AnchorPane anchorPane1=new AnchorPane(arrayList.get(i),button);
        AnchorPane anchorPane2=new AnchorPane(new javafx.scene.control.Label(" "));
        Nvbox.getChildren().addAll(anchorPane1);
        Nvbox.getChildren().addAll(anchorPane2);
        clearNotification(arrayList.get(i), button/*, labelNewArrayList*/);
    }

    private void clearNotification(Label label, JFXButton button/*, ArrayList<Label> ArrayList*/) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               // if (labelArrayList)
                for (int i = 0; i <labelNewArrayList.size() ; i++) {
                    if (label.equals(labelNewArrayList.get(i))){
                        labelNewArrayList.remove(i);
                        Nvbox.getChildren().clear();
                        setNotification();
                        return;
                    }

                }
            }
        });
    }

}
