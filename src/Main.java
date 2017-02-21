import FileIO.InputReader;

public class Main {

    public static void main(String[] args)
    {
        InputReader inputReader = InputReader.GetInstance();
        Map map = Map.GetInstance();
        Graph graph = Graph.GetInstance();

        int numberOfLines = inputReader.GetNumberOfLine();
        for(int currentLine=1; currentLine<=numberOfLines;currentLine++) {

            map.LoadMapDataFromLine(currentLine);
            graph.GenerateGraph();


            //  createEdges();

       /*     ArrayList<Robot> RobotsList;
            ArrayList<Obstacle> obstaclesList;

            RobotsList = map.RobotsList;
            obstaclesList = map.obstaclesList;*/
        }

    }



    //DUBIOUS CODE???

    // Calling function to create edges with current DataStructures.Robot List
  /*  public void createEdges() {
        for (int i=0; i < RobotsList.size(); i++){
            for (int j = i + 1; j < RobotsList.size(); j++){
                Edge newEdge = new Edge(RobotsList.get(i), RobotsList.get(j));
                EdgesList.add(newEdge);
            }
        }
    }*/

}
