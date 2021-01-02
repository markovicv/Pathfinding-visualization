package ui;

import model.Constants;
import model.Node;

import javax.swing.*;
import java.awt.*;

public class Draw{

    public void drawBoard(Graphics g){
        Graphics2D graphics2D = (Graphics2D)g;
        int nodeWidth = Constants.NODE_WIDTH;
        graphics2D.setColor(new Color(255,255,255));
        for(int i=0;i<Constants.ROW_NUMBER;i++){
            graphics2D.drawLine(0,i*nodeWidth,Constants.WIDTH,i*nodeWidth);
        }
        for(int j=0;j<Constants.ROW_NUMBER;j++){
            graphics2D.drawLine(j*nodeWidth,0,j*nodeWidth,Constants.WIDTH);

        }
    }
    public void drawNode(Graphics g, Node node){
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(node.getNodeColor());
        graphics2D.fillRect(node.getX(),node.getY(),node.getNodeWidth()-1,node.getNodeWidth()-1);

    }
    public void drawOval(Graphics g,Node node){
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(node.getNodeColor());
        graphics2D.fillOval(node.getX(),node.getY(),node.getNodeWidth()-1,node.getNodeWidth()-1);
    }


}
