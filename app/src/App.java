import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class App extends JFrame implements ActionListener, KeyListener,Runnable{

  JTextField txtMax;
  JButton btnAddPro,btnAddCon,btnSet,btnStart;
  JPanel panelContent;
  private Content content;
  private int indexC = 1,indexP = 1,max = 9;
  private List<Thread> process;
  private Panel Mypanel;
  JLabel Mylbl;

  public static void main(String[] args) throws Exception {
    App a = new App();
    (new Thread(a)).start();
  }

  public App() {

    process = new ArrayList<Thread>();
    this.setDefaultCloseOperation(3);
    this.setSize(700, 500);
    this.setLocationRelativeTo(null);

    Container con = this.getContentPane();
    con.setLayout(new FlowLayout());
    con.setBackground(Color.DARK_GRAY);
   

    Font font  = new Font("Calibri",Font.BOLD,18);
    Font font1  = new Font("Calibri",Font.BOLD,12);

    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());

    JPanel panel11 = new JPanel();
    JLabel lbl = new JLabel("Tổng số linh kiện");
    txtMax = new JTextField(5);
    btnSet = new JButton("Đặt");
    btnSet.setFont(font);
    lbl.setFont(font);
    panel11.add(lbl);
    txtMax.setFont(font);
    panel11.add(txtMax);
    panel11.add(btnSet);
    panel1.add(panel11,BorderLayout.WEST);

    JPanel panel12 = new JPanel();
    btnAddPro = new JButton("Thêm");
    btnAddCon = new JButton("Thêm");
    btnAddCon.setFont(font1);
    btnAddPro.setFont(font1);
    panel12.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();  
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel lbl2 = new JLabel("Sản Xuất ");
    lbl2.setFont(font);
    panel12.add(lbl2,gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    panel12.add(btnAddPro,gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel lbl1 = new JLabel("Tiêu dùng");
    lbl1.setFont(font);
    panel12.add(lbl1,gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    panel12.add(btnAddCon,gbc);

    panel1.add(panel12,BorderLayout.CENTER);

    btnStart = new JButton("Start");
    btnStart.setFont(font);
    panel1.add(btnStart,BorderLayout.EAST);

    con.add(panel1);

    panelContent = new JPanel();
    panelContent.setLayout(new GridLayout(5,2));
    panelContent.setBackground(Color.WHITE);
    panelContent.setPreferredSize(new Dimension(760,375));
    Mylbl = new JLabel("");
    Mypanel = new Panel();
    Mypanel.setPreferredSize(new Dimension(400,25));
    con.add(Mylbl);
    con.add(Mypanel);
    
    con.add(panelContent);
    

    this.setVisible(true);

    btnAddCon.addActionListener(this);
    btnAddPro.addActionListener(this);
    txtMax.addKeyListener(this);
    btnSet.addActionListener(this);
    btnStart.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()== btnAddCon){
      if(process.size()>max){
        JOptionPane.showMessageDialog(null, "Tối đa tổng Nhà SX và nhà tiêu dùng la 10 !");
        return;
      }
      if(content == null){
        JOptionPane.showMessageDialog(null, "Vui lòng đặt kích thước kho!");
      }
      else{
        String name = "Consumer "+indexC;
        Consumer c= new Consumer(content,name);
        Thread t =  new Thread(c);
        process.add(t);
        JPanel p1 = new JPanel();
        p1.add(new JLabel(name));
        JButton btn = new JButton("Stop");
        p1.add(btn);
        JButton btn1 = new JButton("Start");
        p1.add(btn1);         
        JButton btn2 = new JButton("Exit");
        p1.add(btn2);
        panelContent.add(p1);
        indexC+=1;
        btn.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) { 
            t.suspend();                                                
          } 
        } );
         btn1.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) { 
            if(t.isAlive())t.resume();
            else t.start();
          } 
        } );
        btn2.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) {
            process.remove(t);
            t.stop();
            c.dispose();
            panelContent.remove(p1);
            max+=1;
          } 
        } );
        panelContent.revalidate();
      }
    }
    if(e.getSource()==btnAddPro){
      if(indexC+indexP-2>max){
        JOptionPane.showMessageDialog(null, "Tối đa tổng Nhà SX và nhà tiêu dùng la 10 !");
        return;
      }
      if(content == null){
        JOptionPane.showMessageDialog(null, "Vui lòng đặt kích thước kho!");
      }
      else{
        String name = "Producer "+indexP;
        Producer pr = new Producer(content,name);
        Thread t = new Thread(pr);
        process.add(t);
        JPanel p1 = new JPanel();
        p1.add(new JLabel(name));
        JButton btn = new JButton("Stop");
        p1.add(btn);
        JButton btn1 = new JButton("Start");
        p1.add(btn1);
        JButton btn2 = new JButton("Exit");
        p1.add(btn2);
        panelContent.add(p1);
        indexP+=1;
        btn.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) { 
            t.suspend();
          } 
        } );
         btn1.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) { 
            if(t.isAlive())
              t.resume();
            else t.start();
          } 
        } );
        btn2.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) { 
            process.remove(t);
            t.stop();
            pr.dispose();
            panelContent.remove(p1);
          } 
        } );
        panelContent.revalidate();
      }
    }
    if(e.getSource() == btnStart){
      if(process.size()==0){
        JOptionPane.showMessageDialog(null, "Vui lòng thêm nhà sản xuất và tiêu dùng !");
        return;
      }
      for(int i=0;i<process.size();i++){
        if(!process.get(i).isAlive())
          process.get(i).start();
      }
    }
    if(e.getSource() == btnSet){
      int number = 0;
      try{
        number = Integer.parseInt(txtMax.getText());
        if(number>5000){
          JOptionPane.showMessageDialog(null, "Kích thước tối đa 5000!");
          return;
        }
        if(txtMax.getText().trim().equals("")){
          JOptionPane.showMessageDialog(null, "Vui lòng nhập sức chứa kho!");
        }
        else{
          txtMax.setEditable(false);
          content = new Content(Integer.parseInt(txtMax.getText().trim()));
        }
      } catch (Exception e1) {}
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    char key =e.getKeyChar();
    this.dispatchEvent(e);
    try {
      Integer.parseInt(key+"");
    } catch (Exception e1) {
      e.consume();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
   
    
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int number = 0;
    try {
      number = Integer.parseInt(txtMax.getText());
      if(number>5000){
        JOptionPane.showMessageDialog(null, "Kích thước tối đa 5000!");
      }
    } catch (Exception e1) {}
  }

  @Override
  public void run() {
    float value1 = 0,value = 0;
    int v = 0;
    while(true){

      if(content != null)
      {
        value = 0;
        value1 = (((float)content.getItems()/(float)content.getMaximum()));
        value = value1*400;
        v= (int)value;
      }
        Mypanel.setY(v);
        Mypanel.repaint();
        Mylbl.setForeground(Color.RED);
        value1*=100;
        Mypanel.setTxt("Tồn kho "+String.format("%.2f", value1)+"%");
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      
    }
  }
}
