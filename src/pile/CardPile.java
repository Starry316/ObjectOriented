package pile;

import card.Card;
import util.ImagePool;

import java.awt.*;
import java.util.*;

import static util.Constant.CARDHEIGHT;
import static util.Constant.CARDWIDTH;

/**
 * Created by Starry on 2018/4/28.
 * 这个类是最后放置牌的牌堆
 */
public class CardPile  extends Pile {
        private Stack<Card> pile;
        public CardPile (){
        }
        public CardPile (int position_x ,int position_y,int width,int height,int number){
            this.position_x=position_x;
            this.position_y=position_y;
            this.number=number;
            this.width=width;
            this.height=height;
            pile=new Stack<Card>();
        }
        //绘制方法 ， 在实际应用中做了修改，加了参数，没有重载父类方法
        public void paintCardPile(Graphics2D g, Card movingCard){
            Stroke s =g.getStroke();
            g.setColor(Color.cyan);
            g.drawImage(ImagePool.pileImage, position_x, position_y, CARDWIDTH, CARDHEIGHT, null);
            g.setStroke(s);
            for(Card i :pile){
                if(movingCard!=null) {
                    if (i != movingCard)
                        i.paintCard(g);
                }
                else i.paintCard(g);
            }
        }
        public void pushCard(Card card){
            card.setPosition_y(position_y);
            card.setPosition_x(position_x);
            pile.push(card);
            number++;
        }

    @Override
    public void paintCardPile(Graphics2D g) {

    }

    @Override
    public boolean checkClicked(int x, int y) {
        return false;
    }
    @Override
    public boolean checkSelected(int x , int y){
            if(number>0)
            if(Math.abs(x-(position_x+width/2))<width/2&&Math.abs(y-(position_y+height/2))<height/2)
                return true;
            return false;
        }
    public Card pop(){
            if(number>0){
                number--;
                return pile.pop();
            }
            return null;
        }
    public Card peek(){
            return pile.peek();
        }
    @Override
    public boolean checkMoveIn(Card card , int x, int y){

            if(Math.abs(x-(position_x+width/2))<width/2&&Math.abs(y-(position_y+height/2))<height/2) {

                if (this.number>0&&pile.peek().getNumber() == card.getNumber() - 1 && pile.peek().getType() == card.getType())
                    return true;
                else if (this.number == 0 && card.getNumber() == 1) return true;
            }
            return false;
        }
    public boolean checkMoveIn(Card card){
            if (this.number>0&&pile.peek().getNumber() == card.getNumber() - 1 && pile.peek().getType() == card.getType())
                return true;
            else if (this.number == 0 && card.getNumber() == 1) return true;
            return false;
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


    }


