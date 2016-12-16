package view;/**
 * Created by Gerard van den Bosch on 12/16/2016.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DominoGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Domino's Coupon Grabber by Team Hanze");
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 400,400));
        primaryStage.show();
    }
}
