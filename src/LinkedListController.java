import java.io.IOException;
import java.util.Arrays;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class Node {
    int data;
    Node prev;
    Node Next;

    Node(int key) {
        data = key;
    }
}

class DoublyLinkedList {
    Node Head;
    Node Tail;
    int size = 0;
    Node[] NodeArray;

    public void insert(int key) {
        if (Head == null) {

            Node newNode = new Node(key);
            Head = newNode;
            size++;
            UpdateList();

        } else {
            Node target = Head;

            while (target.Next != null) {
                target = target.Next;
            }

            Node newNode = new Node(key);
            target.Next = newNode;
            newNode.prev = target;
            Tail = newNode;
            size++;
            UpdateList();
        }
    }

    public void insertAt(int key, int position) {

        if (Head == null) {
            insert(key);
        } else {
            Node target = Head;
            Node newNode = new Node(key);
            int counter = 1;
            while (counter < position) {
                target = target.Next;
                counter++;
            }
            newNode.Next = target;
            newNode.prev = target.prev;
            target.prev.Next = newNode;
            target.prev = newNode;
            size++;
            UpdateList();

        }
    }

    public void insertFirst(int key) {
        Node target = Head;
        Node newNode = new Node(key);

        newNode.Next = target;
        target.prev = newNode;
        Head = newNode;
        size++;
        UpdateList();
    }

    public void removeAt(int position) {

        Node target = Head;
        int counter = 1;

        while (counter < position) {
            target = target.Next;
            counter++;
        }

        target.prev.Next = target.Next;
        target.Next.prev = target.prev;
        size--;
        UpdateList();

    }

    public int removeByValue(int key) {
        Node target = Head;
        int counter = 1;
        Boolean terminate = false;

        while (counter <= size && terminate == false) {

            if (counter == size && target.data == key) {
                removeLast();
                terminate = true;
                return counter;
            }
            if (target.data == key) {

                if (counter == 1) {
                    removeFirst();
                    terminate = true;
                    return counter;
                }

                target.prev.Next = target.Next;
                size--;
                terminate = true;
                UpdateList();
                return counter;
            }
            target = target.Next;
            counter++;
        }
        return -1;
    }

    public void removeLast() {
        Tail.prev.Next = null;
        Tail = Tail.prev;
        size--;
        UpdateList();
    }

    public void removeFirst() {
        Node target = Head;
        if (target.Next == null) {
            Head = null;
            size--;
            UpdateList();
        } else {
            Head = target.Next;
            Head.prev = null;
            size--;
            UpdateList();
        }
    }

    public int findByValue(int key) {

        Node target = Head;
        int position = 1;
        while (target != null) {
            if (target.data == key) {
                return position;
            }
            target = target.Next;
            position++;
        }
        return -1;

    }

    public int findByPos(int position) {
        if (position <= size) {
            return position;
        } else {
            return -1;
        }

    }

    public void clearList() {
        Head = null;
        size = 0;
        UpdateList();
    }

    void UpdateList() {
        if (size == 0) {
            NodeArray = null;
        } else if (Head == null) {
            NodeArray = null;
        } else {
            Node[] tempList = new Node[size];
            Node start = Head;
            int index = 0;

            while (start != null) {
                tempList[index] = start;
                start = start.Next;
                index++;
            }
            NodeArray = tempList;
            System.out.println(Arrays.toString(NodeArray));
        }
    }

}

class main {
    public static DoublyLinkedList List = new DoublyLinkedList();
}

public class LinkedListController {

    private main Doubly = new main();

    @FXML
    private TextField insertnode; // target text field for insert Node Function

    @FXML
    void insertnode(ActionEvent event) throws IOException {

        int key = Integer.parseInt(insertnode.getText());
        Doubly.List.insert(key);
        updateGui(Doubly.List.NodeArray, event, -1);

    }

    @FXML
    private TextField insertAtPostion;

    @FXML
    private TextField insertAtValue;

    @FXML
    void insertAt(ActionEvent event) throws IOException {

        int key = Integer.parseInt(insertAtValue.getText());
        int position = Integer.parseInt(insertAtPostion.getText());

        if (position > Doubly.List.size || position == 0) {
            String message = "Invalid input you need to choose position between 1 to " + Doubly.List.size;
            alertBox(message);
        } else {
            Doubly.List.insertAt(key, position);
        }

        updateGui(Doubly.List.NodeArray, event, -1);

    }

    @FXML
    private TextField insertFirst;

    @FXML
    void insertAtFirst(ActionEvent event) throws IOException {
        int key = Integer.parseInt(insertFirst.getText());
        if (Doubly.List.Head == null) {
            Doubly.List.insert(key);
            // alertBox("Your List is empty you need to insert atleast one element to
            // proceed");
        } else {
            Doubly.List.insertFirst(key);
        }
        updateGui(Doubly.List.NodeArray, event, -1);
    }

    @FXML
    private TextField removeAt;

    @FXML
    void removeAt(ActionEvent event) throws IOException {
        int position = Integer.parseInt(removeAt.getText());

        if (position > Doubly.List.size) {
            String message = "Invalid Input: please choose value between 1 to " + Doubly.List.size;
            alertBox(message);
        } else if (position <= 1) {
            Doubly.List.removeFirst();
            updateGui(Doubly.List.NodeArray, event, -1);
        } else if (position == Doubly.List.size) {
            Doubly.List.removeLast();
            updateGui(Doubly.List.NodeArray, event, -1);
        } else {
            Doubly.List.removeAt(position);
            updateGui(Doubly.List.NodeArray, event, -1);
        }

    }

