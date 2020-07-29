package ui;

import algorithm.*;

import javax.swing.*;
import java.awt.*;

public class CommandView extends JPanel {
    private String[] algorithms = {"A*","Dijkstra","BFS","DFS"};
    private JComboBox algoList;
    private JButton btnVisualize = new JButton("Visualize");
    private JButton btnClear = new JButton("Clear");
    private Visualization visualization;


    public CommandView(Visualization visualization){
        this.visualization = visualization;
        this.setLayout(new FlowLayout());
        this.algoList = new JComboBox(algorithms);
        initView();
        initListeners();

    }
    private void initView(){
        btnVisualize.setPreferredSize(new Dimension(120,25));
        btnClear.setPreferredSize(new Dimension(120,25));
        this.algoList.setFocusable(false);
        this.btnVisualize.setFocusable(false);
        this.btnClear.setFocusable(false);
        this.add(algoList);
        this.add(btnVisualize);
        this.add(btnClear);
    }

    private void initListeners(){
        btnVisualize.addActionListener(actionEvent->{
            visualization.setPathFindingAlgo(generatePathfindingAlgo(algoList.getSelectedItem().toString()));
            visualization.startAlgo();
        });
        btnClear.addActionListener(actionEvent->{
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
}
