package businessLayer;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct implements MenuItem {
    private String name;
    private List<MenuItem> items = new ArrayList<MenuItem>();
    private float price = 0;

    public CompositeProduct(String name){
        this.name = name;
    }

    public float computePrice(){
        price = 0;
        for (MenuItem p: items) {
            if (p instanceof BaseProduct)
                price += ((BaseProduct) p).computePrice();
        }
        return price;
    }

    public void addBaseProduct(MenuItem part) {
        this.items.add(part);
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 7;
        hash = prime * hash + ((items == null) ? 0 : items.hashCode());
        hash = prime * hash + ((name == null) ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass())
            return false;
        if (o == this)
            return true;

        CompositeProduct prod = (CompositeProduct) o;

        if (!items.equals(prod.items))
            return false;

        return name.equals(prod.name);
    }
}
