package ui;

import RacingManager.*;
import RacingManager.SSCampeonato.Campeonato;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.Corrida;
import data.*;

import java.util.*;
import java.util.stream.Collectors;

public class TextUI {
    private IRacingManager model;
    private Menu menu;
    private Scanner scin;
    private boolean isLoggedIn;
    private final int MAX = 6;
    CampeonatoDAO campeonatoDAO = CampeonatoDAO.getInstance();

    public static void clearWindow() {
        for (int i = 0; i < 75; i++) {
            System.out.println();
        }
    }

    public TextUI() {
        // Criar o menu
        this.menu = new Menu("Welcome", new String[]{
                "Register",
                "Login",
        });
        this.menu.setHandler(1, this::handleRegister);
        this.menu.setHandler(2, this::handleLogin);

        this.model = new RacingManagerFacade();
        // Set the precondition for options that should only be available to logged-in users
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
        if (utilizadorDAO.get(username) == null) {
            utilizadorDAO.put(utilizador);
            System.out.println("Successfully registered!");
            // Show the submenu
            showSubMenu();
        } else {
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
        Menu subMenu = new Menu(new String[]{
                "Configure Championship",
                "Race Simulation",
        });

        // Set up the handlers for each option in the submenu
        subMenu.setHandler(1, this::showMenuCamp);
        //subMenu.setHandler(2, this::handleRaceSimulation);

        // Run the submenu
        subMenu.run();
    }

    private void showMenuCamp() {
        List<String> campeonatos = campeonatoDAO.getCampsName();
        int campSize = campeonatos.size();

        while (true) {
            // clearWindow();
            System.out.println("Select one of the following Championships:");
            for (int i = 0; i < campSize; i++) {
                String str = Integer.toString(i + 1);
                System.out.println(str + " - " + campeonatos.get(i));
            }
            System.out.println("0 - Back");
            System.out.print("Option: ");
            int campeonato = scin.nextInt();

            if (campeonato == 0) {
                showSubMenu();
            } else if (campeonato > 0 && campeonato <= campSize) {
                Campeonato campSelected = campeonatoDAO.get(campeonatos.get(campeonato - 1));
                // clearWindow();
                showMenuCirc(campSelected);
                break;
            } else {
                System.out.println("Invalid input. Try again!");
            }
        }
    }

    private void showMenuCirc(Campeonato campeonato) {
        List<Circuito> circuitos = campeonatoDAO.getCircuitosCampeonato(campeonato);
        List<String> circuitosNome = new ArrayList<>();
        for (Circuito c : circuitos) {
            circuitosNome.add(c.getNomeCircuito());
        }
        int circuitSize = circuitosNome.size();
        int invalidCounter = 0;

        System.out.println("Circuits in " + campeonato.getNome() + ":");
        for (int i = 0; i < circuitSize; i++) {
            System.out.println(" -> " + circuitosNome.get(i));
        }

        System.out.println("Do you want to move on? (yes/no)");
        String input;
        System.out.print("Option: ");
        while (true) {
            input = scin.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                // clearWindow();
                campeonato.setCircuitos(circuitos);
                handlePlayChampionship(campeonato);
                break;
            } else if (input.equalsIgnoreCase("no")) {
                showMenuCamp();
                break;
            } else if (!input.equalsIgnoreCase("yes") && (!input.equalsIgnoreCase("no")) && (!input.isEmpty())){
                invalidCounter++;
                if (invalidCounter >= 3) {
                    System.out.println("Too many invalid inputs. Going back to previous menu.");
                    break;
                }
                System.out.println("Invalid input. Please try again.");
                System.out.println("Do you want to move on or go back? (yes/no)");
                System.out.print("Option: ");
            }
        }
    }

