package businessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Observer {
    protected Restaurant restaurant;
    public abstract void update(HashMap<Order, ArrayList<MenuItem>> orders);
}
