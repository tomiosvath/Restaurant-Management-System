package businessLayer;

public class BaseProduct implements MenuItem {
    private String name;
    private float price;

    public BaseProduct(String name, float price){
        this.name = name;
        this.price = price;
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

    public float computePrice(){
        return price;
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 7;
        hash = prime * hash + ((name == null) ? 0 : name.hashCode());
        hash = prime * hash + (int) price;
        return hash;
    }

    public boolean equals(Object o){
        if (this.getClass() != o.getClass())
            return false;
        if (o == this)
            return true;

        BaseProduct prod = (BaseProduct) o;

        if (!this.name.equals(prod.name))
            return false;

        return this.price == prod.price;
    }

    public float getPrice() {
        return price;
    }
}
