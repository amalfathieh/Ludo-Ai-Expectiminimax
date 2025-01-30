import java.util.ArrayList;
import java.util.List;

public class Square {

    private int colPosition;
    private int rowPosition;
    private String type;
    private List<Piece> pieces;
    private String letter;
    private Player player;

    public Square(Player player, int rowPosition, int colPosition, String type, String letter) {
        this.player  = player;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.type = type;
        this.pieces = new ArrayList<>();
        this.letter = letter;
    }

    public int getColPosition() {
        return colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    @Override
    public String toString() {
        if (pieces.isEmpty()) {
            return letter;
        }
        return pieces.toString();
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }

    public void setPiece(Piece piece,int indexPath) {
        this.pieces.add(piece);
        piece.setIndexPath(indexPath);
    }

    public void removePiece(Piece piece){
        this.pieces.remove(piece);
    }

    public String getType() {
        return type;
    }
}