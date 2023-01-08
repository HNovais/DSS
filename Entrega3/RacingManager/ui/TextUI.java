package ui;

import RacingManager.*;
import RacingManager.SSCampeonato.Campeonato;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.Corrida;
import com.sun.jdi.VMCannotBeModifiedException;
import data.*;

import java.util.*;
import java.util.stream.Collectors;

public class TextUI {
    private IRacingManager model;
    private Menu menu;
    private Scanner scin;
    private boolean isLoggedIn;
    private final int MAX = 6;


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

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty! Try again.");
            // Return to the main menu
            this.menu.run();
        } else {
            // Create a new Utilizador object with the entered username and password
            Utilizador utilizador = new Utilizador(username, password);

            // Use the UtilizadorDAO to add the new user to the database
            this.model.setUtilizadores(UtilizadorDAO.getInstance());

            if (this.model.getUtilizadores().get(username) == null) {
                this.model.getUtilizadores().put(utilizador);
                System.out.println("Successfully registered!");
                // Show the submenu
                showSubMenu();
            } else {
                System.out.println("Name in use! Try again!");
                // Return to the main menu
                this.menu.run();
            }
        }
    }

    private void handleLogin() {
        System.out.println("Enter your Username: ");
        String username = scin.nextLine();
        System.out.println("Enter your Password: ");
        String password = scin.nextLine();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty! Try again.");
            // Return to the main menu
            this.menu.run();
        } else {
            // Use the UtilizadorDAO to retrieve the user with the entered username
            this.model.setUtilizadores(UtilizadorDAO.getInstance());
            Utilizador utilizador = this.model.getUtilizadores().get(username);

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
    }

    private void showSubMenu() {
        Menu subMenu = new Menu(new String[]{
                "Configure Championship",
                "Race Configuration",
                "Rankings"
        });

        // Set up the handlers for each option in the submenu
        subMenu.setHandler(1, this::showMenuCamp);
        subMenu.setHandler(2, () -> {
            if (flag) {
                this.showRaceSimulationMenu();
            } else {
                System.out.println("Race Configuration are not available until you configure the championship!");
                this.showSubMenu();
            }
        });
        subMenu.setHandler(3, this::showRankings);

        // Run the submenu
        subMenu.run();
    }

    private void showMenuCamp() {
        this.model.getCampF().setCampeonatos(CampeonatoDAO.getInstance());
        CampeonatoDAO campeonatoDAO = this.model.getCampF().getCampeonatos();

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
        this.model.getCampF().setCampeonatos(CampeonatoDAO.getInstance());
        CampeonatoDAO campeonatoDAO = this.model.getCampF().getCampeonatos();

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
        int afinacoes = campeonato.totalAfinacoes();

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
            Jogador j = new Jogador(afinacoes);
            String nome = menuNome();
            j.setNomeJogador(nome);
            j.setCarro(menuCarro());
            j.getCarro().setPiloto(menuPiloto());
            campeonato.jogadores.add(j);
            campeonato.classificacao.put(nome, null);
            campeonato.pontuacao.put(nome, null);
        }
        this.model.getCampF().setCampeonatoAtual(campeonato);
        //handleRaceSimulation(campeonato);
        flag = true;
        this.model.getCampF().getCampeonatoAtual().setVoltas(nVoltas);
        this.model.getCampF().getCampeonatoAtual().generateCorridas();
        showSubMenu();
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
        this.model.getCarroF().setCarros(CarroDAO.getInstance());
        CarroDAO carroDAO = this.model.getCarroF().getCarros();
        List<Carro> carros = carroDAO.getAll();
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
        this.model.setPilotos(PilotoDAO.getInstance());
        PilotoDAO pilotoDAO = this.model.getPilotos();
        List<Piloto> pilotos = pilotoDAO.getAll();
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

    private void showRaceSimulationMenu() {
        Menu subMenu = new Menu("RaceSimulation", new String[]{
                "Race Details",
                "Configure Race",
                "Race Simulation",
        });

        // Set up the handlers for each option in the submenu
        subMenu.setHandler(1, this::handleRaceDetails);
        subMenu.setHandler(2, this::handleConfigureRace);
        subMenu.setHandler(3, this::handleRaceSimulation);

        // Run the submenu
        subMenu.run();
    }

    private void handleRaceDetails() {
        Campeonato campeonato = this.model.getCampF().getCampeonatoAtual();
        int contador = campeonato.getContador();
        Corrida nextCorrida = campeonato.getCorridas().get(contador);

        System.out.println("\tCorrida " + (contador + 1) + "/" + campeonato.getCorridas().size());
        System.out.println(" *** Circuito ***");
        System.out.println("-> " + nextCorrida.getCircuito().getNomeCircuito());
        System.out.println("Voltas: " + nextCorrida.getVoltas());
        System.out.println("Meteorologia: " + nextCorrida.getMeteorologia());


        showRaceSimulationMenu();
    }

    private void handleConfigureRace() {
        Campeonato campeonato = this.model.getCampF().getCampeonatoAtual();
        List<Jogador> jogadoresLista = new ArrayList<>(campeonato.getJogadores());

        for (int j = 0; j < campeonato.getJogadores().size(); j++) {
            String resposta;
            if (jogadoresLista.get(j).getCarro().getCategoria().equalsIgnoreCase("C1") || jogadoresLista.get(j).getCarro().getCategoria().equalsIgnoreCase("C2")) {
                System.out.println("Player " + jogadoresLista.get(j).getNomeJogador());
                if (jogadoresLista.get(j).getAfinacoes() > 0) {
                    System.out.println("You have " + jogadoresLista.get(j).getAfinacoes() + " tunings.");
                    System.out.println("Do you want to Tune your car for this Race? (yes/no)");
                    System.out.print("Option: ");
                    while (true) {
                        resposta = scin.nextLine();
                        if (resposta.equalsIgnoreCase("yes")) {
                            System.out.println("Current Downforce: " + jogadoresLista.get(j).getDownforce());
                            System.out.println("New Downforce: (between 0 and 1)");
                            float downforce = 0.0f;
                            while (true) {
                                downforce = Float.parseFloat(scin.nextLine());
                                if (downforce <= 1 || downforce >= 0) break;
                                else System.out.println("Invalid input. Please try again.");
                            }
                            jogadoresLista.get(j).efetuaAfinacao();
                            jogadoresLista.get(j).alteraDownforce(downforce);
                            // System.out.println(jogadoresLista.get(j).carro.getDownforce());
                            break;
                        } else if (resposta.equalsIgnoreCase("no")) {
                            break;
                        } else if ((!resposta.equalsIgnoreCase("yes")) && (!resposta.equalsIgnoreCase("no")) && (!resposta.isEmpty())) {
                            System.out.println("Invalid input. Please try again.");
                            System.out.println(jogadoresLista.get(j));
                            System.out.println("Do you want to Tune your car for this Race? (yes/no)");
                            System.out.print("Option: ");
                        }
                    }
                } else System.out.println("You don't have tunings.");
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
                    jogadoresLista.get(j).escolhePneus(pneus);
                    //this.model.escolhePneus(jogadoresLista.get(j).getNomeJogador(), campeonato.getNome(), pneus);
                    System.out.println("Select your Engine Mode: (conservador/normal/agressivo)");
                    System.out.print("Option: ");
                    motor = scin.nextLine();
                    if ((motor.equalsIgnoreCase("conservador")) || (motor.equalsIgnoreCase("normal")) || (motor.equalsIgnoreCase("agressivo"))) {
                        // i ou i-1 ??
                        jogadoresLista.get(j).escolheMotor(motor);
                        //this.model.escolheMotor(jogadoresLista.get(j).getNomeJogador(), campeonato.getNome(), motor);
                        // System.out.println(jogadoresLista.get(j).getCarro().getMotor());
                        // System.out.println(jogadoresLista.get(j).getCarro().getPneus());
                        // System.out.println(jogadoresLista.get(j).getId());
                        break;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                    }
                } else System.out.println("Invalid input. Please try again.");
            }
        }
        showRaceSimulationMenu();
    }

    private void handleRaceSimulation() {
        Campeonato campeonato = this.model.getCampF().getCampeonatoAtual();

        System.out.println(campeonato.simulaCorrida());

        if (campeonato.getCircuitos().size() != campeonato.getContador())
            showRaceSimulationMenu();
        else {
            System.out.println("----Championship Concluded!----");
            campeonato.printFinal();
            System.out.println();
            this.model.getCampF().saveResults(getUsernames(campeonato), this.model.getUtilizadores());
            flag = false;
            showSubMenu();
        }
    }

    private Map<String, String> getUsernames(Campeonato campeonato) {
        Map<String, String> usernames = new HashMap<>();
        this.model.setUtilizadores(UtilizadorDAO.getInstance());
        String username, res;

        for (Jogador j : campeonato.getJogadores()) {
            res = "";
            username = "";
            System.out.println("Want to login Jogador " + j.getNomeJogador() + "? (yes/no)");
            while (true) {
                // System.out.println("Want to login Jogador " + j.getNomeJogador() + "? (yes/no)");
                res = scin.nextLine();
                if (res.equalsIgnoreCase("yes")) {
                    System.out.println("Username of the Jogador " + j.getNomeJogador() + ": (0 to cancel)");
                    while (true) {
                        username = scin.nextLine();
                        if (!(username.equalsIgnoreCase("0"))) {
                            usernames.put(j.getNomeJogador(), username);
                            break;
                        } else {
                            System.out.println("Username of the Jogador " + j.getNomeJogador() + ": (0 to cancel)");
                        }
                    }
                    break;
                }
                if (res.equalsIgnoreCase("no")) {
                    break;
                }
                else if (!res.isEmpty()){
                    System.out.println("Want to login Jogador " + j.getNomeJogador() + "? (yes/no)");
                }
            }
        }
        return usernames;
    }

    private void showRankings() {
        this.model.setUtilizadores(UtilizadorDAO.getInstance());
        List<String> rankings = this.model.getUtilizadores().showRanking();
        System.out.println("----Racing Manager Ranking----");
        for (String s : rankings) {
            System.out.println(s);
        }
    }
}



