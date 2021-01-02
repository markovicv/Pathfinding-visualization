package algorithm;

import model.Constants;
import model.Node;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

abstract public class PathFindingAlgo implements Runnable, Observable {
    protected List<Observer> observers = new ArrayList<>();
    protected int sleepTime;
    protected boolean isRunning = false;
    protected Node[][] board;
    protected Node start;
    protected Node end;



    private int[] rowDir = {1,-1,0,0};
    private int[] colDir = {0,0,1,-1 };




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





    public void setSleepTime(int sleepTime){
        this.sleepTime = sleepTime;
    }

    public void sleep(){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected boolean isValid(Node node,int rowDir,int colDir){
        if(node.getRow()<Constants.ROW_NUMBER-1 && node.getRow()>0 && node.getCol()<Constants.ROW_NUMBER-1 && node.getCol()>0 && board[node.getRow()+rowDir][node.getCol()+colDir].getNodeType()!= Constants.NODE_BLOCK)
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

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
    protected void findPath(Map<Node,Node> parents, Node end){
        Node tmpEnd = end;
        while(parents.containsKey(end)){
            end = parents.get(end);
            end.setNodeType(Constants.NODE_PATH);
            sleep();
            notifyObservers();
        }
        start.setNodeType(Constants.NODE_START);
        tmpEnd.setNodeType(Constants.NODE_END);
        notifyObservers();
    }
}
