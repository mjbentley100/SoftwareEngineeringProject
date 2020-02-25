package window;

public class Pixel {

    private int height;
    private int width;
    private int x;
    private int y;
    private boolean on;


    public Pixel(int height, int width, int x, int y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.on = true;
    }


    public boolean getOn(){
        return this.on;
    }

    public void setOn(boolean on){
        this.on = on;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }





}
