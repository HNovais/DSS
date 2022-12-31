package ui;

import RacingManager.IRacingManager;
import RacingManager.RacingManagerFacade;
import RacingManager.Utilizador;
import data.UtilizadorDAO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextUI {
    private IRacingManager model;
    private Menu menu;
    private Scanner scin;
    private boolean isLoggedIn;

    public TextUI() {
        // Criar o menu
        this.menu = new Menu(new String[]{
                "Register",
                "Login",
                //"Login as Admin",
        });
        this.menu.setHandler(1, this::handleRegister);
        this.menu.setHandler(2, this::handleLogin);

        this.model = new RacingManagerFacade();
        // Set the precondition for options that should only be available to logged in users
        // this.menu.setPreCondition(1, () -> this.isLoggedIn);
        // this.menu.setPreCondition(2, () -> this.isLoggedIn);
        scin = new Scanner(System.in);
    }

    public void run() {
        this.menu.run();
        System.out.println("See you soon!...");
    }

    private void handleRegister() {
        System.out.println("Enter a Username: ");
        String username = scin.nextLine();
        System.out.println("Enter a Password: ");
        String password = scin.nextLine();

        // Create a new Utilizador object with the entered username and password
        Utilizador utilizador = new Utilizador(username, password);

        // Use the UtilizadorDAO to add the new user to the database
        UtilizadorDAO utilizadorDAO = UtilizadorDAO.getInstance();
        if(utilizadorDAO.get(username) == null) {
            utilizadorDAO.put(utilizador);
            System.out.println("Successfully registered!");
            // Show the submenu
            showSubMenu();
        }
        else {
            System.out.println("Name in use! Try again!");
            // Return to the main menu
            this.menu.run();
        }
    }

    private void handleLogin() {
        System.out.println("Enter your Username: ");
        String username = scin.nextLine();
        System.out.println("Enter your Password: ");
        String password = scin.nextLine();

        // Use the UtilizadorDAO to retrieve the user with the entered username
        UtilizadorDAO utilizadorDAO = UtilizadorDAO.getInstance();
        Utilizador utilizador = utilizadorDAO.get(username);

        if (utilizador == null) {
            // User not found
            System.out.println("User not found");
            // Return to the main menu
            this.menu.run();
        } else if (!utilizador.getPassword().equals(password)) {
            // Incorrect password
            System.out.println("Invalid Password");
            // Return to the main menu
            this.menu.run();
        } else {
            // Successful login
            this.isLoggedIn = true;
            System.out.println("Successfully logged in!");

            // Show the submenu
            showSubMenu();
        }
    }

    private void showSubMenu() {
        // Create the submenu
        Menu subMenu = new Menu(new String[] {
                "Configurations",
                "Race Simulation",
                //"Logout"
        });

        // Set up the handlers for each option in the submenu
        subMenu.setHandler(1, this::handleConfigurations);
        //subMenu.setHandler(2, this::handleRaceSimulation);

        // Run the submenu
        subMenu.run();
    }

    private void handleConfigurations() {
        // Show the sub-sub menu
        showSubSubMenu();
    }

    private void showSubSubMenu() {
        // Create the submenu
        Menu subMenu = new Menu(new String[] {
                "Select Championship",
                "Choose Car",
                "Choose Pilot",
        });

        // Set up the handlers for each option in the submenu
        //subMenu.setHandler(1, this::handleSelectChampionship);
        //subMenu.setHandler(2, this::handleChooseCar);
        //subMenu.setHandler(2, this::handleChoosePilot);

        // Run the submenu
        subMenu.run();
    }

}



