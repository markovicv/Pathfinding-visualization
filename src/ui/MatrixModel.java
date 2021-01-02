package ui;

import model.Constants;
import model.Node;

import javax.swing.*;

public class MatrixModel {
    public Node[][] board = new Node[Constants.ROW_NUMBER][Constants.ROW_NUMBER];
    public Node startNode = null;
    public Node endNode = null;
    public boolean boardCleared = true;


    public MatrixModel(){

    }


    public void makeBoard(){
        int nodeWidth = Constants.WIDTH/Constants.ROW_NUMBER;
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            for(int j=0;j<Constants.ROW_NUMBER;j++){
                board[i][j] = new Node(i,j, Constants.NODE_EMPTY,nodeWidth);

            }

        }
    }

    public void clearBoard(){
        makeBoard();
        startNode = null;
        endNode=null;
        boardCleared=true;

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

    }

}
