package model;

import java.awt.*;

public class Node {
    private int row;
    private int col;
    private int f;
    private int g;
    private int h;
    private int x;
    private int y;
    private boolean visited;
    private String nodeType;
    private int nodeWidth;
    private Color nodeColor;

    public Node(int row,int col,String nodeType,int nodeWidth){
        this.row = row;
        this.col = col;
        this.nodeType = nodeType;
        this.nodeWidth = nodeWidth;
        this.x = row*nodeWidth;
        this.y = col*nodeWidth;
        this.visited = false;
        this.f = this.g = this.h = Integer.MAX_VALUE;
        this.setNodeColor();
    }
    private void setNodeColor(){
        if(nodeType.equals(Constants.NODE_EMPTY)){
            this.nodeColor = new Color(255,255,255);
        }
        if(nodeType.equals(Constants.NODE_BLOCK)){
            this.nodeColor = new Color(0,0,0);
        }
        if(nodeType.equals(Constants.NODE_START)){
            this.nodeColor = new Color(0,255,0);
        }
        if(nodeType.equals(Constants.NODE_END)){
            this.nodeColor = new Color(255,0,0);
        }
    }
    public void draw(Graphics g){
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(this.nodeColor);
        graphics2D.drawRect(this.x,this.y,this.nodeWidth,this.nodeWidth);
        graphics2D.fillRect(this.x,this.y,this.nodeWidth-1,this.nodeWidth-1);

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
        this.setNodeColor();
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(int nodeWidth) {
        this.nodeWidth = nodeWidth;
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(Color nodeColor) {
        this.nodeColor = nodeColor;
    }
}
