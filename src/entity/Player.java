package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gp = gamePanel;
        this.keyH = keyHandler;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // khoảng cứng để check va chạm
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        Speed = 4;
        // set hướng đi ban đầu cho người chơi khi xuất hiện trên màn hình
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // xử lý sự kiện ấn nút và xử lý hoạt ảnh
    public void update() {
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if(keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            }
            // kiểm tra va chạm của từng khối
            collisionOn = false;
            gp.check.checkTile(this);

            // kiểm tra va chạm của đối tượng
            int objIndex = gp.check.checkObject(this, true);
            pickUpObj(objIndex);
            // nếu va chạm = false thì cho đi
            if(collisionOn == false) {
                switch (direction) {
                    case "up": worldY -= Speed;
                    break;
                    case "down": worldY += Speed;
                    break;
                    case "left": worldX -= Speed;
                    break;
                    case "right": worldX += Speed;
                    break;
                }
            }

            /* mỗi lần gọi hàm update tăng chỉ số Count nếu vượt quá 12
            đặt lại Count, và gán lại giá trị cho Num để thay đổi hoạt ảnh
            tức là sau mỗi 12 milisecond sẽ vẽ lại hoạt ảnh
             */
            spriteCount++;
            if(spriteCount > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum ==2) {
                    spriteNum =1;
                }
                spriteCount =0;
            }
        }

    }

    // set hiệu ứng khi va chạm với từng object cụ thể
    public void pickUpObj(int i) {
        if(i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door");
                    } else {
                        gp.ui.showMessage("You need a key");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    Speed += 1;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed Up !!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
