import java.util.LinkedList;
import java.util.List;

public class Player {

    private final String color;
    private final Piece[] piece;
    private List<Square> path;
    private List<Square> home = new LinkedList<>();;
    private int numberRolled;
    private boolean hasRolledSix;
    private boolean isComputer =false;
    private String playerRank ="lose";

    Player(String color){
        this.color = color;
        piece = new Piece[4];

        if(color.startsWith("B"))
            addPathToB();
        else if (color.startsWith("R"))
            addPathToR();
        else if (color.startsWith("G"))
            addPathToG();
        else if (color.startsWith("Y"))
            addPathToY();

        for (int i =0 ; i<4; i++){
            piece[i] = new Piece(i,color.substring(0, 1));
//            System.out.println(piece[i]);
            Square square = this.home.get(i);
            square.setPiece(piece[i],i);
        }
    }

    public void printPath(){
        for (Square square : this.path) {
            System.out.print(square);
            System.out.print(" ");

        }
        System.out.println();
        for (Square square : this.home) {
            System.out.print(square);
            System.out.print(" ");
        }
        System.out.println();
    }


    public void addPathToB(){
        this.path = new LinkedList<>();
        this.path.add(0,new Square(this , 6,1, "star","**"));
        this.path.add(1,new Square(this , 6,2,  "p", "--"));
        this.path.add(2,new Square(this , 6,3,  "p", "--"));
        this.path.add(3,new Square(this , 6,4,  "p", "--" ));
        this.path.add(4,new Square(this , 6,5,  "p", "--" ));

        this.path.add(5,new Square(this , 5,6,  "p", "--" ));
        this.path.add(6,new Square(this , 4,6,  "p", "--" ));
        this.path.add(7,new Square(this , 3,6, "p", "--" ));
        this.path.add(8,new Square(this , 2,6,  "p", "--" ));
        this.path.add(9,new Square(this , 1,6,  "p", "--" ));
        this.path.add(10,new Square(this , 0,6,  "p", "--" ));
        this.path.add(11,new Square(this , 0,7,  "p", "--" ));

        this.path.add(12,new Square(this , 0,8,  "p", "--" ));
        this.path.add(13,new Square(this , 1,8, "star","**"));
        this.path.add(14,new Square(this , 2,8, "p", "--" ));
        this.path.add(15,new Square(this , 3,8,  "p", "--" ));
        this.path.add(16,new Square(this , 4,8,  "p", "--" ));
        this.path.add(17,new Square(this , 5,8,  "p", "--" ));

        this.path.add(18,new Square(this , 6,9,  "p", "--" ));
        this.path.add(19,new Square(this , 6,10,  "p", "--" ));
        this.path.add(20,new Square(this , 6,11,  "p", "--" ));
        this.path.add(21,new Square(this , 6,12,  "p", "--" ));
        this.path.add(22,new Square(this , 6,13,  "p", "--" ));
        this.path.add(23,new Square(this , 6,14,  "p", "--" ));
        this.path.add(24,new Square(this , 7,14,  "p", "--" ));


        this.path.add(25,new Square(this , 8,14,  "p", "--" ));
        this.path.add(26,new Square(this , 8,13, "star","**"));
        this.path.add(27,new Square(this , 8,12,  "p", "--" ));
        this.path.add(28,new Square(this , 8,11,  "p", "--" ));
        this.path.add(29,new Square(this , 8,10,  "p", "--" ));
        this.path.add(30,new Square(this , 8,9,  "p", "--" ));

        this.path.add(31,new Square(this , 9,8,  "p", "--" ));
        this.path.add(32,new Square(this , 10,8,  "p", "--" ));
        this.path.add(33,new Square(this , 11,8, "p", "--" ));
        this.path.add(34,new Square(this , 12,8,  "p", "--" ));
        this.path.add(35,new Square(this , 13,8,  "p", "--" ));
        this.path.add(36,new Square(this , 14,8,  "p", "--" ));
        this.path.add(37,new Square(this , 14,7,  "p", "--" ));

        this.path.add(38,new Square(this , 14,6,  "p", "--" ));
        this.path.add(39,new Square(this , 13,6, "star","**"));
        this.path.add(40,new Square(this , 12,6, "p", "--" ));
        this.path.add(41,new Square(this , 11,6,  "p", "--" ));
        this.path.add(42,new Square(this , 10,6,  "p", "--" ));
        this.path.add(43,new Square(this , 9,6,  "p", "--" ));

        this.path.add(44,new Square(this , 8,5,  "p", "--" ));
        this.path.add(45,new Square(this , 8,4,  "p", "--" ));
        this.path.add(46,new Square(this , 8,3,  "p", "--" ));
        this.path.add(47,new Square(this , 8,2,  "p", "--" ));
        this.path.add(48,new Square(this , 8,1,  "p", "--" ));
        this.path.add(49,new Square(this , 8,0,  "p", "--" ));

        this.path.add(50,new Square(this , 7,0,  "p", "--" ));

        this.path.add(51,new Square(this , 7,1, "safe","||" ));
        this.path.add(52,new Square(this , 7,2, "safe","||" ));
        this.path.add(53,new Square(this , 7,3, "safe","||" ));
        this.path.add(54,new Square(this , 7,4, "safe","||" ));
        this.path.add(55,new Square(this , 7,5, "safe","||" ));

        this.path.add(56,new Square(this , 7,6, "win","$$" ));

        this.home.add(0, new Square(this, 2, 2, "home" , "  "));
        this.home.add(1, new Square(this, 2, 3, "home" , "  "));
        this.home.add(2, new Square(this, 3, 2, "home" , "  "));
        this.home.add(3, new Square(this, 3, 3, "home" , "  "));
    }

