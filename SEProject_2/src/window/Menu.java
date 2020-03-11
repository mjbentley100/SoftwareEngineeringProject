package window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class Menu extends MouseAdapter {

    private Handler handler;
    private Main main;

    public Menu(Main main, Handler handler) {
        this.main = main;
        this.handler = handler;
    }


    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        if (Main.ProjState == Main.state.MENU) {
            if (mouseOver(mx, my, 20, 120, 100, 50)) {
                main.initFace();
            }
        }

        if (mouseOver(mx, my, 20, 180, 100, 50)) {
            System.exit(1);
        }

    }


    public void render(Graphics g) {
        g.setColor(new Color(120, 120, 120));
        g.fillRect(20, 120, 100, 50);
        g.fillRect(20, 180, 100, 50);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 48));
        g.drawString("Face Guy 3000", 100, 50);
        g.setFont(new Font("Serif", Font.BOLD, 24));
        g.drawString("Play", 45, 150);
        g.drawString("Quit", 45, 210);
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
