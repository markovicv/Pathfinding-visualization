package ui;

import algorithm.*;
import model.Constants;
import model.Node;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Visualization extends JPanel implements MouseMotionListener,MouseListener,KeyListener, Observer {

    public Node[][] board = new Node[Constants.ROW_NUMBER][Constants.ROW_NUMBER];
    public Node startNode = null;
    public Node endNode = null;
    private char currentKey = (char)0;
    public boolean algorithmRunning = false;
    public PathFindingAlgo pathFindingAlgo;
    private ExecutorService executorService;
    private Thread pathFindingThread;


    public Visualization(){
        makeBoard();
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.executorService = Executors.newFixedThreadPool(10);



    }
    public void setPathFindingAlgo(PathFindingAlgo pathFindingAlgo){
        this.pathFindingAlgo = pathFindingAlgo;
        this.pathFindingAlgo.addObserver(this);
        this.pathFindingAlgo.setBoard(board);
        this.pathFindingAlgo.setStart(startNode);
        this.pathFindingAlgo.setEnd(endNode);
        this.pathFindingAlgo.setSleepTime(20);

    }
    public void startAlgo(){
        pathFindingThread = new Thread(this.pathFindingAlgo);
        executorService.submit(pathFindingThread);


    }

    public void makeBoard(){
        int node_width = Constants.WIDTH/Constants.ROW_NUMBER;
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            for(int j=0;j<Constants.ROW_NUMBER;j++){
                board[i][j] = new Node(i,j, Constants.NODE_EMPTY,node_width);

            }

        }
    }


    private void drawBoard(Graphics g){
        Graphics2D graphics2D = (Graphics2D)g;
        int nodeWidth = Constants.WIDTH/Constants.ROW_NUMBER;
        graphics2D.setColor(new Color(255,255,255));
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            graphics2D.drawLine(0,i*nodeWidth,Constants.WIDTH,i*nodeWidth);
        }
        for(int j=0;j<Constants.ROW_NUMBER;j++){
            graphics2D.drawLine(j*nodeWidth,0,j*nodeWidth,Constants.WIDTH);

        }
    }
    private void drawNode(Graphics g,Node node){
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(node.getNodeColor());
        graphics2D.fillRect(node.getX(),node.getY(),node.getNodeWidth()-1,node.getNodeWidth()-1);

    }
    public void clearBoard(){
        makeBoard();
        startNode = null;
        endNode=null;
        repaint();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            for(int j=0;j<Constants.ROW_NUMBER;j++){
                drawNode(g,board[i][j]);
            }
        }
        drawBoard(g);



    }

    private void renderNodeState(MouseEvent e){
        int nodeWidth = Constants.WIDTH/Constants.ROW_NUMBER;
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
            if(node.getNodeType().equals(Constants.NODE_START)){
                node.setNodeType(Constants.NODE_EMPTY);
                startNode = null;
            }
            if(node.getNodeType().equals(Constants.NODE_END)){
                node.setNodeType(Constants.NODE_EMPTY);
                endNode = null;
            }
            repaint();
        }
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(this.pathFindingAlgo==null|| !this.pathFindingAlgo.isRunning())
            renderNodeState(mouseEvent);

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if(this.pathFindingAlgo==null|| !this.pathFindingAlgo.isRunning())
            renderNodeState(mouseEvent);
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
        if(keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() =='e')
            currentKey = keyEvent.getKeyChar();
        if(keyEvent.getKeyChar()=='c')
            clearBoard();
        else if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            if(startNode==null){
                JOptionPane.showMessageDialog(this,Constants.NODE_START_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(endNode == null){
                JOptionPane.showMessageDialog(this,Constants.NODE_END_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            setPathFindingAlgo(new Astar());
            startAlgo();

        }


    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        currentKey = (char)0;


    }
}
