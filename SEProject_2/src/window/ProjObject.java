package window;

import java.awt.*;

public abstract class ProjObject {

    protected double x;
    protected double y;


    public ProjObject(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public abstract void tick();

    public abstract void render(Graphics g);




}
