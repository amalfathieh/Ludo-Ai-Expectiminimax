import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
private List<Player>players;
String[][] board = new String[15][15];
    String[][] test = new String[15][15];

    public Board(List<Player>players) {
        this.players = players;
        initBoard();
        test();
    }

    public void printBoard(){
        updateBoard();
        System.out.println("Blue                                                                 Red");

        for (String[] strings : this.board) {
            for (int j = 0; j < this.board.length; j++) {
                System.out.print(formatString(strings[j]));
            }
            System.out.println();
        }
        System.out.println("Yellow                                                             Green");

    }

    public static String formatString(String input){
        return String.format("%-5s",input);
    }

public void updateBoard() {
    for (int i = 0; i < this.board.length; i++) {
        for (int j = 0; j < this.board[i].length; j++) {
            this.board[i][j] = getDefaultSymbolForSquare(i, j);
        }
    }

    for (Player player : this.players) {
        List<Square> squares = player.getPath();

        for (Square square : squares) {
            int x = square.getRowPosition();
            int y = square.getColPosition();

            // إذا كانت هناك قطع في الخانة، قم بتحديث محتواها
            if (!square.getPieces().isEmpty()) {
                if(square.getType().equals("win")){
                    this.board[x][y] = square.getPieces().size() + square.getPieces().get(0).getColor() ;
                }
                else {
                    StringBuilder content = new StringBuilder(this.board[x][y].equals(getDefaultSymbolForSquare(x, y))
                            ? ""
                            : this.board[x][y]);
                    for (Piece piece : square.getPieces()) {
                        content.append(piece.toString()); // أضف تمثيل القطع إلى المحتوى
                    }
                    this.board[x][y] = content.toString();
                }
            }
        }

        List<Square> homes = player.getHome();
        for (Square home : homes) {
            int x = home.getRowPosition();
            int y = home.getColPosition();

            if (home.getPieces().isEmpty()) {
                board[x][y] = "  ";
            } else {
                Piece piece = home.getPieces().get(0); // احصل على القطعة الوحيدة
                board[x][y] = piece.toString();
            }
        }
    }
}

    private String getDefaultSymbolForSquare(int x, int y) {
        return test[x][y];
    }


    public void initBoard(){
        board[0][0] = "##";
        board[0][1] = "##";
        board[0][2] = "##";
        board[0][3] = "##";
        board[0][4] = "##";
        board[0][5] = "##";
        board[0][6] = "--";
        board[0][7] = "--";
        board[0][8] = "--";
        board[0][9] = "##";
        board[0][10] = "##";
        board[0][11] = "##";
        board[0][12] = "##";
        board[0][13] = "##";
        board[0][14] = "##";

        board[1][0] = "##";
        board[1][1] = "##";
        board[1][2] = "##";
        board[1][3] = "##";
        board[1][4] = "##";
        board[1][5] = "##";
        board[1][6] = "--";
        board[1][7] = "||";
        board[1][8] = "**";
        board[1][9] = "##";
        board[1][10] = "##";
        board[1][11] = "##";
        board[1][12] = "##";
        board[1][13] = "##";
        board[1][14] = "##";

        board[2][0] = "##";
        board[2][1] = "##";
        board[2][2] = "  ";
        board[2][3] = "  ";
        board[2][4] = "##";
        board[2][5] = "##";
        board[2][6] = "--";
        board[2][7] = "||";
        board[2][8] = "--";
        board[2][9] = "##";
        board[2][10] = "##";
        board[2][11] = "  ";
        board[2][12] = "  ";
        board[2][13] = "##";
        board[2][14] = "##";

        board[3][0] = "##";
        board[3][1] = "##";
        board[3][2] = "  ";
        board[3][3] = "  ";
        board[3][4] = "##";
        board[3][5] = "##";
        board[3][6] = "--";
        board[3][7] = "||";
        board[3][8] = "--";
        board[3][9] = "##";
        board[3][10] = "##";
        board[3][11] = "  ";
        board[3][12] = "  ";
        board[3][13] = "##";
        board[3][14] = "##";

        board[4][0] = "##";
        board[4][1] = "##";
        board[4][2] = "##";
        board[4][3] = "##";
        board[4][4] = "##";
        board[4][5] = "##";
        board[4][6] = "--";
        board[4][7] = "||";
        board[4][8] = "--";
        board[4][9] = "##";
        board[4][10] = "##";
        board[4][11] = "##";
        board[4][12] = "##";
        board[4][13] = "##";
        board[4][14] = "##";

        board[5][0] = "##";
        board[5][1] = "##";
        board[5][2] = "##";
        board[5][3] = "##";
        board[5][4] = "##";
        board[5][5] = "##";
        board[5][6] = "--";
        board[5][7] = "||";
        board[5][8] = "--";
        board[5][9] = "##";
        board[5][10] = "##";
        board[5][11] = "##";
        board[5][12] = "##";
        board[5][13] = "##";
        board[5][14] = "##";

        board[6][0] = "--";
        board[6][1] = "**";
        board[6][2] = "--";
        board[6][3] = "--";
        board[6][4] = "--";
        board[6][5] = "--";
        board[6][6] = "##";
        board[6][7] = "$$";
        board[6][8] = "##";
        board[6][9] = "--";
        board[6][10] = "--";
        board[6][11] = "--";
        board[6][12] = "--";
        board[6][13] = "--";
        board[6][14] = "--";

        board[7][0] = "--";
        board[7][1] = "||";
        board[7][2] = "||";
        board[7][3] = "||";
        board[7][4] = "||";
        board[7][5] = "||";
        board[7][6] = "$$";
        board[7][7] = "##";
        board[7][8] = "$$";
        board[7][9] = "||";
        board[7][10] = "||";
        board[7][11] = "||";
        board[7][12] = "||";
        board[7][13] = "||";
        board[7][14] = "--";

        board[8][0] = "--";
        board[8][1] = "--";
        board[8][2] = "--";
        board[8][3] = "--";
        board[8][4] = "--";
        board[8][5] = "--";
        board[8][6] = "##";
        board[8][7] = "$$";
        board[8][8] = "##";
        board[8][9] = "--";
        board[8][10] = "--";
        board[8][11] = "--";
        board[8][12] = "--";
        board[8][13] = "**";
        board[8][14] = "--";

        board[9][0] = "##";
        board[9][1] = "##";
        board[9][2] = "##";
        board[9][3] = "##";
        board[9][4] = "##";
        board[9][5] = "##";
        board[9][6] = "--";
        board[9][7] = "||";
        board[9][8] = "--";
        board[9][9] = "##";
        board[9][10] = "##";
        board[9][11] = "##";
        board[9][12] = "##";
        board[9][13] = "##";
        board[9][14] = "##";

        board[10][0] = "##";
        board[10][1] = "##";
        board[10][2] = "##";
        board[10][3] = "##";
        board[10][4] = "##";
        board[10][5] = "##";
        board[10][6] = "--";
        board[10][7] = "||";
        board[10][8] = "--";
        board[10][9] = "##";
        board[10][10] = "##";
        board[10][11] = "##";
        board[10][12] = "##";
        board[10][13] = "##";
        board[10][14] = "##";

        board[11][0] = "##";
        board[11][1] = "##";
        board[11][2] = "  ";
        board[11][3] = "  ";
        board[11][4] = "##";
        board[11][5] = "##";
        board[11][6] = "--";
        board[11][7] = "||";
        board[11][8] = "--";
        board[11][9] = "##";
        board[11][10] = "##";
        board[11][11] = "  ";
        board[11][12] = "  ";
        board[11][13] = "##";
        board[11][14] = "##";

        board[12][0] = "##";
        board[12][1] = "##";
        board[12][2] = "  ";
        board[12][3] = "  ";
        board[12][4] = "##";
        board[12][5] = "##";
        board[12][6] = "--";
        board[12][7] = "||";
        board[12][8] = "--";
        board[12][9] = "##";
        board[12][10] = "##";
        board[12][11] = "  ";
        board[12][12] = "  ";
        board[12][13] = "##";
        board[12][14] = "##";

        board[13][0] = "##";
        board[13][1] = "##";
        board[13][2] = "##";
        board[13][3] = "##";
        board[13][4] = "##";
        board[13][5] = "##";
        board[13][6] = "**";
        board[13][7] = "||";
        board[13][8] = "--";
        board[13][9] = "##";
        board[13][10] = "##";
        board[13][11] = "##";
        board[13][12] = "##";
        board[13][13] = "##";
        board[13][14] = "##";

        board[14][0] = "##";
        board[14][1] = "##";
        board[14][2] = "##";
        board[14][3] = "##";
        board[14][4] = "##";
        board[14][5] = "##";
        board[14][6] = "--";
        board[14][7] = "--";
        board[14][8] = "--";
        board[14][9] = "##";
        board[14][10] = "##";
        board[14][11] = "##";
        board[14][12] = "##";
        board[14][13] = "##";
        board[14][14] = "##";

    }
