package thing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Starry on 2018/4/11.
 */
public class Card extends JPanel {

    private int position_x;
    private int position_y;
    private int type;
    private int number;
    public Card (){

    }
    public Card (int position_x ,int position_y,int type,int number){
        this.position_x=position_x;
        this.position_y=position_y;
        this.type=type;
        this.number=number;
    }
    public void paintCard(Graphics2D g){
        Stroke s =g.getStroke();
        g.setColor(Color.blue);
        g.fillRect(position_x,position_y,70,100);
        g.setStroke(s);
    }
    public int getPosition_x() {
        return position_x;
    }

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