    private void handlePlayChampionship(Campeonato campeonato) {
        int nJogadores = -1;
        //List<Jogador> jogadores = new ArrayList<>();

        while (true) {
            try {
                System.out.println("How many players will play? (Min: 1 & Max: " + MAX + ")");
                System.out.print("Option: ");
                String input = scin.nextLine();
                nJogadores = Integer.parseInt(input);
                if (nJogadores <= 1 || nJogadores >= 7) {
                    System.out.println("Invalid number of players. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

        for (int i = 1; i <= nJogadores; i++) {
            Jogador j = new Jogador();
            j.setNomeJogador(menuNome());
            j.setCarro(menuCarro());
            j.getCarro().setPiloto(menuPiloto());
            campeonato.jogadores.add(j);
        }

        handleRaceSimulation(campeonato);
    }

    private String menuNome() {
        System.out.println("What is the name of the Player?");
        System.out.print("Name: ");
        String nomeJogador;
        while (true) {
            nomeJogador = scin.nextLine();
            if (nomeJogador.matches("[a-zA-Z]+")) {
                return nomeJogador;
            }
            else if(!nomeJogador.isEmpty()) {
                System.out.println("Invalid input. Please enter a name with only letters.");
                System.out.println("What is the name of the Player?");
                System.out.print("Name: ");
            }
        }
    }

    private Carro menuCarro() {
        List<Carro> carros = CarroDAO.getInstance().getAll();
        int nCarros = -1;

        while (true) {
            try {
                System.out.println("Select one of the following Cars:");
                for (int i = 0; i < carros.size(); i++) {
                    String str = Integer.toString(i + 1);
                    System.out.println(str + " - " + carros.get(i));
                }
                System.out.print("Option: ");
                nCarros = scin.nextInt();
                if (nCarros >= 1 && nCarros <= carros.size()) {
                    break;
                }
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException e) {
                scin.nextLine();
                System.out.println("Invalid input. Please try again.");
            }
        }

        return carros.get(nCarros - 1);
    }

    private Piloto menuPiloto() {
        List<Piloto> pilotos = PilotoDAO.getInstance().getAll();
        int nPiloto = -1;

        while (true) {
            try {
                System.out.println("Select one of the following Pilots:");
                for (int i = 0; i < pilotos.size(); i++) {
                    String str = Integer.toString(i + 1);
                    System.out.println(str + " - " + pilotos.get(i));
                }
                System.out.print("Option: ");
                nPiloto = scin.nextInt();
                if (nPiloto >= 1 && nPiloto <= pilotos.size()) {
                    break;
                }
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException e) {
                scin.nextLine();
                System.out.println("Invalid input. Please try again.");
            }
        }

        return pilotos.get(nPiloto - 1);
    }

    private void handleRaceSimulation(Campeonato campeonato) {
        for (int i = 0; i < campeonato.circuitos.size(); i++) {
            Corrida nextCorrida = campeonato.nextCorrida(i);
            Circuito cAtual = campeonato.getCircuitos().get(i);
            nextCorrida.setCircuito(cAtual);
            nextCorrida.addParticipantes(campeonato.getJogadores());
            List<Jogador> jogadoresLista = new ArrayList<>(campeonato.jogadores);

            System.out.println("\tCorrida " + (i + 1));
            System.out.println("** Circuito **");
            System.out.println("-> " + nextCorrida.getCircuito().getNomeCircuito());
            System.out.println("** Meteorologia **");
            System.out.println("-> " + nextCorrida.getMeteorologia());

            for (int j = 0; j < campeonato.getJogadores().size(); j++) {
                String resposta;
                if (jogadoresLista.get(j).getCarro().getCategoria().equalsIgnoreCase("C1") || jogadoresLista.get(j).getCarro().getCategoria().equalsIgnoreCase("C2")) {
                    System.out.println("Player " + jogadoresLista.get(j).getNomeJogador());
                    System.out.println("Do you want to Tune your car for this Race? (yes/no)");
                    System.out.print("Option: ");
                    while (true) {
                        resposta = scin.nextLine();
                        if (resposta.equalsIgnoreCase("yes")) {
                            System.out.println("Current Downforce: " + jogadoresLista.get(j).getAfinacoes());
                            System.out.println("New Downforce: (between 0 and 1)");
                            float downforce = 0.0f;
                            while (true) {
                                try {
                                    downforce = Float.parseFloat(scin.nextLine());
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please try again.");
                                }
                            }
                            campeonato.alteraDownforce(jogadoresLista.get(j).getNomeJogador(), downforce);
                            // System.out.println(jogadoresLista.get(j).carro.getDownforce());
                            break;
                        } else if (resposta.equalsIgnoreCase("no")) {
                            break;
                        } else if ((!resposta.equalsIgnoreCase("yes")) && (!resposta.equalsIgnoreCase("no")) && (!resposta.isEmpty())) {
                            System.out.println("Invalid input. Please try again.");
                            System.out.println("Do you want to Tune your car for this Race? (yes/no)");
                            System.out.print("Option: ");
                        }
                    }
                }
            }

            for (int j = 0; j < campeonato.getJogadores().size(); j++) {
                String pneus;
                String motor;
                while (true) {
                    System.out.println("Player " + jogadoresLista.get(j).getNomeJogador());
                    System.out.println("Select your Tires: (macio/duro/chuva)");
                    System.out.print("Option: ");
                    pneus = scin.nextLine();
                    if ((pneus.equalsIgnoreCase("macio")) || (pneus.equalsIgnoreCase("duro")) || (pneus.equalsIgnoreCase("chuva"))) {
                        // i ou i-1 ??
                        campeonato.escolhePneus(jogadoresLista.get(j).getNomeJogador(), pneus);
                        System.out.println("Select your Engine Mode: (conservador/normal/agressivo)");
                        System.out.print("Option: ");
                        motor = scin.nextLine();
                        if ((motor.equalsIgnoreCase("conservador")) || (motor.equalsIgnoreCase("normal")) || (motor.equalsIgnoreCase("agressivo"))) {
                            // i ou i-1 ??
                            campeonato.escolheMotor(jogadoresLista.get(j).getNomeJogador(), motor);
                            // System.out.println(jogadoresLista.get(j).getCarro().getMotor());
                            // System.out.println(jogadoresLista.get(j).getCarro().getPneus());
                            // System.out.println(jogadoresLista.get(j).getId());
                            break;
                        }
                        else {
                            System.out.println("Invalid input. Please try again.");
                        }
                    } else System.out.println("Invalid input. Please try again.");
                }
            }
            System.out.println(nextCorrida.simulaCorrida());
        }
    }
}

/*
    private void showMenuAfinacoes() {
        Menu menuAfinacoes = new Menu("Tune", new String[]{
                "Select Downforce",
                "Select Tires",
                "Select Engine Mode",
        });

        // Set up the handlers for each option in the submenu
        //menuAfinacoes.setHandler(1, this::handleDownforce);
        //menuAfinacoes.setHandler(2, this::handleTires);
        //menuAfinacoes.setHandler(2, this::handleEngineMode);


        // Run the submenu
        menuAfinacoes.run();
    }
*/

/*    private Campeonato handleDownforce(Campeonato campeonato) {
        for (int j = 0; j < campeonato.getJogadores().size(); j++) {
            // totalAfinacoes dá zero
            System.out.println("Number of Possible Tunings: " + campeonato.totalAfinacoes());
            System.out.println("Do you want to tune the car? (yes/no)");
            String afinacoes = scin.nextLine();
            if (afinacoes.equals("yes")) {

            } else if (afinacoes.equals("no")) {
                continue;
            }// else System.exit(99);
        }
        return campeonato;
    }

}*/
        /*
            Campeonato campeonatoPneus = selectTires(campeonato);

            Campeonato campeonatoAtual = selectEngineMode(campeonatoPneus);

            for (int j = 0; j < campeonato.getJogadores().size(); j++) {
                System.out.println("Select your Tires: (macio/duro/chuva)");
                String pneus = scin.nextLine();
                if ((pneus.toLowerCase().equals("macio")) || (pneus.toLowerCase().equals("duro")) || (pneus.toLowerCase().equals("chuva"))) {
                    campeonato.escolhePneus(jogadoresLista.get(i).getId(), pneus);
                } else System.exit(0);

                System.out.println("Select your Engine Mode: (conservador/normal/agressivo)");
                String motor = scin.nextLine();
                if ((pneus.toLowerCase().equals("conservador")) || (pneus.toLowerCase().equals("normal")) || (pneus.toLowerCase().equals("agressivo"))) {
                    campeonato.escolheMotor(jogadoresLista.get(i).getId(), motor);
                } else System.exit(0);
            }*/
            // System.out.println(nextCorrida.simulaCorrida());
        //System.out.println("Chego aqui"); }

/*

    private Campeonato selectEngineMode(Campeonato campeonato) {
        List<Piloto> pilotos = PilotoDAO.getInstance().getAll();
        int nPiloto = -1;

        System.out.println("Select one of the following Pilots:");
        for (int i = 0; i < pilotos.size(); i++) {
            String str = Integer.toString(i + 1);
            System.out.println(str + " - " + pilotos.get(i));
        }
        //System.out.println(carroString);
        System.out.print("-> ");
        nPiloto = scin.nextInt();
        // clearWindow();

        return campeonato;
    }*/




