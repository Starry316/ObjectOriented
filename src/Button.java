import util.ImagePool;

import javax.swing.*;
import java.awt.*;

import static util.Constant.CARDHEIGHT;
import static util.Constant.CARDWIDTH;

/**
 * Created by Starry on 2018/5/13.
 */
public class Button extends JPanel {
    private int width;
    private int height;
    private int position_X;
    private int position_Y;
    private int status=0;
    public Button(){

    }
    public Button(int position_X,int position_Y,int width,int height){
        this.position_X=position_X;
        this.position_Y=position_Y;
        this.width=width;
        this.height=height;
    }

    public void paintButton(Graphics2D g){
        if (status==0)
        g.setColor(new Color(237,237,237));
        else  g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD, 30));
        g.drawString("Restart",position_X,position_Y+30);
    }
    public boolean checkIn(int x,int y){
        if (x>position_X&&x<position_X+width&&y>position_Y&&y<position_Y+height)return true;
        return false;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
