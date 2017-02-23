package com.scenarioweek4.main;

import DataStructures.Edge;
import DataStructures.Node;
import DataStructures.Obstacle;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by Gabriel on 21/02/2017.
 */
public class Graph {

    public static Graph instance = null;

    public ArrayList<Node> nodesList;

    private Graph() {
        nodesList = new ArrayList<>();
    }

    public void CleanGraph()
    {
        nodesList = new ArrayList<>();
    }

    public static Graph GetInstance() {
        if (instance == null)
            instance = new Graph();
        return instance;
    }

    public void AddNode(Node node) {
        nodesList.add(node);
    }

    private Line2D buildLineBetweenNodes(Node node1, Node node2) {
        return new Line2D.Double(node1.GetCoordinates().x,
                node1.GetCoordinates().y,
                node2.GetCoordinates().x,
                node2.GetCoordinates().y);
    }

    private void buildEdgeBetweenNodes(Node node1, Node node2, Line2D line2D)
    {
        Edge newEdge = new Edge(node1, node2, line2D);
        node1.edgesList.add(newEdge);
        newEdge = new Edge(node2, node1, line2D);
        node2.edgesList.add(newEdge);
    }

//    public void ConstructGraph() {
//        Map map = Map.GetInstance();
//
//        for (int i = 0; i < nodesList.size(); i++) {
//            Node currentNode = nodesList.get(i);
//
//            for (int j = i + 1; j < nodesList.size(); j++) {
//                Node alternativeNode = nodesList.get(j);
//
//                Line2D newLine = buildLineBetweenNodes(currentNode, alternativeNode);
//
//                if (!isLineIntersectingAnyObstacle(newLine, currentNode, alternativeNode)) {
//                    buildEdgeBetweenNodes(currentNode, alternativeNode, newLine);
//                }
//            }
//        }
//
//        for(Obstacle currentObstacle : map.obstaclesList)
//        {
//            Line2D newLine;
//
//            for (int i = 0; i < currentObstacle.verticesList.size() - 1; i++) {
//                newLine = buildLineBetweenNodes(currentObstacle.verticesList.get(i), currentObstacle.verticesList.get(i + 1));
//                buildEdgeBetweenNodes(currentObstacle.verticesList.get(i), currentObstacle.verticesList.get(i + 1), newLine);
//            }
//
//            newLine = buildLineBetweenNodes(currentObstacle.verticesList.get(0), currentObstacle.verticesList.get(currentObstacle.verticesList.size() - 1));
//            buildEdgeBetweenNodes(currentObstacle.verticesList.get(0), currentObstacle.verticesList.get(currentObstacle.verticesList.size() - 1), newLine);
//        }
//
//    }

    public void ConstructGraph() {

        Map map = Map.GetInstance();

        //Add edges between Robots and Obstacles, but not between two vertexes on the same obstacle
        for (int i=0; i<nodesList.size();i++) {
            Node currentNode = nodesList.get(i);
            for (int j = i + 1; j < nodesList.size(); j++) {
                Node alternativeNode = nodesList.get(j);

                if (currentNode.ParentObstacle != null
                        &&
                        currentNode.ParentObstacle.equals(alternativeNode.ParentObstacle)
                        )
                    continue;

                Line2D newLine = buildLineBetweenNodes(currentNode, alternativeNode);

                if (!isLineIntersectingAnyObstacleEdge(newLine)) {
                    buildEdgeBetweenNodes(currentNode, alternativeNode, newLine);
                }
            }
        }

        for(Obstacle currentObstacle : map.obstaclesList)
        {
            Line2D newLine;

            for (int i=0; i< currentObstacle.verticesList.size() - 1; i++)
            {
                Node currentNode = currentObstacle.verticesList.get(i);
                Node alternativeNode = currentObstacle.verticesList.get(i+1);

                //Adding edges between adjacent vertexes of the obstacle
                newLine = buildLineBetweenNodes(currentNode, alternativeNode);
                buildEdgeBetweenNodes(currentNode, alternativeNode, newLine);

                for (int j=i+2; j< currentObstacle.verticesList.size() ; j++)
                {
                    alternativeNode = currentObstacle.verticesList.get(j);

                    newLine = buildLineBetweenNodes(currentNode, alternativeNode);
                    if(!isLineIntersectingAnyObstacle(newLine))
                        buildEdgeBetweenNodes(currentNode, alternativeNode, newLine);
                }
            }

            newLine = buildLineBetweenNodes(currentObstacle.verticesList.get(0), currentObstacle.verticesList.get(currentObstacle.verticesList.size()-1));
            buildEdgeBetweenNodes(currentObstacle.verticesList.get(0), currentObstacle.verticesList.get(currentObstacle.verticesList.size()-1), newLine);
        }
    }

    private Boolean isLineIntersectingAnyObstacleEdge(Line2D currentLine)
    {
        Map map = Map.GetInstance();

        for (Obstacle currentObstacle : map.obstaclesList) {
            for (Line2D currentObstacleEdge : currentObstacle.edgesList)
            {
                if (currentLine.getP1().distance(currentObstacleEdge.getP1()) == 0
                        ||
                        currentLine.getP1().distance(currentObstacleEdge.getP2()) == 0
                        ||
                        currentLine.getP2().distance(currentObstacleEdge.getP1()) == 0
                        ||
                        currentLine.getP2().distance(currentObstacleEdge.getP2()) == 0)
                    continue;

                if (currentLine.intersectsLine(currentObstacleEdge))
                    return true;
            }
        }

        return false;
    }

    private Boolean isLineIntersectingObstacle(Line2D currentLine, Obstacle currentObstacle)
    {
        return currentObstacle.polygonShape.intersects(currentLine.getBounds());
    }

    private Boolean isLineIntersectingAnyObstacle(Line2D currentLine)
    {
        Map map = Map.GetInstance();

        for (Obstacle currentObstacle:map.obstaclesList) {
            if(isLineIntersectingObstacle(currentLine, currentObstacle))
                return true;
        }
        return false;
    }
}
