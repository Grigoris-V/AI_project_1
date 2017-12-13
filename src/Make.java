import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by greg on 18/11/16.
 */
public class Make {
    public static void main(String[] args)
    {
        String line ;
        Node client = null;


        String fileName1 = "/Users/greg/IdeaProjects/test6/src/client1.csv";
        String fileName2 = "/Users/greg/IdeaProjects/test6/src/taxis1.csv";
        String fileName3 = "/Users/greg/IdeaProjects/test6/src/nodes.csv";

        HashMap TaxiList = new HashMap<Node,String>();
        Node taxi;
        Graph graph = new Graph();

        try {
            FileReader fileReader1 =
                    new FileReader(fileName1);              //diavazoume tis syntetagmenes tou pelati kai tis
                                                            //kai tis apothikeuoyme se ena antikeimeno Node
            BufferedReader bufferedReader1 =
                    new BufferedReader(fileReader1);

            line = bufferedReader1.readLine();
            line = bufferedReader1.readLine();
            String[] S = line.split(",");
            client = new Node(S[0], S[1]);

            bufferedReader1.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName1 + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName1 + "'");
        }

       try {
            FileReader fileReader2 =                                //omoia diabazoume gia to kathe taxi kai ta
                                                                    //apothikeuoyme se ena hashmap me kleidi to node
                    new FileReader(fileName2);

            BufferedReader bufferedReader2 =
                    new BufferedReader(fileReader2);

            line = bufferedReader2.readLine();
            while ((line = bufferedReader2.readLine()) != null) {
                String[] S = line.split(",");
                taxi = new Node(S[0], S[1]);
                TaxiList.put(taxi, S[2]);
            }

            bufferedReader2.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName2 + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName2 + "'");
        }

        try {
            FileReader fileReader3 =                                    //diabazoume tis syntetagmenes kathe komvou
                                                                        //kai ton vazoume sto grafo
                    new FileReader(fileName3);

            BufferedReader bufferedReader3 =
                    new BufferedReader(fileReader3);

            bufferedReader3.readLine();

            line = bufferedReader3.readLine();
            String[] S = line.split(",");
            String currId = S[2];
            String prevId = currId;


            Node currNode = new Node(S[0], S[1]);
            graph.addVertex(currNode);
            Node prevNode = currNode;

            while ((line = bufferedReader3.readLine()) != null) {
                S = line.split(",");
                currNode = new Node(S[0], S[1]);
                Node res = graph.addVertex(currNode);
                currId = S[2];

                if (currId.equals(prevId)){
                    Node X = graph.getVertex(currNode.getX()+currNode.getY());
                    Node Y = graph.getVertex(prevNode.getX()+prevNode.getY());

                    X.addNeighbour(Y);
                    Y.addNeighbour(X);
                }

                prevId = currId;
                if (res != null) {
                    currNode = res;
                }

                prevNode = currNode;
            }

            bufferedReader3.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName3 + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName3 + "'");
        }

        client = graph.put(client);                      //sundeoume ton pelati me ton kontinotero tou komvo

        Iterator it = TaxiList.entrySet().iterator();

        double minDist = Math.pow(10,10);
        String minTaxi = null;

        while (it.hasNext())                        //sundeoume kathe taxi me ton kontinotero komvo
        {
            Map.Entry<Node,String> pair = (Map.Entry)it.next();
            Node key = pair.getKey();
            String value = pair.getValue();

            key = graph.put(key);
            System.out.println(value);
            double newDist = graph.printPath(key,client);   //gia kathe taxi tupwnoume tin diadromi kai kratame tin apostasi
                                                            //gia na vroume to kalytero
            System.out.println();
            if (newDist == -1) {
                System.out.println("Taxi " + value + " couldn't reach destination");
            }
            else {
                if (newDist < minDist) {
                    minDist = newDist;
                    minTaxi = value;
                }
            }


        }
        if (minDist != Math.pow(10,10)) {
            System.out.println();
            System.out.println("Closest taxi is " + minTaxi);
        }
    }
}