import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String[] colorList = { "Blue", "Green", "Red", "Yellow",};

    private static final List<Player> playerList= new ArrayList<Player>();
    private static Player currentPlayer;
    private static Game game;
    private static Expectiminimax exp;
    private static Board board;

    static int countWinnerPlayers =0;
    public static void main(String[] args) {

        boolean inputCorrect = false;
        Scanner scanner = null;
        System.out.println("Choose the number of players (2-4).");

        while (!inputCorrect ){
            scanner = new Scanner(System.in);
            int number = scanner.nextInt();

            if(number > 1 && number < 5) {
                inputCorrect = true;

                System.out.println("[1] Play with a friend.");
                System.out.println("[2] Play with (ExpectMiniMax).");
                System.out.println("[3] (ExpectMiniMax) with (ExpectMiniMax).");
                boolean methodCurrect = false;
                while (!methodCurrect){
                    int method = scanner.nextInt();
                    if(method >0 && method <4)
                        methodCurrect=true;
                        switch (method) {
                            case 1: {
                                for (int i = 0; i < number; i++) {
                                    playerList.add(new Player(colorList[i]));
                                }
                                break;
                            }
                            case 2: {
                                playerList.add(new Player(colorList[0]));
                                for (int i = 1; i < number; i++) {
                                    Player p = new Player(colorList[i] + "Computer");
                                    playerList.add(p);
                                    p.setComputer(true);
                                }
                                break;
                            }
                            case 3: {
                                for (int i = 0; i < number; i++) {
                                    Player p = new Player(colorList[i] + "Computer");
                                    playerList.add(p);
                                    p.setComputer(true);
                                }
                                break;
                            }
                            default:
                                System.out.println("Something went wrong. Type in the number of 1 or 2 or 3.");
                        }
                    }
            } else {
                System.out.println("Something went wrong. Type in the number of players (2-4).");
            }
        }

        for (int i = 0; i< playerList.size(); i++){
            System.out.println(playerList.get(i)+ " has joined to game");
        }

        currentPlayer = playerList.get(0);
        System.out.println(currentPlayer + " starts the game.");

        board = new Board(playerList);

        game = new Game(playerList);

        game.setCurrentPlayer(currentPlayer);
        exp = new Expectiminimax(playerList, currentPlayer );
        runGame();
    }

    public static void runGame() {

        Scanner scanner = null;
        boolean gameCompleted = false;
        game: while(!gameCompleted) {

            System.out.println();
            board.printBoard();
            System.out.println();
            currentPlayer .rollDice();

            boolean movesArePossible = game.movesArePossible(currentPlayer , currentPlayer .getNumberRolled());

            if(!movesArePossible) {
                System.out.println(currentPlayer + " has rolled " + currentPlayer .getNumberRolled() + " There are no possible moves. Moving on...");
                setNextPlayer();
                continue;
            }

            if(currentPlayer .isComputer()){
                boolean turnComplete2 = false;
                while (!turnComplete2) {

                    boolean commandSuccessful = false;
                    System.out.println(currentPlayer + " has rolled " + currentPlayer.getNumberRolled());

                    Game move = exp.findBestMove(game, currentPlayer, 4, currentPlayer.getNumberRolled());
                    if (move == null) {
                        System.out.println(currentPlayer + " has rolled " + currentPlayer.getNumberRolled() + " There are no possible moves. Moving on...");
                        setNextPlayer();
                        continue;
                    }else {
                        if (currentPlayer.isHasRolledSix()) {
                            if (move.newPosition == 0)
                                commandSuccessful = game.takePieceOut(move.piece);
                            else
                                commandSuccessful = game.movePiece(move.piece, currentPlayer.getNumberRolled());
                        } else {
                            commandSuccessful = game.movePiece(move.piece, currentPlayer.getNumberRolled());
                        }
                    }
                    if (commandSuccessful) {

                        if (currentPlayer .hasWon()) {

                            countWinnerPlayers++;
                            currentPlayer.setPlayerRank(countWinnerPlayers);
                            System.out.println("Player " + currentPlayer + " has won the game in " + countWinnerPlayers + " place!");
//                        break game;

                            if (countWinnerPlayers == playerList.size() - 1) {
                                board.printBoard();
                                break game;
                            } else {
                                setNextPlayer();
                                continue;
                            }
                        }

                        if (currentPlayer .isHasRolledSix()) {
                            System.out.println(currentPlayer + " has rolled a six, meaning that " +
                                    "they get another turn. Roll the dice.");
                            continue game;
                        }

                        setNextPlayer();

                        turnComplete2 = true;
                    }
                }
            }
            else {
                System.out.println(currentPlayer + " has rolled " + currentPlayer .getNumberRolled() +
                        ". Commands:\n" +
                        "\"t (piece number)\" without the brackets to take a piece out of the home circle;\n" +  //Type t 1 to move piece 1 out of home circle , t 2 for piece two and t 3 and so on (t(spacebar)(piece number))
                        "\"m (piece number)\" without the brackets to move a piece."); //Type m 1 to move piece 1, m 2 to move piece 2, m 3 and so on (m(spacebar)(piece number))

                boolean turnComplete = false;

                while (!turnComplete) {

                    scanner = new Scanner(System.in);

                    String command = null;
                    boolean commandSuccessful = false;

                    try {
                        command = scanner.next();
                    } catch (Exception e) {
                        System.out.println("Invalid command. Try again.");
                        continue;
                    }

                    if (command.equals("t")) {

                        // if did not roll 6, can't take a piece out
                        if (!currentPlayer .isHasRolledSix()) {
                            System.out.println("Invalid move. Pieces can be taken out only " +
                                    "when a 6 has been rolled.");
                            continue;
                        }

                        int pieceNumber = 0;

                        try {
                            pieceNumber = scanner.nextInt() - 1;
                        } catch (Exception e) {
                            System.out.println("Invalid piece number provided. Try again.");
                            continue;
                        }

                        if (pieceNumber < 0 || pieceNumber > 3) {

                            System.out.println("Wrong piece number!");
                            continue;

                        }

                        Piece piece = currentPlayer .getPiece(pieceNumber);

                        commandSuccessful = game.takePieceOut(piece);

                    } else if (command.equals("m")) {

                        int pieceNumber = 0;

                        try {
                            pieceNumber = scanner.nextInt() - 1;
                        } catch (Exception e) {
                            System.out.println("Invalid piece number provided. Try again.");
                            continue;
                        }

                        if (pieceNumber < 0 || pieceNumber > 3) {

                            System.out.println("Wrong piece number!");
                            continue;

                        }

                        Piece piece = currentPlayer .getPiece(pieceNumber);
                        int squareAmount = currentPlayer .getNumberRolled();

                        commandSuccessful = game.movePiece(piece, squareAmount);

                    } else {

                        System.out.println("Invalid command. Try again.");
                        continue;

                    }

                    if (commandSuccessful) {

                        if (currentPlayer .hasWon()) {
                            countWinnerPlayers++;
                            currentPlayer.setPlayerRank(countWinnerPlayers);
                            System.out.println("Player " + currentPlayer + " has won the game in " + countWinnerPlayers + " place!");

                            if (countWinnerPlayers == playerList.size() - 1) {
                                board.printBoard();
                                break game;
                            } else {
                                setNextPlayer();
                                turnComplete = true;
                                continue;
                            }
                        }

                        if (currentPlayer .isHasRolledSix()) {
                            System.out.println(currentPlayer + " has rolled a six, meaning that " +
                                    "they get another turn. Roll the dice.");
                            continue game;
                        }

                        setNextPlayer();
                        turnComplete = true;

                    } else System.out.println("Move cannot be completed. Try something else.");

                }
            }

        }
        printRankPlayers();
    }

    private static void setNextPlayer() {
        do {
            currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % playerList.size());
        } while (currentPlayer.hasWon());
        game.setCurrentPlayer(currentPlayer);
    }

    public static void printRankPlayers(){
        for (Player player : playerList) {
            if (player.getPlayerRank().equals("lose"))
                System.out.println("Player " + player + " has lose the game 😥");
            else
                System.out.println("Player " + player + " has won the game in " + player.getPlayerRank() + " place!");
        }
    }

}
