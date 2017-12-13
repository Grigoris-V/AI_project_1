/**
 * Created by greg on 18/11/16.
 */
public class Taxis extends Node{
    private String ID;

    public Taxis(String X, String Y, String Z){
        super(X, Y);
        ID = Z;
    }

    public void setID(String S) { ID = S; }

    public String getID(){
        return ID;
    }

    public void printNode() { System.out.println(super.getX() + " " +  super.getY()+ " " + ID);}

}
