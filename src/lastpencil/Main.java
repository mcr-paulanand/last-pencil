package lastpencil;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String PLAYER_USER = "John";
    private static final String PLAYER_BOT = "Jack";
    private static final String PENCIL_CHAR = "|";
    private static final int MIN_PENCIL_TAKEN = 1;
    private static final int MAX_PENCIL_TAKEN = 3;

    public static void main(String[] args) {

        int currentPencils;
        String firstPlayer;
        String[] playerOrder;

        currentPencils = getInitialPencilCount();
        firstPlayer = getFirstPlayerName();
        playerOrder = getPlayerTurnAsArray(firstPlayer);

        mainLoop: while (true) {
            for (String player : playerOrder) {
                if (currentPencils == 0) {
                    System.out.println(player + " won!");
                    break mainLoop;
                }

                System.out.println(PENCIL_CHAR.repeat(currentPencils));
                System.out.println(player + "'s turn!");

                switch (player) {
                    case PLAYER_USER -> currentPencils -= takePencilUser(currentPencils);
                    case PLAYER_BOT -> currentPencils -= takePencilBot(currentPencils);
                }
            }
        }
    }

    private static String[] getPlayerTurnAsArray(String firstPlayer) {
        String[] playerTurnArray = new String[2];
        playerTurnArray[0] = firstPlayer;
        playerTurnArray[1] = firstPlayer.equals(PLAYER_USER) ? PLAYER_BOT : PLAYER_USER;

        return playerTurnArray;
    }

    private static int getInitialPencilCount() {
        int pencilCount;
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many pencils would you like to use:");

        while (true) {
            try {
                pencilCount = Integer.parseUnsignedInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
                continue;
            }

            if (pencilCount == 0) {
                System.out.println("The number of pencils should be positive");
                continue;
            }

            return pencilCount;
        }
    }

    private static String getFirstPlayerName() {
        String playerName;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who will be the first (" + PLAYER_USER + ", " + PLAYER_BOT + ") :");

        while (true) {
            playerName = scanner.nextLine();

            if (playerName.equals(PLAYER_USER) || playerName.equals(PLAYER_BOT)) {
                return playerName;
            } else {
                System.out.println("Choose between " + PLAYER_USER + " and " + PLAYER_BOT);
            }
        }
    }

    private static int takePencilUser(int currentPencilCount) {
        int pencilTaken = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                pencilTaken = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {}

            if (pencilTaken < MIN_PENCIL_TAKEN || pencilTaken > MAX_PENCIL_TAKEN) {
                System.out.println("Possible values: '1', '2' or '3'");
                continue;
            }

            if (pencilTaken > currentPencilCount) {
                System.out.println("Too many pencils were taken");
                continue;
            }

            return pencilTaken;
        }
    }

    private static int takePencilRandom(int currentPencilCount) {
        int pencilTaken;
        Random random = new Random();

        do {
            pencilTaken = random.nextInt(MAX_PENCIL_TAKEN - MIN_PENCIL_TAKEN + 1) + MIN_PENCIL_TAKEN;
        } while (pencilTaken > currentPencilCount);

        return pencilTaken;
    }

    private static int takePencilBot(int currentPencilCount) {
        int pencilTaken = 0;

        if (currentPencilCount >= 5 && (currentPencilCount - 5) % 4 == 0) pencilTaken = takePencilRandom(currentPencilCount);
        if (currentPencilCount >= 4 && (currentPencilCount - 4) % 4 == 0) pencilTaken = 3;
        if (currentPencilCount >= 3 && (currentPencilCount - 3) % 4 == 0) pencilTaken = 2;
        if (currentPencilCount >= 2 && (currentPencilCount - 2) % 4 == 0) pencilTaken = 1;
        if (currentPencilCount == 1) pencilTaken = 1;

        System.out.println(pencilTaken);
        return pencilTaken;
    }

}
