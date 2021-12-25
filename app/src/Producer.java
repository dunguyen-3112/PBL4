import java.awt.*; 
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Producer extends JFrame implements Runnable{

    private Content content;

    private String _name;


    List<String> message;

    BufferedImage img;

    private Graphics g;

    public Producer(Content content, String _name) {
        this.content = content;
        this._name = _name;
        this.message = new ArrayList<String>();
        String m = String.format("%6s", "Sản xuất")+String.format("%42s", "Số lượng")+String.format("%48s","Sức chứa")+
        String.format("%53s","Thời gian");
        this.message.add(m);
        img = new BufferedImage(780, 415,BufferedImage.TYPE_4BYTE_ABGR);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(780, 415);
        //this.setLocationRelativeTo(null);

        this.setTitle(_name);
        this.setVisible(true);
        g = img.getGraphics();
        g.setFont(new Font("Calibri",Font.BOLD,14));
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }


    @Override
    public void paint(Graphics g1) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(5, 0,758,370);
        for(int i=0;i<message.size();i++){
            if (i==0){
                g.setColor(Color.BLUE);
                g.fillRect(5, i*16, 758, 16);
                g.setColor(Color.WHITE);
                g.drawString(message.get(i), 25, (i+1)*16);
                continue;
            }
            if(i%2==1){
                g.setColor(Color.WHITE);
                g.fillRect(5, i*16, 758, 16);
                g.setColor(new Color(80,120,120));
                g.drawString(message.get(i), 25, (i+1)*16);
                continue;
            }
            g.setColor(new Color(240,240,240));
            g.drawString(message.get(i), 25, (i+1)*16);
        }
        g1.drawImage(img, 5, 30, null);
    }



    @Override
    public void run() {
        while (true) {
            synchronized(this.content){
                if(message.size()>20) message.remove(1);
                Random random = new Random();
                int value = random.nextInt(this.content.getMaximum()/4+1);
                if(value == 0)  continue;
                if(this.content.isAlive() || !this.content.isPut(value))
                    this.setTitle(this._name +" awaiting put "+value);
                this.content.put(value);
                this.setTitle(this._name);
                Date time  = new Date();
                String m = String.format("%-50s", value+"")+String.format("%-48s", this.content.getItems()+"/"+this.content.getMaximum())+
                String.format("%-48s",this.content.getBuffer()+"/"+this.content.getMaximum())+
                String.format("%-50s", time.getHours() - 12 +":"+time.getMinutes()+":"+time.getSeconds()+ 
                (time.getHours()>12 ? " PM" : " AM"));
                message.add(m);
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
