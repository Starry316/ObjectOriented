package pile;

import card.Card;

import java.awt.*;
import java.util.*;
import java.util.List;

import static util.Constant.K;
import static util.Constant.UPSIDE;

/**
 * Created by Starry on 2018/4/28.
 * 这个类代表桌子上的七个牌堆
 */
public class DeskPile extends Pile  {
    private int margin =40;
    private Stack<Card> pile;
    public DeskPile(){

    }
    public DeskPile(int position_x ,int position_y,int width,int height,int number){
        this.position_x=position_x;
        this.position_y=position_y;
        this.width=width;
        this.height=height;
        this.number=number;
        pile=new Stack<Card>();
    }
    //绘制方法，后面修改后加了参数，没有继承父类方法
    public void paintCardPile(Graphics2D g,List<Card> movingCards){
        Stroke s =g.getStroke();
        g.setColor(Color.cyan);
       // g.drawRoundRect(position_x,position_y,width,height,20,20);
        g.setStroke(s);
        for(Card i :pile){
            if(movingCards!=null) {
                for (Card j : movingCards)
                    if (j != i)
                        i.paintCard(g);
            }
            else i.paintCard(g);
        }
    }

    /**
     * 将牌加入栈
     * @param card
     */
    public void pushCard(Card card){
            if(number==0) {
                card.setPosition_y(position_y);
                card.setPosition_x(position_x);
            }
            else {
                card.setPosition_x(position_x);
                card.setPosition_y(position_y+margin*number);
            }
        pile.push(card);
        number++;
    }

    /**
     * 将好几张牌加入栈
     * @param cards
     */
    public void pushCard(List<Card> cards){
        for(int i=0;i<cards.size();i++) {
            if (number == 0) {
                cards.get(i).setPosition_y(position_y);
                cards.get(i).setPosition_x(position_x);
            } else {
                cards.get(i).setPosition_x(position_x);
                cards.get(i).setPosition_y(position_y + margin * number);
            }
            pile.push(cards.get(i));
            number++;
        }
    }

    @Override
    public void paintCardPile(Graphics2D g) {

    }

    @Override
    public boolean checkClicked(int x, int y) {
        return false;
    }

    @Override
    public boolean checkSelected(int x, int y) {
        return false;
    }

    /**
     * 检测选中的牌的个数
     * @param x
     * @param y
     * @return 选中的牌的个数
     */
    public int checkSelectedNum(int x , int y) {
        if (number == 1)
            if (Math.abs(x - (position_x + width / 2)) < width / 2 && Math.abs(y - (position_y + height / 2)) < height / 2)
                return 1;
        if (number > 1) {
                if (Math.abs(x - (position_x + width / 2)) < width / 2) {
                    int upnum = 0;
                    for (int i = number - 1; i >= 0; i--) {
                        if (pile.get(i).getSide() == UPSIDE) {
                            upnum++;
                        }
                    }
                    //两种情况，选择一个和选择多个。
                    if (Math.abs(y - (position_y + height / 2) - margin * (number - 1)) < height / 2) {   //选择一个
                        return 1;

                    } else {
                        if (number - (y - position_y) / margin <= upnum)
                            return number - (y - position_y) / margin;
                        return 0;
                    }
                }
            }
            return 0;
    }

    /**
     * 返回最上方牌，并且从牌堆移除
     * @return
     */
    public Card pop(){
        if(number>0){
            number--;
            Card tmp =pile.pop();
            if (pile.size()>0){
                pile.peek().setSide(UPSIDE);
            }
            return tmp;
        }
        return null;
    }

    /**
     * 返回最上方n张牌，并且从牌堆移除
     * @param n
     * @return
     */
    public List<Card> pop(int n){
        List<Card> list=new LinkedList<Card>();
        if(number>=n){
            for(int i=n;i>0;i--)
                list.add(pile.get(number-i));
            for(int i=0;i<n;i++)
            pile.pop();
            if (pile.size()>0){
                pile.peek().setSide(UPSIDE);
            }
            number-=n;
            return list;
        }
        return null;
    }

    /**
     * 返回最上方牌但是不将其从牌堆移除
     * @return
     */
    public Card peek(){
        if(number>0)
        return pile.peek();
        return null;
    }

    /**
     * 返回最上方的n张牌但是不将其从牌堆移除
     * @param n
     * @return
     */
    public List<Card> peek(int n){
        List<Card> list = new LinkedList<Card>();
        for(int i=n;i>0;i--)
            list.add(pile.get(number-i));
        return list;
    }

    /**
     * 检测是否能将牌移入牌堆
     * @param card
     * @param x
     * @param y
     * @return
     */
    public boolean checkMoveIn(Card card , int x, int y){

        if(Math.abs(x-(position_x+width/2))<width/2) {
            if (this.number>0&&(Math.abs(y-(position_y+height/2)-margin*(number-1))<height/2)&&pile.peek().getNumber() == card.getNumber() + 1 && Math.abs(pile.peek().getType() - card.getType())>=2)
                return true;
            if (this.number == 0 && (Math.abs(y-(position_y+height/2))<height/2)&&card.getNumber() == K) return true;
        }
        return false;
    }

    /**
     * 检测是否能放入牌堆
     * @param cards
     * @param x
     * @param y
     * @return
     */
    public boolean checkMoveIn(java.util.List<Card> cards , int x, int y){
        if(Math.abs(x-(position_x+width/2))<width/2)
            if((number==0&&(Math.abs(y-(position_y+height/2))<height/2))||(number>0&&(Math.abs(y-(position_y+height/2)-margin*(number-1))<height/2))){
                if (this.number>0&&pile.peek().getNumber() == cards.get(0).getNumber() + 1 && Math.abs(pile.peek().getType() - cards.get(0).getType())>=2)
                    return true;
                if (this.number == 0 && cards.get(0).getNumber() == K) return true;
            }
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
