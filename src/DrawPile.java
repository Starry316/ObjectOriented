import util.ImagePool;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static util.Constant.CARDHEIGHT;
import static util.Constant.CARDWIDTH;

/**
 * Created by Starry on 2018/4/28.
 */
public class DrawPile extends JPanel {
    private int position_x;
    private int position_y;
    private int width;
    private int height;
    private int number;
    private List<Card> pile=new LinkedList<Card>();
    private int pos;
    public DrawPile (){

    }
    public DrawPile (int position_x ,int position_y,int width,int height){
        this.position_x=position_x;
        this.position_y=position_y;
        this.number=0;
        this.width=width;
        this.height=height;
        pos=0;
    }
    public void paintCardPile(Graphics2D g){
        if(pos>0)
        g.drawImage(ImagePool.backimage,position_x-120,position_y,CARDWIDTH,CARDHEIGHT,null);
        for(int i =0 ;i<=pos;i++){
            pile.get(i).paintCard(g);
        }
    }
    public void change(){
        pos--;
        if(pos<0)pos=number-1;
    }
    public boolean checkClicked(int x ,int y){
        if(Math.abs(x-(position_x-120+width/2))<width/2&&Math.abs(y-(position_y+height/2))<height/2)
                return true;
        return false;
    }
    public boolean checkSelected(int x ,int y){
        if(number>0)
            if(Math.abs(x-(position_x+width/2))<width/2&&Math.abs(y-(position_y+height/2))<height/2)
                return true;
        return false;
    }
    public void clear(){
        pile.clear();
        number=0;
        pos=0;
    }
    public Card pop(){
        if(number>0){
            number--;
            Card tmp = pile.get(pos);
            pile.remove(tmp);
            pos--;
            return tmp;
        }
        return null;
    }
    public Card peek(){
        if(pos<0){
           // /pos=number-1;
            return null;
           // return pile.get(pos);
        }

        return pile.get(pos);
    }

    public void pushCard(Card card){
        pile.add(card);number++;
        card.setPosition_x(position_x);
        card.setPosition_y(position_y);
        pos=number-1;
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



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPos() {
        return pos;
    }
}


