package CounterStriker.core;

import CounterStriker.common.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine {
    private final Controller controller;
    private final BufferedReader reader;

    public EngineImpl() {
        this.controller = new ControllerImpl();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String result = null;
            try {
                result = processInput();

                if (result.equals(Command.Exit.name())) {
                    break;
                }

            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.reader.readLine();
        String[] tokens = input.split("\\s");

        Command command = Command.valueOf(tokens[0]);
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        String result = null;

        switch (command) {
            case AddPlayer:
                result = this.addPlayer(data);
                break;
            case Exit:
                result = Command.Exit.name();
                break;
            case StartGame:
                result = this.start();
                break;
            case Report:
                result = this.report();
                break;
            case AddGun:
                result = this.addGun(data);
                break;
        }

        return result;
    }

    private String addGun(String[] data) {
        String gunType = data[0];
        String gunName = data[1];
        int bulletsCount = Integer.parseInt(data[2]);

        return controller.addGun(gunType, gunName, bulletsCount);
    }

    private String report() {
        return controller.report();
    }

    private String addPlayer(String[] data) {
        String playerType = data[0];
        String username = data[1];
        int health = Integer.parseInt(data[2]);
        int armor = Integer.parseInt(data[3]);
        String gunName = data[4];

        return controller.addPlayer(playerType, username, health, armor, gunName);
    }

    private String start() {
        return controller.startGame();
    }
}
