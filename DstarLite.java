import java.util.*;

public class DstarLite{
    private static Node[][] grid = new Node[10][10];
    
    public static Node start;
    public static final int goalX = 8, goalY = 8;
    public static final int startX = 1, startY = 1;

    static{
        for(int y = 0; y < grid.length; ++y)
            for(int x = 0; x < grid[0].length; ++x)
                grid[y][x] = new Node(x,y);
        
        
        start = grid[startY][startX];
    }

    public static void main(String[] args){
        Node goal = grid[goalY][goalX];
        goal.setRHS(0.0);
        goal.setH(start.getEdgeCost(goal));
        goal.key = goal.calculateKey();

        Node.km = 0.0;
        Node sLast = start;
        int step = 0;
        
        PriorityQueue<Node> U = new PriorityQueue<>(new NodeCompare());
        U.add(goal);
        ArrayList<Node> path = new ArrayList<>();

        computeShortestPath(U);
        // for(int y = 0; y < grid.length; ++y){
        //     for(int x =0; x < grid[0].length; ++x){
        //         if(grid[y][x].key[0] == Double.POSITIVE_INFINITY) System.out.print("I ");
        //         else System.out.print(Math.round(grid[y][x].key[0]) + " ");
        //     }
        // System.out.println();
        // }
        while(start != goal){
            // if(start.getRHS() == Double.POSITIVE_INFINITY) break;
            path.add(start);
            double minC = Double.POSITIVE_INFINITY;
            Node minNode = null;
            for(Node n : getNeighbors(start)){
                double newC = start.getEdgeCost(n) + n.getG();
                if(newC < minC){
                    minNode = n;
                    minC = newC;
                }
            }

            start = minNode;
            if(step == 3){
                Node.km += sLast.getEdgeCost(start);
                sLast = start;

                Node obstacle1 = grid[5][5];
                double cOld, c;
                for(Node n : getNeighbors(obstacle1)){
                    
                    cOld = obstacle1.getEdgeCost(n);
                    obstacle1.setBlocked(true);
                    c = obstacle1.getEdgeCost(n);

                    if(obstacle1 != goal){
                        if(cOld > c) obstacle1.setRHS(Math.min(obstacle1.getRHS(), c + n.getG()));
                        else if(obstacle1.getRHS() == cOld + n.getG()){
                            double minRHS = Double.POSITIVE_INFINITY;
                        for(Node s : getNeighbors(n)){
                            double rhs = n.getEdgeCost(s) + s.getG();
                            if(rhs < minRHS) minRHS = rhs;
                        }
                        n.setRHS(minRHS);
                        }
                    }
                    updateNode(n, U);
                }

                computeShortestPath(U);
            }
        

            step++;
        }

        for(Node p : path)
            System.out.println(p.x + "," + p.y);
        // for(int y = 0; y < grid.length; ++y){
        //     for(int x =0; x < grid[0].length; ++x){
        //         if(grid[y][x].key[0] == Double.POSITIVE_INFINITY) System.out.print("I ");
        //         else System.out.print(Math.round(grid[y][x].key[0]) + " ");
        //     }
        // System.out.println();
        // }
                
    }

    

    public static void updateNode(Node u, PriorityQueue<Node> U){
        if(u.getG() != u.getRHS() && U.contains(u)){
            U.remove(u);
            u.key = u.calculateKey();
            U.add(u);
            //might bring logic error if it can't recognize object once values are changed
        }
        else if(u.getG() != u.getRHS() && !U.contains(u)){
            u.key = u.calculateKey();
            U.add(u);
        }
        else if(u.getG() == u.getRHS() && U.contains(u)){
            U.remove(u);   
        }  
    }

    public static ArrayList<Node> getNeighbors(Node u){
        ArrayList<Node> neighbors = new ArrayList<>();

        for(int y = -1; y <= 1 && u.y + y < 9 && u.y + y > -1; ++y)
            for(int x = -1; x <=1 && u.x + x < 9 && u.x + x > -1; ++x){
                if(x == 0 && y == 0) continue;
                neighbors.add(grid[u.y + y][u.x + x]);
            }

        return neighbors;
    }

    public static void computeShortestPath(PriorityQueue<Node> U){
        NodeCompare comp = new NodeCompare();
        while(!U.isEmpty() && ((comp.compare(U.peek(), start) < 0)|| start.getRHS() > start.getG())){
            Node u = U.peek();
            if(u.x == start.x && u.y == start.y) System.out.println("Start found.");
            double[] kOld = u.key;
            double[] kNew = u.calculateKey();
            
            if(kOld[0] < kNew[0] || kOld[1] < kNew[1]){
                U.remove(u);
                u.key = kNew;
                U.add(u);
            }else if(u.getG() > u.getRHS()){
                u.setG(u.getRHS());
                U.remove(u);

                for(Node n : getNeighbors(u)){
                    if(n != start) n.setRHS(Math.min(n.getRHS(), u.getG() + n.getEdgeCost(u)));
                    updateNode(n, U);
                }
            }else{
                double gOld = u.getG();
                u.setG(Double.POSITIVE_INFINITY);

                for(Node n : getNeighbors(u)){
                    if(n.getRHS() == gOld + n.getEdgeCost(u) && n != start){
                        double minRHS = Double.POSITIVE_INFINITY;
                        for(Node s : getNeighbors(n)){
                            double rhs = n.getEdgeCost(s) + s.getG();
                            if(rhs < minRHS) minRHS = rhs;
                        }
                        n.setRHS(minRHS);
                    }
                    updateNode(n, U);
                }
            }
            start.key = start.calculateKey();
        }
    }

    // public static int find(Object o, Object[] arr){
    //     for(int i = 0; i < arr.length; ++i){
    //         if(arr[i] == o) return i;
    //     }

    //     return -1;
    // }
}