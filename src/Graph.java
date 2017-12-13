import java.util.*;

/**
 * Created by greg on 18/11/16.
 */
public class Graph{
    private Hashtable<String, Node> g; //o grafos ylopoieitai ws ena hashtable pou exei toys kombous


    public Graph(){
        g = new Hashtable();
    }

    public Node addVertex(Node New){
        Node res = g.putIfAbsent(New.getX()+New.getY(), New);
        return res;
    }

    public Node getVertex(String S){
        return g.get(S);
    }

    public double AStar(Node S, Node G){
        Comparator<Node> comparator = new NodeComparator();
        PriorityQueue<Node> list = new PriorityQueue(10, comparator);   //to metwpo anazitisis
        HashSet<Node> visited = new HashSet<>();        //oi komvoi poy exoyme episkeftei
        HashSet<Node> queue = new HashSet<>();  //exei ta idia me tin oura, alla prosferei pio grigori euresi stoixeiwn
                                                //gia na blepoyme an kati yparxei sto metwpo anazitisis idi


        S.setHeuristic(G);
        S.setReal(0);
        list.add(S);
        queue.add(S);

        while (!list.isEmpty()) {        //oso to metwpo den einai adeio synexizoyme
            Node current = list.poll();
            queue.remove(current);
            if (current.equals(G)) {
                break;
            }
            visited.add(current);

            ArrayList<Tuple> neighbourhood = current.getNeighbours(); //briskoume toys geitones
            double distance = current.getReal();    //pairnoume tin trexousa apostasi

            for (int i = 0; i < neighbourhood.size(); i++) { //elegxoume kathe geitona
                Tuple X = neighbourhood.get(i);
                Node X1 = X.getX();
                double X2 = X.getY();

                if (!visited.contains(X1))          //an ton exoume episkefthei tote den asxoloymaste
                {
                    if (!queue.contains(X1)) {       //elegxoume an einai sto metwpo
                        X1.setReal(distance + X2);
                        X1.setHeuristic(G);         //an den einai ton bazoume
                        X1.setPrevNode(current);
                        list.add(X1);
                        queue.add(X1);
                    } else {
                        double newReal = distance + X2;       //an einai sto metwpo elegxoume ti nea real apostasi
                        //an einai mikroteri ton bgazoume kai ton bazoume pali
                        //gia na allaxei i proteraiotita
                        if (X1.getReal() > newReal) {
                            list.remove(X1);
                            X1.setReal(newReal);
                            X1.setPrevNode(current);
                            list.add(X1);
                        }
                    }
                }
            }
        }
        return G.getReal();
    }

    public ArrayList<Node>  constructPath(Node G){      //ftiaxnei mia lista me to monopati me basi to prev node
        ArrayList<Node> result = new ArrayList<>();
        result.add(G);
        Node prev = G.getPrevNode();

        while (prev != null){
            result.add(prev);
            prev = prev.getPrevNode();
        }

        return result;
    }

    public double printPath(Node S, Node G){        //tupwnei to monopati pou exei ftiaxtei apo prin
        double distance = AStar(S, G);
        if (distance == -1)
            return -1;


        ArrayList<Node> result = constructPath(G);
        int i = result.size()-1;


        while (i >= 0) {
            Node current = result.get(i);
            System.out.println(current.getX() + "," + current.getY() + ",0");
            i--;
        }


        return  distance;
    }

    public Node put(Node S)     //topothetei tous pelates kai ta taxi ston grafo an den yparxoyn sto arxeio nodes
    {
        if (!g.containsValue(S))
        {
            double X1 = S.getXCoord();
            double Y1 = S.getYCoord();

            double dist = Math.pow(10,10);
            Node neighbour = null;
            Set<String> keys = g.keySet();


            for (String key: keys)
            {
                Node cand = g.get(key);
                double X2 = cand.getXCoord();
                double Y2 = cand.getYCoord();


                double newDist = Math.sqrt(Math.pow(X1-X2,2.0) + Math.pow(Y1-Y2,2.0));

                if (newDist < dist)
                {
                    dist = newDist;
                    neighbour = cand;
                }

            }

            S.addNeighbour(neighbour);
            neighbour.addNeighbour(S);
            g.put(S.getX()+S.getY(),S);
            return S;
        }
        else{
            return g.get(S.getX()+S.getY());
        }

    }

    public boolean contains(Node S){
        return g.containsKey(S.getX() + S.getY());
    }


}
