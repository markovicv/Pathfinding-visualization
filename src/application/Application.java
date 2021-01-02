package application;

import model.Constants;
import ui.*;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application(){
        super("Path finding");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(Constants.WIDTH+15,Constants.HEIGHT);
        setSize(Constants.WIDTH,Constants.HEIGHT);
//        Visualization visualization = new Visualization(new Draw(),new MatrixModel());
        MatrixView mv = new MatrixView();
        CommandView commandView = new CommandView();

//        visualization.setPreferredSize(new Dimension(Constants.WIDTH,Constants.WIDTH));
        commandView.setPreferredSize(new Dimension(Constants.WIDTH,60));
        this.add(new JScrollPane(mv),BorderLayout.CENTER);
        this.add(commandView,BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){
        Application app = new Application();
    }
}
