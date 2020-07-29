package algorithm;

import model.Constants;
import model.Node;

import java.util.*;

public class Astar extends PathFindingAlgo {

    public Astar(){

    }


    @Override
    public void start() {
        isRunning=true;
        astarPathFinding();
        isRunning = false;

    }
    private void findPath(Map<Node,Node> parents,Node end){
        Node tmpEnd = end;
        while(parents.containsKey(end)){
            end = parents.get(end);
            end.setNodeType(Constants.NODE_PATH);
            notifyObservers();
        }
        start.setNodeType(Constants.NODE_START);
        tmpEnd.setNodeType(Constants.NODE_END);
        notifyObservers();
    }

    private void astarPathFinding(){
        Queue<Node> queue = new PriorityQueue<Node>();
        Map<Node,Node>parents = new HashMap<>();
        queue.add(start);
        start.setVisited(true);
        start.setG(0);
        start.setH(manhatanDistance(start,end));
        start.calculateF();

        while(!queue.isEmpty()){
            Node currentNode = queue.poll();

            if(currentNode == end){
               findPath(parents,end);
                return;
            }
            for(Node neighbor:getNodeNeighbors(currentNode)){
                int gScoreTmp = currentNode.getG()+1;
                if(gScoreTmp<neighbor.getG()){
                    parents.put(neighbor,currentNode);
                    neighbor.setG(gScoreTmp);
                    neighbor.setH(manhatanDistance(neighbor,end));
                    neighbor.calculateF();

                    if(!neighbor.isVisited()){
                        queue.add(neighbor);
                        neighbor.setVisited(true);
                        if(neighbor == end)
                            neighbor.setNodeType(Constants.NODE_END);
                        else
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



    }
}
