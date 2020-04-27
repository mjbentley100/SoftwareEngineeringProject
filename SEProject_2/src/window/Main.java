package window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class Main extends Canvas implements Runnable{


    public static state ProjState = state.MENU;
    private boolean running = false;
    public Handler handler;
    private PythonProcess pythonProcess = new PythonProcess();;

    //sound Handler
    private SoundRecorder se;


    //testing
    private int testCount = 0;

    //Variables
    private int width;
    private int height;
    private int numtick;

    //Program windows
    public Menu menu;
    public FaceWindow face;

    //Decision tree
    private DecisionTree tree;

    //Main Thread
    private Thread thread;



    public enum state {
        MENU,
        PLAY
    }


    private Main(){

        se = new SoundRecorder();
        tree = new DecisionTree();
        tree.createTree();

        width = 500;
        height = 500;
        new MainFrame(width, height, "Face Guy 3000", this);
        handler = new Handler();

        if (ProjState == state.MENU) {
            menu = new Menu(this, handler);
            this.addMouseListener(menu);

        } else if (ProjState == state.PLAY) { //For testing purposes only (will never execute normally)
            this.face = new FaceWindow(this, handler);
        }

    }

    public synchronized void start() {
        pythonProcess.start();
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            pythonProcess.stop();
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
        this.numtick++;
        handler.tick();

        if (ProjState == state.MENU) {

        } else if (numtick % 10 == 1){
            se.recordAudio();
            //System.out.println("Main loop " + testCount);
            //testCount++;
            face.tick(tree.getFace(tree.parseAudio(), tree.getTree().getRoot()));
        }

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(173, 176, 183));
        g.fillRect(0,0,width, height);

        if (ProjState == state.MENU) {
            menu.render(g);
        } else if (ProjState == state.PLAY) {
            face.render(g);
        }


        g.dispose();
        bs.show();
    }


    public void initFace() {
        face = new FaceWindow(this, handler);
        Main.ProjState = Main.state.PLAY;
        removeMouseListener(menu);
        addMouseListener(face);
    }



    public static void main(String args[]) {
        new Main();
    }


}
