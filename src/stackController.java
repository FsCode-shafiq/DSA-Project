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

// Stack implementation in Java

class Stack {

    // store elements of stack
    public int Stackarr[] = null;
    // represent top of stack
    public int top = -1;
    // total capacity of the stack
    public int capacity = -2;

    // push elements to the top of stack
    public void push(int key) {
        Stackarr[++top] = key;
    }

    public void setStackSize(int size) {

        Stackarr = new int[size];
        capacity = size;
        top = -1;
    }

    // pop elements from top of stack
    public int pop() {
        // pop element from top of stack
        return Stackarr[top--];
    }

    // return size of the stack
    public int getSize() {
        return top + 1;
    }

    // check if the stack is empty
    public Boolean isEmpty() {
        return top == -1;
    }

    // check if the stack is full
    public Boolean isFull() {
        return top == capacity - 1;
    }
}

class holdData {

    public static Stack stack = new Stack();

}

public class stackController {

    private holdData stackContainer = new holdData();

    @FXML
    private TextField value;

    @FXML
    private TextField stackSize;

    @FXML
    void push(ActionEvent event) throws IOException {

        int size = Integer.parseInt(stackSize.getText());
        int key = Integer.parseInt(value.getText());
        if (stackContainer.stack.isFull()) {
            String message = "Your stack is full!";
            alertBox(message);
        } else if (stackContainer.stack.Stackarr == null) {
            stackContainer.stack.setStackSize(size);
            stackContainer.stack.push(key);
            updateGui(event);

        } else {
            stackContainer.stack.push(key);
            updateGui(event);

        }
    }

    @FXML
    void peek(ActionEvent event) throws IOException {
        if(stackContainer.stack.isEmpty()){
            String message = "your stack is empty you need to insert element first";
            alertBox(message);
        }else{
            showPeek(event);
        }
    }

    @FXML
    void pop(ActionEvent event) throws IOException {

        if (stackContainer.stack.isEmpty()) {
            String message = "your stack is empty";
            alertBox(message);
        } else {
            stackContainer.stack.pop();
            updateGui(event);
        }

    }

    @FXML
    private Pane stackPane;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private Text text;

    @FXML
    private void updateGui(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("stackScene.fxml"));

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stackPane = (Pane) scene.lookup("#stackPane");
        int layoutY = 0;

        for (int i = 0; i <= stackContainer.stack.top; i++) {

            HBox stack = new HBox();
            stack.setLayoutX(0);
            stack.setLayoutY(layoutY);
            stack.setPrefWidth(140);
            stack.setPrefHeight(50);
            stack.setStyle("-fx-background-color: rgb(249,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
            text = new Text();
            String Data = Integer.toString(stackContainer.stack.Stackarr[i]);
            text.setText(Data);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setStyle("-fx-font-size: 36;");
            text.setWrappingWidth(140);

            stack.getChildren().add(text);
            stackPane.getChildren().addAll(stack);

            layoutY += 70;
        }
        TextField tempField = (TextField) scene.lookup("#stackSize");
        tempField.setText("" + stackContainer.stack.capacity);
        tempField.setDisable(true);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void showPeek(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("stackScene.fxml"));

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stackPane = (Pane) scene.lookup("#stackPane");
        int layoutY = 0;

        for (int i = 0; i <= stackContainer.stack.top; i++) {

            HBox stack = new HBox();
            stack.setLayoutX(0);
            stack.setLayoutY(layoutY);
            stack.setPrefWidth(140);
            stack.setPrefHeight(50);
            
            if(i == stackContainer.stack.top){
                stack.setStyle("-fx-background-color: rgb(13,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
            }else{
                stack.setStyle("-fx-background-color: rgb(249,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
            }
            
            text = new Text();
            String Data = Integer.toString(stackContainer.stack.Stackarr[i]);
            text.setText(Data);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setStyle("-fx-font-size: 36;");
            text.setWrappingWidth(140);

            stack.getChildren().add(text);
            stackPane.getChildren().addAll(stack);

            layoutY += 70;
        }
        TextField tempField = (TextField) scene.lookup("#stackSize");
        tempField.setText("" + stackContainer.stack.capacity);
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

    @FXML
    void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
