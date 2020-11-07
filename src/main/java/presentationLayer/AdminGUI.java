package presentationLayer;

import businessLayer.*;
import businessLayer.MenuItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class AdminGUI extends JFrame implements ActionListener, IRestaurantProcessing {
    private JPanel panel = new JPanel();
    private Restaurant restaurant;

    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtName_1;
    private JButton btnAdd;//add base product
    private JButton btnDelete;//delete base product
    private JButton btnEdit; //edit base product
    private JButton btnAddComposite;
    private JButton btnDelete_1; //delete composite
    private JButton btnShow;



    private LinkedList<JCheckBox> checkboxList;
    private JPanel scrollContainer;
    private JScrollPane checkBoxScrollPane;


    public AdminGUI(Restaurant restaurant){
        this.restaurant = restaurant;

        setTitle("Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1012, 549);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        checkBox();

        JLabel lblMenu = new JLabel("Products");
        lblMenu.setBounds(800, 10, 100, 20);
        panel.add(lblMenu);

        JLabel lblAddBaseProduct = new JLabel("Add Base Product");
        lblAddBaseProduct.setBounds(20, 34, 123, 25);
        panel.add(lblAddBaseProduct);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(20, 85, 56, 16);
        panel.add(lblName);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(20, 126, 56, 16);
        panel.add(lblPrice);

        txtName = new JTextField();
        txtName.setBounds(73, 82, 116, 22);
        panel.add(txtName);
        txtName.setColumns(10);

        txtPrice = new JTextField();
        txtPrice.setBounds(73, 123, 116, 22);
        panel.add(txtPrice);
        txtPrice.setColumns(10);

        btnAdd = new JButton("Add ");
        btnAdd.setBounds(20, 172, 97, 25);
        panel.add(btnAdd);

        JLabel lblAddCompositeProduct = new JLabel("Add Composite Product");
        lblAddCompositeProduct.setBounds(20, 252, 166, 30);
        panel.add(lblAddCompositeProduct);

        txtName_1 = new JTextField();
        txtName_1.setBounds(73, 309, 116, 22);
        panel.add(txtName_1);
        txtName_1.setColumns(10);

        JLabel lblName_1 = new JLabel("Name");
        lblName_1.setBounds(20, 312, 56, 16);
        panel.add(lblName_1);


        btnShow = new JButton("Show Menu");
        btnShow.setBounds(400, 172, 130, 25);
        panel.add(btnShow);

        btnDelete_1 = new JButton("Delete Composite");
        btnDelete_1.setBounds(200, 383, 150, 25);
        panel.add(btnDelete_1);

        btnAddComposite = new JButton("Add Composite");
        btnAddComposite.setBounds(20, 383, 150, 25);
        panel.add(btnAddComposite);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(151, 172, 97, 25);
        panel.add(btnDelete);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(270, 172, 97, 25);
        panel.add(btnEdit);


        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnEdit.addActionListener(this);
        btnAddComposite.addActionListener(this);
        btnDelete_1.addActionListener(this);
        btnShow.addActionListener(this);
    }

    private void table(){
        String[] column = {"Name", "Price"};
        String[][] data = new String[restaurant.getMenu().size()][2];
        JTable table = new JTable(data, column);

        table.setBounds(100, 100, 900, 500);

        int line = 0;
        for (MenuItem item: restaurant.getMenu()) {
            data[line][0] = item.getName();
            data[line][1] = String.valueOf(item.computePrice());
            line++;
        }

        JFrame frame1 = new JFrame();
        JScrollPane scrollPane = new JScrollPane(table);

        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setBounds(100, 100, 700, 500);

        frame1.add(scrollPane);

        frame1.setVisible(true);
    }

    private void checkBox(){
        checkboxList = new LinkedList<>();

        for (MenuItem items: restaurant.getMenu()) {
            if (items instanceof BaseProduct)
                checkboxList.add(new JCheckBox(items.getName()));
        }
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

    public void frameSetVisible(boolean visible) {
        this.setVisible(visible);
    }

    @Override
    public void createItem(MenuItem item) {
        String itemName = txtName.getText();
        try{
            float itemPrice = Float.parseFloat(txtPrice.getText());
            if (itemPrice > 0){
                restaurant.createItem(new BaseProduct(itemName, itemPrice));
                JOptionPane.showMessageDialog(null, "Item creation finished successfully!");
                panel.remove(checkBoxScrollPane);
                checkBox();
                repaint();
                revalidate();
            }
            else JOptionPane.showMessageDialog(null, "ERROR (creating menu item)");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Item creation failed!");
        }
    }

    @Override
    public void deleteItem(MenuItem item) {
        item = restaurant.findByName(txtName.getText());
        if (item != null){
            restaurant.deleteItem(item);
            JOptionPane.showMessageDialog(null, "Item deleted successfully!");
            panel.remove(checkBoxScrollPane);
            checkBox();
            repaint();
            revalidate();
        }
        else
            JOptionPane.showMessageDialog(null, "Error deleting item");
    }

    @Override
    public void editItem(MenuItem oldItem, MenuItem newItem) {
        MenuItem old = restaurant.findByName(txtName.getText());
        float price = Float.parseFloat(txtPrice.getText());
        if (old != null && price > 0){
           MenuItem itemNew = new BaseProduct(txtName.getText(), price);
           restaurant.editItem(old, itemNew);
           JOptionPane.showMessageDialog(null, "Item updated successfully!");
           panel.remove(checkBoxScrollPane);
           checkBox();
           repaint();
           revalidate();
        }
        else
            JOptionPane.showMessageDialog(null, "Error on updating!");
    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItem> items) {
        JOptionPane.showMessageDialog(null, "Admin can't create order");
    }

    @Override
    public float computePriceOrder(Order order) {
        JOptionPane.showMessageDialog(null, "Admin can't create order");
        return 0;
    }

    @Override
    public void generateBill(Order order) {
        JOptionPane.showMessageDialog(null, "Admin can't create order");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnAdd) {
            createItem(null);
        }
        if (source == btnDelete){
            deleteItem(null);
        }
        if (source == btnEdit){
            editItem(null, null);
        }
        if (source == btnAddComposite) {
            CompositeProduct item = new CompositeProduct(txtName_1.getText());

            for (JCheckBox b : checkboxList) {
                if (b.isSelected()) {
                    MenuItem selectedItem = restaurant.findByName(b.getText());
                    if (selectedItem instanceof CompositeProduct)
                        JOptionPane.showMessageDialog(null, "Only Base products are taken into consideration");
                    else
                        item.addBaseProduct(selectedItem);
                }
            }

            restaurant.createItem(item);
            panel.remove(checkBoxScrollPane);
            checkBox();
            repaint();
            revalidate();
            JOptionPane.showMessageDialog(null, "Composite creation successful");
        }
        if (source == btnDelete_1){
            MenuItem item = restaurant.findByName(txtName_1.getText());
            if (item != null){
                restaurant.deleteItem(item);
                JOptionPane.showMessageDialog(null, "Item deleted successfully!");
                panel.remove(checkBoxScrollPane);
                checkBox();
                repaint();
                revalidate();
            }
            else
                JOptionPane.showMessageDialog(null, "Error deleting item");
        }
        if (source == btnShow){
            table();
        }
    }
}
