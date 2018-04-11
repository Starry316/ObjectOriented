import thing.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Starry on 2018/4/11.
 */
public class panel extends JPanel {
    private int height = 0;
    private int width = 0;
    private int x_loacte = 0;
    private int y_loacte = 0;
    private Main mainBoard = null;
    private Card test ;
    public panel(int width, int height, Main mainBoard) {
        this.mainBoard = mainBoard;
        this.height = height;
        this.width = width;
        setLayout(null);
        setBounds(x_loacte, y_loacte, width, height);
        setBackground(Color.white);
        setVisible(true);
        setFocusable(true);

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        test.paintCard(gg);
    }
    public void Init() {
        test = new Card(60,60,1,1);
        new paintThread().start();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("鼠标点击Panel："+e.getX()+"  "+e.getY());
            }
        });


    }

    private class paintThread extends Thread{
        @Override
        public void run() {
            while(true){
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
