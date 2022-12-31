package ui;

import RacingManager.*;
import RacingManager.SSCampeonato.Campeonato;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.Corrida;
import data.*;

import java.awt.desktop.SystemEventListener;
import java.util.*;
import java.util.stream.Collectors;

public class TextUI {
    private IRacingManager model;
    private Menu menu;
    private Scanner scin;
    private boolean isLoggedIn;
    private final int MAX = 6;

    public static void clearWindow() {
        for (int i = 0;i<75;i++){
            System.out.println();
        }
    }

    public TextUI() {
        // Criar o menu
        this.menu = new Menu(new String[]{
                "Register",
                "Login",
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
                "Play Championship",
                "Race Simulation",
        });

        // Set up the handlers for each option in the submenu
        subMenu.setHandler(1, this::handlePlayChampionship);
        //subMenu.setHandler(2, this::handleRaceSimulation);

        // Run the submenu
        subMenu.run();
    }

    private void handlePlayChampionship() {
        // Configurar Campeonatos

        // Colocar ocasioes de erro e listas vazias etc
        System.out.println("How many players will play (Max: " + MAX + "): ");
        int nJogadores = scin.nextInt();

        if(nJogadores > MAX) {
            System.out.println("Error: Number Invalid ");
            // Run the submenu
            showSubMenu();
        }

        CampeonatoDAO campeonatoDAO = CampeonatoDAO.getInstance();

        // Ver o que imprime o GetCampsName
        Menu Championship = new Menu("Select one of the following Championships:", campeonatoDAO.getCampsName());
        System.out.println("Enter your selection: ");
        String Campeonato = scin.nextLine();

        clearWindow();

        Campeonato c = campeonatoDAO.get(Campeonato);
        // c.setParticipantes(nJogadores);
        List<Circuito> circuitos = new ArrayList<>();
        for (Corrida corrida : c.getCorridas()) {
            circuitos.add(corrida.getCircuito());
        }

        for (Circuito circuito : circuitos) {
            System.out.println("Name: " + circuito.getNomeCircuito() + "and Distance: " + circuito.getDistancia());
        }

        clearWindow();

        int i = 0;
        while(i < nJogadores) {
            System.out.println("Jogador " + "i");
            CarroDAO carroDAO = CarroDAO.getInstance();
            Map<String, Carro> carros = carroDAO.getAll();
            for (Carro carro : carros.values()) {
                System.out.println("ID: " + carro.getIdCarro() + "HP: " + carro.getPotencia() + "Brand: " + carro.getMarca() + "Model: " + carro.getModelo() + "Category: " + carro.getCategoria());
            }
            System.out.println("Enter the ID: ");
            String IDCarro = scin.nextLine();
            Carro Carro = carroDAO.get(IDCarro);

            clearWindow();

            PilotoDAO pilotoDAO = PilotoDAO.getInstance();
            Map<String, Piloto> pilotos = pilotoDAO.getAll();
            for (Piloto piloto : pilotos.values()) {
                // Nao sei se e necessario imprimir mais coisas CTS SVA
                System.out.println("Nome: " + piloto.getNomePiloto());
            }
            System.out.println("Enter the ID: ");
            String NomePiloto = scin.nextLine();
            Piloto piloto = pilotoDAO.get(NomePiloto);

            Jogador j = new Jogador();
            j.setPiloto(piloto);
            j.setCarro(Carro);
            j.setId("i");
            c.getJogadores().add(j);
        }
        clearWindow();

        // Configurar Corridas
        List<Corrida> corrida = c.getCorridas();
        int index = 0;
        Corrida atual = corrida.get(index);

        System.out.println("Circuit :" + atual.getCircuito().getNomeCircuito() + " and Weather Situation " + atual.getMeteorologia());

        clearWindow();

        int j = 0;
        while(j < nJogadores) {
            System.out.println("Jogador " + "i");
            System.out.println("Do you want to change the tuning of the car? (yes/no)");
            String resposta = scin.nextLine();

            if(resposta.toLowerCase().equals("yes")) {
                System.out.println("Jogador " + "i");
                // Fazer afinações
            }
            else if(resposta.toLowerCase().equals("no"));
            else {
                // Mandar novamente a pergunta
                System.out.println("Wrong Answer!");
            }

            Set<Jogador> jog = c.getJogadores();
            List<Jogador> jogList = new ArrayList<>(jog);
            Jogador jogador = jogList.get(i);
            String idJ = jogador.getId();

            System.out.println("Select the Tieres: (Macio/Duro/Chuva)");
            // Ver se a resposta é correta
            String respostaPneus = scin.nextLine();
            c.escolhePneus(idJ, respostaPneus);

            System.out.println("Select the Engine: (Conservador/Normal/Agressivo)");
            // Ver se a resposta é correta
            String respostaMotor = scin.nextLine();
            c.escolheMotor(idJ, respostaPneus);
        }
    }

    private void showSub2Menu() {
        // Create the submenu
        Menu sub2Menu = new Menu(new String[] {
                "Select Championship",
                "Select Car",
                "Select Pilot",
        });

        // Set up the handlers for each option in the submenu
        sub2Menu.setHandler(1, this::handlePlayChampionship);
        //subMenu.setHandler(2, this::handleRaceSimulation);

        // Run the submenu
        sub2Menu.run();
    }

}



