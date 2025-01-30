import java.util.Random;

public class Die {
    private int face;

    int roll() {
        int face = (int) (Math.random() * 6 + 1);
        assert face>=1 && face<=6;
        return face;
    }

}
