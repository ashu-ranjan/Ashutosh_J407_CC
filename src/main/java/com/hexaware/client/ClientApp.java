package com.hexaware.client;

import com.hexaware.dao.OrderManagementRepositoryImpl;
import com.hexaware.entity.*;

import com.hexaware.exception.ProductAlreadyExistException;
import com.hexaware.exception.ProductNotExistException;
import com.hexaware.util.TableFormatterUtil;
import java.util.List;
import java.util.Scanner;

public class ClientApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final OrderManagementRepositoryImpl processor = new OrderManagementRepositoryImpl();

    public static void main(String[] args) {
        System.out.println("\n---> Welcome to Order Management System <---");

        while (true) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> handleCreateProduct();
                case 2 -> handleGetAllProducts();
                case 3 -> handleGetProductById();
                case 4 -> {
                    processor.closeConnection();
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n======= MENU =======");
        System.out.println("1. Create Product");
        System.out.println("2. Get All Products");
        System.out.println("3. Get Product By ID");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleCreateProduct() {
        try {
            System.out.print("Enter Product ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Description: ");
            String desc = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Quantity in Stock: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Product Type (Electronics/Clothing): ");
            String type = scanner.nextLine();

            Product product = null;

            if (type.equalsIgnoreCase("Electronics")) {
                System.out.print("Enter Brand: ");
                String brand = scanner.nextLine();

                System.out.print("Enter Warranty Period (in months): ");
                int warranty = Integer.parseInt(scanner.nextLine());

                product = new Electronics(id, name, desc, price, quantity, type, brand, warranty);

            } else if (type.equalsIgnoreCase("Clothing")) {
                System.out.print("Enter Size: ");
                String size = scanner.nextLine();

                System.out.print("Enter Color: ");
                String color = scanner.nextLine();

                product = new Clothing(id, name, desc, price, quantity, type, size, color);
            } else {
                System.out.println("Invalid product type.");
                return;
            }

            if (processor.createProduct(product)) {
                System.out.println("Product created successfully!");
            }

        } catch (ProductAlreadyExistException e) {
            System.out.println("Product Already Exists : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while creating product: " + e.getMessage());
        }
    }

    private static void handleGetAllProducts() {
        List<Product> products = processor.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available!");
        } else {
            System.out.println("\nList of Products:");
            TableFormatterUtil.printUnifiedProductHeader();
            products.forEach(p -> System.out.println(p.toString()));
            TableFormatterUtil.printUnifiedProductFooter();
        }
    }


    private static void handleGetProductById() {
        try {
            System.out.print("Enter Product ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Product product = processor.getProductById(id);
            if (product == null) {
                System.out.println("No product found with ID : " + id);
                return;
            }

            if (product instanceof Electronics) {
                TableFormatterUtil.printElectronicsHeader();
            } else if (product instanceof Clothing) {
                TableFormatterUtil.printClothingHeader();
            }

            System.out.println(product.toDetailedString());

            if (product instanceof Electronics) {
                TableFormatterUtil.printElectronicsFooter();
            } else if (product instanceof Clothing) {
                TableFormatterUtil.printClothingFooter();
            }

        } catch (ProductNotExistException e) {
            System.out.println("Product does not exist: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while retrieving product: " + e.getMessage());
        }
    }


}


