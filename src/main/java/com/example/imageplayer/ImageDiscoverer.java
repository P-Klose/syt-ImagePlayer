package com.example.imageplayer;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageDiscoverer extends Thread{
    private Image srcImage;
    private int width;
    private int height;
    private WritableImage destImage;
    private ImagePlayerController controller;
    private boolean isRunning;

    public ImageDiscoverer(Image img) {
        this.srcImage = img;
        this.width = (int)srcImage.getWidth();
        this.height = (int)srcImage.getHeight();
        destImage = new WritableImage(width,height);
        initPixels();
    }

    private void initPixels() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Color c = Color.rgb(
                        (int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255), 0.5
                );
                destImage.getPixelWriter().setColor(column,row,c);
            }
        }
    }

    public void discoverImage(){
        isRunning = true;
        for (int row = 0; row < height; row++) {
            if(isRunning){
                int localRow = row;
                Platform.runLater(()->{
                    for (int column = 0; column < width; column++) {
                        Color c = srcImage.getPixelReader().getColor(column,localRow);
                        destImage.getPixelWriter().setColor(column,localRow,c);
                    }
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    synchronized (this){
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void run() {
        discoverImage();
        if(this.controller != null){
            this.controller.notifyCompletet(1.0);
        }
    }

    public Image getDestinationImage(){
        return destImage;
    }

    public void addObserver(ImagePlayerController controller) {
        this.controller = controller;
    }

    public void removeObserver(ImagePlayerController controller) {
        this.controller = null;
    }

    public void pauseDiscoverer() {
        synchronized (this){
            this.isRunning = false;
        }
    }
    public synchronized void continiousDiscoverer(){
        synchronized (this){
            this.isRunning = true;
            notifyAll();
        }
    }
}
