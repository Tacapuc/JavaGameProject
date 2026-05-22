/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import entity.Player;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.MainP;

public class TileManager {

    MainP gp;
    Tile[] tile;
    int mapTileNum[][];
    public int spawnCol = 0;
    public int spawnRow = 0;

    public TileManager(MainP gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/resources/maps/castletest");
    }

    public void getTileImage() {
        loadTile(0, "/resources/tiles/tiles/void.png", true);
        loadTile(1, "/resources/tiles/tiles/grass4.png", false);
        loadTile(2, "/resources/tiles/water.png", true);
        loadTile(3, "/resources/tiles/sand.png", false);
        loadTile(4, "/resources/tiles/tiles/stone1.PNG", false);
        loadTile(5, "/resources/tiles/tiles/stone2.png", true);
        loadTile(6, "/resources/tiles/leaf.png", true);
        loadTile(7, "/resources/tiles/ph1.png", false);

    }

    private void loadTile(int index, String path, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(path));
            tile[index].collision = collision;
        } catch (IOException e) {
            System.out.println("Failed to load tile: " + path);
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath) {
        int cols = 0, rows = 0;
        // First pass: count rows and columns
        try (InputStream is = getClass().getResourceAsStream(filepath); BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
                System.err.println("Map file not found: " + filepath);
                return;
            }
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] numbers = line.split("\\s+");
                cols = Math.max(cols, numbers.length);
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (cols == 0 || rows == 0) {
            System.err.println("Map file is empty or invalid.");
            return;
        }

        // Update world size
        gp.maxWorldCol = cols;
        gp.maxWorldRow = rows;
        gp.worldWidth = cols * gp.tileSize;
        gp.worldHeight = rows * gp.tileSize;

        mapTileNum = new int[cols][rows];

        // Second pass: read data, handling 'x' for spawn
        try (InputStream is = getClass().getResourceAsStream(filepath); BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
                return;
            }
            int row = 0;
            String line;
            while ((line = br.readLine()) != null && row < rows) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] tokens = line.split("\\s+");
                for (int col = 0; col < cols; col++) {
                    String token = (col < tokens.length) ? tokens[col] : "0";
                    int tileNum;
                    if (token.equals("x")) {
                        // This is the spawn point
                        spawnCol = col;
                        spawnRow = row;
                        tileNum = 4; // stone tile
                    } else {
                        tileNum = Integer.parseInt(token);
                    }
                    // Validate tile index
                    if (tileNum < 0 || tileNum >= tile.length || tile[tileNum] == null) {
                        System.err.println("Invalid tile " + tileNum + " at [" + col + "," + row + "] – using 0");
                        tileNum = 0;
                    }
                    mapTileNum[col][row] = tileNum;
                }
                row++;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int firstCol = gp.cameraX / gp.tileSize;
        int firstRow = gp.cameraY / gp.tileSize;

        int lastCol = firstCol + gp.maxScreenCol + 1;
        int lastRow = firstRow + gp.maxScreenRow + 1;

        // Clamp to world bounds
        firstCol = Math.max(0, firstCol);
        firstRow = Math.max(0, firstRow);
        lastCol = Math.min(lastCol, gp.maxWorldCol);
        lastRow = Math.min(lastRow, gp.maxWorldRow);

        for (int col = firstCol; col < lastCol; col++) {
            for (int row = firstRow; row < lastRow; row++) {
                int tileNum = mapTileNum[col][row];   // safe now
                // Also ensure tileNum is within tile array bounds
                if (tileNum < 0 || tileNum >= tile.length || tile[tileNum] == null) {
                    tileNum = 0; // fallback to grass
                }
                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                int screenX = worldX - gp.cameraX;
                int screenY = worldY - gp.cameraY;
                g2.drawImage(tile[tileNum].image, screenX, screenY,
                        gp.tileSize, gp.tileSize, null);
            }
        }
    }

    public boolean isCollision(double nextX, double nextY, Player player) {

        int leftWorldX = (int) nextX + player.solidArea.x;
        int rightWorldX = (int) nextX + player.solidArea.x + player.solidArea.width;
        int topWorldY = (int) nextY + player.solidArea.y;
        int bottomWorldY = (int) nextY + player.solidArea.y + player.solidArea.height;

        int leftCol = leftWorldX / gp.tileSize;
        int rightCol = rightWorldX / gp.tileSize;
        int topRow = topWorldY / gp.tileSize;
        int bottomRow = bottomWorldY / gp.tileSize;

        // Check 4 corners
        if (tile[mapTileNum[leftCol][topRow]].collision) {
            return true;
        }
        if (tile[mapTileNum[rightCol][topRow]].collision) {
            return true;
        }
        if (tile[mapTileNum[leftCol][bottomRow]].collision) {
            return true;
        }
        if (tile[mapTileNum[rightCol][bottomRow]].collision) {
            return true;
        }

        return false;
    }

}
