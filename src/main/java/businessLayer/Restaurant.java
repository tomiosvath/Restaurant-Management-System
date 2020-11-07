/**
 * @author Osvath Tamas
 * Class for implementing the restaurant's functionalities
 */
package businessLayer;

import dataLayer.FileService;
import dataLayer.RestaurantSerializator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Restaurant implements IRestaurantProcessing, Serializable {
    private HashSet<MenuItem> menu;//= new HashSet<MenuItem>();
    private HashMap<Order, ArrayList<MenuItem>> orders;
    private RestaurantSerializator serializator;
    private int noBill = 1;
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public Restaurant(){
        orders = new HashMap<Order, ArrayList<MenuItem>>();
        serializator = new RestaurantSerializator();
        menu = serializator.deserializeMenu();
    }

    public MenuItem findByName(String name){
        MenuItem itemSearched = null;
        for (MenuItem item: menu){
            if (item.getName().equals(name))
                itemSearched = item;
        }
        return itemSearched;
    }

    public Order findByTable(int table){
        for (Order order: orders.keySet())
            if (order.getTable() == table)
                return order;
        return null;
    }

    @Override
    public void createItem(MenuItem item) {
        assert item != null;
        menu.add(item);
        assert wellDefined();
        serializator.serializeMenu(menu);
    }

    @Override
    public void deleteItem(MenuItem item) {
        assert item != null;
        menu.remove(item);
        assert wellDefined();
        serializator.serializeMenu(menu);
    }

    @Override
    public void editItem(MenuItem oldItem, MenuItem newItem) {
        assert oldItem != null;
        assert newItem != null;
        menu.remove(oldItem);
        menu.add(newItem);
        assert wellDefined();
        serializator.serializeMenu(menu);

    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItem> items) {
        assert order != null;
        assert items.size() != 0;
        orders.put(order, items);
        assert wellDefined();
        notifyAllObservers();
    }

    @Override
    public float computePriceOrder(Order order) {
        assert order != null;
        assert orders.containsKey(order);
        float price = 0;
        ArrayList<MenuItem> items;
        items = orders.get(order);
        for (MenuItem item: items)
            price += item.computePrice();
        assert price > 0;
        return price;
    }

    @Override
    public void generateBill(Order order) {
        ArrayList<MenuItem> items = orders.get(order);

        FileService.setOutputFile("Bill" + noBill + ".txt");
        FileService.write("Bill no " + noBill++ + "\n\n");
        FileService.write("Date " + order.getDate().toString());
        FileService.write("\nOrder no " + order.getOrderId() + "\nTable no " + order.getTable() + "\n\nOrdered products\n");

        float total = 0;
        for (MenuItem item: items) {
            FileService.write(item.getName() + " - " + item.computePrice() + " euro\n");
            total += item.computePrice();
        }
        FileService.write("\nTotal: " + total + " euro");
        FileService.closeFile();
    }

    /**
     * Well Defined method for the Restaurant Class.
     * If there are two orders with the same orderId, the test fails
     * @return true if Well Defined, false otherwise
     */
    private boolean wellDefined(){
        for (Order o1: orders.keySet()){
            for (Order o2: orders.keySet())
                if (!o1.equals(o2) && o1.getOrderId() == o2.getOrderId())
                    return false;
        }
        return true;
    }

    public HashSet<MenuItem> getMenu() {
        return menu;
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrders(){
        return orders;
    }

    public void attachObserver(Observer observer){
        observers.add(observer);
    }

    private void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update(orders);
        }
    }

    /*public static void main(String[] args){
        MenuItem item1 = new BaseProduct("apple", 2);
        MenuItem item2 = new BaseProduct("strawberry", (float)3.5);
        CompositeProduct item = new CompositeProduct("fruit salad");
        item.addBaseProduct(item1);
        item.addBaseProduct(item2);
        Restaurant r = new Restaurant();
        r.createItem(item);
        HashSet<MenuItem> menu = r.getMenu();
        for (MenuItem items: menu) {
            System.out.println(items.getName());
            //if (items instanceof CompositeProduct)
                //System.out.println(" composite");
        }

    }*/
}
