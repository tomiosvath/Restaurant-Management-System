package dataLayer;

import businessLayer.MenuItem;

import java.io.*;
import java.util.HashSet;

public class RestaurantSerializator {
    private final String file = "restaurant.ser";

    public void serializeMenu(HashSet <MenuItem> menu){
        try{
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(menu);
            out.close();
            fileOut.close();
            //System.out.println("Serialized data is saved in /restaurant.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<MenuItem> deserializeMenu(){
        HashSet<MenuItem> menu = null;
        try{
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            menu = (HashSet<MenuItem>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return menu;
    }
}
