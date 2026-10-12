import java.util.Comparator;

public class NodeCompare implements Comparator<Node>{

    public int compare(Node n1, Node n2){
        n1.calculateKey();
        n2.calculateKey();
        
        double n1f = n1.key[0];
        double n2f = n2.key[0];

        int n1i = (int) (n1f * 10.0);
        int n2i = (int) (n2f * 10.0);

        if(n1i == n2i)
            return Double.compare(n1.key[1], n2.key[1]);
        return Double.compare(n1f, n2f);
    }
}