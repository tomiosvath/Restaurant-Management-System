package presentationLayer;

import businessLayer.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener{
    private JPanel panel = new JPanel();
    private JButton btnAdmin = new JButton("Admin");
    private JButton btnWaiter = new JButton("Waiter");
    private JButton btnChef = new JButton("Chef");
    private AdminGUI adminGUI;
    private WaiterGUI waiterGUI;
    private ChefGUI chefGUI;
    private Restaurant restaurant;


    public MainFrame(Restaurant restaurant){
        this.restaurant = restaurant;
        adminGUI = new AdminGUI(restaurant);
        waiterGUI = new WaiterGUI(restaurant);
        chefGUI = new ChefGUI(restaurant);
        restaurant.attachObserver(chefGUI);

        setTitle("Restaurant Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        addButtons();

        this.setVisible(true);
    }


    private void addButtons(){
        btnAdmin.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnAdmin.setBounds(140, 50, 190, 90);
        panel.add(btnAdmin);
        btnAdmin.addActionListener(this);

        btnWaiter.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnWaiter.setBounds(140, 170, 190, 90);
        panel.add(btnWaiter);
        btnWaiter.addActionListener(this);

        btnChef.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnChef.setBounds(140, 290, 190, 90);
        panel.add(btnChef);
        btnChef.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == btnAdmin) {adminGUI.frameSetVisible(true);}
        else if(source == btnWaiter) {waiterGUI.frameSetVisible(true);}
        else if(source == btnChef) {chefGUI.frameSetVisible(true);}
    }
}
