package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class InteractiveNormalInterpolation extends Application {
    private Canvas canvas = new Canvas(1024, 768);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage stage) {
        // Елементи для введення координат нормалей
        TextField inputNaX = new TextField("1.0");
        TextField inputNaY = new TextField("0.0");
        TextField inputNkX = new TextField("0.0");
        TextField inputNkY = new TextField("1.0");

        // Підписи під текстовими полями
        Label labelNaX = new Label("Координата X для нормалі A:");
        Label labelNaY = new Label("Координата Y для нормалі A:");
        Label labelNkX = new Label("Координата X для нормалі B:");
        Label labelNkY = new Label("Координата Y для нормалі B:");

        // Елемент для вибору кількості внутрішніх векторів
        Spinner<Integer> vectorCountSpinner = new Spinner<>(10, 200, 20);
        Label vectorCountLabel = new Label("Кількість внутрішніх векторів:");

        // Кнопка для оновлення візуалізації
        Button updateButton = new Button("Намалювати");

        // Панель для розташування елементів
        GridPane controls = new GridPane();
        controls.setHgap(10);
        controls.setVgap(10);
        controls.add(labelNaX, 0, 0);
        controls.add(inputNaX, 0, 1);
        controls.add(labelNaY, 1, 0);
        controls.add(inputNaY, 1, 1);
        controls.add(labelNkX, 2, 0);
        controls.add(inputNkX, 2, 1);
        controls.add(labelNkY, 3, 0);
        controls.add(inputNkY, 3, 1);
        controls.add(vectorCountLabel, 4, 0);
        controls.add(vectorCountSpinner, 4, 1);
        controls.add(updateButton, 5, 1);

        // Панель для малювання та керування
        Pane root = new Pane();
        root.getChildren().addAll(canvas, controls);

        // Обробник для кнопки оновлення
        updateButton.setOnAction(e -> {
            double[] Na = {Double.parseDouble(inputNaX.getText()), Double.parseDouble(inputNaY.getText())};
            double[] Nk = {Double.parseDouble(inputNkX.getText()), Double.parseDouble(inputNkY.getText())};
            int vectorCount = vectorCountSpinner.getValue();
            drawInterpolation(Na, Nk, vectorCount);
        });



        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Interactive Spherical-Angular Interpolation of Normals");
        stage.show();
    }

    private void drawInterpolation(double[] Na, double[] Nk, int vectorCount) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double phi = Math.PI; // Фіксований кут для інтерполяції
        vectorCount++;
        for (int i = 0; i <= vectorCount; i++) {
            double t = (double) i / vectorCount;
            double[] Nt = {
                    Na[0] * Math.cos(t * phi) + Nk[0] * Math.sin(t * phi),
                    Na[1] * Math.cos(t * phi) + Nk[1] * Math.sin(t * phi)
            };
            drawVector(gc, Nt, 400, 300, Color.BLACK);
        }
    }


    private void drawVector(GraphicsContext gc, double[] vector, double startX, double startY, Color color) {
        double scale = 150; // Масштаб для кращої видимості
        gc.setStroke(color);
        gc.strokeLine(startX, startY, startX + vector[0] * scale, startY - vector[1] * scale);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
