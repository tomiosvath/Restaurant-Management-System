package businessLayer;

import java.io.Serializable;

public interface MenuItem extends Serializable {
    float computePrice();
    String getName();
    void setName(String name);
    void setPrice(float price);
}
