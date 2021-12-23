package com.example.imageplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class ImagePlayerController {
    @FXML
    public Label filepath;
    @FXML
    public ImageView imageView;
    @FXML
    public BorderPane borderPane;
    @FXML
    public Button btnStart;
    @FXML
    public Button btnLoad;
    @FXML
    public Button btnPause;

    private ImageDiscoverer discoverer;
    @FXML
    public void onLoadClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File("./src/images"));
        File imageFile = fileChooser.showOpenDialog(null);
        if(imageFile != null){
            filepath.setText(imageFile.getName());
            setImage(new Image(imageFile.toURI().toString()));
        }
    }
    private void setImage(Image image){
        int width =Math.min(600, (int)(image.getWidth()*0.25));
        int height =Math.min(600, (int)(image.getHeight()*0.25));
        discoverer = new ImageDiscoverer(image);
        discoverer.addObserver(this);
        imageView.setImage(discoverer.getDestinationImage());
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        borderPane.setCenter(null);
        borderPane.setCenter(imageView);
        //borderPane.setPrefHeight(height + buttonBar.getPrefHeight());
        borderPane.setPrefHeight(height + 25);
        //borderPane.setPrefWidth(Math.max(width, buttonBar.getPrefWidth()));
        borderPane.setPrefWidth(Math.max(width, 100));
        Scene scene = borderPane.getScene();
        if (scene != null){
            scene.getWindow().sizeToScene();
        }
    }

    @FXML
    public void onDiscoverImageClicked(ActionEvent actionEvent) {
        btnPause.setDisable(false);
        btnStart.setDisable(true);
        btnLoad.setDisable(true);

        discoverer.start();
    }

    public void notifyCompletet(double percentCompletet) {
        if(percentCompletet == 1.0){
            btnPause.setDisable(true);
            btnStart.setDisable(false);
            btnLoad.setDisable(false);
        }
    }

    @FXML
    public void onPauseClicked(ActionEvent actionEvent) {
        if(btnPause.getText().equalsIgnoreCase("pause")){
            btnPause.setText("Resume");
            discoverer.pauseDiscoverer();
        }else{
            btnPause.setText("Pause");
            discoverer.continiousDiscoverer();
        }
    }
}