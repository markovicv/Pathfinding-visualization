package algorithm;

import model.Constants;
import model.Node;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

abstract public class PathFindingAlgo implements Runnable, Observable {
    protected List<Observer> observers = new ArrayList<>();
    protected int sleepTime;
    protected boolean isRunning = false;
    protected Node[][] board;
    protected Node start;
    protected Node end;

    private int[] rowDir = {1,-1,0,0};
    private int[] colDir = {0,0,1,-1};




    public abstract void start();

    public void setBoard(Node[][] board){
        this.board = board;
    }
    public void setStart(Node start){
        this.start = start;
    }
    public void setEnd(Node end){
        this.end = end;
    }
    public boolean isAlgoRunning(boolean run) {
        return isRunning;
    }


    public void setSleepTime(int sleepTime){
        this.sleepTime = sleepTime;
    }

    protected void sleep(){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected boolean isValid(Node node,int rowDir,int colDir){
        if(node.getRow()<49 && node.getRow()>0 && node.getCol()<49 && node.getCol()>0 && board[node.getRow()+rowDir][node.getCol()+colDir].getNodeType()!= Constants.NODE_BLOCK)
            return true;
        return false;

    }
    protected int manhatanDistance(Node node1,Node node2){
        return Math.abs(node1.getRow()-node2.getRow()) + Math.abs(node1.getCol()-node2.getCol());
    }
    protected List<Node> getNodeNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        for(int i=0;i<rowDir.length;i++){
            if(isValid(node,rowDir[i],colDir[i])){
                neighbors.add(board[node.getRow()+rowDir[i]][node.getCol()+colDir[i]]);
            }
        }
        return neighbors;

    }

    @Override
    public void addObserver(Observer o) {
        if(!observers.contains(o)){
            observers.add(o);
        }
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers)
            o.update();
    }


    @Override
    public void run() {
        start();
    }
}
