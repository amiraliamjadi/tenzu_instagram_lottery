package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

   public static List<Image> imageStartAnimation = new ArrayList<>();

   public static List<Image> imagePannelAnimation = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

       /* primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);*/


        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));


        for (int i = 0 ; i < 101 ; i++)
        {
            if (i > 0)
            {
                imageStartAnimation.add(new Image("start_animation/" + i + ".png"));
            }

            if (i < 62)
            {
                imagePannelAnimation.add(new Image("pannel_animation/" + i + ".png"));
            }

        }



        Scene scene = new Scene(root, 10240*648/4320 ,648);
        primaryStage.setScene(scene);
        primaryStage.show();



    }



    public static void main(String[] args)
    {

       /*  ye method baraye class Application ke vaghti ba parameter args seda zade shavad
         ( badihatan aval e barname avalin chizi ke ejra mishavad hamin ast )
         be class Application miravad va barname haye JAVA FX ke zadim ra ejra mikonad*/
        launch(args);



    }
}
