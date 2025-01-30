public class Piece {

    private int pieceNum;
    private String color;
    private boolean isOut;
    private boolean completed;
    private int indexPath;

    Piece(int pieceNum, String color){
        this.pieceNum = pieceNum+1;
        this.color= color;
        this.indexPath =-1;
    }

    public int getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(int indexPath) {
        this.indexPath = indexPath;
    }

    @Override
    public String toString() {
        return color + pieceNum;
    }

    String getColor(){
        return color;
    }

    int getPieceNum(){
        return pieceNum;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    void setCompleted(){
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
