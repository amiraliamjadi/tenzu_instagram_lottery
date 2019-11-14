package gui;

import db.CatchData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;


public class MenuController
{
    private int i = 0;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView exitButtonImageView;

    @FXML
    private ImageView settingButtonImageView;

    @FXML
    private ImageView startButtonImageView;

    @FXML
    private ImageView catchDataButtonImageView;

    @FXML
    private StackPane contentViewForChange;

    @FXML
    private ImageView startAnimationView;



    @FXML
    public void initialize()
    {
        menuObjectsScaleConfig();

    }


    @FXML
    protected void handleStartButtonAction(ActionEvent actionEvent) throws IOException
    {

        exitButtonImageView.setVisible(false);
        settingButtonImageView.setVisible(false);
        catchDataButtonImageView.setVisible(false);
        startAnimationView.setVisible(true);
        startButtonImageView.setVisible(false);


        // animation
        Timeline timeline = new Timeline();
        timeline.setCycleCount(100);
        EventHandler imageEvent = new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {

                startAnimationView.setImage(Main.imageStartAnimation.get(i));
                    i++;

                    if (i == 100)
                    {
                        try
                        {
                            // change fxml file
                            StackPane stackPane = FXMLLoader.load(getClass().getResource("start_lottery.fxml"));
                            contentViewForChange.getChildren().setAll(stackPane);

                        }
                        catch (Exception e)
                        {
                            System.err.println(e.getMessage());
                        }

                        startAnimationView.setVisible(false);

                }

            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.millis(40),imageEvent);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }


    @FXML
    protected void handleSettingButtonAction(ActionEvent actionEvent) throws IOException
    {
        StackPane stackPane = FXMLLoader.load(getClass().getResource("setting.fxml"));
        contentViewForChange.getChildren().setAll(stackPane);
    }

    @FXML
    protected void handleCatchDataButtonAction(ActionEvent actionEvent)throws Exception
    {
        CatchData catchData = new CatchData();

        catchData.startCatchData();
    }

    @FXML
    protected void handleExitButtonAction(ActionEvent actionEvent)
    {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void menuObjectsScaleConfig()
    {
        // screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenwidth = screenSize.getWidth();
        double screenheight = screenSize.getHeight();

        // ratios
        final double realWidthBackgroundImage = 10240;
        final double realHeightBackgroundImage = 4320;

        double ratioWidth = screenwidth/realWidthBackgroundImage;
        double ratioHeight = screenheight/realHeightBackgroundImage;


        // background
        backgroundImageView.setFitWidth(screenwidth);
        backgroundImageView.setFitHeight(screenheight);

        // animation view
        startAnimationView.setFitWidth(screenwidth);
        startAnimationView.setFitHeight(screenheight);

        // exit button
        final double realWidthExitButton = 344;
        final double realHeightExitButton = 374;
        exitButtonImageView.setFitWidth(ratioWidth*realWidthExitButton);
        exitButtonImageView.setFitHeight(ratioHeight*realHeightExitButton);

        // setting button
        final double realWidthSettingButton = 351;
        final double realHeightSettingButton = 372;
        settingButtonImageView.setFitWidth((ratioWidth*realWidthSettingButton));
        settingButtonImageView.setFitHeight(ratioHeight*realHeightSettingButton);

        // start button
        final double realWidthStartButton = 2733;
        final double realHeightStartButton = 1215;
        startButtonImageView.setFitWidth(ratioWidth*realWidthStartButton);
        startButtonImageView.setFitHeight(ratioHeight*realHeightStartButton);

        // catch data button
        final double realWidthCatchDataButton = 1057;
        final double realHeightCatchDataButton = 1058;
        catchDataButtonImageView.setFitWidth(ratioWidth*realWidthCatchDataButton);
        catchDataButtonImageView.setFitHeight(ratioHeight*realHeightCatchDataButton);
    }



}
