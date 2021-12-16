package com.example.imageplayer;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageDiscoverer {
    private Image srcImage;
    private int width;
    private int height;
    private WritableImage destImage;

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

    public Image getDestinationImage(){
        return destImage;
    }
}
