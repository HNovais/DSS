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
        for (int i = 0;i<75;i++){
            System.out.println();
        }
    }

    public TextUI() {
        // Criar o menu
        this.menu = new Menu("Sign in / Sign up", new String[]{
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

    // Ver o que acontece qd apertas 0
    private void showSubMenu() {
        Menu subMenu = new Menu(new String[] {
                "Play Championship",
                "Race Simulation",
        });

        // Set up the handlers for each option in the submenu
        subMenu.setHandler(1, this::showMenuCamp);
        //subMenu.setHandler(2, this::handleRaceSimulation);

        // Run the submenu
        subMenu.run();
    }

    private void showMenuCamp(){
        List<String> campeonatos = campeonatoDAO.getCampsName();
        int campSize = campeonatos.size();
        clearWindow();
        System.out.println("Select one of the following Championships:");
        for(int i = 0; i < campSize; i++) {
            String str = Integer.toString(i+1);
            System.out.println(str + " - " + campeonatos.get(i));
        }
        System.out.println("0 - Exit");
        System.out.print("-> ");
        int campeonato = scin.nextInt();

        if(campeonato == 0) {
           showSubMenu();
        }
        Campeonato campSelected = campeonatoDAO.get(campeonatos.get(campeonato));
        clearWindow();
        showMenuCirc(campSelected);
    }

    private void showMenuCirc(Campeonato campeonato){
        List<Circuito> circuitos = campeonatoDAO.getCircuitosCampeonato(campeonato);

        List<String> circuitosNome = new ArrayList<>();
        for(Circuito c : circuitos) {
            circuitosNome.add(c.getNomeCircuito());
        }
        int circuitSize = circuitosNome.size();

        System.out.println("Circuits in " + campeonato.getNome() + ":");
        for(int i = 0; i < circuitSize; i++) {
            System.out.println(circuitosNome.get(i));
        }
        System.out.println("Press 0 to go back and 1 to move on!");
        System.out.print("-> ");
        int circuito = scin.nextInt();

        if(circuito == 0) {
            showMenuCamp();
        }
        else if(circuito == 1){
            clearWindow();
            campeonato.setCircuitos(circuitos);
            handlePlayChampionship(campeonato);
        }
        else System.exit(0);
    }

    private void handlePlayChampionship(Campeonato campeonato) {
        int nJogadores = -1;
        List<Jogador> jogadores = new ArrayList<>();

        do{
            System.out.println("How many players will play (Max: " + MAX + "): ");
            nJogadores = scin.nextInt();
            clearWindow();
        } while (nJogadores <= 1 || nJogadores >= 7);

        for(int i = 1; i <= nJogadores; i++){
            Jogador jogador = new Jogador();
            System.out.println("Player " + i);
            //jogador.setId(scin.nextLine());
            jogador.setCarro(menuCarro());
            jogador.setPiloto(menuPiloto());
            campeonato.jogadores.add(jogador);
        }

        showSubMenu();
    }

    private Carro menuCarro(){
        List<Carro> carros = CarroDAO.getInstance().getAll();
        List<String> carroString = new ArrayList<>();
        int nCarros = -1;

        for(Carro c : carros){
            carroString.add(carros.toString());
        }

        //do{
        System.out.println("Select one of the following Cars:");
        for(int i = 0; i < carros.size(); i++) {
            String str = Integer.toString(i+1);
            System.out.println(str + " - " + carroString.get(i));
        }

        //System.out.println(carroString);
        System.out.print("-> ");
        nCarros = scin.nextInt();
        clearWindow();
        //} while (nCarros <= 0 || nCarros >= carros.size());

        return carros.get(nCarros);
    }

    private Piloto menuPiloto(){
        List<Piloto> pilotos = PilotoDAO.getInstance().getAll();
        List<String> pilotoString = new ArrayList<>();
        int nPiloto = -1;

        for(Piloto p : pilotos){
            pilotoString.add(pilotos.toString());
        }

        System.out.println("Select one of the following Pilots:");
        for(int i = 0; i < pilotos.size(); i++) {
            String str = Integer.toString(i+1);
            System.out.println(str + " - " + pilotoString.get(i));
        }
        //System.out.println(carroString);
        System.out.print("-> ");
        nPiloto = scin.nextInt();
        clearWindow();

        return pilotos.get(nPiloto);
    }

/*
    private void handleConfigureChampionship(Campeonato campeonato){
        for(int i = 0; i <= campeonato.circuitos.size(); i++){
            Corrida nextCorrida = campeonato.nextCorrida(i);
            nextCorrida.addParticipantes(campeonato.getJogadores());

            System.out.println("---------- Circuito: " + nextCorrida.getCircuito().getNomeCircuito() + " ----------");
            System.out.println("\n\n\n---------- Meteorologia: " + nextCorrida.getMeteorologia() + " ----------");
            // FAZER ALTERACOES AO CARRO
            nextCorrida.simulaCorrida();
        }
    }*/


    /*

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

     */

}



