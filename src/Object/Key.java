package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends SuperObject {
    public Key() {
        name = "Key";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/key.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
