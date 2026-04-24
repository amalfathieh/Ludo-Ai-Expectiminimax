
import java.util.LinkedList;
import java.util.List;

public class Player {

    private final String color;
    private final Piece[] piece;
    private List<Square> path;
    private List<Square> home;

    private int numberRolled;
    private boolean hasRolledSix;
    private boolean isComputer = false;
    String playerRank = "lose";

    // --- مصفوفات الإحداثيات الثابتة للمسارات (تم استخراجها من كودك القديم) ---
    private static final int[][] BLUE_PATH = {
            {6,1}, {6,2}, {6,3}, {6,4}, {6,5}, {5,6}, {4,6}, {3,6}, {2,6}, {1,6}, {0,6}, {0,7},
            {0,8}, {1,8}, {2,8}, {3,8}, {4,8}, {5,8}, {6,9}, {6,10}, {6,11}, {6,12}, {6,13}, {6,14}, {7,14},
            {8,14}, {8,13}, {8,12}, {8,11}, {8,10}, {8,9}, {9,8}, {10,8}, {11,8}, {12,8}, {13,8}, {14,8}, {14,7},
            {14,6}, {13,6}, {12,6}, {11,6}, {10,6}, {9,6}, {8,5}, {8,4}, {8,3}, {8,2}, {8,1}, {8,0}, {7,0},
            {7,1}, {7,2}, {7,3}, {7,4}, {7,5}, {7,6}
    };

    private static final int[][] RED_PATH = {
            {1,8}, {2,8}, {3,8}, {4,8}, {5,8}, {6,9}, {6,10}, {6,11}, {6,12}, {6,13}, {6,14}, {7,14},
            {8,14}, {8,13}, {8,12}, {8,11}, {8,10}, {8,9}, {9,8}, {10,8}, {11,8}, {12,8}, {13,8}, {14,8}, {14,7},
            {14,6}, {13,6}, {12,6}, {11,6}, {10,6}, {9,6}, {8,5}, {8,4}, {8,3}, {8,2}, {8,1}, {8,0}, {7,0},
            {6,0}, {6,1}, {6,2}, {6,3}, {6,4}, {6,5}, {5,6}, {4,6}, {3,6}, {2,6}, {1,6}, {0,6}, {0,7},
            {1,7}, {2,7}, {3,7}, {4,7}, {5,7}, {6,7}
    };

    private static final int[][] GREEN_PATH = {
            {8,13}, {8,12}, {8,11}, {8,10}, {8,9}, {9,8}, {10,8}, {11,8}, {12,8}, {13,8}, {14,8}, {14,7},
            {14,6}, {13,6}, {12,6}, {11,6}, {10,6}, {9,6}, {8,5}, {8,4}, {8,3}, {8,2}, {8,1}, {8,0}, {7,0},
            {6,0}, {6,1}, {6,2}, {6,3}, {6,4}, {6,5}, {5,6}, {4,6}, {3,6}, {2,6}, {1,6}, {0,6}, {0,7},
            {0,8}, {1,8}, {2,8}, {3,8}, {4,8}, {5,8}, {6,9}, {6,10}, {6,11}, {6,12}, {6,13}, {6,14}, {7,14},
            {7,13}, {7,12}, {7,11}, {7,10}, {7,9}, {7,8}
    };

    private static final int[][] YELLOW_PATH = {
            {13,6}, {12,6}, {11,6}, {10,6}, {9,6}, {8,5}, {8,4}, {8,3}, {8,2}, {8,1}, {8,0}, {7,0},
            {6,0}, {6,1}, {6,2}, {6,3}, {6,4}, {6,5}, {5,6}, {4,6}, {3,6}, {2,6}, {1,6}, {0,6}, {0,7},
            {0,8}, {1,8}, {2,8}, {3,8}, {4,8}, {5,8}, {6,9}, {6,10}, {6,11}, {6,12}, {6,13}, {6,14}, {7,14},
            {8,14}, {8,13}, {8,12}, {8,11}, {8,10}, {8,9}, {9,8}, {10,8}, {11,8}, {12,8}, {13,8}, {14,8}, {14,7},
            {13,7}, {12,7}, {11,7}, {10,7}, {9,7}, {8,7}
    };

    public Player(String color) {
        this.color = color;
        this.piece = new Piece[4];
        this.home = new LinkedList<>();
        this.path = new LinkedList<>();

        // بناء المسار والمنزل بشكل ديناميكي
        initPathAndHome();

        // إنشاء القطع ووضعها في المنزل
        for (int i = 0; i < 4; i++) {
            piece[i] = new Piece(i, color.substring(0, 1));
            Square square = this.home.get(i);
            square.setPiece(piece[i], i);
        }
    }

