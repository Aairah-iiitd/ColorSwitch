package sample;

import java.io.*;

public class TotalStars implements Serializable {

    private int total;

    TotalStars(){
        total = 0;
    }

    public void addstar(int a){
        total += a;
    }

    public int getTotal() {
        return total;
    }

    public void saveStars() throws IOException {
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("stars.txt"));
            out.writeObject(this);
        }finally {
            out.close();
        }
    }

    public static TotalStars getStars() throws IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        TotalStars a;
        try{
            in = new ObjectInputStream(new FileInputStream("stars.txt"));
            a = (TotalStars) in.readObject();
        }finally {
            in.close();
        }
        return a;
    }
}
