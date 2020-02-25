package window;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<ProjObject> object  = new LinkedList<ProjObject>();



    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            ProjObject tmpObject = object.get(i);
            tmpObject.tick();
        }

    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            ProjObject tmpObject = object.get(i);
            tmpObject.render(g);
        }
    }


    public void addObject(ProjObject o) {
        this.object.add(o);
    }

    public void removeObject(ProjObject o) {
        this.object.remove(o);
    }






}
