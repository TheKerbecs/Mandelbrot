
import com.sun.prism.paint.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_W;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author radle
 */
public class panel extends javax.swing.JPanel {

    /**
     * Creates new form panel
     */
    BufferedImage bim;
    static final int MAX_ITER = 1000;
    static final double DEFAULTZOOM = 300.0;
    static final double DEFAULTTOPLEFTX = -3.5;
    static final double DEFAULTTOPLEFTY = +1.6;
    double zoomFactor = DEFAULTZOOM;
    double topLeftX = DEFAULTTOPLEFTX;
    double topLeftY = DEFAULTTOPLEFTY;
    int WIDTH = 1792, HEIGHT = 1008;
    //double x = 531.3;
    //double y = 450.2;
    double x = WIDTH/2;
    double y = HEIGHT/2;
    private frame fr;
    Timer Timr;
    int delay = 10;

    public panel() {
        initComponents();
        this.setFocusable(true);
        this.requestFocus();
        bim = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Timr = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjustZoom(x, y, zoomFactor * 1.2f);
                x = WIDTH/2;;
                y = HEIGHT/2;
            }
        });

    }

    public void setFrame(frame fr) {
        this.fr = fr;
        updateFractal();
    }

    private void updateFractal() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double c_r = getXPos(x);
                double c_i = getYPos(y);
                int iterCount = computeIterations(c_r, c_i);
                int pixelColor = makeColor(iterCount);
                bim.setRGB(x, y, pixelColor);
            }
        }
        String timerRunning = Timr.isRunning() ? "Running":"Stopped";
        fr.setTitle("Zoom: " + Integer.toString((int)zoomFactor) + "%" + "    lmb zoom in, rmb zoom out, wasd to move" + "Timer: " + timerRunning );
        repaint();
    }

    private int makeColor(int iterCount) {

        int color = 0b011011100001100101101000;
        int mask = 0b000000000000010101110111;
        int shiftMag = iterCount / 13;

        if (iterCount == MAX_ITER) {
            return Color.BLACK.getIntArgbPre();
        }

        return color | (mask << shiftMag);
    }

    private double getXPos(double x) {
        return x / zoomFactor + topLeftX;
    }

    private double getYPos(double y) {
        return y / zoomFactor - topLeftY;
    }

    private int computeIterations(double cr, double ci) {
        double zr = 0.0;
        double zi = 0.0;
        int iterCount = 0;
        while (zr * zr + zi * zi <= 4.0) {
            double zrtmp = zr;
            zr = zr * zr - zi * zi + cr;
            zi = 2 * zi * zrtmp + ci;
            if (iterCount >= MAX_ITER) {
                return MAX_ITER;
            }
            iterCount++;
        }
        return iterCount;
    }

    private void moveUp() {
        double curHeight = HEIGHT / zoomFactor;
        topLeftY += curHeight / 8;
        updateFractal();
     
    }

    private void moveDown() {
        double curHeight = HEIGHT / zoomFactor;
        topLeftY -= curHeight / 10;
        updateFractal();
    }

    private void moveLeft() {
        double curWidth = WIDTH / zoomFactor;
        topLeftX -= curWidth / 10;
        updateFractal();
    }

    private void moveRight() {
        double curWidth = WIDTH / zoomFactor;
        topLeftX += curWidth /10;
        updateFractal();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(bim, 0, 0, this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

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

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
//        Timr.start();
        switch (evt.getExtendedKeyCode()) {
            case VK_W:
                moveUp();
                break;
            case VK_S:
                moveDown();
                break;
            case VK_A:
                moveLeft();
                break;
            case VK_D:
                moveRight();
                break;
           case VK_SPACE:
               if(!Timr.isRunning()){
                   Timr.start();
               }else {
                   Timr.stop();
               }
        }
    }//GEN-LAST:event_formKeyPressed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        double x = (double) evt.getX();
        double y = (double) evt.getY();
        this.x = x;
        this.y = y;
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                if(!Timr.isRunning()){
                    adjustZoom(x, y, zoomFactor * 1.2f);
                }else{
                    adjustZoom(x, y, zoomFactor);
                }
                break;
            case MouseEvent.BUTTON3:
                if(!Timr.isRunning()){
                    adjustZoom(x, y, zoomFactor / 2);
                }else{
                    adjustZoom(x, y, zoomFactor);
                }
                break;
            
        }

    }//GEN-LAST:event_formMousePressed

    private void adjustZoom(double x, double y, double d) {

        topLeftX += x / zoomFactor;
        topLeftY -= y / zoomFactor;
        zoomFactor = d;
        topLeftX -= (WIDTH / 2) / zoomFactor;
        topLeftY += (HEIGHT / 2) / zoomFactor;
        updateFractal();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
