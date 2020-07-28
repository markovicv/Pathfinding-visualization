package ui;

import model.Constants;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Visualization extends JPanel implements MouseMotionListener,MouseListener,KeyListener {

    public Node[][] board = new Node[50][50];
    public Node startNode = null;
    public Node endNode = null;
    private char currentKey = (char)0;
    public boolean algorithmRunning = false;



    public Visualization(){
        makeBoard();
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);


    }

    public void makeBoard(){
        int node_width = 800/50;
        for(int i=0;i<50;i++){
            for(int j=0;j<50;j++){
                board[i][j] = new Node(i,j, Constants.NODE_EMPTY,node_width);

            }

        }
    }


    private void drawBoard(Graphics g){
        Graphics2D graphics2D = (Graphics2D)g;
        int nodeWidth = 800/50;
        graphics2D.setColor(new Color(255,255,255));
        for(int i=0;i<50;i++){
            graphics2D.drawLine(0,i*nodeWidth,800,i*nodeWidth);
            for(int j=0;j<50;j++){
                graphics2D.drawLine(j*nodeWidth,0,j*nodeWidth,800);

            }
        }
    }
    private void drawNode(Graphics g,Node node){
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(node.getNodeColor());
        graphics2D.fillRect(node.getX(),node.getY(),node.getNodeWidth()-1,node.getNodeWidth()-1);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i=0;i<50;i++){
            for(int j=0;j<50;j++){
                drawNode(g,board[i][j]);
            }
        }
        drawBoard(g);



    }

    private void renderNode(MouseEvent e){
        int nodeWidth = 800/50;
        int row = e.getX()/nodeWidth;
        int col = e.getY()/nodeWidth;
        Node node = board[row][col];

        if(SwingUtilities.isLeftMouseButton(e)){


            if(currentKey == 's' && startNode==null){
                startNode = node;
                startNode.setNodeType(Constants.NODE_START);
                repaint();



            }
             if(currentKey == 'e' && endNode==null){
                endNode = node;
                endNode.setNodeType(Constants.NODE_END);
                repaint();

            }
             if(node.getNodeType().equals(Constants.NODE_EMPTY)){
                node.setNodeType(Constants.NODE_BLOCK);
                repaint();

            }

        }
        else if(SwingUtilities.isRightMouseButton(e)){
            if(node.getNodeType().equals(Constants.NODE_BLOCK)){
                node.setNodeType(Constants.NODE_EMPTY);
            }
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        renderNode(mouseEvent);

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        renderNode(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        currentKey = keyEvent.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        currentKey = (char)0;


    }
}
