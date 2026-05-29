package main;

import entity.*;
import java.awt.*;
import static java.lang.Math.sqrt;

import loot.LootItem;
import tile.TileManager;
import item.ItemHandler;
import ui.*;
import combat.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ilya.bespalov
 */
public class MainP extends javax.swing.JPanel implements Runnable {
    //alla konstanter som används och intiering av alla klasser

    public static int tileSize = 64;
    public int maxWorldCol;
    public int maxWorldRow;
    public int worldWidth;
    public int worldHeight;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int scWidth = tileSize * maxScreenCol;
    public final int scHeight = tileSize * maxScreenRow;
    public int cameraX;
    public int cameraY;
    public BufferedImage deadInt;

    public boolean damageNumberTimer;
    int speed = 4;
    MainF mf;

    Thread gameThread;
    int fps = 60;
    public int tick;
    KeyH keyH = new KeyH(this);
    public ItemHandler iH = new ItemHandler(this);
    public Player p1 = new Player("Tacapuc", 100, 100, "player", this);
    public Enemy e1 = new Enemy("Fiend", 100, 100, "enemy", this);
    TileManager tileM = new TileManager(this);

    public MouseH mouseH;
    public UIManager uiManager = new UIManager();
    public EntityManager entManager = new EntityManager();
    public UI_Inventory i_UI = new UI_Inventory(p1, this);
    public UI_CharacterSheet c_UI = new UI_CharacterSheet(this, p1);
    public UI_Tooltip t_UI = new UI_Tooltip();
    public UI_DropdownMenu d_UI = new UI_DropdownMenu(this);
    public UI_UnitFrame ufOwn_UI = new UI_UnitFrame(this, p1);
    public UI_UnitFrame ufTarget_UI = new UI_UnitFrame(this, p1);
    public UI_ActionBar ab_UI = new UI_ActionBar();
    public UI_Spellbook s_UI = new UI_Spellbook(this, p1);
    public UI_LootWindow lW_UI = new UI_LootWindow(this);
    public CombatLogic combatLogic = new CombatLogic(this);
    public UI_DamageNumberManager damageNumberManager = new UI_DamageNumberManager(this);
    public UI_KeyBindGuide kbg_UI = new UI_KeyBindGuide();

    public Font font;
    public Color titleColor = new Color(204, 153, 0);

    public MainP(MainF mf) {
        this.mf = mf;
        mouseH = new MouseH(this, p1, mf);
        initComponents();
        System.out.println(this.scHeight);
        e1.x = 576;
        e1.y = 576;

        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(scWidth, scHeight));
        this.addMouseMotionListener(mouseH);
        this.addMouseListener(mouseH);
        startGameThread();
        //lägga in spelaren i position där "x" är
        p1.x = tileM.spawnCol * tileSize;
        p1.y = tileM.spawnRow * tileSize;
        iH.createItems();
        //lägga in allt i sina listor
        uiInit();
        entInit();
        initLoot();
        iH.allItems();

        //custom font
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("frizqt.ttf")).deriveFont(11f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

    }

    //lägga in saker i loottabellen för en fiende
    public void initLoot() {
        e1.lootTable.addLootItem(new LootItem(iH.getItem(0), 0.8));
        e1.lootTable.addLootItem(new LootItem(iH.getItem(2), 0.2));
        e1.lootTable.addLootItem(new LootItem(iH.getItem(5), 1));
    }

    public void uiInit() {

        uiManager.add(ufOwn_UI);
        uiManager.add(ufTarget_UI);
        uiManager.add(kbg_UI);
        uiManager.add(i_UI);
        uiManager.add(c_UI);

        uiManager.add(ab_UI);
        uiManager.add(s_UI);
        uiManager.add(lW_UI);

        uiManager.add(t_UI);
        uiManager.add(d_UI);

        ufOwn_UI.toggle();
        ab_UI.toggle();
        d_UI.loadAllMenus();
        kbg_UI.toggle();
        ufOwn_UI.closeOnEsc = false;
    }

    public void entInit() {
        entManager.add(p1);
        entManager.add(e1);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //göra så att texten inte är pixlig som helvete
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        g2.setColor(Color.white);
        tileM.draw(g2);
        e1.drawEntity(g2, this);
        p1.drawEntity(g2, this);
        uiManager.draw(g2, this);
        damageNumberManager.draw(g2);

//        g2.setColor(Color.red);
//        g2.fillRect((int)p1.playerX, (int)p1.playerY, tileSize, tileSize);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    //det här hanterar det mesta av logiken i spelet men allt rörelse relaterat borde egentligen ha flyttats till player klassen.
    public void update() {

        damageNumberManager.update();

        int dx = 0;
        int dy = 0;

        if (keyH.moveup) {
            p1.direction = "up";
            dy -= speed;
        }
        if (keyH.movedown) {
            p1.direction = "down";
            dy += speed;
        }
        if (keyH.moveleft) {
            p1.direction = "left";
            dx -= speed;
        }
        if (keyH.moveright) {
            p1.direction = "right";
            dx += speed;
        }
        if (!p1.playingAttackAnim) {
            p1.running = (dx != 0 || dy != 0);
        } else {
            p1.running = false;
        }

        //var spelaren hypotetiskt skulle hamna
        double nextX = p1.x + dx;
        double nextY = p1.y + dy;

        //om det är ingen kollision då flyttar vi
        if (!tileM.isCollision(nextX, p1.y, p1)) {
            p1.x = nextX;
        }

        if (!tileM.isCollision(p1.x, nextY, p1)) {
            p1.y = nextY;
        }

// Centrera kameran/"världen" på spelaren
        cameraX = (int) (p1.x - scWidth / 2 + tileSize / 2);
        cameraY = (int) (p1.y - scHeight / 2 + tileSize / 2);

// Om spelaren är nära kanten ska kameran inte heller visa voiden på hälften av skärmen
        int maxCameraX = Math.max(0, worldWidth - scWidth);
        int maxCameraY = Math.max(0, worldHeight - scHeight);

        cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

        // låta inte spelaren ur världen
        if (p1.x < 0) {
            p1.x = 0;
        }

        if (p1.y < 0) {
            p1.y = 0;
        }

        if (p1.x > worldWidth - tileSize) {
            p1.x = worldWidth - tileSize;
        }

        if (p1.y > worldHeight - tileSize) {
            p1.y = worldHeight - tileSize;
        }

    }

    @Override

    //logik för att uppdatera spelet fps gånger per sekund
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            combatLogic.attack();
            update();
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });

            try {
                double remTime = nextDrawTime - System.nanoTime();
                remTime = remTime / 1000000;
                if (remTime < 0) {
                    remTime = 0;
                }
                Thread.sleep((long) remTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
