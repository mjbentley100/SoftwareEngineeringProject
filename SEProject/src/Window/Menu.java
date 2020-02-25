package Window;

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


    public void render(Graphics g) {

    }


}
