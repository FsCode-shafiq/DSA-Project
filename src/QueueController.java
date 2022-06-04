import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class Queue {
    public int size = 0;
    public int[] QueueArray;
    public int rear;
    public int front;

    Queue() {
        front = -1;
        rear = -1;
    }

    public void Enqueue(int element) {
        if (front == -1) {
            front++;
        }
        rear++;
        QueueArray[rear] = element;
    }

    public void setQueue(int size) {
        this.size = size;
        this.QueueArray = new int[size];
    }

    public void Dequeue() {
        if (this.front >= this.rear) {
            this.front = -1;
            this.rear = -1;
        } else {
            front++;
        }
    }

    public Boolean isFull() {

        if (this.rear == this.size - 1) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean isEmpty() {
        if (front == -1 && rear == -1) {
            return true;
        } else {
            return false;
        }
    }

    public void resetQueue() {
        this.QueueArray = null;
        size = 0;
        front =-1;
        rear = -1;
    }

}

class QueueData {
    public static Queue queue = new Queue();
}

public class QueueController {

    private QueueData queueContainer = new QueueData();

    @FXML
    private TextField queueSize;

    @FXML
    private TextField value;

    @FXML
    void enQueue(ActionEvent event) throws IOException {

        if (queueContainer.queue.size == 0) {
            int size = Integer.parseInt(queueSize.getText());
            queueContainer.queue.setQueue(size);
        }

        if (queueContainer.queue.isFull()) {

            String message = "Your queue is full ! You are not able to add more elements";
            alertBox(message);

        } else {
            int element = Integer.parseInt(value.getText());
            queueContainer.queue.Enqueue(element);
            updateGui(event);
        }

    }

    @FXML
    void DeQueue(ActionEvent event) throws IOException {

        queueContainer.queue.Dequeue();
        if (queueContainer.queue.isEmpty()) {
            String message = "Your queue is empty";
            reset(event);
            alertBox(message);
        }
        updateGui(event);

    }

    @FXML
    void reset(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("queueScene.fxml"));

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        queueContainer.queue.resetQueue();
        stage.show();
    }

    @FXML
    private Pane QueuePane;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    void updateGui(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("queueScene.fxml"));

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        QueuePane = (Pane) scene.lookup("#QueuePane");

        int layoutY = 0;

        for (int i = queueContainer.queue.front; i <= queueContainer.queue.rear; i++) {
            HBox queue = new HBox();
            queue.setLayoutX(0);
            queue.setLayoutY(layoutY);
            queue.setPrefWidth(140);
            queue.setPrefHeight(50);
            queue.setStyle("-fx-background-color: rgb(249,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
            Text text = new Text();
            String Data = Integer.toString(queueContainer.queue.QueueArray[i]);
            text.setText(Data);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setStyle("-fx-font-size: 36;");
            text.setWrappingWidth(140);

            queue.getChildren().add(text);
            QueuePane.getChildren().addAll(queue);

            layoutY += 70;
        }
        TextField tempField = (TextField) scene.lookup("#queueSize");
        tempField.setText("" + queueContainer.queue.size);
        tempField.setDisable(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void alertBox(String message) {

        Alert alertBox = new Alert(AlertType.ERROR);
        alertBox.setContentText(message);
        alertBox.show();

    }
}
