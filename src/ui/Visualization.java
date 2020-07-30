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

public class Visualization extends JPanel implements MouseMotionListener,MouseListener,KeyListener,MouseWheelListener, Observer {

    public Node[][] board = new Node[Constants.ROW_NUMBER][Constants.ROW_NUMBER];
    private int[] rowNumber = {20,50,80,100};
    private int zoom = 2;
    public Node startNode = null;
    private boolean boardCleared=true;
    public Node endNode = null;
    private char currentKey = (char)0;
    public PathFindingAlgo pathFindingAlgo;
    private ExecutorService executorService;
    private Thread pathFindingThread;
    private Draw draw;


    public Visualization(Draw draw){
        makeBoard();
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.addMouseWheelListener(this);
        this.draw = draw;
        this.executorService = Executors.newFixedThreadPool(10);



    }
    public void setPathFindingAlgo(PathFindingAlgo pathFindingAlgo,int pathfindingSpeed){
        this.pathFindingAlgo = pathFindingAlgo;
        this.pathFindingAlgo.addObserver(this);
        this.pathFindingAlgo.setBoard(board);
        this.pathFindingAlgo.setStart(startNode);
        this.pathFindingAlgo.setEnd(endNode);
        this.pathFindingAlgo.setSleepTime(pathfindingSpeed);

    }
    public void startAlgo(){
        pathFindingThread = new Thread(this.pathFindingAlgo);
        executorService.submit(pathFindingThread);


    }

    public boolean isAlgorithmRunning(){
        if(pathFindingAlgo!=null)
            return pathFindingAlgo.isRunning();
        return false;
    }
    public boolean errorChecking(){
        if(startNode==null){
            JOptionPane.showMessageDialog(this,Constants.NODE_START_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if(endNode == null){
            JOptionPane.showMessageDialog(this,Constants.NODE_END_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public void makeBoard(){
        int node_width = Constants.WIDTH/Constants.ROW_NUMBER;
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            for(int j=0;j<Constants.ROW_NUMBER;j++){
                board[i][j] = new Node(i,j, Constants.NODE_EMPTY,node_width);

            }

        }
    }


    public void clearBoard(){
        makeBoard();
        startNode = null;
        endNode=null;
        boardCleared=true;
        repaint();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            for(int j=0;j<Constants.ROW_NUMBER;j++){
                draw.drawNode(g,board[i][j]);
            }
        }
        draw.drawBoard(g);



    }

    private void renderNodeState(MouseEvent e){
        int nodeWidth = Constants.WIDTH/Constants.ROW_NUMBER;
        int row = e.getX()/nodeWidth;
        int col = e.getY()/nodeWidth;
        Node node = board[row][col];
        boardCleared=false;
        if(SwingUtilities.isLeftMouseButton(e)){
            if(currentKey == 's' && startNode==null){
                startNode = node;
                startNode.setNodeType(Constants.NODE_START);
                repaint();

            }
             else if(currentKey == 'e' && endNode==null){
                endNode = node;
                endNode.setNodeType(Constants.NODE_END);
                repaint();

            }
             else if(node.getNodeType().equals(Constants.NODE_EMPTY)){
                node.setNodeType(Constants.NODE_BLOCK);
                repaint();

            }


        }
        else if(SwingUtilities.isRightMouseButton(e)){
            if(node.getNodeType().equals(Constants.NODE_BLOCK)){
                node.setNodeType(Constants.NODE_EMPTY);
            }
            else if(node.getNodeType().equals(Constants.NODE_START)){
                node.setNodeType(Constants.NODE_EMPTY);
                startNode = null;
            }
            else if(node.getNodeType().equals(Constants.NODE_END)){
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
        if(mouseEvent.getX()>=Constants.WIDTH || mouseEvent.getX()<=0 || mouseEvent.getY()>=Constants.WIDTH || mouseEvent.getY()<=0)
            return;
        if(this.pathFindingAlgo==null|| !this.pathFindingAlgo.isRunning())
            renderNodeState(mouseEvent);

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if(this.pathFindingAlgo==null|| !this.pathFindingAlgo.isRunning() )
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


    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        currentKey = (char)0;


    }
    private void redrawGrid(){
        Constants.ROW_NUMBER = rowNumber[zoom];
        board = new Node[Constants.ROW_NUMBER][Constants.ROW_NUMBER];
        makeBoard();
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation()<0) {
            if (zoom <= 0)
                return;
            if(isAlgorithmRunning())
                return;
            if(!boardCleared){
                JOptionPane.showMessageDialog(this,Constants.NODE_START_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            zoom--;
           redrawGrid();
        }

        else {
            if(zoom>=3)
                return;
            if(isAlgorithmRunning())
                return;
            if(!boardCleared){
                JOptionPane.showMessageDialog(this,Constants.ZOOM_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            zoom++;
            redrawGrid();
        }

    }
}