public void test(){
    test[0][0] = "##";
    test[0][1] = "##";
    test[0][2] = "##";
    test[0][3] = "##";
    test[0][4] = "##";
    test[0][5] = "##";
    test[0][6] = "--";
    test[0][7] = "--";
    test[0][8] = "--";
    test[0][9] = "##";
    test[0][10] = "##";
    test[0][11] = "##";
    test[0][12] = "##";
    test[0][13] = "##";
    test[0][14] = "##";

    test[1][0] = "##";
    test[1][1] = "##";
    test[1][2] = "##";
    test[1][3] = "##";
    test[1][4] = "##";
    test[1][5] = "##";
    test[1][6] = "--";
    test[1][7] = "||";
    test[1][8] = "**";
    test[1][9] = "##";
    test[1][10] = "##";
    test[1][11] = "##";
    test[1][12] = "##";
    test[1][13] = "##";
    test[1][14] = "##";

    test[2][0] = "##";
    test[2][1] = "##";
    test[2][2] = "  ";
    test[2][3] = "  ";
    test[2][4] = "##";
    test[2][5] = "##";
    test[2][6] = "--";
    test[2][7] = "||";
    test[2][8] = "--";
    test[2][9] = "##";
    test[2][10] = "##";
    test[2][11] = "  ";
    test[2][12] = "  ";
    test[2][13] = "##";
    test[2][14] = "##";

    test[3][0] = "##";
    test[3][1] = "##";
    test[3][2] = "  ";
    test[3][3] = "  ";
    test[3][4] = "##";
    test[3][5] = "##";
    test[3][6] = "--";
    test[3][7] = "||";
    test[3][8] = "--";
    test[3][9] = "##";
    test[3][10] = "##";
    test[3][11] = "  ";
    test[3][12] = "  ";
    test[3][13] = "##";
    test[3][14] = "##";

    test[4][0] = "##";
    test[4][1] = "##";
    test[4][2] = "##";
    test[4][3] = "##";
    test[4][4] = "##";
    test[4][5] = "##";
    test[4][6] = "--";
    test[4][7] = "||";
    test[4][8] = "--";
    test[4][9] = "##";
    test[4][10] = "##";
    test[4][11] = "##";
    test[4][12] = "##";
    test[4][13] = "##";
    test[4][14] = "##";

    test[5][0] = "##";
    test[5][1] = "##";
    test[5][2] = "##";
    test[5][3] = "##";
    test[5][4] = "##";
    test[5][5] = "##";
    test[5][6] = "--";
    test[5][7] = "||";
    test[5][8] = "--";
    test[5][9] = "##";
    test[5][10] = "##";
    test[5][11] = "##";
    test[5][12] = "##";
    test[5][13] = "##";
    test[5][14] = "##";

    test[6][0] = "--";
    test[6][1] = "**";
    test[6][2] = "--";
    test[6][3] = "--";
    test[6][4] = "--";
    test[6][5] = "--";
    test[6][6] = "##";
    test[6][7] = "$$";
    test[6][8] = "##";
    test[6][9] = "--";
    test[6][10] = "--";
    test[6][11] = "--";
    test[6][12] = "--";
    test[6][13] = "--";
    test[6][14] = "--";

    test[7][0] = "--";
    test[7][1] = "||";
    test[7][2] = "||";
    test[7][3] = "||";
    test[7][4] = "||";
    test[7][5] = "||";
    test[7][6] = "$$";
    test[7][7] = "##";
    test[7][8] = "$$";
    test[7][9] = "||";
    test[7][10] = "||";
    test[7][11] = "||";
    test[7][12] = "||";
    test[7][13] = "||";
    test[7][14] = "--";

    test[8][0] = "--";
    test[8][1] = "--";
    test[8][2] = "--";
    test[8][3] = "--";
    test[8][4] = "--";
    test[8][5] = "--";
    test[8][6] = "##";
    test[8][7] = "$$";
    test[8][8] = "##";
    test[8][9] = "--";
    test[8][10] = "--";
    test[8][11] = "--";
    test[8][12] = "--";
    test[8][13] = "**";
    test[8][14] = "--";

    test[9][0] = "##";
    test[9][1] = "##";
    test[9][2] = "##";
    test[9][3] = "##";
    test[9][4] = "##";
    test[9][5] = "##";
    test[9][6] = "--";
    test[9][7] = "||";
    test[9][8] = "--";
    test[9][9] = "##";
    test[9][10] = "##";
    test[9][11] = "##";
    test[9][12] = "##";
    test[9][13] = "##";
    test[9][14] = "##";

    test[10][0] = "##";
    test[10][1] = "##";
    test[10][2] = "##";
    test[10][3] = "##";
    test[10][4] = "##";
    test[10][5] = "##";
    test[10][6] = "--";
    test[10][7] = "||";
    test[10][8] = "--";
    test[10][9] = "##";
    test[10][10] = "##";
    test[10][11] = "##";
    test[10][12] = "##";
    test[10][13] = "##";
    test[10][14] = "##";

    test[11][0] = "##";
    test[11][1] = "##";
    test[11][2] = "  ";
    test[11][3] = "  ";
    test[11][4] = "##";
    test[11][5] = "##";
    test[11][6] = "--";
    test[11][7] = "||";
    test[11][8] = "--";
    test[11][9] = "##";
    test[11][10] = "##";
    test[11][11] = "  ";
    test[11][12] = "  ";
    test[11][13] = "##";
    test[11][14] = "##";

    test[12][0] = "##";
    test[12][1] = "##";
    test[12][2] = "  ";
    test[12][3] = "  ";
    test[12][4] = "##";
    test[12][5] = "##";
    test[12][6] = "--";
    test[12][7] = "||";
    test[12][8] = "--";
    test[12][9] = "##";
    test[12][10] = "##";
    test[12][11] = "  ";
    test[12][12] = "  ";
    test[12][13] = "##";
    test[12][14] = "##";

    test[13][0] = "##";
    test[13][1] = "##";
    test[13][2] = "##";
    test[13][3] = "##";
    test[13][4] = "##";
    test[13][5] = "##";
    test[13][6] = "**";
    test[13][7] = "||";
    test[13][8] = "--";
    test[13][9] = "##";
    test[13][10] = "##";
    test[13][11] = "##";
    test[13][12] = "##";
    test[13][13] = "##";
    test[13][14] = "##";

    test[14][0] = "##";
    test[14][1] = "##";
    test[14][2] = "##";
    test[14][3] = "##";
    test[14][4] = "##";
    test[14][5] = "##";
    test[14][6] = "--";
    test[14][7] = "--";
    test[14][8] = "--";
    test[14][9] = "##";
    test[14][10] = "##";
    test[14][11] = "##";
    test[14][12] = "##";
    test[14][13] = "##";
    test[14][14] = "##";

}


}
