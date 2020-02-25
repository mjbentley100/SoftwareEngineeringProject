package window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{


    public static state ProjState = state.MENU;
    private boolean running = false;
    public Handler handler;
    private int width;
    private int height;
    private Menu menu;
    private Thread thread;


    public enum state {
        MENU,
        PLAY
    }


    private Main(){
        width = 500;
        height = 500;
        new MainFrame(width, height, "Face Guy 3000", this);
        handler = new Handler();

        if (ProjState == state.MENU) {
            menu = new Menu(this, handler);
        }

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        this.requestFocus();
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {

        handler.tick();

        if (ProjState == state.MENU) {

        } else {

        }

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(54, 183, 104));
        g.fillRect(0,0,width, height);

        if (ProjState == state.MENU) {
            menu.render(g);
        }


        g.dispose();
        bs.show();
    }



    public static void main(String args[]) {
        new Main();
    }


}
