package window;

import java.io.IOException;

public class PythonProcess {
    Process p;

    public PythonProcess() {
    }

    public void start () {
        System.out.println("RUNNING!");
        try {
            p = Runtime.getRuntime().exec("..\\decision_tree\\venv\\Scripts\\python.exe ..\\decision_tree\\AudioAnalyzer.py");
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
