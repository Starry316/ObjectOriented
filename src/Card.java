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
    Image imageUp= null;
    Image imageBack =null;
    public Card (){

    }
    public Card (int type,int number,int side){

        this.type=type;
        this.number=number;
        this.side=side;
        String pictureUp = "picture/";
            switch (type){
                case DIAMOND : pictureUp+="2-";break;
                case HEART :pictureUp+="0-";break;
                case CLUB :pictureUp+="3-";break;
                case SPADE:pictureUp+="1-";break;
            }
        pictureUp+=(number-1)+".png";

        try {
            imageUp = ImageIO.read(new File(pictureUp));
        } catch (IOException e) {
                e.printStackTrace();
        }



        try {
            String pictureBack = "picture/myback.png";
            ImageIcon icon=new ImageIcon(pictureBack);
            imageBack = ImageIO.read(new   File(pictureBack));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
    public void paintCard(Graphics2D g){
        Stroke s =g.getStroke();

        if(side==UPSIDE)
        g.drawImage(imageUp, position_x, position_y, 96, 130, null);
        else g.drawImage(imageBack, position_x, position_y, 96, 130, null);
    }

    public void paintSelectedCard(Graphics2D g){
        Stroke s =g.getStroke();
        AlphaComposite alphaComposite1=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.42f);
        g.setComposite(alphaComposite1);//透明度
        g.fillRect(position_x-4,position_y-4,104,138);
        AlphaComposite alphaComposite2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g.setComposite(alphaComposite2);
        if(side==UPSIDE)
            g.drawImage(imageUp, position_x, position_y, 96, 130, null);
        else g.drawImage(imageBack, position_x, position_y, 96, 130, null);

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
