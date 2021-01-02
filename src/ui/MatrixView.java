package ui;

import algorithm.Astar;
import algorithm.PathFindingAlgo;
import model.Constants;
import model.Node;
import observer.Observer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixView extends JPanel implements MouseWheelListener, ChangeListener, KeyListener,MouseMotionListener,MouseListener, Observer {

    public Node[][] board = new Node[Constants.ROW_NUMBER][Constants.ROW_NUMBER];
    public static int NODE_WIDTH = 20;
    public static int MIN_NODE_WIDTH=10;
    public static int MAX_NODE_WIDTH = 40;

    public Node startNode;
    public Node endNode;
    public PathFindingAlgo pathFindingAlgo;
    private Thread pathFindingThread;
    private ExecutorService executorService;

    private char currentKey = (char)0;

    public boolean boardCleared = true;
    private int cellWidth = NODE_WIDTH;
    private boolean isBeingPressed = false;


    public MatrixView(){
        initBoard();
        this.pathFindingAlgo = new Astar();
        setFocusable(true);
        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        this.executorService = Executors.newFixedThreadPool(10);
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
    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics graph = g.create();
        makeNodes(graph);
        graph.dispose();
    }


    private void makeNodes(Graphics g){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                Node node = board[i][j];
                g.setColor(node.getNodeColor());
                g.fillRect(i*cellWidth,j*cellWidth,cellWidth-1,cellWidth-1);
                g.setColor(new Color(255,255,255));
                g.drawRect(i*cellWidth,j*cellWidth,cellWidth,cellWidth);

            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(mouseEvent.getX()>=Constants.WIDTH || mouseEvent.getX()<=0 || mouseEvent.getY()>=Constants.HEIGHT || mouseEvent.getY()<=0)
            return;
        if(isBeingPressed){
           redrawAlgo(this.startNode,mouseEvent);
        }
        else
            renderNodeState(mouseEvent);
    }

    private void redrawAlgo(Node startNode,MouseEvent mouseEvent){
        int row = mouseEvent.getX()/cellWidth;
        int col = mouseEvent.getY()/cellWidth;
        Node n = board[row][col];

        if(!this.isAlgorithmRunning()){

            startNode.setNodeType(Constants.NODE_EMPTY);
            startNode = n;
            startNode.setNodeType(Constants.NODE_START);


            this.clearAlgo();
            this.setPathFindingAlgo(this.pathFindingAlgo,0);
            this.startAlgo();
            repaint();
        }
    }


    private void renderNodeState(MouseEvent e){
        int row = e.getX()/cellWidth;
        int col = e.getY()/cellWidth;
        Node node = board[row][col];
        boardCleared=false;
        if(SwingUtilities.isLeftMouseButton(e)){


            if(currentKey == 's' && startNode==null && node.getNodeType().equals(Constants.NODE_EMPTY)){
                startNode = node;
                startNode.setNodeType(Constants.NODE_START);
                repaint();

            }
            else if(currentKey == 'e' && endNode==null && node.getNodeType().equals(Constants.NODE_EMPTY)){
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

    public void setPathFindingAlgo(PathFindingAlgo pathFindingAlgo,int pathfindingSpeed){
        System.out.println(pathFindingAlgo);
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
        revalidate();
    }
    public void clearBoard(){
        initBoard();
        startNode = null;
        endNode=null;
        boardCleared=true;
        repaint();

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



    public void clearAlgo(){
        for(int i=0;i< board.length;i++){
            for(int j=0;j<board[0].length;j++){
                Node n=board[i][j];
                if(n.getNodeType().equals(Constants.NODE_PATH) || n.getNodeType().equals(Constants.NODE_VALID) || n.getNodeType().equals(Constants.NODE_NOT_VALID))
                    board[i][j] = new Node(i,j,Constants.NODE_EMPTY,Constants.WIDTH/Constants.ROW_NUMBER);
                else if(n.getNodeType().equals(Constants.NODE_START)){
                    board[i][j] = new Node(i,j,Constants.NODE_START,Constants.WIDTH/Constants.ROW_NUMBER);
                    startNode = board[i][j];
                }
                else if(n.getNodeType().equals(Constants.NODE_END)){
                    board[i][j] = new Node(i,j,Constants.NODE_END,Constants.WIDTH/Constants.ROW_NUMBER);
                    endNode = board[i][j];
                }


            }
        }
        repaint();

    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {

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


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        renderNodeState(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(startNode!=null){
            int row = mouseEvent.getX()/cellWidth;
            int col = mouseEvent.getY()/cellWidth;

            if(startNode.getRow()==row && startNode.getCol()==col){
                isBeingPressed=true;
//                startNode.setNodeType(Constants.NODE_EMPTY);


            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(isBeingPressed){
            redrawAlgo(this.startNode,mouseEvent);

            isBeingPressed=false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
