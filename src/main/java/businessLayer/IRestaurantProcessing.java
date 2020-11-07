/**
 * @author Osvath Tamas
 * Public Interface for the Restaurant Management system
 */
package businessLayer;

import java.util.ArrayList;

public interface IRestaurantProcessing {
    /**
     * @pre item != null
     * @param item -the item to be created
     */
    void createItem(MenuItem item);

    /**
     * @pre item != null
     * @param item -the item to be deleted
     */
    void deleteItem(MenuItem item);

    /**
     * @pre oldItem != null
     * @pre newItem != null
     *
     * @param oldItem - old item
     * @param newItem - new item
     */
    void editItem(MenuItem oldItem, MenuItem newItem);

    /**
     * @pre order  != null
     * @pre items.size() != 0
     *
     * @param order -the order
     * @param items -the items
     */
    void createOrder(Order order, ArrayList<MenuItem> items);

    /**
     * @pre order != null
     * @pre order is in the orders list
     * @post price > 0
     * @param order - the order for which the price is computed
     * @return the price
     */
    float computePriceOrder(Order order);

    /**
     * @pre order != null
     * @param order - the order for which the bill is generated
     */
    void generateBill(Order order);
}
