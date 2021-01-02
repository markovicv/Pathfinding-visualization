package application;

import model.Constants;
import ui.*;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application(){
        super("Path finding");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WIDTH,Constants.HEIGHT);
        MatrixView mv = new MatrixView();
        CommandView commandView = new CommandView(mv);

        final JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(mv);
        commandView.setPreferredSize(new Dimension(Constants.WIDTH,60));
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(commandView,BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args){
        Application app = new Application();
    }
}
