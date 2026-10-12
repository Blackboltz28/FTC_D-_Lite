import java.util.Comparator;

public class NodeCompare implements Comparator<Node>{

    public int compare(Node n1, Node n2){
        n1.calculateKey();
        n2.calculateKey();
        

        if(Math.abs(n1.key[0] - n2.key[0]) < 0.01)
            return Double.compare(n1.key[1], n2.key[1]);
        return Double.compare(n1.key[0], n2.key[0]);
    }
}