    public void addPathToR(){
        path = new LinkedList<>();

        this.path.add(0,new Square(this , 1,8, "star","**"));
        this.path.add(1,new Square(this , 2,8, "p", "--" ));
        this.path.add(2,new Square(this , 3,8,  "p", "--" ));
        this.path.add(3,new Square(this , 4,8,  "p", "--" ));
        this.path.add(4,new Square(this , 5,8,  "p", "--" ));

        this.path.add(5,new Square(this , 6,9,  "p", "--" ));
        this.path.add(6,new Square(this , 6,10,  "p", "--" ));
        this.path.add(7,new Square(this , 6,11,  "p", "--" ));
        this.path.add(8,new Square(this , 6,12,  "p", "--" ));
        this.path.add(9,new Square(this , 6,13,  "p", "--" ));
        this.path.add(10,new Square(this , 6,14,  "p", "--" ));
        this.path.add(11,new Square(this , 7,14,  "p", "--" ));

        this.path.add(12,new Square(this , 8,14,  "p", "--" ));
        this.path.add(13,new Square(this , 8,13, "star","**"));
        this.path.add(14,new Square(this , 8,12,  "p", "--" ));
        this.path.add(15,new Square(this , 8,11,  "p", "--" ));
        this.path.add(16,new Square(this , 8,10,  "p", "--" ));
        this.path.add(17,new Square(this , 8,9,  "p", "--" ));

        this.path.add(18,new Square(this , 9,8,  "p", "--" ));
        this.path.add(19,new Square(this , 10,8,  "p", "--" ));
        this.path.add(20,new Square(this , 11,8, "p", "--" ));
        this.path.add(21,new Square(this , 12,8,  "p", "--" ));
        this.path.add(22,new Square(this , 13,8,  "p", "--" ));
        this.path.add(23,new Square(this , 14,8,  "p", "--" ));
        this.path.add(24,new Square(this , 14,7,  "p", "--" ));

        this.path.add(25,new Square(this , 14,6,  "p", "--" ));
        this.path.add(26,new Square(this , 13,6, "star","**"));
        this.path.add(27,new Square(this , 12,6, "p", "--" ));
        this.path.add(28,new Square(this , 11,6,  "p", "--" ));
        this.path.add(29,new Square(this , 10,6,  "p", "--" ));
        this.path.add(30,new Square(this , 9,6,  "p", "--" ));

        this.path.add(31,new Square(this , 8,5,  "p", "--" ));
        this.path.add(32,new Square(this , 8,4,  "p", "--" ));
        this.path.add(33,new Square(this , 8,3,  "p", "--" ));
        this.path.add(34,new Square(this , 8,2,  "p", "--" ));
        this.path.add(35,new Square(this , 8,1,  "p", "--" ));
        this.path.add(36,new Square(this , 8,0,  "p", "--" ));

        this.path.add(37,new Square(this , 7,0,  "p", "--" ));

        this.path.add(38,new Square(this , 6,0,  "p", "--" ));
        this.path.add(39,new Square(this , 6,1, "star","**"));
        this.path.add(40,new Square(this , 6,2,  "p", "--" ));
        this.path.add(41,new Square(this , 6,3,  "p", "--" ));
        this.path.add(42,new Square(this , 6,4,  "p", "--" ));
        this.path.add(43,new Square(this , 6,5,  "p", "--" ));

        this.path.add(44,new Square(this , 5,6,  "p", "--" ));
        this.path.add(45,new Square(this , 4,6,  "p", "--" ));
        this.path.add(46,new Square(this , 3,6, "p", "--" ));
        this.path.add(47,new Square(this , 2,6,  "p", "--" ));
        this.path.add(48,new Square(this , 1,6,  "p", "--" ));
        this.path.add(49,new Square(this , 0,6,  "p", "--" ));
        this.path.add(50,new Square(this , 0,7,  "p", "--" ));

        this.path.add(51,new Square(this , 1,7, "safe","||" ));
        this.path.add(52,new Square(this , 2,7, "safe","||" ));
        this.path.add(53,new Square(this , 3,7, "safe","||" ));
        this.path.add(54,new Square(this , 4,7, "safe","||" ));
        this.path.add(55,new Square(this , 5,7, "safe","||" ));

        this.path.add(56,new Square(this , 6,7, "win","$$" ));

        this.home.add(0, new Square(this, 2, 11, "home" , "  "));
        this.home.add(1, new Square(this, 2, 12, "home" , "  "));
        this.home.add(2, new Square(this, 3, 11, "home" , "  "));
        this.home.add(3, new Square(this, 3, 12, "home" , "  "));
    }

