

public class Node{
    private double g, rhs, f;
    public double[] key;
    public int x, y;
    private boolean isBlocked;
    private double h = 0.0;


    public static double km = 0.0;
    
    
    public Node(int x, int y){
        g = Double.POSITIVE_INFINITY;
        rhs = Double.POSITIVE_INFINITY;
        f = Double.POSITIVE_INFINITY;
        key = new double[2];
        this.x = x;
        this.y = y;

        isBlocked = false;
    }

    public double[] calculateKey(){
        double[] result = new double[2];
        result[0] = Math.min(g, rhs) + h + km;
        result[1] = Math.min(g, rhs);

        return result;
    }
    

    public double getEdgeCost(Node n2){
        if(isBlocked == true) return Double.POSITIVE_INFINITY;
        return Math.sqrt(Math.pow((n2.x - x), 2.0) + Math.pow(n2.y - y, 2.0));
    }

    public double getFCost(){
        return f;
    }

    public void setG(double g){
        this.g = g;
    }

    public double getG(){
        return g;
    }

    public void setRHS(double rhs){
        this.rhs = rhs;
    }

    public double getRHS(){
        return rhs;
    }

    public void setH(double h){
        this.h = h;
    }

    public double getH(){
        return h;
    }

    

}