package businessLayer;

import java.util.Date;

public class Order {
    private int orderId, table;
    private Date date;

    public Order(int orderId, int table){
        this.orderId = orderId;
        this.table = table;
        this.date = new Date();
    }

    public int hashCode(){
        final int prime = 31;
        int hash = 7;
        hash = prime * hash + orderId;
        hash = prime * hash + table;
        hash = prime * hash + ((date == null) ? 0 : date.hashCode());
        return hash;
    }

    public boolean equals(Object o){
        if (this.getClass() != o.getClass())
            return false;
        if (o == this)
            return true;

        Order order = (Order) o;

        return this.orderId == order.orderId && this.table == order.table
                && this.date.compareTo(order.date) == 0;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTable() {
        return table;
    }

    public Date getDate() {
        return date;
    }


    /*public static void main(String[] args){
        Date date = new Date();
        //Order o1 = new Order(3, 5, date);
        //System.out.println(o1.hashCode());
        //Order o2 = new Order(1, 5, date);
        //System.out.println(o1.equals(o2));
    }*/
}