    public void addPathToG(){
        path = new LinkedList<>();


        this.path.add(0,new Square(this , 8,13, "star" ,"**"));
        this.path.add(1,new Square(this , 8,12,  "p", "--" ));
        this.path.add(2,new Square(this , 8,11,  "p", "--" ));
        this.path.add(3,new Square(this , 8,10,  "p", "--" ));
        this.path.add(4,new Square(this , 8,9,  "p", "--" ));

        this.path.add(5,new Square(this , 9,8,  "p", "--" ));
        this.path.add(6,new Square(this , 10,8,  "p", "--" ));
        this.path.add(7,new Square(this , 11,8, "p", "--" ));
        this.path.add(8,new Square(this , 12,8,  "p", "--" ));
        this.path.add(9,new Square(this , 13,8,  "p", "--" ));
        this.path.add(10,new Square(this , 14,8,  "p", "--" ));
        this.path.add(11,new Square(this , 14,7,  "p", "--" ));

        this.path.add(12,new Square(this , 14,6,  "p", "--" ));
        this.path.add(13,new Square(this , 13,6, "star","**"));
        this.path.add(14,new Square(this , 12,6, "p", "--" ));
        this.path.add(15,new Square(this , 11,6,  "p", "--" ));
        this.path.add(16,new Square(this , 10,6,  "p", "--" ));
        this.path.add(17,new Square(this , 9,6,  "p", "--" ));

        this.path.add(18,new Square(this , 8,5,  "p", "--" ));
        this.path.add(19,new Square(this , 8,4,  "p", "--" ));
        this.path.add(20,new Square(this , 8,3,  "p", "--" ));
        this.path.add(21,new Square(this , 8,2,  "p", "--" ));
        this.path.add(22,new Square(this , 8,1,  "p", "--" ));
        this.path.add(23,new Square(this , 8,0,  "p", "--" ));

        this.path.add(24,new Square(this , 7,0,  "p", "--" ));

        this.path.add(25,new Square(this , 6,0,  "p", "--" ));
        this.path.add(26,new Square(this , 6,1, "star","**" ));
        this.path.add(27,new Square(this , 6,2,  "p", "--" ));
        this.path.add(28,new Square(this , 6,3,  "p", "--" ));
        this.path.add(29,new Square(this , 6,4,  "p", "--" ));
        this.path.add(30,new Square(this , 6,5,  "p", "--" ));

        this.path.add(31,new Square(this , 5,6,  "p", "--" ));
        this.path.add(32,new Square(this , 4,6,  "p", "--" ));
        this.path.add(33,new Square(this , 3,6, "p", "--" ));
        this.path.add(34,new Square(this , 2,6,  "p", "--" ));
        this.path.add(35,new Square(this , 1,6,  "p", "--" ));
        this.path.add(36,new Square(this , 0,6,  "p", "--" ));
        this.path.add(37,new Square(this , 0,7,  "p", "--" ));

        this.path.add(38,new Square(this , 0,8,  "p", "--" ));

        this.path.add(39,new Square(this , 1,8, "star","**" ));
        this.path.add(40,new Square(this , 2,8,  "p", "--" ));
        this.path.add(41,new Square(this , 3,8,  "p", "--" ));
        this.path.add(42,new Square(this , 4,8,  "p", "--" ));
        this.path.add(43,new Square(this , 5,8,  "p", "--" ));

        this.path.add(44,new Square(this , 6,9,  "p", "--" ));
        this.path.add(45,new Square(this , 6,10,  "p", "--" ));
        this.path.add(46,new Square(this , 6,11,  "p", "--" ));
        this.path.add(47,new Square(this , 6,12,  "p", "--" ));
        this.path.add(48,new Square(this , 6,13,  "p", "--" ));
        this.path.add(49,new Square(this , 6,14,  "p", "--" ));
        this.path.add(50,new Square(this , 7,14,  "p", "--" ));

        this.path.add(51,new Square(this , 7,13, "safe","||" ));
        this.path.add(52,new Square(this , 7,12, "safe","||" ));
        this.path.add(53,new Square(this , 7,11, "safe","||" ));
        this.path.add(54,new Square(this , 7,10, "safe","||" ));
        this.path.add(55,new Square(this , 7,9, "safe","||" ));

        this.path.add(56,new Square(this , 7,8, "win" ,"$$"));

        this.home.add(0, new Square(this, 11, 11, "home" , "  "));
        this.home.add(1, new Square(this, 11, 12, "home" , "  "));
        this.home.add(2, new Square(this, 12, 11, "home" , "  "));
        this.home.add(3, new Square(this, 12, 12, "home" , "  "));

    }

