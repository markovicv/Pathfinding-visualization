package algorithm;

import model.Constants;
import model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DFS extends PathFindingAlgo {

    @Override
    public void start() {
        dfs();

    }
    private void findPath(Map<Node,Node> parents, Node end){
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

    private void dfs(){
        Stack<Node> stack = new Stack<>();
        Map<Node,Node>parents = new HashMap<>();
        stack.push(start);

        while(!stack.empty()){
            Node currentNode = stack.pop();
            currentNode.setVisited(true);
            currentNode.setNodeType(Constants.NODE_NOT_VALID);
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
                    notifyObservers();
                    sleep();
                    stack.push(neighbor);
                }
            }
        }

    }

}
