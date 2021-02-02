package sample;
import java.io.*;
public class Serialise {
    public static void serialise(Player p) throws IOException {
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("out.txt"));
            out.writeObject(p);
        }finally {
            out.close();
        }
    }

    public static Player deserialise() throws IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream("out.txt"));
            Player p = (Player) in.readObject();
            return p;
        }finally {
            in.close();
        }
    }
}