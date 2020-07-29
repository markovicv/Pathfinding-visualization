package ui;

import algorithm.*;
import model.Constants;

import javax.swing.*;
import java.awt.*;

public class CommandView extends JPanel {
    private String[] algorithms = {"A*","Dijkstra","BFS","DFS"};
    private String[] speedOptions = {"slow","medium","fast"};
    private JComboBox algoList;
    private JComboBox speedList;
    private JButton btnVisualize = new JButton("Visualize");
    private JButton btnClear = new JButton("Clear");
    private Visualization visualization;


    public CommandView(Visualization visualization){
        this.visualization = visualization;
        this.setLayout(new FlowLayout());
        this.algoList = new JComboBox(algorithms);
        this.speedList = new JComboBox(speedOptions);
        initView();
        initListeners();

    }
    private void initView(){
        btnVisualize.setPreferredSize(new Dimension(120,25));
        btnClear.setPreferredSize(new Dimension(120,25));
        this.algoList.setFocusable(false);
        this.btnVisualize.setFocusable(false);
        this.btnClear.setFocusable(false);
        this.speedList.setFocusable(false);
        this.add(new JLabel("Pathfinding algorithm: "));
        this.add(algoList);
        this.add(new JLabel("Speed: "));
        this.add(speedList);
        this.add(btnVisualize);
        this.add(btnClear);
    }

    private void initListeners(){
        btnVisualize.addActionListener(actionEvent->{
            visualization.setPathFindingAlgo(generatePathfindingAlgo(algoList.getSelectedItem().toString()),generatePathfindingSpeed(speedList.getSelectedItem().toString()));
            if(!visualization.errorChecking() && !visualization.isAlgorithmRunning())
                visualization.startAlgo();
        });
        btnClear.addActionListener(actionEvent->{
            if(!visualization.isAlgorithmRunning())
            visualization.clearBoard();
        });

    }
    private PathFindingAlgo generatePathfindingAlgo(String algo){
        if(algo.equals("A*"))
            return new Astar();
        else if(algo.equals("Dijkstra"))
            return new Dijkstra();
        else if(algo.equals("BFS"))
            return new BFS();
        else
            return new DFS();
    }
    private int generatePathfindingSpeed(String speed){
        if(speed.equals("slow"))
            return Constants.SLOW;
        else if(speed.equals("medium"))
            return Constants.MEDIUM;
        else
            return Constants.FAST;
    }
}
