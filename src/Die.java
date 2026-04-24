import java.util.Random;

public class Die {
    // نعرّف Random مرة واحدة كمتغير ثابت (Static) لأداء أفضل
    private static final Random random = new Random();

    public int roll() {
        // nextInt(6) تعطي رقم من 0 إلى 5، نجمع 1 ليصبح من 1 إلى 6
        return random.nextInt(6) + 1;
    }
}
