package algorithm;

import model.Constants;
import model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra extends PathFindingAlgo {

    @Override
    public void start() {
        isRunning =true;
        dijkstraPathFinding();
        isRunning=false;

    }



    private void dijkstraPathFinding(){
        Queue<Node> queue = new PriorityQueue<Node>();
        Map<Node,Node> parents = new HashMap<>();
        start.setG(0);
        start.setH(0);
        start.calculateF();
        start.setVisited(true);

        queue.add(start);

        while(!queue.isEmpty()){
            Node currentNode = queue.poll();

            if(currentNode == end){
                findPath(parents,end);
                return;
            }
            for(Node neighbor : getNodeNeighbors(currentNode)){
                int gScoreTmp = currentNode.getG()+1;
                if(gScoreTmp < neighbor.getG()){
                    parents.put(neighbor,currentNode);
                    neighbor.setG(gScoreTmp);
                    neighbor.setH(0);
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
