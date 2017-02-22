package DataStructures;

import java.util.ArrayList;

/**
 * Created by Gabriel on 21/02/2017.
 */

public class Node {

    private Coordinates coordinates;

    public ArrayList<Robot> robotsAtThisNode;
    public ArrayList<Edge> edgesList;

    public Node(double x, double y)
    {
        robotsAtThisNode = new ArrayList<>();
        edgesList = new ArrayList<>();
        coordinates = new Coordinates(x,y);
    }

    public Node(double x, double y, Robot currentRobot)
    {
        this(x,y);
        robotsAtThisNode.add(currentRobot);
    }

    public void AddNeighbouringNode(Node node)
    {
        Edge edge = new Edge(this, node);
        edgesList.add(edge);
    }

    public Coordinates GetCoordinates()
    {
        return coordinates;
    }
}
