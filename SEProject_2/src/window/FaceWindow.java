package window;

import java.awt.*;
import java.awt.event.MouseAdapter;

public class FaceWindow extends MouseAdapter {

    private Handler handler;
    private Main main;
    private Pixel[][] pixelArray;
    private Pixel testPixel;


    public FaceWindow(Main main, Handler handler) {
        this.handler = handler;
        this.main = main;
        testPixel = new Pixel(20, 20, 50, 50);


    }


    public void render(Graphics g) {
        g.setColor(new Color(1,1,1));
        g.fillRect(testPixel.getX(), testPixel.getY(), testPixel.getWidth(), testPixel.getHeight());
    }


}