    @FXML
    private TextField removeByValue;

    @FXML
    void removeByValue(ActionEvent event) throws IOException {

        int key = Integer.parseInt(removeByValue.getText());

        if (Doubly.List.size == 0) {
            String message = "Your list is Already empty";
            alertBox(message);
        } else {
            int isRemove = Doubly.List.removeByValue(key);
            if (isRemove == -1) {
                String message = key + " not found in List";
                alertBox(message);
            } else {
                updateGui(Doubly.List.NodeArray, event, -1);
            }
        }
    }

    @FXML
    void removeFirst(ActionEvent event) throws IOException {

        Doubly.List.removeFirst();
        updateGui(Doubly.List.NodeArray, event, -1);

    }

    @FXML
    void removeLast(ActionEvent event) throws IOException {

        Doubly.List.removeLast();
        updateGui(Doubly.List.NodeArray, event, -1);

    }

    @FXML
    private TextField findByValue;

    @FXML
    void findByValue(ActionEvent event) throws IOException {
        int key = Integer.parseInt(findByValue.getText());
        int targetPosition = Doubly.List.findByValue(key);

        if (targetPosition == -1) {
            String message = key + " Not fount in list";
            alertBox(message);
        } else {
            updateGui(Doubly.List.NodeArray, event, targetPosition);
        }
    }

    @FXML
    private TextField findPosition;

    @FXML
    void findByPosition(ActionEvent event) throws IOException {
        int position = Integer.parseInt(findPosition.getText());

        if (position == -1) {
            String message = "Invalid input please choose position between 1 to " + Doubly.List.size;
            alertBox(message);
        } else {
            updateGui(Doubly.List.NodeArray, event, position);
        }

    }

    @FXML
    void clearList(ActionEvent event) throws IOException {
        Doubly.List.clearList();
        updateGui(Doubly.List.NodeArray, event, -1);
    }

    @FXML
    private Pane listcontainer;
    private Scene scene;
    private Stage stage;
    private Parent root;
    private Text text;

    @FXML

    void updateGui(Node[] NodeArray, ActionEvent event, int targetPosition) throws IOException {

        root = FXMLLoader.load(getClass().getResource("linkedListScene.fxml"));

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        listcontainer = (Pane) scene.lookup("#listcontainer");

        int LineStartX = 238;
        int LineEndX = 206;
        int hboxLayoutX = 100;
        int index = 1;
        if (NodeArray != null) {

            for (Node node : NodeArray) {

                HBox hbox = new HBox();

                hbox.setLayoutX(hboxLayoutX);
                hbox.setLayoutY(76);
                hbox.setPrefWidth(106);
                hbox.setPrefHeight(47);

                if (index == targetPosition) {
                    hbox.setStyle(
                            "-fx-background-color: rgb(13,141,3);" + "-fx-border-width:2;" + "-fx-border-color: black");
                } else {
                    hbox.setStyle("-fx-background-color: rgb(249,141,3);" + "-fx-border-width:2;"
                            + "-fx-border-color: black");
                }

                HBox pre = new HBox();

                pre.setPrefWidth(50);
                pre.setPrefHeight(47);
                if (index == targetPosition) {
                    pre.setStyle(
                            "-fx-background-color: rgb(13,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
                } else {
                    pre.setStyle("-fx-background-color: rgb(249,141,3);" + "-fx-border-width:1;"
                            + "-fx-border-color: black");
                }

                hbox.getChildren().add(pre);

                text = new Text();
                String Data = Integer.toString(node.data);
                text.setText(Data);
                text.setFill(Color.WHITE);
                text.setTextAlignment(TextAlignment.CENTER);
                text.setStyle("-fx-font-size: 36;");
                text.setWrappingWidth(60);

                hbox.getChildren().add(text);

                HBox pre2 = new HBox();

                pre2.setPrefWidth(50);
                pre2.setPrefHeight(47);
                if (index == targetPosition) {
                    pre2.setStyle(
                            "-fx-background-color: rgb(13,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
                } else {
                    pre2.setStyle("-fx-background-color: rgb(249,141,3);" + "-fx-border-width:1;"
                            + "-fx-border-color: black");
                }
                hbox.getChildren().add(pre2);

                listcontainer.getChildren().add(hbox);

                Line line = new Line(LineStartX, 100, LineEndX, 100);

                listcontainer.getChildren().add(line);

                if (node.Next == null) {

                    Line vLine = new Line(LineStartX, 100, LineStartX, 132);
                    Line hLine1 = new Line(LineStartX + 20, 132, LineEndX + 10, 132);
                    Line hLine2 = new Line(LineStartX + 15, 142, LineEndX + 15, 142);

                    listcontainer.getChildren().add(vLine);
                    listcontainer.getChildren().add(hLine1);
                    listcontainer.getChildren().add(hLine2);

                }
                LineStartX += 138;
                LineEndX += 138;
                hboxLayoutX += 138;
                index++;
            }
        }

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
    void Back(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
