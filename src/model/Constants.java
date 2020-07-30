package model;

import java.awt.*;

public class Constants {

    public final static String NODE_EMPTY = "EMPTY";
    public final static String NODE_BLOCK = "BLOCK";
    public final static String NODE_START = "START";
    public final static String NODE_END = "END";
    public final static String NODE_VALID = "VALID";
    public final static String NODE_NOT_VALID = "NOT_VALID";
    public final static String NODE_PATH = "NODE_PATH";
    public final static Color LIGHT_BLUE = new Color(135,206,250);
    public final static Color RED = new Color(255,0,0);
    public final static Color GREEN = new Color(0,255,0);
    public final static Color BLACK = new Color(0,0,0);
    public final static Color WHITE = new Color(255,255,255);
    public final static Color LIGHT_GREY = new Color(160,160,160);
    public final static Color GOLD = new Color(240,215,53);

    public final static String NODE_START_ERROR = "Start node is not defined";
    public final static String NODE_END_ERROR = "End node is not defined";
    public final static String ZOOM_ERROR = "Board must be cleared";
    public final static String ALGO_WORKING_ERROR = "Algorithm is working";

    public final static int WIDTH = 800;
    public final static int HEIGHT = 900;
    public static int ROW_NUMBER = 80;

    public static final int SLOW = 20;
    public static final int MEDIUM = 8;
    public static final int FAST = 1;

}
