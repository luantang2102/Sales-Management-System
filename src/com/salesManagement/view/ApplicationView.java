package com.salesManagement.view;

import com.salesManagement.controller.ServiceController;
import com.salesManagement.model.Customer;
import com.salesManagement.model.Order;
import com.salesManagement.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ApplicationView extends JFrame {
    public JFrame userFrame;
    public JLabel userNameLabel;
    public JLabel userPassLabel;
    public JLabel userPassSecLabel;
    public JTextField userNameTextField;
    public JPasswordField userPassTextField;
    public JPasswordField userPassSecTextField;

    public JButton loginButton;
    public JButton registerButton;

    public JPanel root;
    public JTable table;
    public JComboBox boxTables;
    public JButton viewButton;
    public JButton insertButton;
    public JTextField filterTextField;
    public JComboBox boxColumns;
    public JButton filteringButton;
    public JComboBox boxYears;
    public JComboBox boxMonths;
    public JComboBox boxDays;
    public JRadioButton detailRButton;
    public JRadioButton overViewRButton;
    public JLabel grandTotalLabel;
    public JLabel grandTotal;;
    public JButton deleteButton;
    public JButton updateButton;
    private JMenu file;
    private JMenu about;
    public JMenuItem importFile;
    public JMenuItem logOut;
    public JLabel facebook;

    public JFrame insertFrame;
    public JPanel jPanelIO;
    public JButton addButton;
    public JFrame updateFrame;
    public JButton saveButton;

    //Customer components
    public JLabel customerIdLabel;
    public JLabel customerIdText;
    public JLabel customerNameLabel;
    public JLabel customerPhoneNumLabel;
    public JTextField customerNameTextField;
    public JTextField phoneNumTextField;
    //Product components
    public JLabel productIdLabel;
    public JLabel productIdText;
    public JLabel productNameLabel;
    public JLabel productPriceLabel;
    public JTextField productNameTextField;
    public JTextField productPriceTextField;
    //Order components
    public JLabel orderIdLabel;
    public JLabel orderIdText;
    public JLabel amountLabel;
    public JLabel customerLabel;
    public JLabel productLabel;
    public JTextField amountTextField;
    public JComboBox boxCustomers;
    public JComboBox boxProducts;

    public List<char[]> selectedIds;

    public void createUserFrame() {
        userFrame = new JFrame("Account Login");
        userFrame.setSize(new Dimension(240, 100));
        userFrame.setLocationRelativeTo(null);
        userFrame.setLayout(new BorderLayout());
        JButton signUpButton = new JButton("Sign Up");
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> {
            userFrame.dispose();
            createSignInFrame();
        });
        signUpButton.addActionListener(e -> {
            userFrame.dispose();
            createSignUpFrame();
        });
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(2, 1));
        jPanelIO.add(signInButton);
        jPanelIO.add(signUpButton);
        userFrame.add(jPanelIO, BorderLayout.CENTER);
        userFrame.setVisible(true);
        root.setVisible(false);
        userFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createSignInFrame() {
        userFrame = new JFrame("Sign In");
        userFrame.setSize(new Dimension(220, 220));
        userFrame.setLocationRelativeTo(null);
        userFrame.setLayout(new BorderLayout());
        userNameLabel = new JLabel("User Name:");
        userPassLabel = new JLabel("Password: ");

        userNameTextField = new JTextField(50);
        userPassTextField = new JPasswordField(50);
        loginButton = new JButton("Login");
        ActionListener actionListener = new ServiceController(this);
        loginButton.addActionListener(actionListener);
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(5, 1));
        jPanelIO.add(userNameLabel);
        jPanelIO.add(userNameTextField);
        jPanelIO.add(userPassLabel);
        jPanelIO.add(userPassTextField);
        jPanelIO.add(loginButton);
        userFrame.add(jPanelIO, BorderLayout.CENTER);
        userFrame.setVisible(true);
        root.setVisible(false);
        userFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createSignUpFrame() {
        userFrame = new JFrame("Sign Up");
        userFrame.setSize(new Dimension(220, 250));
        userFrame.setLocationRelativeTo(null);
        userFrame.setLayout(new BorderLayout());
        userNameLabel = new JLabel("User Name:");
        userPassLabel = new JLabel("Password: ");
        userPassSecLabel = new JLabel("Confirm Password: ");
        userNameTextField = new JTextField("");
        userPassTextField = new JPasswordField("");
        userPassSecTextField = new JPasswordField("");
        registerButton = new JButton("Register");
        ActionListener actionListener = new ServiceController(this);
        registerButton.addActionListener(actionListener);
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(7, 3));
        jPanelIO.add(userNameLabel);
        jPanelIO.add(userNameTextField);
        jPanelIO.add(userPassLabel);
        jPanelIO.add(userPassTextField);
        jPanelIO.add(userPassSecLabel);
        jPanelIO.add(userPassSecTextField);
        jPanelIO.add(registerButton);
        userFrame.add(jPanelIO, BorderLayout.CENTER);
        userFrame.setVisible(true);
        root.setVisible(false);
        userFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createDialog(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }
    public int createYesNoDialog(String message) {
        return JOptionPane.showConfirmDialog(root,message, "Delete" + " " + boxTables.getSelectedItem(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }
    public void createCustomersTable(List<Customer> customers) {
        if(customers.isEmpty()) {
            createDialog("Customer not found");
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Customer Id");
        model.addColumn("Name");
        model.addColumn("Phone Num");

        int no = 1;
        for(Customer customer : customers) {
            model.addRow(new Object[]{String.valueOf(no++), String.valueOf(customer.getCustomerId()), String.valueOf(customer.getName()), String.valueOf(customer.getPhoneNum())});
        }
        table.setModel(model);
        table.getColumnModel().getColumn(0).setMaxWidth(27);
        table.setVisible(true);
    }
    public void createProductsTable(List<Product> products) {
        if(products.isEmpty()) {
            createDialog("Product not found");
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Product Id");
        model.addColumn("Name");
        model.addColumn("Price");

        int no = 1;
        for(Product product : products) {
            model.addRow(new Object[]{String.valueOf(no++), String.valueOf(product.getProductId()), String.valueOf(product.getName()), String.valueOf(product.getPrice())});
        }

        table.setModel(model);
        table.getColumnModel().getColumn(0).setMaxWidth(27);
        table.setVisible(true);
    }
    public void createOrdersTable(List<Order> orders) {
        if(orders.isEmpty()) {
            createDialog("Order not found");
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Order Id");
        model.addColumn("Order Date");
        model.addColumn("Customer Name");
        model.addColumn("Product Name");
        model.addColumn("Quantity");

        int no = 1;
        for(Order order : orders) {
            model.addRow(new Object[]{no++, String.valueOf(order.getOrderId()), String.valueOf(order.getOrderDate()), String.valueOf(order.getCustomer().getName()), String.valueOf(order.getProduct().getName()), order.getAmount()});
        }

        table.setModel(model);
        table.getColumnModel().getColumn(0).setMaxWidth(27);
        table.setVisible(true);
    }
    public void createOrderOverViewTable(List<List<String>> overViewList) {
        DefaultTableModel model = new DefaultTableModel();
        if(boxColumns.getSelectedItem().equals("Customer")){
            model.addColumn("No.");
            model.addColumn("Order Date");
            model.addColumn("Customer Name");
            model.addColumn("Total");
            int no = 1;
            double grandTotal = 0;
            for(List<String> data : overViewList) {
                grandTotal+=Double.parseDouble(data.get(2));
                model.addRow(new Object[]{no++, data.get(0), data.get(1), data.get(2)});
            }
            this.grandTotal.setText(String.valueOf(grandTotal));
        }
        else if(boxColumns.getSelectedItem().equals("Product")) {
            model.addColumn("No.");
            model.addColumn("Order Date");
            model.addColumn("Product Name");
            model.addColumn("Quantity");
            model.addColumn("Total");
            int no = 1;
            double grandTotal = 0;
            for(List<String> data : overViewList) {
                grandTotal+=Double.parseDouble(data.get(3));
                model.addRow(new Object[]{no++, data.get(0), data.get(1), data.get(2), data.get(3)});
            }
            this.grandTotal.setText(String.valueOf(grandTotal));
        }
        table.setModel(model);
        table.getColumnModel().getColumn(0).setMaxWidth(27);
        table.setVisible(true);

    }
    public void customerInsertionFrame() {
        insertFrame = new JFrame("Insert customer");
        insertFrame.setSize(new Dimension(500, 150));
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setLayout(new BorderLayout());
        //Create customer insertion frame
        customerNameLabel = new JLabel("Name: ");
        customerPhoneNumLabel = new JLabel("Phone number: ");
        customerNameTextField = new JTextField(50);
        phoneNumTextField = new JTextField(50);
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(3, 2));
        jPanelIO.add(customerNameLabel);
        jPanelIO.add(customerNameTextField);
        jPanelIO.add(customerPhoneNumLabel);
        jPanelIO.add(phoneNumTextField);
        insertFrame.add(jPanelIO, BorderLayout.CENTER);
        ActionListener actionListener = new ServiceController(this);
        addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        insertFrame.add(addButton, BorderLayout.SOUTH);
        insertFrame.setVisible(true);
        insertFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowAdapter windowAdapter = new ServiceController(this);
        insertFrame.addWindowListener(windowAdapter);
    }
    public void productInsertionFrame() {
        insertFrame = new JFrame("Insert product");
        insertFrame.setSize(new Dimension(500, 150));
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setLayout(new BorderLayout());
        productNameLabel = new JLabel("Name: ");
        productPriceLabel = new JLabel("Price: ");
        productNameTextField = new JTextField(50);
        productPriceTextField = new JTextField(50);
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(3, 2));
        jPanelIO.add(productNameLabel);
        jPanelIO.add(productNameTextField);
        jPanelIO.add(productPriceLabel);
        jPanelIO.add(productPriceTextField);
        insertFrame.add(jPanelIO, BorderLayout.CENTER);
        ActionListener actionListener = new ServiceController(this);
        addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        insertFrame.add(addButton, BorderLayout.SOUTH);
        insertFrame.setVisible(true);
        insertFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowAdapter windowAdapter = new ServiceController(this);
        insertFrame.addWindowListener(windowAdapter);
    }
    public void orderInsertionFrame(List<Customer> customers, List<Product> products)   {
        insertFrame = new JFrame("Insert order");
        insertFrame.setSize(new Dimension(500, 150));
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setLayout(new BorderLayout());

        amountLabel = new JLabel("Amount: ");
        customerLabel = new JLabel("Customer name: ");
        productLabel = new JLabel("Product name: ");

        amountTextField = new JTextField(50);
        boxCustomers = new JComboBox();
        for(Customer customer : customers){
            boxCustomers.addItem(String.valueOf(customer.getName()));
        }
        boxCustomers.setSelectedIndex(-1);
        boxProducts = new JComboBox();
        for(Product product : products) {
            boxProducts.addItem(String.valueOf(product.getName()));
        }
        boxProducts.setSelectedIndex(-1);

        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(4, 2));
        jPanelIO.add(customerLabel);
        jPanelIO.add(boxCustomers);
        jPanelIO.add(productLabel);
        jPanelIO.add(boxProducts);
        jPanelIO.add(amountLabel);
        jPanelIO.add(amountTextField);
        insertFrame.add(jPanelIO, BorderLayout.CENTER);
        ActionListener actionListener = new ServiceController(this);
        addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        insertFrame.add(addButton, BorderLayout.SOUTH);
        insertFrame.setVisible(true);
        insertFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowAdapter windowAdapter = new ServiceController(this);
        insertFrame.addWindowListener(windowAdapter);
    }
    public void customerUpdateFrame(Customer customer) {
        updateFrame = new JFrame("Update Customer");
        updateFrame.setSize(new Dimension(500, 150));
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setLayout(new BorderLayout());
        customerIdLabel = new JLabel("Customer ID: ");
        customerIdText = new JLabel(String.valueOf(customer.getCustomerId()));
        customerNameLabel = new JLabel("Name: ");
        customerPhoneNumLabel = new JLabel("Phone number: ");
        customerNameTextField = new JTextField(50);
        customerNameTextField.setText(String.valueOf(customer.getName()));
        phoneNumTextField = new JTextField(50);
        phoneNumTextField.setText(String.valueOf(customer.getPhoneNum()));
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(3, 2));
        jPanelIO.add(customerIdLabel);
        jPanelIO.add(customerIdText);
        jPanelIO.add(customerNameLabel);
        jPanelIO.add(customerNameTextField);
        jPanelIO.add(customerPhoneNumLabel);
        jPanelIO.add(phoneNumTextField);
        updateFrame.add(jPanelIO, BorderLayout.CENTER);
        ActionListener actionListener = new ServiceController(this);
        saveButton = new JButton("Save");
        saveButton.addActionListener(actionListener);
        updateFrame.add(saveButton, BorderLayout.SOUTH);
        updateFrame.setVisible(true);
        updateFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowAdapter windowAdapter = new ServiceController(this);
        updateFrame.addWindowListener(windowAdapter);
    }
    public void productUpdateFrame(Product product) {
        updateFrame = new JFrame("Update Product");
        updateFrame.setSize(new Dimension(500, 150));
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setLayout(new BorderLayout());
        productIdLabel = new JLabel("Product ID: ");
        productIdText = new JLabel(String.valueOf(product.getProductId()));
        productNameLabel = new JLabel("Name: ");
        productPriceLabel = new JLabel("Price: ");
        productNameTextField = new JTextField(50);
        productNameTextField.setText(String.valueOf(product.getName()));
        productPriceTextField = new JTextField(50);
        productPriceTextField.setText(String.valueOf(product.getPrice()));
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(3, 2));
        jPanelIO.add(productIdLabel);
        jPanelIO.add(productIdText);
        jPanelIO.add(productNameLabel);
        jPanelIO.add(productNameTextField);
        jPanelIO.add(productPriceLabel);
        jPanelIO.add(productPriceTextField);
        updateFrame.add(jPanelIO, BorderLayout.CENTER);
        ActionListener actionListener = new ServiceController(this);
        saveButton = new JButton("Save");
        saveButton.addActionListener(actionListener);
        updateFrame.add(saveButton, BorderLayout.SOUTH);
        updateFrame.setVisible(true);
        updateFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowAdapter windowAdapter = new ServiceController(this);
        updateFrame.addWindowListener(windowAdapter);
    }
    public void orderUpdateFrame(Order order, List<Customer> customers, List<Product> products) {
        updateFrame = new JFrame("Update Product");
        updateFrame.setSize(new Dimension(500, 150));
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setLayout(new BorderLayout());
        orderIdLabel = new JLabel("Order ID: ");
        orderIdText = new JLabel(String.valueOf(order.getOrderId()));
        amountLabel = new JLabel("Amount: ");
        customerLabel = new JLabel("Customer name: ");
        productLabel = new JLabel("Product name: ");
        amountTextField = new JTextField(50);
        amountTextField.setText(String.valueOf(order.getAmount()));
        boxCustomers = new JComboBox();
        int flag = 0;
        for(Customer customer : customers){
            boxCustomers.addItem(String.valueOf(customer.getName()));
            if(String.valueOf(customer.getCustomerId()).equals(String.valueOf(order.getCustomer().getCustomerId()))) {
                boxCustomers.setSelectedIndex(flag);
            }
            flag++;
        }
        boxProducts = new JComboBox();
        flag = 0;
        for(Product product : products) {
            boxProducts.addItem(String.valueOf(product.getName()));
            if(String.valueOf(product.getProductId()).equals(String.valueOf(order.getProduct().getProductId()))) {
                boxProducts.setSelectedIndex(flag);
            }
            flag++;
        }
        jPanelIO = new JPanel();
        jPanelIO.setLayout(new GridLayout(4, 2));
        jPanelIO.add(orderIdLabel);
        jPanelIO.add(orderIdText);
        jPanelIO.add(customerLabel);
        jPanelIO.add(boxCustomers);
        jPanelIO.add(productLabel);
        jPanelIO.add(boxProducts);
        jPanelIO.add(amountLabel);
        jPanelIO.add(amountTextField);
        updateFrame.add(jPanelIO, BorderLayout.CENTER);
        ActionListener actionListener = new ServiceController(this);
        saveButton = new JButton("Save");
        saveButton.addActionListener(actionListener);
        updateFrame.add(saveButton, BorderLayout.SOUTH);
        updateFrame.setVisible(true);
        updateFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        WindowAdapter windowAdapter = new ServiceController(this);
        updateFrame.addWindowListener(windowAdapter);
    }
    public void changeBoxColumns(String tableName) {
        if(tableName.equals("Customers")) {
            boxColumns.removeAllItems();
            boxColumns.addItem("Customer ID");
            boxColumns.addItem("Name");
            boxColumns.addItem("Phone number");
            detailRButton.setVisible(false);
            overViewRButton.setVisible(false);
            hideTotalAmount();
            insertButton.setVisible(true);
            deleteButton.setVisible(true);
            updateButton.setVisible(true);
        }
        else if(tableName.equals("Products")) {
            boxColumns.removeAllItems();
            boxColumns.addItem("Product ID");
            boxColumns.addItem("Name");
            boxColumns.addItem("Price");
            detailRButton.setVisible(false);
            overViewRButton.setVisible(false);
            hideTotalAmount();
            insertButton.setVisible(true);
            deleteButton.setVisible(true);
            updateButton.setVisible(true);
        }
        else if(tableName.equals("Orders")) {
            if(detailRButton.isSelected()) {
                boxColumns.removeAllItems();
                boxColumns.addItem("Order ID");
                boxColumns.addItem("Date");
                boxColumns.addItem("Customer ID");
                boxColumns.addItem("Customer Name");
                boxColumns.addItem("Customer Phone Number");
                boxColumns.addItem("Product ID");
                boxColumns.addItem("Product Name");
                boxColumns.addItem("Product Price");
                detailRButton.setVisible(true);
                overViewRButton.setVisible(true);
                hideTotalAmount();
                insertButton.setVisible(true);
                deleteButton.setVisible(true);
                updateButton.setVisible(true);
            }
            else if(overViewRButton.isSelected()) {
                boxColumns.removeAllItems();
                boxColumns.addItem("Customer");
                boxColumns.addItem("Product");
                detailRButton.setVisible(true);
                overViewRButton.setVisible(true);
                showTotalAmount();
                insertButton.setVisible(false);
                deleteButton.setVisible(false);
                updateButton.setVisible(false);
            }
        }

    }
    public void showDateBoxes(List<Order> orders) {
        boxYears.removeAllItems();
        boxYears.addItem("Year");
        int current_year = 2023;
        for(Order order : orders) {
            LocalDate localDate =order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(localDate.getYear() > 2023) {
                current_year = localDate.getYear();
            }
        }
        for(int i=2023;i<=current_year;i++) {
            boxYears.addItem(i);
        }
        boxYears.setSelectedIndex(0);
        boxYears.setVisible(true);
        ActionListener actionListener = new ServiceController(this);
        boxYears.addActionListener(actionListener);


        boxMonths.removeAllItems();
        String[] months = {"Month","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for(String month : months) {
            boxMonths.addItem(month);
        }
        boxMonths.setSelectedIndex(0);
        boxMonths.setVisible(false);
        boxMonths.addActionListener(actionListener);

        boxDays.removeAllItems();
        String[] allDay = {"Day", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        for(String day : allDay) {
            boxDays.addItem(day);
        }
        boxDays.setSelectedIndex(0);
        boxDays.setVisible(false);
        boxMonths.addActionListener(e -> {
            boxDays.removeAllItems();
            String[] days = {"Day", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" };
            for(String day : days) {
                boxDays.addItem(day);
            }
            int check = boxMonths.getSelectedIndex();
            if(check != 2) {
                boxDays.addItem("29");
                boxDays.addItem("30");
                if(check != 4 && check != 6 && check != 9 && check != 11) {
                    boxDays.addItem("31");
                }
            }
            boxDays.setSelectedIndex(0);
            boxDays.setVisible(true);
        });

    }
    public void hideDateBoxes() {
        boxYears.setVisible(false);
        boxYears.removeAllItems();
        boxMonths.setVisible(false);
        boxMonths.removeAllItems();
        boxDays.setVisible(false);
        boxDays.removeAllItems();
    }
    public void showTotalAmount() {
        grandTotal.setVisible(true);
        grandTotalLabel.setVisible(true);
    }
    public void hideTotalAmount() {
        grandTotal.setVisible(false);
        grandTotalLabel.setVisible(false);
    }

    public ApplicationView() {
        setTitle("Sale Management System v1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 700));
        setContentPane(root);
        ActionListener actionListener = new ServiceController(this);
        MouseListener mouseListener = new ServiceController(this);
        setVisible(true);
        table.addMouseListener(mouseListener);
        viewButton.addActionListener(actionListener);
        insertButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        viewButton.addActionListener(actionListener);
        updateButton.addActionListener(actionListener);
        boxTables.setSelectedIndex(-1);
        boxTables.addActionListener(actionListener);
        boxYears.setVisible(false);
        boxMonths.setVisible(false);
        boxDays.setVisible(false);
        boxColumns.addActionListener(actionListener);
        filteringButton.addActionListener(actionListener);
        detailRButton.addActionListener(actionListener);
        detailRButton.setVisible(false);
        overViewRButton.addActionListener(actionListener);
        overViewRButton.setVisible(false);
        grandTotal.setVisible(false);
        grandTotalLabel.setVisible(false);
        createUserFrame();
        setEnabled(false);
        logOut.addActionListener(actionListener);

        facebook.setForeground(Color.BLUE.darker());
        facebook.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.facebook.com/luantang21/"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                facebook.setText("<html><a href=''>My Facebook</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                facebook.setText("My Facebook     ");
            }
        });
    }



    public static void main(String[] args)  {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ApplicationView frame = new ApplicationView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
