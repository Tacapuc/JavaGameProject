import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class MapEditor extends JFrame {
    private static final int TILE_SIZE = 32;
    private static final int DEFAULT_COLS = 20;
    private static final int DEFAULT_ROWS = 20;

    // Tile definitions: index, name, color
    private static final TileInfo[] TILES = {
        new TileInfo(0, "Void", Color.BLACK),
        new TileInfo(1, "Grass", Color.GREEN),
        new TileInfo(2, "Water", Color.BLUE),
        new TileInfo(3, "Sand", Color.YELLOW),
        new TileInfo(4, "Stone", Color.GRAY),
        new TileInfo(5, "Stone (collide)", Color.DARK_GRAY),
        new TileInfo(6, "Leaf", new Color(34, 139, 34)),
        new TileInfo(7, "Placeholder", new Color(255, 0, 255))
    };

    private int cols = DEFAULT_COLS;
    private int rows = DEFAULT_ROWS;
    private int[][] tiles;
    private Point spawn = null;
    private int selectedTile = 1; // default to grass
    private boolean placingSpawn = false;

    private JPanel gridPanel;
    private JLabel statusLabel;
    private JFileChooser fileChooser;

    public MapEditor() {
    setTitle("Tile Map Editor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initialize map array (no GUI yet)
    tiles = new int[rows][cols];

    // Create menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveAsItem = new JMenuItem("Save As");
    newItem.addActionListener(e -> newMap());
    openItem.addActionListener(e -> openMap());
    saveItem.addActionListener(e -> saveMap(false));
    saveAsItem.addActionListener(e -> saveMap(true));
    fileMenu.add(newItem);
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.add(saveAsItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    // Tile palette (vertical scrolling)
    JPanel palette = new JPanel();
    palette.setLayout(new BoxLayout(palette, BoxLayout.Y_AXIS));
    palette.setBorder(BorderFactory.createTitledBorder("Tiles"));

    for (TileInfo tile : TILES) {
        JButton btn = new JButton(tile.name + " (" + tile.index + ")");
        btn.setBackground(tile.color);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getPreferredSize().height));
        btn.addActionListener(e -> {
            placingSpawn = false;
            selectedTile = tile.index;
            statusLabel.setText("Selected: " + tile.name + " (" + tile.index + ")");
        });
        palette.add(btn);
        palette.add(Box.createRigidArea(new Dimension(0, 5))); // spacing between buttons
    }

    // Spawn button
    JButton spawnBtn = new JButton("Spawn (x)");
    spawnBtn.setBackground(Color.RED);
    spawnBtn.setOpaque(true);
    spawnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    spawnBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, spawnBtn.getPreferredSize().height));
    spawnBtn.addActionListener(e -> {
        placingSpawn = true;
        selectedTile = 4; // stone
        statusLabel.setText("Placing spawn (stone tile)");
    });
    palette.add(spawnBtn);

    JScrollPane paletteScroll = new JScrollPane(palette);
    paletteScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    paletteScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    paletteScroll.setPreferredSize(new Dimension(200, 300));
    add(paletteScroll, BorderLayout.WEST);

    // Grid panel
    gridPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGrid(g);
        }
    };
    gridPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            handleMouse(e.getX(), e.getY());
        }
    });
    gridPanel.addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            handleMouse(e.getX(), e.getY());
        }
    });

    // Initialize grid (now safe)
    clearMap();

    JScrollPane gridScroll = new JScrollPane(gridPanel);
    add(gridScroll, BorderLayout.CENTER);

    // Status bar
    statusLabel = new JLabel("Selected: Grass (1)");
    add(statusLabel, BorderLayout.SOUTH);

    fileChooser = new JFileChooser(".");
    pack();
    setLocationRelativeTo(null);
}

    private void handleMouse(int x, int y) {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;
        if (col >= 0 && col < cols && row >= 0 && row < rows) {
            if (placingSpawn) {
                // Remove previous spawn if any
                if (spawn != null) {
                    tiles[spawn.y][spawn.x] = 4; // keep stone under old spawn
                }
                spawn = new Point(col, row);
                tiles[row][col] = 4; // stone tile
                statusLabel.setText("Spawn placed at (" + col + "," + row + ")");
            } else {
                tiles[row][col] = selectedTile;
                statusLabel.setText("Painted at (" + col + "," + row + ") with tile " + selectedTile);
            }
            gridPanel.repaint();
        }
    }

    private void drawGrid(Graphics g) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;
                int tile = tiles[row][col];
                Color color = getTileColor(tile);
                g.setColor(color);
                g.fillRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
            }
        }
        // Draw spawn marker
        if (spawn != null) {
            int x = spawn.x * TILE_SIZE;
            int y = spawn.y * TILE_SIZE;
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, TILE_SIZE));
            FontMetrics fm = g.getFontMetrics();
            String s = "S";
            int sx = x + (TILE_SIZE - fm.stringWidth(s)) / 2;
            int sy = y + (TILE_SIZE + fm.getAscent() - fm.getDescent()) / 2;
            g.drawString(s, sx, sy);
        }
    }

    private Color getTileColor(int index) {
        for (TileInfo t : TILES) {
            if (t.index == index) return t.color;
        }
        return Color.MAGENTA; // fallback for unknown indices
    }

    private void clearMap() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tiles[r][c] = 1; // default to grass
            }
        }
        spawn = null;
        updateGridSize();
    }

    private void updateGridSize() {
        gridPanel.setPreferredSize(new Dimension(cols * TILE_SIZE, rows * TILE_SIZE));
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void newMap() {
        String w = JOptionPane.showInputDialog(this, "Enter width (columns):", cols);
        String h = JOptionPane.showInputDialog(this, "Enter height (rows):", rows);
        try {
            int newCols = Integer.parseInt(w);
            int newRows = Integer.parseInt(h);
            if (newCols > 0 && newRows > 0) {
                cols = newCols;
                rows = newRows;
                tiles = new int[rows][cols];
                clearMap();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number");
        }
    }

    private void openMap() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                // First pass: determine size
                int maxCols = 0;
                int rowCount = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split("\\s+");
                    maxCols = Math.max(maxCols, parts.length);
                    rowCount++;
                }
                scanner.close();

                // Second pass: read data
                Scanner reader = new Scanner(file);
                cols = maxCols;
                rows = rowCount;
                tiles = new int[rows][cols];
                spawn = null;
                int r = 0;
                while (reader.hasNextLine() && r < rows) {
                    String line = reader.nextLine().trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split("\\s+");
                    for (int c = 0; c < cols; c++) {
                        String token = (c < parts.length) ? parts[c] : "0";
                        if (token.equals("x")) {
                            tiles[r][c] = 4; // stone
                            spawn = new Point(c, r);
                        } else {
                            tiles[r][c] = Integer.parseInt(token);
                        }
                    }
                    r++;
                }
                reader.close();
                updateGridSize();
                statusLabel.setText("Loaded " + file.getName());
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage());
            }
        }
    }

    private void saveMap(boolean saveAs) {
        if (saveAs || fileChooser.getSelectedFile() == null) {
            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
        }
        File file = fileChooser.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file)) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (spawn != null && spawn.x == c && spawn.y == r) {
                        writer.print("x");
                    } else {
                        writer.print(tiles[r][c]);
                    }
                    if (c < cols - 1) writer.print(" ");
                }
                writer.println();
            }
            statusLabel.setText("Saved to " + file.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }

    // Helper class for tile info
    private static class TileInfo {
        int index;
        String name;
        Color color;
        TileInfo(int index, String name, Color color) {
            this.index = index;
            this.name = name;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapEditor().setVisible(true));
    }
}