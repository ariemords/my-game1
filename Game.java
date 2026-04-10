import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// המחלקה הראשית של המשחק
public class Game extends JPanel implements ActionListener, KeyListener {
    
    // מיקומי השחקן
    int playerX = 50, playerY = 270, playerW = 30, playerH = 30;
    double velY = 0;
    double gravity = 0.6;
    boolean isGrounded = false;
    
    // מיקומי האויב
    int enemyX = 800, enemyY = 270, enemyW = 30, enemyH = 30;
    int enemySpeed = 3;
    
    Timer timer;

    public Game() {
        setFocusable(true);
        addKeyListener(this);
        // טיימר שמעדכן את המשחק 60 פעמים בשנייה
        timer = new Timer(16, this);
        timer.start();
    }

    // פונקציית הציור של המשחק
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // רקע שמיים
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, 800, 400);
        
        // רצפה
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, 300, 800, 100);
        
        // שחקן (ירוק)
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, playerW, playerH);
        
        // אויב (אדום)
        g.setColor(Color.RED);
        g.fillRect(enemyX, enemyY, enemyW, enemyH);
    }

    // הלוגיקה שקורית כל פריים
    @Override
    public void actionPerformed(ActionEvent e) {
        // כוח משיכה
        velY += gravity;
        playerY += velY;

        // עצירה על הרצפה
        if (playerY >= 270) {
            playerY = 270;
            velY = 0;
            isGrounded = true;
        }

        // תנועת אויב
        enemyX -= enemySpeed;
        if (enemyX < 0) {
            enemyX = 800; // מחזיר את האויב להתחלה
        }

        // בדיקת התנגשות (Game Over)
        Rectangle pRect = new Rectangle(playerX, playerY, playerW, playerH);
        Rectangle eRect = new Rectangle(enemyX, enemyY, enemyW, enemyH);
        
        if (pRect.intersects(eRect)) {
            playerX = 50; // חזרה להתחלה במקרה של פסילה
            enemyX = 800;
        }

        repaint(); // ציור מחדש של המסך
    }

    // קליטת מקשים מהמקלדת
    @Override
    public void keyPressed(KeyEvent e) {
        // קפיצה
        if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) && isGrounded) {
            velY = -12;
            isGrounded = false;
        }
        // תנועה ימינה ושמאלה
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) playerX += 5;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) playerX -= 5;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    // הפונקציה שמפעילה את התוכנה
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Platformer Game");
        Game game = new Game();
        frame.add(game);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // ממקם את החלון באמצע המסך
        frame.setVisible(true);
    }
}