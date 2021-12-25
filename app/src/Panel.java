import java.awt.Color;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel {
    
    private int y = 0;
    private String txt;

    Panel(){
      super();
    }
    public String getTxt() {
        return txt;
    }
    public void setTxt(String txt) {
        this.txt = txt;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0,400,25);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0,y,25);
        g.setColor(Color.BLACK);
        g.drawString(txt, 10, 17);
    }
}
