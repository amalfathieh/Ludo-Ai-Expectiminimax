
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private List<Player> players ;
    Player currentPlayer;
    Piece piece;
    int newPosition;

    Game(Player currentPlayer, Piece piece, int newPosition){
        this.currentPlayer = currentPlayer;
        this.piece = piece;
        this.newPosition=newPosition;
    }
    // في Game.java
    public Game(List<Player> playersList) {
        this.players = playersList; // تأكد من التخزين الصحيح
    }
    // أضف Getter لقائمة اللاعبين
    public List<Player> getPlayers() {
        return players;
    }
    // === أضف هذه الدوال في نهاية كلاس Game ===

    // Getter للاعبين
    public List<Player> getPlayersList() { return players; }

    // Getter و Setter للاعب الحالي
    public Player getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayerPublic(Player player) { this.currentPlayer = player; }

    // دالة نهاية اللعبة (عامة)
    public boolean checkGameOver() {
        int count = 0;
        for (Player player : players) {
            if (player.hasWon()) count++;
        }
        return count >= players.size() - 1;
    }

    // دالة مساعدة للعثور على لاعب باللون
    public Player findPlayerByColor(String color) {
        for (Player p : players) {
            if (p.getColor().equals(color)) return p;
        }
        return null;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    boolean takePieceOut(Piece piece) {
        if(piece.isOut())
            return false;

        boolean successfullyMoved = takeOut(piece);

        if(successfullyMoved) {
            piece.setOut(true);
            return true;
        }
        return false;

    }

    boolean takeOut(Piece piece) {
        if (!piece.isOut()) {
            // الحجر في المنزل فهرسه هو (رقم الحجر - 1)
            int homeIdx = piece.getPieceNum() - 1;
            Square oldSquare = currentPlayer.getHome().get(homeIdx);

            oldSquare.removePiece(piece);
            Square newSquare = currentPlayer.getPath().get(0);
            newSquare.setPiece(piece, 0); // نضع الحجر في بداية المسار (Index 0)
            piece.setOut(true);
            piece.setIndexPath(0); // تحديث الفهرس
            return true;
        }
        return false;
    }

    boolean movePiece(Piece piece, int steps) {

        if(!piece.isOut() && steps!=6)
            return false;
        if(!piece.isOut() && steps ==6) {
            takeOut(piece);
            return true;
        }
        int currentPieceIndex = piece.getIndexPath();

//        التأكد من أن الوجهة داخل حدود المسار.
        int endPosition = currentPieceIndex +  steps;
        if(endPosition >= currentPlayer.getPath().size())
            return false;

        //        يجب أن لا يكون هناك عوائق على الطريق.
        for (int i= currentPieceIndex+1; i < endPosition; i++){
            if(isBlocked( i)){
                return false;
            }
        }

        Square newSquare = currentPlayer.getPath().get(endPosition);

        //مربع الوجهة ليس امن ولا نجمة -> فحص اذا في قطع عدو وارجاع قطع العدو للمنزل
        if(!newSquare.getType().equals("star") && !newSquare.getType().equals("safe")){
            int x = newSquare.getRowPosition();
            int y = newSquare.getColPosition();
            //قتل قطعة عدو
            containsOneEnemyPiece( x, y);
        }
//        نقل القطعة إلى الموقع الجديد
        movePieceToNewPosition(piece, endPosition);
        return true;
    }

    void movePieceToNewPosition(Piece piece, int endPosition){
        Square newSquare = currentPlayer.getPath().get(endPosition);
        Square oldSquare = currentPlayer.getPath().get(piece.getIndexPath());
        oldSquare.removePiece(piece);

        newSquare.setPiece(piece, endPosition);

        if(endPosition+1 == currentPlayer.getPath().size())
            piece.setCompleted();
    }

    void containsOneEnemyPiece(int x , int y) {
        for (Player player: players) {
            if (player.getColor().equals(currentPlayer.getColor())) continue;

            for (Square square2 : player.getPath()){
                if(x == square2.getRowPosition() && y == square2.getColPosition()){
                    List<Piece> pieces = square2.getPieces();
                    if(!pieces.isEmpty()){
                        // استخدام Iterator أو loop مرنة لتجنب الأخطاء
                        Piece enemyPiece = pieces.get(0);
                        square2.removePiece(enemyPiece);

                        Square homeSquare = player.getHome().get(enemyPiece.getPieceNum() - 1);
                        homeSquare.setPiece(enemyPiece, enemyPiece.getPieceNum() - 1);
                        enemyPiece.setOut(false);
                        enemyPiece.setIndexPath(-1); // إعادة الفهرس للمنزل
                        return;
                    }
                }
            }
        }
    }

    boolean movesArePossible(Player player, int numberRolled) {
        if(numberRolled==6) {
            for(int i=0; i<4; i++) {
                Piece piece = player.getPiece(i);
                if(canTakePieceOut(piece))
                    return true;
            }
        }

        for(int i=0; i<4; i++) {
            Piece piece = player.getPiece(i);
            if(canMovePiece(piece, numberRolled))
                return true;
        }
        return false;
    }

    public boolean canTakePieceOut(Piece piece) {

        if(piece.isOut())
            return false;

        boolean canMove = canMovePiece(piece, 6);
        if(canMove)
            return true;

        return false;
    }

    
    public boolean canMovePiece(Piece currentPiece, int steps) {
//        التحقق مما إذا كانت قطعة يمكنها التحرك عددًا معينًا من المربعات:

// يجب أن تكون القطعة قد خرجت من منزلها (أو النرد يساوي 6).
        if(!currentPiece.isOut() &&  steps!=6)
            return false;
//        يجب أن لا تتجاوز الموضع النهائي حدود المسار.
        int currentPieceIndex = currentPiece.getIndexPath();
        
        int endPosition =  currentPieceIndex +  steps;
        if(endPosition >= currentPlayer.getPath().size())
            return false; // cannot finish without exact number

        //        يجب أن لا يكون هناك عوائق على الطريق.
        for (int i= currentPieceIndex+1; i< endPosition; i++){
            if(isBlocked(i)){
                return false;
            }
        }

        return true;
    }

    boolean isBlocked( int indexPath){
        Square square = currentPlayer.getPath().get(indexPath);
        int x = square.getRowPosition();
        int y = square.getColPosition();

        for (Player player: players) {
            if (player.getColor().equals(currentPlayer.getColor())) {
                continue;
            }
            List<Square> square1 = player.getPath();
            for (Square square2:square1){
                if(x == square2.getRowPosition() && y == square2.getColPosition()){
                    List<Piece> pieces = square2.getPieces();
                    if(pieces.isEmpty()){
                        break;
                    }
                    else {
                        if (pieces.size() > 1) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    public List<Piece> getOpponentPieces(Player currentPlayer){
        List<Piece> opponentPieces = new ArrayList<>();

        for (Player player: players) {
            if (player.getColor().equals(currentPlayer.getColor())) {
                continue;
            }
            opponentPieces.addAll(Arrays.asList(player.getPieces()));
        }
        return opponentPieces;

    }
    public boolean isis(Player oneP, Piece one, Player twoP, Piece two){
        Square square1 = oneP.getPath().get(one.getIndexPath());
        int x1 = square1.getRowPosition();
        int y1 = square1.getColPosition();

        Square square2 = twoP.getPath().get(two.getIndexPath());
        int x2 = square2.getRowPosition();
        int y2 = square2.getColPosition();

        return x1 == x2 && y1 == y2;
    }

    public boolean isissame(Player enmy, Piece enmyP, Player twoP, int numm){
        Square square1 = enmy.getPath().get(enmyP.getIndexPath());
        int x1 = square1.getRowPosition();
        int y1 = square1.getColPosition();

        Square newSquare2 = twoP.getPath().get( numm);
        int x2 = newSquare2.getRowPosition();
        int y2 = newSquare2.getColPosition();

        return x1 == x2 && y1 == y2;
    }


    boolean isGameOver(){
            int count=0;
            for (Player player : players){
                if (player.hasWon()){
                    count++;
                }
            }
        return count == players.size() - 1;
    }
}
