package algorithm;

import model.Constants;
import model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DFS extends PathFindingAlgo {

    @Override
    public void start() {
        isRunning=true;
        dfs();
        isRunning = false;

    }


    private void dfs(){
        Stack<Node> stack = new Stack<>();
        Map<Node,Node>parents = new HashMap<>();
        stack.push(start);

        while(!stack.empty()){
            Node currentNode = stack.pop();
            currentNode.setVisited(true);
            if(currentNode == end){
                findPath(parents,end);
                return;
            }

            for(Node neighbor:getNodeNeighbors(currentNode)){
                if(!neighbor.isVisited()){
                    parents.put(neighbor,currentNode);
                    if(neighbor==end)
                        neighbor.setNodeType(Constants.NODE_END);
                    else
                        neighbor.setNodeType(Constants.NODE_VALID);

                    stack.push(neighbor);
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
