import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;
import java.awt.*;

public class demo extends JFrame implements Runnable{

    private Graphics g;
    BufferedImage img;
    private int i = 0;
    public demo(){
        this.setTitle("title");
        
        this.setDefaultCloseOperation(3);
        this.setSize(500, 300);

        img = new BufferedImage(500, 300,BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        this.setVisible(true);
        
    }


    @Override
    public void paint(Graphics g1) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLUE);
        g.drawRect(20,100, 400, 39);
        g.setColor(Color.CYAN);
        Random random = new Random();
        int value = random.nextInt(400);
        i+=value*0.1;
        if (i>400){
            i = i-value;
        }
        g.fillRect(20, 100, i, 39);
        g1.drawImage(img, 0, 0, null);
    }

    public static void main(String[] args) {
        (new Thread(new demo())).start();
    }


    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
               
                e.printStackTrace();
            }
        }
        
    }
}
