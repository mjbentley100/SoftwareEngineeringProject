package window;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class FaceWindow extends MouseAdapter {

    private Handler handler;
    private Main main;
    private Pixel[][] pixelArray;

    //to be removed
    private Random rand;
    private int pixX;
    private int pixY;
    private int onOff;


    public FaceWindow(Main main, Handler handler) {
        this.handler = handler;
        this.main = main;

        //to be removed
        rand = new Random();



        pixelArray = new Pixel[8][8];

        for (int i = 0; i < 8; i++){
            for (int j = 0; j <8; j++){
                pixelArray[i][j] = new Pixel(45, 45, 50 + (50 * j), 50 + (50 * i));
                if (j % 2 == 0) {
                    pixelArray[i][j].setOn(false);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {


    }


    public void tick(){


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //this.pixX = rand.nextInt(8);
                //this.pixY = rand.nextInt(8);
                this.onOff = rand.nextInt(2);

                if (onOff == 0) {
                    this.pixelArray[i][j].setOn(true);
                } else {
                    this.pixelArray[i][j].setOn(false);
                }
            }
        }
    }


    public void render(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (pixelArray[i][j].getOn() == true) {
                    g.setColor(new Color(1,1,1));
                    g.fillRect(pixelArray[i][j].getX(), pixelArray[i][j].getY(), pixelArray[i][j].getWidth(), pixelArray[i][j].getHeight());

                } else {
                    g.setColor(new Color(255, 255, 255));
                    g.fillRect(pixelArray[i][j].getX(), pixelArray[i][j].getY(), pixelArray[i][j].getWidth(), pixelArray[i][j].getHeight());
                }

            }
        }


    }


}
