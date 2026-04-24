import java.util.ArrayList;
import java.util.List;

public class Expectiminimax {

    private final String aiColor;
    private static int nodesVisited = 0;

    public Expectiminimax(List<Player> players, Player aiPlayer) {
        this.aiColor = aiPlayer.getColor();
    }

    /**
     * الدالة الرئيسية: تجد أفضل حركة للـ AI
     */
    public Game findBestMove(Game gameState, Player currentPlayer, int depth, int diceValue) {
        nodesVisited = 0;
        List<Game> possibleMoves = generatePossibleMoves(gameState, currentPlayer, diceValue);

        if (possibleMoves.size() == 1) {
            return possibleMoves.get(0);
        }

        if (possibleMoves.isEmpty()) {
            System.out.println("⚠️ No possible moves for dice: " + diceValue);
            return null;
        }

        Game bestMove = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        for (Game move : possibleMoves) {
            // ⭐ التعديل هنا:
            // 1. إذا لم يكن النرد 6 (لأن 6 يعطيك دور إضافي)، نقوم بالتبديل للاعب التالي
            if (diceValue != 6) {
                switchToNextPlayer(move);
            }

            // 2. نستدعي الدالة ونمرر true (لأن الخطوة القادمة في اللعبة هي رمي النرد)
            double moveValue = expectiminimax(move, depth - 1, true);

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }

        System.out.println("🎯 Best Move Value: " + String.format("%.2f", bestValue));
        System.out.println("🔍 Nodes Visited: " + nodesVisited);
        return bestMove;
    }

