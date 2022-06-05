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

class hashTable {

    public int[] hashArray;
    public int tableSize = 0;

    public void setHashArray(int size) {
        this.tableSize = size;
        hashArray = new int[size];
        for (int i = 0; i < hashArray.length; i++) {
            hashArray[i] = -1;
        }
    }

    public void Add(int key) {
        int hash = divisionHash(key);
        if (isEmpty(hash)) {
            hashArray[hash] = key;
        } else {
            Boolean terminator = false;
            int attempt = 0;
            int newHash = 0;
            while (!terminator && newHash < tableSize) {
                newHash = linearProbing(hash, attempt);
                if (isEmpty(newHash)) {
                    hashArray[newHash] = key;
                    terminator = true;
                } else {
                    attempt++;
                }
            }
        }
    }

    public Boolean delete(int key) {
        int hash = divisionHash(key);
        if (isValid(hash, key)) {
            hashArray[hash] = -2;
            return true;
        } else {
            Boolean terminator = false;
            int attempt = 0;
            int newHash = 0;
            while (!terminator && attempt < tableSize) {
                newHash = linearProbing(hash, attempt);
                if (isValid(newHash, key)) {
                    hashArray[newHash] = -2;
                    terminator = true;
                    return true;
                } else {
                    attempt++;
                }
            }
        }
        return false;
    }

    public int search(int key) {
        int hash = divisionHash(key);
        if (isValid(hash, key)) {
            return hash;
        } else {
            Boolean terminator = false;
            int attempt = 0;
            int newHash = 0;
            while (!terminator && attempt < tableSize) {
                newHash = linearProbing(hash, attempt);
                if (isValid(newHash, key)) {
                    return newHash;
                } else {
                    attempt++;
                }
            }
        }
        return -1;
    }

    public int linearProbing(int hash, int attempt) {
        return (hash + attempt) % tableSize;
    }

    public int divisionHash(int key) {
        return key % tableSize;
    }

    public Boolean isEmpty(int hash) {
        if (hashArray[hash] == -1 || hashArray[hash] == -2) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isValid(int hash, int key) {
        if (hashArray[hash] == key) {
            return true;
        } else {
            return false;
        }
    }

}

class maintainTable {
    public static hashTable hash = new hashTable();
}

public class hashController {

    private maintainTable table = new maintainTable();

    @FXML
    private Pane tablePane;

    @FXML
    private TextField tablesize;

    @FXML
    private TextField value;

    @FXML
    void Add(ActionEvent event) throws IOException {

        if (table.hash.tableSize == 0) {
            int size = Integer.parseInt(tablesize.getText());
            table.hash.setHashArray(size);
            int key = Integer.parseInt(value.getText());
            table.hash.Add(key);
            updateGui(event, -1);
        } else {
            int key = Integer.parseInt(value.getText());
            table.hash.Add(key);
            updateGui(event, -1);
        }

    }

    @FXML
    void Delete(ActionEvent event) throws IOException {
        int key = Integer.parseInt(value.getText());
        if (table.hash.tableSize == 0) {
            String message = "Your table is empty";
            alertBox(message);
        } else if (table.hash.delete(key)) {
            updateGui(event, -1);
        } else {
            String message = "Key not found in hash table";
            alertBox(message);
        }
    }

    @FXML
    void search(ActionEvent event) throws IOException {

        int key = Integer.parseInt(value.getText());

        int searchIndex = table.hash.search(key);

        if (searchIndex != -1) {
            updateGui(event, searchIndex);
        } else {
            String message = "Key not found in hash table";
            alertBox(message);
        }

    }

    @FXML
    private Stage stage;
    private Parent root;
    private Scene scene;
    private Pane hashPane;
    private Text text;
    // private TextField tablesize;

    @FXML
    private void updateGui(ActionEvent event, int searchIndex) throws IOException {

        root = FXMLLoader.load(getClass().getResource("hashTableScene.fxml"));

        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        hashPane = (Pane) scene.lookup("#hashPane");
        int layoutX = 0;

        for (int i = 0; i < table.hash.hashArray.length; i++) {
            HBox hash = new HBox();
            hash.setLayoutX(layoutX);
            hash.setLayoutY(0);
            hash.setPrefWidth(100);
            hash.setPrefHeight(50);
            if (searchIndex != i) {
                hash.setStyle(
                        "-fx-background-color: rgb(249,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
            } else {
                hash.setStyle(
                        "-fx-background-color: rgb(13,141,3);" + "-fx-border-width:1;" + "-fx-border-color: black");
            }
            text = new Text();
            String Data = Integer.toString(table.hash.hashArray[i]);
            text.setText(Data);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setStyle("-fx-font-size: 36;");
            text.setWrappingWidth(100);

            hash.getChildren().add(text);
            hashPane.getChildren().addAll(hash);

            layoutX += 101;
        }

        TextField tempField = (TextField) scene.lookup("#tablesize");
        tempField.setText("" + table.hash.tableSize);
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