package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Boots extends SuperObject {
    public Boots() {
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/boots.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
