package sed.node.control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {       
        String javaString2 =
                """
                /*Hello World.java*/
                public class HelloWorld
                {
                    public static void main(String[] args)
                    {
                        System.out.println("Hello World");
                    }
                }
                """;

        CodeView codeView = new CodeView(javaString2, CodeView.CodeLanguage.JAVA);
        VBox.setVgrow(codeView, Priority.ALWAYS);

        VBox root = new VBox(new StackPane(new Label("CodeView Test")), codeView);
        root.setStyle("-fx-background-colo: green");
        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args)
    {
        launch();
    }
}