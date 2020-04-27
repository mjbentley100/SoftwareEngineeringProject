package window;

import java.io.IOException;

public class PythonProcess {
    Process p;

    public PythonProcess() {
    }

    public void start () {
        System.out.println("RUNNING!");
        try {
            p = Runtime.getRuntime().exec("python C:\\Users\\mjben\\Documents\\GitHub\\SoftwareEngineeringProject\\decision_tree\\AudioAnalyzer.py");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            p.destroy();
        } catch (Exception e) {}
    }
}
