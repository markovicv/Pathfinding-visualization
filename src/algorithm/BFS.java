package algorithm;

import model.Constants;
import model.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS extends PathFindingAlgo{

    @Override
    public void start() {
        isRunning=true;
        bfs();
        isRunning=false;

    }


    private void bfs(){
        LinkedList<Node> queue = new LinkedList<Node>();
        Map<Node,Node> parents = new HashMap<>();
        start.setVisited(true);
        queue.add(start);

        while(!queue.isEmpty()){
            Node currentNode = queue.poll();

            if(currentNode == end){
                findPath(parents,end);
                return;
            }
            for(Node neighbor:getNodeNeighbors(currentNode)){
                if(!neighbor.isVisited()){
                    neighbor.setVisited(true);
                    parents.put(neighbor,currentNode);
                    if(neighbor==end)
                        neighbor.setNodeType(Constants.NODE_END);
                    else
                        neighbor.setNodeType(Constants.NODE_VALID);

                    queue.add(neighbor);
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