    // دالة واحدة تقوم بعمل الدوال الأربعة القديمة
    private void initPathAndHome() {
        int[][] coords;
        int[][] homeCoords;

        // تحديد الإحداثيات بناءً على اللون
        if (color.startsWith("B")) {
            coords = BLUE_PATH;
            homeCoords = new int[][]{{2, 2}, {2, 3}, {3, 2}, {3, 3}};
        } else if (color.startsWith("R")) {
            coords = RED_PATH;
            homeCoords = new int[][]{{2, 11}, {2, 12}, {3, 11}, {3, 12}};
        } else if (color.startsWith("G")) {
            coords = GREEN_PATH;
            homeCoords = new int[][]{{11, 11}, {11, 12}, {12, 11}, {12, 12}};
        } else if (color.startsWith("Y")) {
            coords = YELLOW_PATH;
            homeCoords = new int[][]{{11, 2}, {11, 3}, {12, 2}, {12, 3}};
        } else {
            throw new IllegalArgumentException("Unknown color: " + color);
        }

        // بناء مربعات المسار
        for (int i = 0; i < coords.length; i++) {
            int r = coords[i][0];
            int c = coords[i][1];
            String type = "p";
            String symbol = "--";

            // تحديد نوع المربع بناءً على النمط الثابت في لعبتك
            if (i == 0 || i == 13 || i == 26 || i == 39) {
                type = "star";
                symbol = "**";
            } else if (i >= 51 && i <= 55) {
                type = "safe";
                symbol = "||";
            } else if (i == 56) {
                type = "win";
                symbol = "$$";
            }

            this.path.add(i, new Square(this, r, c, type, symbol));
        }

        // بناء مربعات المنزل
        for (int i = 0; i < 4; i++) {
            this.home.add(i, new Square(this, homeCoords[i][0], homeCoords[i][1], "home", "  "));
        }
    }

    public void printPath() {
        for (Square square : this.path) {
            System.out.print(square + " ");
        }
        System.out.println();
        for (Square square : this.home) {
            System.out.print(square + " ");
        }
        System.out.println();
    }

    // Getters and Setters

    public List<Square> getPath() { return this.path; }
    public List<Square> getHome() { return this.home; }
    public Piece getPiece(int index) { return piece[index]; }
    public Piece[] getPieces() { return piece; }
    public String getColor() { return color; }
    public boolean isComputer() { return isComputer; }
    public void setComputer(boolean computer) { isComputer = computer; }
    public int getNumberRolled() { return numberRolled; }
    public boolean isHasRolledSix() { return hasRolledSix; }
    public String getPlayerRank() { return playerRank; }

    boolean hasWon() {
        for(int i = 0; i < 4; i++) {
            if(!piece[i].isCompleted()) {
                return false;
            }
        }
        return true;
    }

    void rollDice() {
        numberRolled = new Die().roll();
        hasRolledSix = (numberRolled == 6);
    }

    public void setPlayerRank(int rank) {
        switch (rank) {
            case 1: this.playerRank = "first"; break;
            case 2: this.playerRank = "second"; break;
            case 3: this.playerRank = "third"; break;
            default: this.playerRank = "lose";
        }
    }

    @Override
    public String toString() {
        return "Player " + color;
    }

    // اسمح للـ AI بتغيير رقم النرد المحاكي
    public void setNumberRolled(int numberRolled) {
        this.numberRolled = numberRolled;
    }

    // اسمح للـ AI بتحديد ما إذا كان الرقم 6
    public void setHasRolledSix(boolean hasRolledSix) {
        this.hasRolledSix = hasRolledSix;
    }

    // === أضف هذه الدوال في نهاية كلاس Player ===

    // Getters للمتغيرات الخاصة
    public int getNumberRolledValue() { return numberRolled; }
    public boolean getHasRolledSixValue() { return hasRolledSix; }
    public boolean getIsComputerValue() { return isComputer; }
    public String getPlayerRankValue() { return playerRank; }

    // Setters للمتغيرات الخاصة (مطلوبة للنسخ في الـ AI)
    public void setNumberRolledValue(int value) { this.numberRolled = value; }
    public void setHasRolledSixValue(boolean value) { this.hasRolledSix = value; }
    public void setIsComputerValue(boolean value) { this.isComputer = value; }
    public void setPlayerRankValue(String value) { this.playerRank = value; }

    // دالة مساعدة للوصول للقطع (لأن المصفوفة خاصة)
    public Piece[] getPiecesArray() { return piece; }
}