    public void addPathToY(){
        this.path = new LinkedList<>();
        this.path.add(0,new Square(this , 13,6, "star","**" ));
        this.path.add(1,new Square(this , 12,6, "p", "--" ));
        this.path.add(2,new Square(this , 11,6,  "p", "--" ));
        this.path.add(3,new Square(this , 10,6,  "p", "--" ));
        this.path.add(4,new Square(this , 9,6,  "p", "--" ));

        this.path.add(5,new Square(this , 8,5,  "p", "--" ));
        this.path.add(6,new Square(this , 8,4,  "p", "--" ));
        this.path.add(7,new Square(this , 8,3,  "p", "--" ));
        this.path.add(8,new Square(this , 8,2,  "p", "--" ));
        this.path.add(9,new Square(this , 8,1,  "p", "--" ));
        this.path.add(10,new Square(this , 8,0,  "p", "--" ));

        this.path.add(11,new Square(this , 7,0,  "p", "--" ));

        this.path.add(12,new Square(this , 6,0,  "p", "--" ));


        this.path.add(13,new Square(this , 6,1, "star","**" ));
        this.path.add(14,new Square(this , 6,2, "p","--" ));
        this.path.add(15,new Square(this , 6,3, "p","--" ));
        this.path.add(16,new Square(this , 6,4, "p","--" ));
        this.path.add(17,new Square(this , 6,5, "p","--" ));

        this.path.add(18,new Square(this , 5,6,  "p", "--" ));
        this.path.add(19,new Square(this , 4,6,  "p", "--" ));
        this.path.add(20,new Square(this , 3,6, "p", "--" ));
        this.path.add(21,new Square(this , 2,6,  "p", "--" ));
        this.path.add(22,new Square(this , 1,6,  "p", "--" ));
        this.path.add(23,new Square(this , 0,6,  "p", "--" ));
        this.path.add(24,new Square(this , 0,7,  "p", "--" ));

        this.path.add(25,new Square(this , 0,8,  "p", "--" ));
        this.path.add(26,new Square(this , 1,8, "star","**" ));
        this.path.add(27,new Square(this , 2,8, "p", "--" ));
        this.path.add(28,new Square(this , 3,8,  "p", "--" ));
        this.path.add(29,new Square(this , 4,8,  "p", "--" ));
        this.path.add(30,new Square(this , 5,8,  "p", "--" ));

        this.path.add(31,new Square(this , 6,9,  "p", "--" ));
        this.path.add(32,new Square(this , 6,10,  "p", "--" ));
        this.path.add(33,new Square(this , 6,11,  "p", "--" ));
        this.path.add(34,new Square(this , 6,12,  "p", "--" ));
        this.path.add(35,new Square(this , 6,13,  "p", "--" ));
        this.path.add(36,new Square(this , 6,14,  "p", "--" ));
        this.path.add(37,new Square(this , 7,14,  "p", "--" ));


        this.path.add(38,new Square(this , 8,14,  "p", "--" ));
        this.path.add(39,new Square(this , 8,13, "star","**" ));
        this.path.add(40,new Square(this , 8,12,  "p", "--" ));
        this.path.add(41,new Square(this , 8,11,  "p", "--" ));
        this.path.add(42,new Square(this , 8,10,  "p", "--" ));
        this.path.add(43,new Square(this , 8,9,  "p", "--" ));

        this.path.add(44,new Square(this , 9,8,  "p", "--" ));
        this.path.add(45,new Square(this , 10,8,  "p", "--" ));
        this.path.add(46,new Square(this , 11,8, "p", "--" ));
        this.path.add(47,new Square(this , 12,8,  "p", "--" ));
        this.path.add(48,new Square(this , 13,8,  "p", "--" ));
        this.path.add(49,new Square(this , 14,8,  "p", "--" ));
        this.path.add(50,new Square(this , 14,7,  "p", "--" ));

        this.path.add(51,new Square(this , 13,7, "safe","||" ));
        this.path.add(52,new Square(this , 12,7, "safe","||" ));
        this.path.add(53,new Square(this , 11,7, "safe","||" ));
        this.path.add(54,new Square(this , 10,7, "safe","||" ));
        this.path.add(55,new Square(this , 9,7, "safe","||" ));

        this.path.add(56,new Square(this , 8,7, "win" ,"$$"));


        this.home.add(0, new Square(this, 11, 2, "home" , "  "));
        this.home.add(1, new Square(this, 11, 3, "home" , "  "));
        this.home.add(2, new Square(this, 12, 2, "home" , "  "));
        this.home.add(3, new Square(this, 12, 3, "home" , "  "));
    }

    public List<Square> getPath() {
        return this.path;
    }

    public List<Square> getHome() {
        return home;
    }

    public Piece getPiece(int index) {
        return piece[index];
    }

    @Override
    public String toString() {
        return "Player " + color;
    }

    boolean hasWon() {
        for(int i=0; i<4; i++)
            if(!piece[i].isCompleted())
                return false;
        return true;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public String getColor() {
        return color;
    }

    public Piece[] getPieces() {
        return piece;
    }
    void rollDice() {
        numberRolled = new Die().roll();

        hasRolledSix = numberRolled == 6;
    }

    public int getNumberRolled() {
        return numberRolled;
    }

    public boolean isHasRolledSix() {
        return hasRolledSix;
    }

    public void setPlayerRank(int rank){
        switch (rank){
            case 1: {
                this.playerRank  = "first";
                break;
            }
            case 2:{
                this.playerRank  = "second";
                break;
            }
            case 3 :{
                this.playerRank  = "third";
                break;
            }
            default:
                this.playerRank = "lose";
        }
    }

    public String getPlayerRank() {
        return playerRank;
    }
}
