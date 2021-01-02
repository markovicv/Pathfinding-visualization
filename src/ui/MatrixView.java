package ui;

import model.Constants;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MatrixView extends JPanel implements MouseWheelListener {


    public Node[][] board = new Node[Constants.ROW_NUMBER][Constants.ROW_NUMBER];
    public static int NODE_WIDTH = 20;
//    public static int ROW_NUMBER = 250;
    public static int MIN_NODE_WIDTH=10;
    public static int MAX_NODE_WIDTH = 40;

    private int cellWidth = NODE_WIDTH;

    public MatrixView(){
        initBoard();

        addMouseWheelListener(this);
        updateView();

    }

    private void updateView(){
        int width = board.length*cellWidth;
        int height = board[0].length*cellWidth;
        Dimension size = new Dimension(width,height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void initBoard(){
        int nodeWidth = cellWidth;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                board[i][j] = new Node(i,j, Constants.NODE_EMPTY,nodeWidth);

            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics graph = g.create();
        makeNodes(graph);
        makeBoard(graph);
        graph.dispose();
    }

    private void makeNodes(Graphics g){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                Node node = board[i][j];
                g.setColor(node.getNodeColor());
                g.fillRect(i*cellWidth,j*cellWidth,cellWidth-1,cellWidth-1);

            }
        }
    }
    private void makeBoard(Graphics g){
            g.setColor(new Color(255,255,255));

            for(int i=0;i<Constants.WIDTH/cellWidth;i++){
                g.drawLine(0,i*cellWidth,cellWidth,i*cellWidth);
            }
            for(int j=0;j<Constants.WIDTH/cellWidth;j++){
                g.drawLine(j*cellWidth,0,j*cellWidth,cellWidth);

        }
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        if(mouseWheelEvent.getWheelRotation()<0 && cellWidth<MAX_NODE_WIDTH ){
            int newSize = cellWidth+20;
            cellWidth = Math.min(newSize,MAX_NODE_WIDTH);

            updateView();
        }
        else if(mouseWheelEvent.getWheelRotation()>0 && cellWidth > MIN_NODE_WIDTH){
            int newSize = cellWidth-10;
            cellWidth = Math.max(newSize,MIN_NODE_WIDTH);
            updateView();
        }
        repaint();
    }
}
