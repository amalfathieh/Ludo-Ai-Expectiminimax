import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // الثوابت والمتغيرات العامة
    private static final String[] colorList = {"Blue", "Green", "Red", "Yellow"};
    private static final List<Player> playerList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in); // تعريف Scanner مرة واحدة فقط

    private static Player currentPlayer;
    private static Game game;
    private static Expectiminimax exp;
    private static Board board;
    private static int countWinnerPlayers = 0;

    public static void main(String[] args) {
        System.out.println("=== Welcome to Ludo Game ===");

        // 1. إعداد اللعبة
        initializeGame();

        // 2. تهيئة الكائنات الأساسية
        board = new Board(playerList);
        game = new Game(playerList);
        currentPlayer = playerList.get(0);
        game.setCurrentPlayer(currentPlayer);

        // تهيئة الذكاء الاصطناعي
        exp = new Expectiminimax(playerList, currentPlayer);

        // 3. بدء حلقة اللعبة
        runGameLoop();

        // إغلاق السكانر عند انتهاء البرنامج تماماً
        scanner.close();
    }

    // --- قسم إعداد اللعبة ---
    private static void initializeGame() {
        int numPlayers = getValidInput("Choose the number of players (2-4):", 2, 4);

        System.out.println("[1] Play with friends (Human vs Human).");
        System.out.println("[2] Play with Computer (Human vs CPU).");
        System.out.println("[3] Watch Computer play (CPU vs CPU).");
        int mode = getValidInput("Select game mode:", 1, 3);

        // إنشاء اللاعبين بناءً على النمط المختار
        switch (mode) {
            case 1: // Human vs Human
                for (int i = 0; i < numPlayers; i++) {
                    playerList.add(new Player(colorList[i]));
                }
                break;
            case 2: // Human vs CPU
                playerList.add(new Player(colorList[0])); // اللاعب الأول بشر
                for (int i = 1; i < numPlayers; i++) {
                    Player p = new Player(colorList[i] + " (Computer)");
                    p.setComputer(true);
                    playerList.add(p);
                }
                break;
            case 3: // CPU vs CPU
                for (int i = 0; i < numPlayers; i++) {
                    Player p = new Player(colorList[i] + " (Computer)");
                    p.setComputer(true);
                    playerList.add(p);
                }
                break;
        }

        System.out.println("\nGame Started! Players joined:");
        for (Player p : playerList) {
            System.out.println("- " + p);
        }
        System.out.println("==============================\n");
    }

    // --- حلقة اللعبة الرئيسية ---
    private static void runGameLoop() {
        boolean gameRunning = true;

        while (gameRunning) {
            // التحقق مما إذا انتهت اللعبة (بقي لاعب واحد فقط)
            if (countWinnerPlayers >= playerList.size() - 1) {
                System.out.println("\n=== GAME OVER ===");
                board.printBoard();
                printRankPlayers();
                break;
            }

            // طباعة اللوحة
            System.out.println();
            board.printBoard();
            System.out.println("\n--------------------------------");
            System.out.println("Turn: " + currentPlayer);

            // رمي النرد
            currentPlayer.rollDice();
            int diceValue = currentPlayer.getNumberRolled();
            System.out.println(currentPlayer + " rolled a: [" + diceValue + "]");

            // التحقق من وجود حركات ممكنة
            if (!game.movesArePossible(currentPlayer, diceValue)) {
                System.out.println("No possible moves for " + currentPlayer + ". Skipping turn...");
                setNextPlayer();
                continue;
            }

            boolean turnSuccessful;
            if (currentPlayer.isComputer()) {
                turnSuccessful = handleComputerTurn(diceValue);
            } else {
                turnSuccessful = handleHumanTurn(diceValue);
            }

            // معالجة ما بعد الحركة (الفوز أو الدور التالي)
            if (turnSuccessful) {
                if (currentPlayer.hasWon()) {
                    handlePlayerWin();
                    // إذا بقي لاعب واحد فقط، تنتهي اللعبة في اللفة القادمة
                }

                // قاعدة الرقم 6: اللاعب يلعب مرة أخرى
                if (currentPlayer.isHasRolledSix() && !currentPlayer.hasWon()) {
                    System.out.println("Rolled a 6! " + currentPlayer + " gets another turn!");
                    // لا نستدعي setNextPlayer()، الحلقة ستعيد نفس اللاعب
                } else {
                    setNextPlayer();
                }
            }
        }
    }

    // --- معالجة دور الكمبيوتر ---
    // === في ملف Main.java - دالة handleComputerTurn ===

    private static boolean handleComputerTurn(int diceValue) {
        try { Thread.sleep(1000); } catch (InterruptedException e) {} // تأخير للرؤية

        Game bestMoveState = exp.findBestMove(game, currentPlayer, 2, diceValue);

        if (bestMoveState == null || bestMoveState.piece == null) {
            System.out.println("Computer has no valid moves.");
            return true; // ننهي الدور
        }

        // ربط الحجر المنسوخ بالأصلي
        Piece originalPiece = exp.mapClonedPieceToOriginal(bestMoveState, game, bestMoveState.piece);

        if (originalPiece != null) {
            if (!originalPiece.isOut() && diceValue == 6) {
                return game.takePieceOut(originalPiece);
            } else {
                return game.movePiece(originalPiece, diceValue);
            }
        }
        return false;
    }
    // --- معالجة دور البشر ---
    private static boolean handleHumanTurn(int diceValue) {
        System.out.println("Commands: 't [1-4]' to take out (needs 6), 'm [1-4]' to move.");

        while (true) {
            System.out.print("Enter command > ");
            String command = scanner.next();

            // التحقق من صحة رقم القطعة
            int pieceIndex;
            if (scanner.hasNextInt()) {
                pieceIndex = scanner.nextInt() - 1; // تحويل من 1-4 إلى 0-3
            } else {
                System.out.println("Invalid piece number format.");
                scanner.nextLine(); // تنظيف السطر
                continue;
            }

            if (pieceIndex < 0 || pieceIndex > 3) {
                System.out.println("Invalid piece number! Choose 1 to 4.");
                continue;
            }

            Piece piece = currentPlayer.getPiece(pieceIndex);
            boolean success = false;

            if (command.equalsIgnoreCase("t")) {
                if (!currentPlayer.isHasRolledSix()) {
                    System.out.println("You need a 6 to take a piece out!");
                    continue;
                }
                success = game.takePieceOut(piece);
                if (!success) System.out.println("Cannot take this piece out (maybe already out?).");

            } else if (command.equalsIgnoreCase("m")) {
                success = game.movePiece(piece, diceValue);
                if (!success) System.out.println("Cannot move this piece (blocked or invalid path).");

            } else {
                System.out.println("Unknown command. Use 't' or 'm'.");
                continue;
            }

            if (success) return true;
        }
    }

    // --- دوال مساعدة ---

    private static void handlePlayerWin() {
        countWinnerPlayers++;
        currentPlayer.setPlayerRank(countWinnerPlayers);
        System.out.println("🎉🎉 Player " + currentPlayer + " has WON in position " + countWinnerPlayers + "! 🎉🎉");
    }

    private static void setNextPlayer() {
        int currentIndex = playerList.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % playerList.size();
        Player nextPlayer = playerList.get(nextIndex);

        // تخطي اللاعبين الذين فازوا بالفعل
        while (nextPlayer.hasWon()) {
            nextIndex = (nextIndex + 1) % playerList.size();
            // حماية من الحلقة اللانهائية إذا انتهت اللعبة
            if (nextIndex == currentIndex) break;
            nextPlayer = playerList.get(nextIndex);
        }

        currentPlayer = nextPlayer;
        game.setCurrentPlayer(currentPlayer);
    }

    private static int getValidInput(String message, int min, int max) {
        int input;
        while (true) {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
            } else {
                scanner.next(); // تجاهل المدخل الخاطئ
            }
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
        }
    }

    public static void printRankPlayers() {
        System.out.println("\n--- Final Rankings ---");
        for (Player player : playerList) {
            if (player.getPlayerRank().equals("lose"))
                System.out.println(player + ": Did not finish.");
            else
                System.out.println(player + ": Finished " + player.getPlayerRank());
        }
    }
}
