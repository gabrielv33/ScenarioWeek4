import FileIO.InputReader;

public class Main {

    public static void main(String[] args)
    {
        InputReader inputReader = InputReader.GetInstance();
        Map map = Map.GetInstance();
        Graph graph = Graph.GetInstance();

        //start test purposes
        map.LoadMapDataFromLine(2);
        graph.ConstructGraph();
        //end test purposes

        int numberOfLines = inputReader.GetNumberOfLine();
        for(int currentLine=1; currentLine<=numberOfLines;currentLine++) {

            map.LoadMapDataFromLine(currentLine);
        }
    }
}
