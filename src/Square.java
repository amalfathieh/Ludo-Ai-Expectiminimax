import java.util.ArrayList;
import java.util.List;

public class Square {

    // هذه الخصائص لا تتغير بعد إنشاء المربع، لذا جعلناها final
    private final int rowPosition;
    private final int colPosition;
    private final String type;
    private final String letter;
    private final Player player;

    private final List<Piece> pieces;

    public Square(Player player, int rowPosition, int colPosition, String type, String letter) {
        this.player = player;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.type = type;
        this.letter = letter;
        this.pieces = new ArrayList<>();
    }

    public int getColPosition() {
        return colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public String getType() {
        return type;
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPiece(Piece piece, int indexPath) {
        this.pieces.add(piece);
        piece.setIndexPath(indexPath);
    }

    public void removePiece(Piece piece) {
        this.pieces.remove(piece);
    }

    @Override
    public String toString() {
        // إذا كان المربع فارغاً، نطبع رمزه (مثل -- أو **)
        if (pieces.isEmpty()) {
            return letter;
        }
        // إذا كان يحتوي على قطع، نطبعها
        return pieces.toString();
    }
}
