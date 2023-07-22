package main;

import entity.Entity;

public class CollisionCheck {
    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.Speed) / gp.tileSize;
                tileNum1 = gp.manager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.manager.mapTileNum[entityRightCol][entityTopRow];
                if(gp.manager.tile[tileNum1].collision == true || gp.manager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.Speed) / gp.tileSize;
                tileNum1 = gp.manager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.manager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.manager.tile[tileNum1].collision == true || gp.manager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.Speed) / gp.tileSize;
                tileNum1 = gp.manager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.manager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.manager.tile[tileNum1].collision == true || gp.manager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.Speed) / gp.tileSize;
                tileNum1 = gp.manager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.manager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.manager.tile[tileNum1].collision == true || gp.manager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        /* cho khoảng cứng trên tăng lên nếu khoảng cứng của thực thể trùng với đối tượng
                        thì cho va chạm của player = true, và khi player = true thì lấy ra chỉ
                        số của obj đó
                        */

                        entity.solidArea.y -= entity.Speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.Speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.Speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.Speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
