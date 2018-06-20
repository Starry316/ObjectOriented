package pile;

import util.ImagePool;

import card.Card;
import java.awt.*;
import java.util.*;
import java.util.List;

import static util.Constant.CARDHEIGHT;
import static util.Constant.CARDWIDTH;

/**
 * Created by Starry on 2018/4/28.
 * 这个类是抽牌的牌堆
 */
public class DrawPile extends Pile {
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

    /**
     * 切换显示在最上方的牌
     */
    public void change(){
        pos--;
        if(pos<0)pos=number-1;
    }

    /**
     * 检测是否点击
     * @param x
     * @param y
     * @return
     */
    public boolean checkClicked(int x ,int y){
        if(Math.abs(x-(position_x-120+width/2))<width/2&&Math.abs(y-(position_y+height/2))<height/2)
                return true;
        return false;
    }

    /**
     * 检测是否选中
     * @param x
     * @param y
     * @return
     */
    public boolean checkSelected(int x ,int y){
        if(number>0)
            if(Math.abs(x-(position_x+width/2))<width/2&&Math.abs(y-(position_y+height/2))<height/2)
                return true;
        return false;
    }

    @Override
    public boolean checkMoveIn(Card card, int x, int y) {
        return false;
    }

    /**
     * 清空牌堆中的牌
     */
    public void clear(){
        pile.clear();
        number=0;
        pos=0;
    }

    /**
     * 返回最上方的牌并移出牌堆
     * @return
     */
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

    /**
     * 返回最上方的牌但是不移出牌堆
     * @return
     */
    public Card peek(){
        if(pos<0){
           // /pos=number-1;
            return null;
           // return pile.get(pos);
        }

        return pile.get(pos);
    }

    /**
     * 将新牌push进来
     * @param card
     */
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


