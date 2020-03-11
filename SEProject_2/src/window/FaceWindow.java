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
    private boolean active;
    private int stbtnX = 200;
    private int stbtnY = 5;

    public FaceWindow(Main main, Handler handler) {
        this.handler = handler;
        this.main = main;
        this.active = false;

        //to be removed
        rand = new Random();



        pixelArray = new Pixel[16][16];

        for (int i = 0; i < 16; i++){
            for (int j = 0; j <16; j++){
                pixelArray[i][j] = new Pixel(22, 22, 48 + (25 * j), 60 + (25 * i));
                if (j % 2 == 0) {
                    pixelArray[i][j].setOn(false);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (Main.ProjState == Main.state.PLAY) {
            if (mouseOver(mx, my, stbtnX, stbtnY, 100, 50)) {
                if (!active) {
                    active = true;
                } else {
                    active = false;
                }
            }
        }

    }


    public void tick(){

        if (active) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
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

    }


    public void render(Graphics g) {
        if (active) {

            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {

                    g.setColor(new Color(200, 0, 12));
                    g.fillRect(stbtnX, stbtnY, 100, 50);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Serif", Font.BOLD, 24));
                    g.drawString("Stop", stbtnX + 25, stbtnY + 30);


                    if (pixelArray[i][j].getOn() == true) {
                        g.setColor(new Color(1, 1, 1));
                        g.fillRect(pixelArray[i][j].getX(), pixelArray[i][j].getY(), pixelArray[i][j].getWidth(), pixelArray[i][j].getHeight());

                    } else {
                        g.setColor(new Color(255, 255, 255));
                        g.fillRect(pixelArray[i][j].getX(), pixelArray[i][j].getY(), pixelArray[i][j].getWidth(), pixelArray[i][j].getHeight());
                    }

                }
            }
        } else {
            g.setColor(new Color(34, 200, 97));
            g.fillRect(stbtnX, stbtnY, 100, 50);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 24));
            g.drawString("Start", stbtnX + 25, stbtnY + 30);
        }


    }


    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }

        return false;
    }



}
