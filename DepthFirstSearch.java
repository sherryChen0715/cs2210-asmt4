import java.util.ArrayList;
import java.util.Stack;
public class DepthFirstSearch {
    RouteGraph inputGraph;
    Stack<Intersection> stack;
    public DepthFirstSearch(RouteGraph graph){
        this.inputGraph = graph;
        stack = new Stack<Intersection>();
    }
    public Stack<Intersection> path(Intersection startVertex, Intersection endVertex) throws GraphException{
        stack = new Stack<Intersection>();
        pathRec(startVertex, endVertex);
        return stack;
    }
    public Boolean pathRec(Intersection startVertex, Intersection endVertex){
        ArrayList<Road> edges = inputGraph.insertEdge();
        startVertex.setMark(true);
        stack.push(startVertex);
        if(startVertex==endVertex){
            return true;
        }
        for(startVertex!=endVertex){
            
            if(){
                if(pathRec(startVertex,endVertex)){
                    return true;
                }
            }
        }
        stack.pop();
        return false;
    }
}
