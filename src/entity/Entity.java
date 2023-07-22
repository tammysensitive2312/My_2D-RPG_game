package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
//    protected int healPoint;
//    protected int manaPoint;
//    protected int Attack;
//    protected int Defence;
    public int worldX, worldY;
    public int Speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCount = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
