
import java.util.ArrayList;
import java.util.List;

public class Expectiminimax {


    private List<Player> players;
    Player currentPlayer;
    static int counter = 0;

    public Expectiminimax(List<Player> players, Player currentPlayer) {
        this.players = players;
        this.currentPlayer = currentPlayer;
    }

    public Game findBestMove(Game game, Player currentPlayer, int depth, int diceValue) {
        List<Game> possibleMoves = generatePossibleMoves(game, currentPlayer, diceValue);
        Game bestMove = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        counter = 0;
        for (Game move : possibleMoves) {
            double moveValue = expectiminimax(game, move, depth - 1, false);
            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }
        System.out.println("evaluate: " + bestValue);
        System.out.println("count node: " + counter);

        return bestMove;
    }

    private double expectiminimax(Game game, Game move, int depth, boolean isMaxPlayer) {
        counter++;
        if (depth == 0 || game.isGameOver()) {
            return evaluateState3(move);
        }

        if (isMaxPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Game nextMove : generatePossibleMoves(game, game.currentPlayer, game.currentPlayer.getNumberRolled())) {
                double eval = expectiminimax(game, nextMove, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            double expectedValue = 0;
            for (int i = 1; i <= 6; i++) {
                double probability = 1.0 / 6; // Assuming fair dice
                expectedValue += probability * expectiminimax(game, move, depth - 1, true);
            }
            return expectedValue;
        }
    }

    private List<Game> generatePossibleMoves(Game game, Player player, int diceValue) {
        // Implement logic to generate possible moves based on the current game state and dice value
        List<Game> possibleMoves = new ArrayList<>();
        // Check if the player can move a piece from home
        if (diceValue == 6) {
            for (Piece piece : player.getPieces()) {
                if (game.canTakePieceOut(piece)) {
                    possibleMoves.add(new Game(player, piece, 0)); // Move a piece from home to start
                }
            }
        }
        for (Piece piece : player.getPieces()) {
            if (game.canMovePiece(piece, diceValue))
                possibleMoves.add(new Game(player, piece, piece.getIndexPath() + diceValue)); // Move a piece from home to start
        }
        return possibleMoves;
    }

    private double evaluateState3(Game game) {
        //لاعب وقطعة ولوين اروح
        //قتل
        //اخراج من بيت
        //مكان امن
        //قرب نهاية
        double score = 0;
        for (Player player : players) {
            for (Piece piece : player.getPieces()) {
                if (!piece.isOut()) {
                    score += (game.currentPlayer.equals(game.currentPlayer)) ? 800 : -800;
                } else {
                    if (game.isissame(player, game.piece, game.currentPlayer, game.newPosition))
                        score += (game.currentPlayer.equals(currentPlayer)) ? 1000 : -1000;
                    else {
                        if ( player.getPath().get(game.newPosition).equals("star"))
                            score += (game.currentPlayer.equals(currentPlayer)) ? 400 : -400;
                        if (player.getPath().get(game.newPosition).equals("safe"))
                            score += (game.currentPlayer.equals(currentPlayer)) ? 10 : -10;

                        if (player.hasWon()) {
                            score += (game.currentPlayer.equals(currentPlayer)) ? 500 : -500;
                        }
                    }
                }
            }}
        return score;
    }

    public int evaluateState(Game move) {
        int score = 0;

        // عدد القطع المكتملة
        for (Piece piece : move.currentPlayer.getPieces()) {
            if (piece.isCompleted()) {
                score += 50;
            } else if (piece.isOut()) {
                int c= piece.getIndexPath();
                if(currentPlayer.getPath().get(c).getType().equals("safe") || currentPlayer.getPath().get(c).getType().equals("star"))
                    score+=50;
//                score += piece.getIndexPath()/3; // مسافة الحركة الحالية
            }
        }

        for (Player opponent : players) {
            if (opponent.getColor().equals(currentPlayer.getColor())) continue;
            for (Piece opponentPiece : opponent.getPieces()) {
                if (opponentPiece.isOut() && opponentPiece.getIndexPath() < 10) {
                    score += 10;
                }
                if(!opponentPiece.isOut()){
                    score+=30;
                }
            }
        }
        return score;
    }

}