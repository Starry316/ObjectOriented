import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static util.Constant.*;

/**
 * Created by Starry on 2018/4/11.
 */
public class GameBoard extends JPanel {
    private int height = 0;
    private int width = 0;
    private int x_loacte = 0;
    private int y_loacte = 0;
    private int origin_x;
    private int origin_y;
    private int selectedPile;
    private int timeCount;
    private boolean movebackflag=false;
    private Main mainBoard = null;
    private int type[] ={HEART,DIAMOND,SPADE,CLUB};
    private Card cards[];
    private Card movingCard;
    private List<Card> movingCards;
    private DrawPile drawPile;
    private CardPile piles[] =new CardPile[4];
    private DeskPile deskPiles[]=new DeskPile[7];
    private CardPile originPile;
    private DeskPile originDeskPile;
    public GameBoard(int width, int height, Main mainBoard) {
        this.mainBoard = mainBoard;
        this.height = height;
        this.width = width;
        setLayout(null);
        setBounds(x_loacte, y_loacte, width, height);
        setBackground(Color.white);
        setVisible(true);
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
             //   super.mousePressed(e);
                for(CardPile i:piles)
                    if(i.checkSelected(e.getX(),e.getY())){
                        movingCard=i.peek();
                        originPile=i;
                        origin_x=movingCard.getPosition_x();
                        origin_y=movingCard.getPosition_y();
                        selectedPile=CARDPILE;
                    }
                for(DeskPile i:deskPiles) {
                        int n=i.checkSelected(e.getX(), e.getY());
                      //  System.out.println(n);
                        if (n > 0) {
                        movingCards = i.peek(n);
                        originDeskPile = i;
                        origin_x = movingCards.get(0).getPosition_x();
                        origin_y = movingCards.get(0).getPosition_y();
                        selectedPile = DESKPILE;
                    }
                }
                 if(drawPile.checkSelected(e.getX(),e.getY())){
                     movingCard=drawPile.peek();
                     if(movingCard!=null) {
                         origin_x = movingCard.getPosition_x();
                         origin_y = movingCard.getPosition_y();
                         selectedPile = DRAWPILE;
                     }
                 }

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.isMetaDown()){//检测鼠标右键单击
                    System.out.println("右键单击");
                    for(DeskPile i:deskPiles){
                        for(CardPile j:piles){
                            if(i.getNumber()>0&&j.checkMoveIn(i.peek())){
                                j.pushCard(i.pop());
                                System.out.println("可以移动");
                                return;
                            }
                        }
                    }
                    for(CardPile j:piles){
                        if(j.checkMoveIn(drawPile.peek())){
                            j.pushCard(drawPile.pop());
                            System.out.println("可以移动draw");
                            return;
                        }
                    }
                }
                else if(drawPile.checkSelected(e.getX(),e.getY()))drawPile.change();

            }


            @Override
            public void mouseMoved(MouseEvent e) {
               // super.mouseMoved(e);
                System.out.println("移动"+e.getX());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println(movingCard.getNumber());
           //     super.mouseReleased(e);
                if(movingCard!=null){
                    for(CardPile i:piles){
                        if(i.checkMoveIn(movingCard,e.getX(),e.getY())) {
                            i.pushCard( movingCard);
                            originPilePOP();
                            movingCard=null;
                            return;
                        }
                    }
                    for(DeskPile i:deskPiles){
                   //     System.out.println(i.checkMoveIn(movingCard,e.getX(),e.getY())+""+movingCard.getNumber());
                        if(i.checkMoveIn(movingCard,e.getX(),e.getY())) {
                            i.pushCard( movingCard);
                            originPilePOP();
                            movingCard=null;
                            return;
                        }

                    }
                    movingCard.move(origin_x,origin_y);
                    movingCard=null;
                }
                else if(movingCards!=null){
                    for(DeskPile i:deskPiles){
                        if(i.checkMoveIn(movingCards,e.getX(),e.getY())) {
                            i.pushCard( movingCards);
                            originDeskPile.pop(movingCards.size());
                            movingCards=null;
                            return;
                        }
                    }
                    for(CardPile i:piles){
                        if(movingCards.size()==1&&i.checkMoveIn(movingCards.get(0),e.getX(),e.getY())) {
                            i.pushCard( movingCards.get(0));
                            originDeskPile.pop(1);
                            movingCards=null;
                            return;
                        }
                    }
                    for(int i=0;i<movingCards.size();i++) {
                        movingCards.get(i).move(origin_x,origin_y+20*i);
                    }movingCards = null;
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(movingCard!=null){
                    movingCard.setPosition_x(e.getX()-35);
                    movingCard.setPosition_y(e.getY()-50);
                }
                if(movingCards!=null){
                    for(int i=0;i<movingCards.size();i++){
                        movingCards.get(i).setPosition_x(e.getX()-35);
                        movingCards.get(i).setPosition_y(e.getY()-50+i*20);
                    }
                }
            }
        }
        );

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        for(CardPile i :piles){
            i.paintCardPile(gg,movingCard);
        }
        for(DeskPile i:deskPiles){
            i.paintCardPile(gg,movingCards);
        }
        drawPile.paintCardPile(gg);
        if (movingCard!=null)
        movingCard.paintSelectedCard(gg);
        if(movingCards!=null)
        for(Card i:movingCards)
            i.paintSelectedCard(gg);
    }
    public void Init() {
        timeCount=0;
        cards=new Card[52];
        int count =-1;
        for(int i=0;i<4;i++)
            for(int j=1;j<=13;j++){
                cards[++count]=new Card(type[i],j,UPSIDE);
            }
        Random generator = new Random();
        for (int i = 0; i < 52; i++) {
            int j = Math.abs(generator.nextInt() % 52);
            Card temp = cards[i];
            cards[i]=cards[j];
            cards[j]=temp;
        }
        drawPile=new DrawPile(200,200,96,130);

        piles[0]= new CardPile(500,200,96,130,0);
        piles[1]= new CardPile(600,200,96,130,0);
        piles[2]= new CardPile(700,200,96,130,0);
        piles[3]= new CardPile(800,200,96,130,0);
        for(int i=0;i<7;i++){
            deskPiles[i]=new DeskPile(200+120*i,400,96,130,0);
        }
        for(int i=0;i<7;i++){
            for(int j=i;j>=0;j--){
                deskPiles[i].pushCard(cards[count--]);
                if(j!=0)deskPiles[i].peek().setSide(BACKSIDE);
            }
        }

        while (count>=0)drawPile.pushCard(cards[count--]);
        new paintThread().start();


    }
    private void originPilePOP(){
        switch (selectedPile){
            case DESKPILE : originDeskPile.pop();break;
            case CARDPILE : originPile.pop();break;
            case DRAWPILE : drawPile.pop();break;
        }
    }
    private boolean isWin(){
        int sum=0;
        for(CardPile i:piles){
            sum+=i.getNumber();
        }
        if (sum==52)return true;
        return false;
    }
    private class paintThread extends Thread{
        @Override
        public void run() {
            while(true){
                repaint();

                try {
                    Thread.sleep(16);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
