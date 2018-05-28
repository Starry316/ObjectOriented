package pile;

import card.Card;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Starry on 2018/5/13.
 */
public abstract class Pile extends JPanel {
    protected int position_x; //记录绘制位置
    protected int position_y;
    protected int width; //宽度
    protected int height; //高度
    protected int number; //目前含有的牌的个数
    protected List<Card> pile=new LinkedList<Card>(); //存储牌的链表

    /**
     * 用于绘制牌堆的方法
     * @param g
     */
    public abstract void paintCardPile(Graphics2D g);

    /**
     * 用于检测是否点击到牌堆方法
     * @param x
     * @param y
     * @return
     */
    public abstract boolean checkClicked(int x ,int y);

    /**
     * 检测是否选中牌堆中牌的方法
     * @param x
     * @param y
     * @return
     */
    public abstract boolean  checkSelected(int x ,int y);

    /**
     * 检测某张牌是否可以移动到牌堆
     * @param card
     * @param x
     * @param y
     * @return
     */
    public abstract boolean checkMoveIn(Card card , int x, int y);
}
