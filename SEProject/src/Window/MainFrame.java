package Window;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends Canvas {
    public MainFrame(int width, int height, String title, Main proj) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(proj);
        frame.setVisible(true);
        proj.start();
    }







}
