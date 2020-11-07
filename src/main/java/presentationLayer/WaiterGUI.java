package presentationLayer;

import businessLayer.IRestaurantProcessing;
import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class WaiterGUI extends JFrame implements ActionListener, IRestaurantProcessing {
    private Restaurant restaurant;
    private int orderId = 1;

    private JPanel panel = new JPanel();

    private JTextField txtTable;
    private JButton btnAdd;
    private JButton btnPrice;
    private JButton btnBill;
    private JButton btnShow;

    private LinkedList<JCheckBox> checkboxList, checkboxList2;
    private JPanel scrollContainer, scrollContainer2;
    private JScrollPane checkBoxScrollPane, checkBoxScrollPane2;

    public WaiterGUI(Restaurant restaurant){
        this.restaurant = restaurant;

        setTitle("Waiter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1012, 549);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        checkBox();
        checkBox2();

        JLabel lblCreateOrder = new JLabel("Create Order (Select items)");
        lblCreateOrder.setBounds(20, 34, 200, 25);
        panel.add(lblCreateOrder);

        JLabel lblMenu = new JLabel("MENU");
        lblMenu.setBounds(800, 10, 100, 20);
        panel.add(lblMenu);

        JLabel lblOrders = new JLabel("Orders(displaying table's number)");
        lblOrders.setBounds(350, 10, 200, 20);
        panel.add(lblOrders);

        JLabel lblTable = new JLabel("Table");
        lblTable.setBounds(20, 85, 56, 16);
        panel.add(lblTable);

        txtTable = new JTextField();
        txtTable.setBounds(73, 82, 116, 22);
        panel.add(txtTable);

        btnAdd = new JButton("Add Order");
        btnAdd.setBounds(20, 120, 97, 25);
        panel.add(btnAdd);

        btnShow = new JButton("Show Orders");
        btnShow.setBounds(20, 200, 130, 25);
        panel.add(btnShow);

        btnPrice = new JButton("Calculate Price");
        btnPrice.setBounds(130, 120, 135, 25);
        panel.add(btnPrice);

        btnBill = new JButton("Generate Bill");
        btnBill.setBounds(20, 160, 135, 25);
        panel.add(btnBill);

        btnShow.addActionListener(this);
        btnBill.addActionListener(this);
        btnAdd.addActionListener(this);
        btnPrice.addActionListener(this);
    }

    public void frameSetVisible(boolean visible) {
        this.setVisible(visible);
        update();
    }

    private void table(){
        String[] column = {"OrderId", "TableID", "Date"};
        String[][] data = new String[restaurant.getOrders().size()][3];
        JTable table = new JTable(data, column);

        table.setBounds(100, 100, 900, 500);

        int line = 0;
        for (Order o: restaurant.getOrders().keySet()) {
            data[line][0] = String.valueOf(o.getOrderId());
            data[line][1] = String.valueOf(o.getTable());
            data[line][2] = String.valueOf(o.getDate());
            line++;
        }

        JFrame frame1 = new JFrame();
        JScrollPane scrollPane = new JScrollPane(table);

        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setBounds(100, 100, 700, 500);

        frame1.add(scrollPane);

        frame1.setVisible(true);
    }

    private void update(){
        panel.remove(checkBoxScrollPane);
        panel.remove(checkBoxScrollPane2);
        checkBox();
        checkBox2();
        repaint();
        revalidate();
    }


    private void checkBox(){
        checkboxList = new LinkedList<>();

        for (MenuItem items: restaurant.getMenu())
            checkboxList.add(new JCheckBox(items.getName()));

        scrollContainer = new JPanel();
        scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));

        for (JCheckBox c: checkboxList)
            scrollContainer.add(c);

        checkBoxScrollPane = new JScrollPane(scrollContainer);
        checkBoxScrollPane.setBounds(638, 35, 338, 393);
        checkBoxScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        checkBoxScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        checkBoxScrollPane.setPreferredSize(new Dimension(0, 300));

        panel.add(checkBoxScrollPane);
    }

    private void checkBox2(){
        checkboxList2 = new LinkedList<>();

        for (Order order: restaurant.getOrders().keySet())
            checkboxList2.add(new JCheckBox(String.valueOf(order.getTable())));

        scrollContainer2 = new JPanel();
        scrollContainer2.setLayout(new BoxLayout(scrollContainer2, BoxLayout.Y_AXIS));

        for (JCheckBox c: checkboxList2)
            scrollContainer2.add(c);

        checkBoxScrollPane2 = new JScrollPane(scrollContainer2);
        checkBoxScrollPane2.setBounds(300, 35, 300, 393);
        checkBoxScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        checkBoxScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        checkBoxScrollPane2.setPreferredSize(new Dimension(0, 300));

        panel.add(checkBoxScrollPane2);
    }

    @Override
    public void createItem(MenuItem item) {
        JOptionPane.showMessageDialog(null, "Waiter has no permission");
    }

    @Override
    public void deleteItem(MenuItem item) {
        JOptionPane.showMessageDialog(null, "Waiter has no permission");
    }

    @Override
    public void editItem(MenuItem oldItem, MenuItem newItem) {
        JOptionPane.showMessageDialog(null, "Waiter has no permission");
    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItem> items) {
        ArrayList<MenuItem> itemList = new ArrayList<MenuItem>();
        int tableId = -1;

        try{
            tableId = Integer.parseInt(txtTable.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Not a number");
            ex.printStackTrace();
        }

        if (tableId > 0) {
            Order newOrder = new Order(orderId++, tableId);
            for (JCheckBox b : checkboxList) {
                if (b.isSelected()) {
                    MenuItem selectedItem = restaurant.findByName(b.getText());
                    itemList.add(selectedItem);
                }
            }

            if (!itemList.isEmpty()) {
                restaurant.createOrder(newOrder, itemList);
            }
            else
                JOptionPane.showMessageDialog(null, "No items selected");
        }
        else
            JOptionPane.showMessageDialog(null, "Order can't be created");

        update();
    }

    @Override
    public float computePriceOrder(Order order) {
        for (JCheckBox b: checkboxList2)
            if (b.isSelected()){
                int table = Integer.parseInt(b.getText());
                order = restaurant.findByTable(table);
                float price = restaurant.computePriceOrder(order);
                JOptionPane.showMessageDialog(null, "Order price is: " + price + " euro");
                break;
            }
        return 0;
    }

    @Override
    public void generateBill(Order order){
        for (JCheckBox b: checkboxList2)
            if (b.isSelected()){
                int table = Integer.parseInt(b.getText());
                order = restaurant.findByTable(table);
                restaurant.generateBill(order);
                JOptionPane.showMessageDialog(null, "Bill generated");
                break;
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnAdd){
            createOrder(null, null);
        }
        if (source == btnPrice){
            computePriceOrder(null);
        }
        if (source == btnBill){
            generateBill(null);
        }
        if (source == btnShow) {
            table();
        }
    }
}
