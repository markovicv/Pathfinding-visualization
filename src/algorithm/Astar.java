package algorithm;

import model.Constants;
import model.Node;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar extends PathFindingAlgo {

    public Astar(){

    }


    @Override
    public void start() {
        isRunning=true;
        astarPathFinding();
        isRunning = false;

    }

    private void astarPathFinding(){
        Queue<Node> queue = new PriorityQueue<Node>();
        queue.add(start);
        start.setG(0);
        start.setH(manhatanDistance(start,end));
        start.calculateF();

        while(!queue.isEmpty()){
            Node currentNode = queue.poll();
            currentNode.setVisited(true);

            if(currentNode == end){
                return;
            }
            for(Node neighbor:getNodeNeighbors(currentNode)){
                int gScoreTmp = currentNode.getG()+1;
                if(gScoreTmp<neighbor.getG()){
                    neighbor.setG(gScoreTmp);
                    neighbor.setH(manhatanDistance(neighbor,end));
                    neighbor.calculateF();

                    if(!neighbor.isVisited()){
                        queue.add(neighbor);
                        neighbor.setVisited(true);
                        neighbor.setNodeType(Constants.NODE_VALID);

                    }
                }
            }

            notifyObservers();
            sleep();
            if(currentNode!=start){
                currentNode.setNodeType(Constants.NODE_NOT_VALID);
            }

        }
        System.out.println("OSAS");


    }
}
