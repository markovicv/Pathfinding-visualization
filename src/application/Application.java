package application;

import model.Constants;
import ui.CommandView;
import ui.Visualization;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application(){
        super("Path finding");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Constants.WIDTH+15,Constants.HEIGHT);
        Visualization visualization = new Visualization();
        CommandView commandView = new CommandView(visualization);

        visualization.setPreferredSize(new Dimension(800,800));
        commandView.setPreferredSize(new Dimension(800,100));

        this.add(visualization,BorderLayout.CENTER);
        this.add(commandView,BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){
        Application app = new Application();
    }
}
