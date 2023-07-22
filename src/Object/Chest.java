package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Chest extends SuperObject {
    public Chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/chest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
