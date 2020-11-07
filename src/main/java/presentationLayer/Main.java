package presentationLayer;

import businessLayer.Restaurant;


public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        MainFrame frame = new MainFrame(restaurant);
    }
}
