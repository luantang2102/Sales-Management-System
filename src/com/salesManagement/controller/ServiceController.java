package com.salesManagement.controller;

import com.salesManagement.model.Customer;
import com.salesManagement.model.Order;
import com.salesManagement.model.Product;
import com.salesManagement.model.User;
import com.salesManagement.service.*;
import com.salesManagement.view.ApplicationView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServiceController extends WindowAdapter implements ActionListener, MouseListener {
    public ApplicationView view;
    int i = 1;

    CustomerService customerService = new CustomerService();
    ProductService productService = new ProductService();
    OrderService orderService = new OrderService();
    UserService userService = UserService.getInstance();

    public ServiceController(ApplicationView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        boolean reset = false;
        if(actionCommand.equals("Login")) {
            if(userService.getUser(view.userNameTextField.getText()) == null) {
                view.createDialog("User name does not exist!");
                view.userNameTextField.setText("");
                view.userPassTextField.setText("");
            }
            else {
                if(String.valueOf(view.userPassTextField.getPassword()).equals(userService.getCurrentUser().getUserPass())) {
                    System.out.println("Right");
                    view.userFrame.dispose();
                    view.setEnabled(true);
                    view.root.setVisible(true);
                }
                else {
                    view.createDialog("Incorrect password!");
                    view.userPassTextField.setText("");
                }
            }
        }
        if(actionCommand.equals("Log Out")) {
            view.setEnabled(false);
            view.createUserFrame();
            view.table.setModel(new DefaultTableModel());
            view.boxColumns.setSelectedIndex(-1);
            view.boxTables.setSelectedIndex(-1);
        }
        if(actionCommand.equals("Register")) {
            if(view.userNameTextField.getText().equals("") || String.valueOf(view.userPassTextField).equals("") || String.valueOf(view.userPassSecTextField).equals("")) {
                view.createDialog("Please fill in all required fields!");
            }
            else {
                boolean check = true;
                for (User user : userService.getAll()) {
                    if (user.getUserName().equals(view.userNameTextField.getText())) {
                        view.createDialog("Username has already been taken!");
                        view.userNameTextField.setText("");
                        view.userPassTextField.setText("");
                        view.userPassSecTextField.setText("");
                        check = false;
                        break;
                    }
                }
                if (check) {
                    if (Arrays.equals(view.userPassSecTextField.getPassword(), view.userPassTextField.getPassword())) {
                        int result = userService.save(new User(view.userNameTextField.getText(), String.valueOf(view.userPassSecTextField.getPassword())));
                        if (result == 1) {
                            view.createDialog("Account successfully created!");
                            view.userFrame.dispose();
                            view.createSignInFrame();
                        } else {
                            view.createDialog("Account creation failed!");
                        }
                    } else {
                        view.createDialog("Passwords did not match!");
                    }
                }
            }
        }
        if(actionCommand.equals("boxTablesChange")) {
            if(view.boxTables.getSelectedIndex() != -1) {
                view.changeBoxColumns(String.valueOf(view.boxTables.getItemAt(view.boxTables.getSelectedIndex())));
                reset = true;
            }
        }
        if(actionCommand.equals("boxColumnsChange")) {
            //Check when we change boxTables, the boxColumns will be removed and null
            if(view.boxColumns.getSelectedItem() != null) {
                if(view.boxColumns.getSelectedItem().equals("Date") || view.boxColumns.getSelectedItem().equals("Customer") || view.boxColumns.getSelectedItem().equals("Product")) {
                    view.filterTextField.setVisible(false);
                    view.showDateBoxes(orderService.getAll());
                }
                else {
                    view.filterTextField.setVisible(true);
                    view.hideDateBoxes();
                }
                if(view.boxColumns.getSelectedItem().equals("Product") || view.boxColumns.getSelectedItem().equals("Customer")) {
                    view.createOrderOverViewTable(orderService.orderOverView(String.valueOf(view.boxColumns.getSelectedItem()), String.valueOf(view.boxYears.getSelectedItem()), String.valueOf(view.boxMonths.getSelectedItem()), String.valueOf(view.boxDays.getSelectedItem())));
                }
            }
            view.filterTextField.setText("");
            view.selectedIds = Collections.emptyList();

        }
        if(actionCommand.equals("Year")) {
            view.boxMonths.setVisible(true);
        }
        if(actionCommand.equals("Month")) {
            view.boxDays.setVisible(true);
        }
        if(actionCommand.equals("All") || reset) {
            if(view.boxTables.getSelectedIndex() == -1) {
                view.createDialog("Please choose a table!");
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Customers")) {
                view.createCustomersTable(customerService.getAll());
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Products")) {
                view.createProductsTable(productService.getAll());
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Orders")) {
                if(view.detailRButton.isSelected()) {
                    view.createOrdersTable(orderService.getAll());
                }
                else if(view.overViewRButton.isSelected()) {
                    view.createOrderOverViewTable(orderService.orderOverView(String.valueOf(view.boxColumns.getSelectedItem()), "Year", "Month", "Day"));
                    view.boxYears.setSelectedIndex(0);
                    view.boxMonths.setSelectedIndex(0);
                    view.boxDays.setSelectedIndex(0);
                }
            }
        }
        if(actionCommand.equals("Insert")) {
            if(view.boxTables.getSelectedIndex() == -1) {
                view.createDialog("Please choose a table!");
            }
            else {
                view.setEnabled(false);
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Customers")) {
                view.customerInsertionFrame();
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Products")) {
                view.productInsertionFrame();
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Orders")) {
                view.orderInsertionFrame(customerService.getAll(), productService.getAll());
            }
        }
        if(actionCommand.equals("Add")){
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Customers")) {
                if(view.customerNameTextField.getText().equals("") || view.phoneNumTextField.getText().equals("")) {
                    view.createDialog("Please fill in all required fields!");
                }
                else {
                    int result = customerService.save(new Customer(view.customerNameTextField.getText().toCharArray(), view.phoneNumTextField.getText().toCharArray()));
                    view.customerNameTextField.setText("");
                    view.phoneNumTextField.setText("");
                    if(result == 0) {
                        view.createDialog("Add failed!");
                    }
                    else {
                        view.createDialog("Added!");
                    }
                }

            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Products")) {
                if(view.productPriceTextField.getText().equals("") || view.productNameTextField.getText().equals("")) {
                    view.createDialog("Please fill in all required fields!");
                }
                else {
                    int result = productService.save(new Product(view.productNameTextField.getText().toCharArray(), Double.parseDouble(view.productPriceTextField.getText())));
                    view.productNameTextField.setText("");
                    view.productPriceTextField.setText("");
                    if(result == 0) {
                        view.createDialog("Add failed!");
                    }
                    else {
                        view.createDialog("Added!");
                    }
                }
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Orders")) {
                if(view.boxCustomers.getSelectedIndex() == -1 || view.boxProducts.getSelectedIndex() == -1 || view.amountTextField.getText().equals("")) {
                    view.createDialog("Please fill in all required fields!");
                }
                else {
                    Customer selectedCustomer = new Customer();
                    int i=0;
                    for(Customer customer : customerService.getAll()) {
                        if(view.boxCustomers.getSelectedIndex() == i) {
                            selectedCustomer = customer;
                            break;
                        }
                        i++;
                    }
                    Product selectedProduct = new Product();
                    for(Product product : productService.getAll()) {
                        if(String.valueOf(product.getName()).equals(view.boxProducts.getSelectedItem())) {
                            selectedProduct = product;
                            break;
                        }
                    }
                    int result = orderService.save(new Order(null , selectedCustomer, selectedProduct, Integer.parseInt(view.amountTextField.getText())));
                    view.amountTextField.setText("");
                    view.boxCustomers.setSelectedIndex(-1);
                    view.boxProducts.setSelectedIndex(-1);
                    if(result == 0) {
                        view.createDialog("Add failed!");
                    }
                    else {
                        view.createDialog("Added!");
                    }
                }
            }
        }
        if(actionCommand.equals("Delete")){
            if(view.boxTables.getSelectedIndex() == -1) {
                view.createDialog("Please choose a table!");
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Customers")) {
                for(char[] customerId : view.selectedIds) {
                    if(view.createYesNoDialog("Deleting " + String.valueOf(customerId) + "?") == 0) {
                        int result = customerService.delete(customerId);
                        if(result == 0) {
                            view.createDialog("Delete " + String.valueOf(customerId) +  " failed!");
                        }
                        else {
                            view.createDialog("Deleted " + String.valueOf(customerId) + "!");
                        }
                    }
                }
                view.createCustomersTable(customerService.getAll());
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Products")) {
                for(char[] productId : view.selectedIds) {
                    if(view.createYesNoDialog("Deleting " + String.valueOf(productId) + "?") == 0) {
                        int result  = productService.delete(productId);
                        if(result == 0) {
                            view.createDialog("Delete " + String.valueOf(productId) +  " failed!");
                        }
                        else {
                            view.createDialog("Deleted " + String.valueOf(productId) + "!");
                        }
                    }
                }
                view.createProductsTable(productService.getAll());
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Orders")) {
                for(char[] orderId : view.selectedIds) {
                    if(view.createYesNoDialog("Deleting " + String.valueOf(orderId) + "?") == 0) {
                        int result = orderService.delete(orderId);
                        if(result == 0) {
                            view.createDialog("Delete " + String.valueOf(orderId) +  " failed!");
                        }
                        else {
                            view.createDialog("Deleted " + String.valueOf(orderId) + "!");
                        }
                    }
                }
                view.createOrdersTable(orderService.getAll());
            }
        }
        if(actionCommand.equals("Update")){
            if(view.boxTables.getSelectedIndex() == -1) {
                view.createDialog("Please choose a table!");
            }
            else {
                view.setEnabled(false);
            }
            if(view.boxTables.getSelectedItem().equals("Customers")) {
                if(view.selectedIds.isEmpty()) {
                    view.createDialog("Please choose a customer!");
                    view.setEnabled(true);
                }
                else {
                    for (char[] customerId : view.selectedIds) {
                        view.customerUpdateFrame(customerService.getByCustomerId(customerId));
                    }
                }
            }
            if(view.boxTables.getSelectedItem().equals("Products")) {
                if(view.selectedIds.isEmpty()) {
                    view.createDialog("Please choose a product!");
                    view.setEnabled(true);
                }
                else {
                    for (char[] productId : view.selectedIds) {
                        view.productUpdateFrame(productService.getByProductId(productId));
                    }
                }
            }
            if(view.boxTables.getSelectedItem().equals("Orders")) {
                if(view.selectedIds.isEmpty()) {
                    view.createDialog("Please choose an order!");
                    view.setEnabled(true);
                }
                else {
                    for (char[] orderId : view.selectedIds) {
                        view.orderUpdateFrame(orderService.getByOrderId(orderId), customerService.getAll(), productService.getAll());
                    }
                }
            }
        }
        if(actionCommand.equals("Save")) {
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Customers")) {
                int result = customerService.update(new Customer(view.customerIdText.getText().toCharArray(), view.customerNameTextField.getText().toCharArray(), view.phoneNumTextField.getText().toCharArray()));
                view.updateFrame.dispose();
                if(result == 0) {
                    view.createDialog("Update failed!");
                }
                else {
                    view.createDialog("Updated!");
                }
                view.createCustomersTable(customerService.getAll());
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Products")) {
                int result = productService.update(new Product(view.productIdText.getText().toCharArray(), view.productNameTextField.getText().toCharArray(), Double.parseDouble(view.productPriceTextField.getText())));
                view.updateFrame.dispose();
                if(result == 0) {
                    view.createDialog("Update failed!");
                }
                else {
                    view.createDialog("Updated!");
                }
                view.createProductsTable(productService.getAll());
            }
            if(view.boxTables.getItemAt(view.boxTables.getSelectedIndex()).equals("Orders")) {
                Customer selectedCustomer = new Customer();
                for(Customer customer : customerService.getAll()) {
                    if(String.valueOf(customer.getName()).equals(view.boxCustomers.getSelectedItem())) {
                        selectedCustomer = customer;
                        break;
                    }
                }
                Product selectedProduct = new Product();
                for(Product product : productService.getAll()) {
                    if(String.valueOf(product.getName()).equals(view.boxProducts.getSelectedItem())) {
                        selectedProduct = product;
                        break;
                    }
                }
                int result = orderService.update(new Order(view.orderIdText.getText().toCharArray(), null , selectedCustomer, selectedProduct, Integer.parseInt(view.amountTextField.getText())));
                view.updateFrame.dispose();
                if(result == 0) {
                    view.createDialog("Update failed!");
                }
                else {
                    view.createDialog("Updated!");
                }
                view.createOrdersTable(orderService.getAll());
            }
        }
        if(actionCommand.equals("Filtering")) {
            if(view.boxTables.getSelectedIndex() == -1) {
                view.createDialog("Please choose a table!");
            }
            if(view.boxTables.getSelectedItem().equals("Customers")) {
                if(view.boxColumns.getSelectedItem().equals("Name")) {
                    view.createCustomersTable(customerService.getByName(view.filterTextField.getText()));
                }
                else if(view.boxColumns.getSelectedItem().equals("Phone number")) {
                    view.createCustomersTable(customerService.getByPhoneNum(view.filterTextField.getText()));
                }
                else if(view.boxColumns.getSelectedItem().equals("Customer ID")) {
                    List<Customer> customers = new ArrayList<>();
                    Customer customer = customerService.getByCustomerId(view.filterTextField.getText().toCharArray());
                    if(customer != null) {
                        customers.add(customer);
                    }
                    view.createCustomersTable(customers);
                }
            }
            if(view.boxTables.getSelectedItem().equals("Products")) {
                if(view.boxColumns.getSelectedItem().equals("Name")) {
                    view.createProductsTable(productService.getByName(view.filterTextField.getText()));
                }
                else if(view.boxColumns.getSelectedItem().equals("Price")) {
                    view.createProductsTable(productService.getByPrice(Double.valueOf(view.filterTextField.getText())));
                }
                else if(view.boxColumns.getSelectedItem().equals("Product ID")) {
                    List<Product> products = new ArrayList<>();
                    Product product = productService.getByProductId(view.filterTextField.getText().toCharArray());
                    if(product != null) {
                        products.add(product);
                    }
                    view.createProductsTable(products);
                }
            }
            if(view.boxTables.getSelectedItem().equals("Orders")) {
                if(view.detailRButton.isSelected()) {
                    if(view.boxColumns.getSelectedItem().equals("Order ID")) {
                        List<Order> orders = new ArrayList<>();
                        Order order = orderService.getByOrderId((view.filterTextField.getText().toCharArray()));
                        if(order != null) {
                            orders.add(order);
                        }
                        view.createOrdersTable(orders);
                    }
                    if(view.boxColumns.getSelectedItem().equals("Date")) {
                        view.createOrdersTable(orderService.getByDate(String.valueOf(view.boxYears.getSelectedItem()), String.valueOf(view.boxMonths.getSelectedItem()), String.valueOf(view.boxDays.getSelectedItem())));
                    }
                    if(view.boxColumns.getSelectedItem().equals("Customer ID")) {
                        view.createOrdersTable(orderService.getByCustomerId(view.filterTextField.getText().toCharArray()));
                    }
                    else if(view.boxColumns.getSelectedItem().equals("Customer Name")) {
                        view.createOrdersTable(orderService.getByCustomerName(view.filterTextField.getText()));
                    }
                    else if(view.boxColumns.getSelectedItem().equals("Customer Phone Number")) {
                        view.createOrdersTable(orderService.getByCustomerPhoneNum(view.filterTextField.getText()));
                    }
                    else if(view.boxColumns.getSelectedItem().equals("Product ID")) {
                        view.createOrdersTable(orderService.getByProductId(view.filterTextField.getText().toCharArray()));
                    }
                    else if(view.boxColumns.getSelectedItem().equals("Product Name")) {
                        view.createOrdersTable(orderService.getByProductName(view.filterTextField.getText()));
                    }
                    else if(view.boxColumns.getSelectedItem().equals("Product Price")) {
                        view.createOrdersTable(orderService.getByPrice(Double.parseDouble(view.filterTextField.getText())));
                    }
                }
                else if(view.overViewRButton.isSelected()) {
                    view.createOrderOverViewTable(orderService.orderOverView(String.valueOf(view.boxColumns.getSelectedItem()), String.valueOf(view.boxYears.getSelectedItem()), String.valueOf(view.boxMonths.getSelectedItem()), String.valueOf(view.boxDays.getSelectedItem())));
                }
            }
        }

        if(actionCommand.equals("Over View")) {
            view.detailRButton.setSelected(false);
            view.changeBoxColumns("Orders");
            view.createOrderOverViewTable(orderService.orderOverView(String.valueOf(view.boxColumns.getSelectedItem()), String.valueOf(view.boxYears.getSelectedItem()), String.valueOf(view.boxMonths.getSelectedItem()), String.valueOf(view.boxDays.getSelectedItem())));
        }
        if(actionCommand.equals("Detail")) {
            view.changeBoxColumns("Orders");
            view.createOrdersTable(orderService.getAll());
            view.overViewRButton.setSelected(false);
        }
        System.out.println(i++ + " " + actionCommand);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            JTable target = (JTable)e.getSource();
            int[] selectedRows = target.getSelectedRows();
            if(selectedRows == null) System.out.println("Fucked");
            List<char[]> selectedId = new ArrayList<>();
            for(int selectedRow : selectedRows) {
                selectedId.add(String.valueOf(target.getModel().getValueAt(selectedRow, 1)).toCharArray());
            }
            view.selectedIds = selectedId;
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
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        view.setEnabled(true);
    }
}
