package main;

import entity.Player;
import tiles.TileManager;
import Object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // thiết kế giao diện của trò chơi
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldRow * tileSize;
    public final int worldHeight = maxWorldCol * tileSize;


    // FPS = Frame Per Second
    int FPS = 60;

//    // set vị trí hiện tại của nv và tốc độ di chuyển
//    int playerX = 100, playerY = 100;
//    int playerSpeed = 4;
    // constructor ko tham số
    public GamePanel() {
        // set giao diện cửa sổ
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        //thêm sự kiện ấn nút và để cho màn hình có thể tập trung vào sự kiện ấn nút
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setUpGame() {
        setter.setObject();
    }

    KeyHandler keyHandler = new KeyHandler();
//    tạo luồng để game có thể chạy liên tục mà không bị ngắt quãng bới hành động
    Thread gameThread;
    public SuperObject obj[] = new SuperObject[10];
    public CollisionCheck check = new CollisionCheck(this);
    public AssertSetter setter = new AssertSetter(this);
    public Player player = new Player(this, keyHandler);
    TileManager manager = new TileManager(this);
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }



    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                /* trong vòng lặp chính của trò chơi dùng để làm 2 việc đó là update và draw
            ra màn hình cập nhật trạng thái(vị trí, hoạt động) của nhân vật liên tục và vẽ nhân vật lên panel

            update : update information such as charater positions
            draw : draw the screen with the updated information
             */
                update();
                repaint();
                delta--;
                drawCount++; // đếm số lần vẽ
            }

            if(timer >= 1000000000) {
                System.out.println("FPS = " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }


    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // tile
        manager.draw(g2);
        // object
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        // player
        player.draw(g2);
        g2.dispose();
    }

}
