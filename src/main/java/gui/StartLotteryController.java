package gui;

import db.DatabaseHandle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;


import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartLotteryController implements Initializable
{

    @FXML
    private ImageView pannelImageView;

    @FXML
    private StackPane startLotteryRootPane;

    @FXML
    private ImageView pannelWinnerImageView;




    private ImageView gradientBorderImageView = new ImageView(new Image("gui/gradient_border.png"));

    private Button startAutoScrollingButton = new Button();

    private DatabaseHandle databaseHandle = new DatabaseHandle();

    private final ListView<UsersModel> listView;

    private int pannelAnimationIterator = 0;






    public StartLotteryController()
    {
        ArrayList<DatabaseHandle.UserObjects> usersData = databaseHandle.queryInstaData();
        ObservableList<UsersModel> data = FXCollections.observableArrayList();
        for (int i = 0 ; i < usersData.size() ; i++)
        {
            data.add(i , new UsersModel(usersData.get(i).getUsername(),usersData.get(i).getText(),usersData.get(i).getId()));
        }

        listView = new ListView<UsersModel>(data);
        listView.setCellFactory(new Callback<ListView<UsersModel>, ListCell<UsersModel>>() {
            @Override
            public ListCell<UsersModel> call(ListView<UsersModel> listView) {
                return new UsersListCell();
            }
        });



    }

    @Override
    public void initialize(URL url , ResourceBundle resourceBundle)
    {
        // listview pannel
        listView.setPrefSize(595,440);
        listView.setMaxSize(595,440);
        startLotteryRootPane.getChildren().add(listView);


        // all nodes scale configuration
        lotteryObjectsScaleConfig();



        // button for start scrolling
        startAutoScrollingButton.setVisible(false);
        startLotteryRootPane.getChildren().add(startAutoScrollingButton);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            public void run()
            {
                startAutoScrollingButtonMethod();
            }
        };

        int delay = 3;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();



    }



    private void startAutoScrollingButtonMethod()
    {

        startAutoScrollingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {

                Node node = listView.lookup(".scroll-bar:vertical");

                if (node instanceof ScrollBar)
                {
                    final ScrollBar bar = (ScrollBar) node;

                    // animation scroll
                    Timeline timeline = new Timeline();
                    timeline.setCycleCount(1);


                    EventHandler onfinished = new EventHandler() {
                        @Override
                        public void handle(Event event)
                        {

                            HBox content;

                            Text username;
                            Text textComment;

                            ImageView imageView;
                            ImageView imageViewCircle;

                            Image image;
                            Image imageCircle;



                            username = new Text();
                            username.setFont(new Font("Arial", 20));
                            textComment = new Text();

                            VBox vBox = new VBox(username, textComment);
                            vBox.setSpacing(8);

                            imageView = new ImageView();
                            imageView.setFitWidth(56.8);
                            imageView.setFitHeight(56.8);

                            imageViewCircle = new ImageView();
                            imageViewCircle.setFitWidth(56.8);
                            imageViewCircle.setFitHeight(56.8);

                            StackPane stackPane = new StackPane(imageView , imageViewCircle);

                            content = new HBox(stackPane , vBox);
                            content.setSpacing(10);
                            content.setMaxHeight(58);
                            content.setMaxWidth(578.5);


                            username.setText(listView.getItems().get(listView.getItems().size() - 4).getUsername());
                            textComment.setText(listView.getItems().get(listView.getItems().size() - 4).getTextComment());
                            File file = new File(databaseHandle.querySettingImagePath() + listView.getItems().get(listView.getItems().size() - 4).getImageId() + ".png");
                            image = new Image(file.toURI().toString());
                            imageView.setImage(image);

                            imageCircle = new Image("gui/circle_profile_image.png");
                            imageViewCircle.setImage(imageCircle);



                            startLotteryRootPane.getChildren().add(gradientBorderImageView);
                            startLotteryRootPane.getChildren().add(content);
                            gradientBorderImageView.setVisible(true);


                            // animation hide listview

                            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                            Runnable task = new Runnable() {
                                public void run()
                                {
                                    Timeline timeline = new Timeline();
                                    timeline.setCycleCount(1);

                                    EventHandler onFinished = new EventHandler() {
                                        @Override
                                        public void handle(Event event)
                                        {

                                            // animation pannel

                                            Timeline timeline = new Timeline();
                                            timeline.setCycleCount(62);



                                            EventHandler imageEvent = new EventHandler<ActionEvent>()
                                            {
                                                public void handle(ActionEvent t)
                                                {

                                                    pannelImageView.setImage(Main.imagePannelAnimation.get(pannelAnimationIterator));
                                                    pannelAnimationIterator++;

                                                    if (pannelAnimationIterator == 62)
                                                    {
                                                        System.out.println("aaaaaaaaaaaaaaaaaa");

                                                    }

                                                }
                                            };

                                            KeyFrame keyFrame = new KeyFrame(Duration.millis(40),imageEvent);
                                            timeline.getKeyFrames().add(keyFrame);
                                            timeline.play();


                                        }
                                    };

                                    KeyValue keyValue = new KeyValue(listView.opacityProperty(), 0);
                                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(1),onFinished,keyValue);

                                    timeline.getKeyFrames().addAll(keyFrame);
                                    timeline.play();
                                }
                            };

                            int delay = 1;
                            scheduler.schedule(task, delay, TimeUnit.SECONDS);
                            scheduler.shutdown();





                        }
                    };

                   /* KeyValue keyValueStart = new KeyValue(bar.valueProperty(),0.1);
                    KeyFrame keyFrameStart = new KeyFrame(Duration.seconds(1),keyValueStart);

                    KeyValue keyValue = new KeyValue(bar.valueProperty(),0.9);
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(3),keyValue);*/

                    KeyValue keyValueEnd = new KeyValue(bar.valueProperty(),1);
                    KeyFrame keyFrameEnd = new KeyFrame(Duration.seconds(4),onfinished,keyValueEnd);


                    timeline.getKeyFrames().addAll(keyFrameEnd);
                    timeline.play();



                }
                else {
                    System.out.println("nulllllllllllllllllllll");
                }

            }
        });


        startAutoScrollingButton.fire();

    }




    private void lotteryObjectsScaleConfig()
    {
        // screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenwidth = screenSize.getWidth();
        double screenheight = screenSize.getHeight();

        // ratios
        final double realWidthBackgroundImage = 3440;
        final double realHeightBackgroundImage = 1440;

        double ratioWidth = screenwidth/realWidthBackgroundImage;
        double ratioHeight = screenheight/realHeightBackgroundImage;


        // pannel image view
        final double realWidthPannel = 1480;
        final double realHeightPannel = 1102;

        final double widthPannel = ratioWidth*realWidthPannel;
        final double heightPannel = ratioHeight*realHeightPannel;

        pannelImageView.setFitWidth(widthPannel);
        pannelImageView.setFitHeight(heightPannel);


        // gradient border
        final double realWidthGradientBorder = 1627;
        final double realHeightGradientBorder = 329;

        final double widthGradientBorder = ratioWidth*realWidthGradientBorder;
        final double heightGradientBorder = ratioHeight*realHeightGradientBorder;

        gradientBorderImageView.setFitWidth(widthGradientBorder+2);
        gradientBorderImageView.setFitHeight(heightGradientBorder-40);
        gradientBorderImageView.setVisible(false);

        // pannel winner
        final double realWidthPannelWinner = 1480;
        final double realHeightPannelWinner = 182;

        final double widthPannelWinner = ratioWidth*realWidthPannelWinner;
        final double heightPannelWinner = ratioHeight*realHeightPannelWinner;

        pannelWinnerImageView.setFitWidth(widthPannelWinner);
        pannelWinnerImageView.setFitHeight(heightPannelWinner);
        pannelWinnerImageView.setVisible(false);


    }


    private static class UsersModel {
        private String username;
        private String textComment;
        private String imageId;

        private String getUsername()
        {
            return username;
        }
        private String getTextComment()
        {
            return textComment;
        }
        private String getImageId()
        {
            return imageId;
        }

        private UsersModel(String username, String textComment, String imageId)
        {
            super();
            this.username = username;
            this.textComment = textComment;
            this.imageId = imageId;
        }
    }




    private class UsersListCell extends ListCell<UsersModel>
    {
        private HBox content;

        private Text username;
        private Text textComment;

        private ImageView imageView;
        private ImageView imageViewCircle;


        private UsersListCell()
        {
            super();
            username = new Text();
            username.setFont(new Font("Arial", 20));
            textComment = new Text();

            VBox vBox = new VBox(username, textComment);
            vBox.setSpacing(8);

            imageView = new ImageView();
            imageView.setFitWidth(56.8);
            imageView.setFitHeight(56.8);

            imageViewCircle = new ImageView();
            imageViewCircle.setFitWidth(56.8);
            imageViewCircle.setFitHeight(56.8);

            StackPane stackPane = new StackPane(imageView , imageViewCircle);

            content = new HBox(stackPane , vBox);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(UsersModel item, boolean empty)
        {
            Image image;
            Image imageCircle;

            String imageId;

            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter

                username.setText(item.getUsername());
                textComment.setText(item.getTextComment());

                imageId = item.getImageId();
                File file = new File(databaseHandle.querySettingImagePath() + imageId + ".png");
                image = new Image(file.toURI().toString());
                imageView.setImage(image);

                imageCircle = new Image("gui/circle_profile_image.png");
                imageViewCircle.setImage(imageCircle);


                setGraphic(content);
            }
            else {
                setGraphic(null);
            }
        }
    }




}
