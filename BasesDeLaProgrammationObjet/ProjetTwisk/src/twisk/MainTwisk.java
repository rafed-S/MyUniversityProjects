package twisk;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.vues.VueMenu;
import twisk.vues.VueMondeIG;
import twisk.vues.VueOutils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class MainTwisk extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MondeIG monde = new MondeIG();
        BorderPane root = new BorderPane();
        root.setTop(new VueMenu(monde));
        root.setBottom(new VueOutils(monde));
        root.setCenter(new VueMondeIG(monde));
        TailleComposants tt = new TailleComposants();
        primaryStage.setScene(new Scene(root, tt.getSizeMondeHeight(), tt.getSizeMondeWidth()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