    /**
     * خوارزمية Expectiminimax التكرارية
     */
    private double expectiminimax(Game state, int depth, boolean isChanceNode) {
        nodesVisited++;

        if (depth == 0 || state.checkGameOver()) {
            return evaluateState(state);
        }

        if (isChanceNode) {
            // === عقدة الفرصة (رمي النرد) ===
            double expectedValue = 0;
            double probability = 1.0 / 6.0;

            for (int dice = 1; dice <= 6; dice++) {
                Game stateAfterDice = cloneGameState(state);

                // تحديث النرد باستخدام الـ Setters
                Player cp = stateAfterDice.getCurrentPlayer();
                if (cp != null) {
                    cp.setNumberRolledValue(dice);
                    cp.setHasRolledSixValue(dice == 6);
                }

                double val = expectiminimax(stateAfterDice, depth - 1, false);
                expectedValue += probability * val;
            }
            return expectedValue;

        } else {
            // === عقدة القرار (الـ AI يختار حركة) ===
            Player cp = state.getCurrentPlayer();
            if (cp == null) return evaluateState(state);

            int diceValue = cp.getNumberRolledValue();
            List<Game> moves = generatePossibleMoves(state, cp, diceValue);

            if (moves.isEmpty()) {
                Game nextState = cloneGameState(state);
                switchToNextPlayer(nextState);
                return expectiminimax(nextState, depth, true);
            }

            double maxEval = Double.NEGATIVE_INFINITY;
            for (Game move : moves) {
                double eval = expectiminimax(move, depth - 1, true);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        }
    }

    /**
     * توليد الحركات الممكنة
     */
    private List<Game> generatePossibleMoves(Game game, Player player, int diceValue) {
        List<Game> moves = new ArrayList<>();

        //  إذا كان النرد 6 ويوجد حجر في المنزل، أخرجه فوراً ولا تفكر بغيره!
        if (diceValue == 6) {
            boolean tookPieceOut = false;
            for (Piece piece : player.getPieces()) {
                if (!piece.isOut()) {
                    Game newState = cloneGameState(game);
                    Piece clonedPiece = findClonedPiece(newState, piece);
                    if (clonedPiece != null && newState.takePieceOut(clonedPiece)) {
                        newState.piece = clonedPiece;
                        moves.add(newState);
                        tookPieceOut = true;
                        break; //
                    }
                }
            }
            // إذا نجحنا في إخراج حجر، أعد هذه الحركة فوراً وتجاهل تحريك الأحجار الأخرى
            if (tookPieceOut) {
                return moves;
            }
        }

        // إذا لم يكن النرد 6، أو لا يوجد أحجار في المنزل، قم بتوليد حركات التقدم العادية
        for (Piece piece : player.getPieces()) {
            if (piece.isOut() && !piece.isCompleted()) {
                if (game.canMovePiece(piece, diceValue)) {
                    Game newState = cloneGameState(game);
                    Piece clonedPiece = findClonedPiece(newState, piece);
                    if (clonedPiece != null && newState.movePiece(clonedPiece, diceValue)) {
                        newState.piece = clonedPiece;
                        moves.add(newState);
                    }
                }
            }
        }
        return moves;
    }

    /**
     * دالة التقييم (Heuristic)
     */
    private double evaluateState(Game state) {
        double score = 0;
        Player aiPlayer = state.findPlayerByColor(aiColor);
        if (aiPlayer == null) return 0;

        for (Player player : state.getPlayersList()) {
            boolean isAI = player.getColor().equals(aiColor);
            int multiplier = isAI ? 1 : -1;

            for (Piece piece : player.getPieces()) {
                if (piece.isCompleted()) {
                    score += multiplier * 5000; // زيادة الوزن
                    continue;
                }

                if (!piece.isOut()) {
                    score += multiplier * -500; // عقوبة قاسية للبقاء في المنزل (بدل +50)
                    continue;
                }

                // مكافأة الخروج من المنزل فوراً
                score += multiplier * 100;

                int index = piece.getIndexPath();
                score += multiplier * (index * 10); // وزن المسافة

                // مكافأة المناطق الآمنة
                Square square = player.getPath().get(index);
                if ("safe".equals(square.getType()) || "star".equals(square.getType())) {
                    score += multiplier * 200;
                }
            }
        }
        return score;
    }

    /**
     * نسخ عميق لحالة اللعبة
     */
    // في Expectiminimax.java - داخل cloneGameState
    private Game cloneGameState(Game original) {
        Game cloned = new Game(new ArrayList<>());

        // 1. نسخ اللاعبين أولاً
        for (Player origPlayer : original.getPlayersList()) {
            Player clonedPlayer = clonePlayer(origPlayer);
            cloned.getPlayersList().add(clonedPlayer);
        }

        // 2. تعيين اللاعب الحالي من القائمة المنسوخة (وليس الأصلية!)
        if (original.getCurrentPlayer() != null) {
            String currentColor = original.getCurrentPlayer().getColor();
            for (Player p : cloned.getPlayersList()) {
                if (p.getColor().equals(currentColor)) {
                    cloned.setCurrentPlayerPublic(p); // ✅ الآن يشير للنسخة
                    break;
                }
            }
        }
        return cloned;
    }
    /**
     * نسخ لاعب مع قطعه
     */
    private Player clonePlayer(Player original) {
        Player cloned = new Player(original.getColor());

        // نسخ البيانات المتغيرة باستخدام الـ Setters
        cloned.setNumberRolledValue(original.getNumberRolledValue());
        cloned.setHasRolledSixValue(original.getHasRolledSixValue());
        cloned.setIsComputerValue(original.getIsComputerValue());
        cloned.setPlayerRankValue(original.getPlayerRankValue());

        // نسخ حالة القطع
        Piece[] origPieces = original.getPiecesArray();
        Piece[] clonedPieces = cloned.getPiecesArray();

        for (int i = 0; i < 4; i++) {
            Piece origPiece = origPieces[i];
            Piece clonedPiece = clonedPieces[i];

            clonedPiece.setOut(origPiece.isOut());
            if (origPiece.isCompleted()) {
                clonedPiece.setCompleted();
            }
            clonedPiece.setIndexPath(origPiece.getIndexPath());

            updateSquareReferences(cloned, clonedPiece, origPiece);
        }

        return cloned;
    }

    /**
     * تحديث مراجع القطع في المربعات
     */
    private void updateSquareReferences(Player clonedPlayer, Piece clonedPiece, Piece originalPiece) {
        if (!originalPiece.isOut()) {
            int homeIndex = originalPiece.getPieceNum() - 1;
            Square clonedHome = clonedPlayer.getHome().get(homeIndex);
            clonedHome.getPieces().clear();
            clonedHome.getPieces().add(clonedPiece);
        } else {
            int pathIndex = originalPiece.getIndexPath();
            if (pathIndex >= 0 && pathIndex < clonedPlayer.getPath().size()) {
                Square clonedSquare = clonedPlayer.getPath().get(pathIndex);
                clonedSquare.getPieces().clear();
                clonedSquare.getPieces().add(clonedPiece);
            }
        }
    }

    /**
     * إيجاد قطعة منسوخة مطابقة
     */
    private Piece findClonedPiece(Game clonedGame, Piece originalPiece) {
        for (Player p : clonedGame.getPlayersList()) {
            if (p.getColor().substring(0, 1).equals(originalPiece.getColor())) {
                for (Piece cp : p.getPieces()) {
                    if (cp.getPieceNum() == originalPiece.getPieceNum()) {
                        return cp;
                    }
                }
            }
        }
        return null;
    }

    /**
     * التبديل للاعب التالي ⭐ هام جداً
     */
    private void switchToNextPlayer(Game state) {
        Player current = state.getCurrentPlayer();
        if (current == null) return;

        List<Player> players = state.getPlayersList();
        int idx = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getColor().equals(current.getColor())) {
                idx = i;
                break;
            }
        }
        if (idx == -1) return;

        int nextIdx = (idx + 1) % players.size();
        while (players.get(nextIdx).hasWon() && nextIdx != idx) {
            nextIdx = (nextIdx + 1) % players.size();
        }

        state.setCurrentPlayerPublic(players.get(nextIdx));
    }

    /**
     * التحقق من نهاية اللعبة
     */
    private boolean isGameOver(Game state) {
        return state.checkGameOver();
    }

    /**
     * ⭐ دالة إصلاح مرجعية القطعة: تحويل قطعة منسوخة إلى أصلية
     */
    public Piece mapClonedPieceToOriginal(Game clonedState, Game originalState, Piece clonedPiece) {
        if (clonedPiece == null) return null;

        String colorPrefix = clonedPiece.getColor();
        int pieceNum = clonedPiece.getPieceNum();

        for (Player origPlayer : originalState.getPlayersList()) {
            if (origPlayer.getColor().startsWith(colorPrefix)) {
                for (Piece origPiece : origPlayer.getPieces()) {
                    if (origPiece.getPieceNum() == pieceNum) {
                        return origPiece;
                    }
                }
            }
        }
        return null;
    }

    public static int getNodesVisited() {
        return nodesVisited;
    }
}