package presentationLayer;

import businessLayer.MenuItem;
import businessLayer.Observer;
import businessLayer.Order;
import businessLayer.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.HashMap;

public class ChefGUI extends Observer {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    private JTextArea txtShow = new JTextArea();


    public ChefGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        frame.setTitle("Chef");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 700, 700);

        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(panel);
        panel.setLayout(null);

        txtShow.setBounds(40, 20, 600, 600);
        txtShow.setEditable(false);
        panel.add(txtShow);

        JLabel lblMonitor = new JLabel("Chef's Monitor");
        lblMonitor.setBounds(300, 0, 100, 20);
        panel.add(lblMonitor);

    }

    public void frameSetVisible(boolean visible) {
        frame.setVisible(visible);
    }


    public void update(HashMap<Order, ArrayList<MenuItem>> orders) {
        StringBuilder text = new StringBuilder();
        for (Order order: orders.keySet()){
            ArrayList<MenuItem> items = orders.get(order);
            text.append("Order no.").append(order.getOrderId()).append(" table no.").append(order.getTable());
            text.append("\nProducts: ");
            for (MenuItem item: items)
                text.append(item.getName()).append(", ");
            text.append("\n \n");
        }
        txtShow.setText(text.toString());
    }

}
