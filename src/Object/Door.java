package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Door extends SuperObject {
    public Door() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/door.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
