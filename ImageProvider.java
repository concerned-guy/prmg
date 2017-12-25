import java.io.File;
import java.util.Random;

public class ImageProvider {
    private static ImageProvider instance = null;

    private String[] images;
    private Random rand;

    private ImageProvider() {
        File imageDir = new File("image");
        this.images = imageDir.list();
        this.rand = new Random();
    }

    private static ImageProvider getInstance() {
        if (instance == null) instance = new ImageProvider();
        return instance;
    }

    public static String randomImage() {
        return getInstance().images[getInstance().rand.nextInt(getInstance().images.length)];
    }
}
