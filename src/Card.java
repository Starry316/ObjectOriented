import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static util.Constant.*;

/**
 * Created by Starry on 2018/4/11.
 */
public class Card extends JPanel {

    private int position_x;
    private int position_y;
    private int side; //zhengfanmian
    private int type;
    private int number;
    public Card (){

    }
    public Card (int type,int number,int side){

        this.type=type;
        this.number=number;
        this.side=side;

    }
    public void paintCard(Graphics2D g){
        Stroke s =g.getStroke();
        Image image= null;
        if (side==UPSIDE) {
            String picture = "picture/";
            switch (type){
                case DIAMOND : picture+="2-";break;
                case HEART :picture+="0-";break;
                case CLUB :picture+="3-";break;
                case SPADE:picture+="1-";break;
            }
            picture+=(number-1)+".png";

            try {
                image = ImageIO.read(new File(picture));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                String picture = "picture/myback.png";
                ImageIcon icon=new ImageIcon(picture);
                image = ImageIO.read(new   File(picture));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

     //   ImageIcon icon=new ImageIcon(picture);
        g.drawImage(image, position_x, position_y, 96, 130, null);
    }
    public void move(int moveX,int moveY){

        this.position_x=moveX;
        this.position_y=moveY;

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

    public void setSide(int side) {
        this.side = side;
    }

    public int getSide() {
        return side;
    }
}
