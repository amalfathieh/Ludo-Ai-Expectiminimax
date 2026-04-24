public class Piece {

    // استخدمنا final لأن رقم القطعة ولونها لا يتغيران أبداً خلال اللعبة
    private final int pieceNum;
    private final String color;

    private boolean isOut;
    private boolean completed;
    private int indexPath;

    public Piece(int pieceNum, String color) {
        this.pieceNum = pieceNum + 1;
        this.color = color;
        this.indexPath = -1; // -1 تعني أنها لا تزال في المنزل
        this.isOut = false;
        this.completed = false;
    }

    public int getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(int indexPath) {
        this.indexPath = indexPath;
    }

    @Override
    public String toString() {
        return color + pieceNum; // مثال: R1 أو B2
    }

    public String getColor() {
        return color;
    }

    public int getPieceNum() {
        return pieceNum;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        this.isOut = out;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}