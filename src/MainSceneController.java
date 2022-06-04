
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneController {

    @FXML
    private Scene scene;
    private Stage stage;

    @FXML
    void selectQueue(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("queueScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void selectStack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("stackScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void selectHashTable(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hashTableScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void selectLinkedList(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("linkedListScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void hideAll() {

    }

}
