package panel;

import card.Card;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pile.CardPile;
import pile.DeskPile;
import pile.DrawPile;


import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static util.Constant.*;

/**
 * Created by Starry on 2018/4/11.
 */
public class GameBoard extends JPanel {
    private int height = 0;
    private int width = 0;
    private int x_loacte = 0;
    private int y_loacte = 0;
    private int dis_x=0;
    private int dis_y=0;
    private int origin_x;
    private int origin_y;
    private int selectedPile;
    private int timeCountS;
    private int timeCountM;
    private boolean startFlag=false;
    private boolean showed=false;
    private int type[] ={HEART,DIAMOND,SPADE,CLUB};
    private Card cards[];
    private Card movingCard;
    private List<Card> movingCards;
    private DrawPile drawPile;
    private CardPile piles[] =new CardPile[4];
    private DeskPile deskPiles[]=new DeskPile[7];
    private CardPile originPile;
    private DeskPile originDeskPile;
    private Button restartButton;



    public GameBoard(int width, int height) {
        this.height = height;
        this.width = width;
        setLayout(null);
        setBounds(x_loacte, y_loacte, width, height);
        setVisible(true);
        setFocusable(true);
        //添加监听器
        addMouseListener(new MouseAdapter() {
            /**
             * 添加拖动的监听
             * 同时检测选中的牌堆以及选中的牌
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {

                if(!startFlag)startFlag=true; //记录游戏开始
                for(CardPile i:piles)
                    if(i.checkSelected(e.getX(),e.getY())){
                        movingCard=i.peek();
                        originPile=i;
                        origin_x=movingCard.getPosition_x();
                        origin_y=movingCard.getPosition_y();
                        dis_x=e.getX()-origin_x;
                        dis_y=e.getY()-origin_y;
                        selectedPile=CARDPILE;
                    }
                for(DeskPile i:deskPiles) {
                        int n=i.checkSelectedNum(e.getX(), e.getY());
                        if (n > 0) {
                        movingCards = i.peek(n);
                        originDeskPile = i;
                        origin_x = movingCards.get(0).getPosition_x();
                        origin_y = movingCards.get(0).getPosition_y();
                        dis_x=e.getX()-origin_x;
                        dis_y=e.getY()-origin_y;
                        selectedPile = DESKPILE;
                    }
                }
                 if(drawPile.checkSelected(e.getX(),e.getY())){
                     movingCard=drawPile.peek();
                     if(movingCard!=null) {
                         origin_x = movingCard.getPosition_x();
                         origin_y = movingCard.getPosition_y();
                         dis_x=e.getX()-origin_x;
                         dis_y=e.getY()-origin_y;
                         selectedPile = DRAWPILE;
                     }
                 }

            }

            /**
             * 鼠标点击的监听器，用于检测右键和左键
             * 点击右键自动检测可以移动到CardPile牌堆的牌
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(!startFlag)startFlag=true; //记录游戏开始
                if (restartButton.checkIn(e.getX(),e.getY()))InitWithoutWashCards();
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
                        if(drawPile.getPos()>=0&&j.checkMoveIn(drawPile.peek())){
                            j.pushCard(drawPile.pop());
                            System.out.println("可以移动");
                            return;
                        }
                    }
                }
                else if(drawPile.checkClicked(e.getX(),e.getY()))drawPile.change(); //左键点击到drawPile的话，drawPile切换

            }

            /**
             * 检测鼠标移动，目前只用于restart按钮的颜色变化
             * @param e
             */
            @Override
            public void mouseMoved(MouseEvent e) {

                if (restartButton.checkIn(e.getX(),e.getY()))restartButton.setStatus(1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if(movingCard!=null){
                    for(CardPile i:piles){
                        if(i.checkMoveIn(movingCard,movingCard.getMiddleX(),movingCard.getMiddleY())) {
                            i.pushCard( movingCard);
                            originPilePOP();
                            movingCard=null;
                            return;
                        }
                    }
                    for(DeskPile i:deskPiles){
                   //     System.out.println(i.checkMoveIn(movingCard,e.getX(),e.getY())+""+movingCard.getNumber());
                        if(i.checkMoveIn(movingCard,movingCard.getMiddleX(),movingCard.getMiddleY())) {
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
                        if(i.checkMoveIn(movingCards,movingCards.get(0).getMiddleX(),movingCards.get(0).getMiddleY())) {
                            i.pushCard( movingCards);
                            originDeskPile.pop(movingCards.size());
                            movingCards=null;
                            return;
                        }
                    }
                    for(CardPile i:piles){
                        if(movingCards.size()==1&&i.checkMoveIn(movingCards.get(0),movingCards.get(0).getMiddleX(),movingCards.get(0).getMiddleY())) {
                            i.pushCard( movingCards.get(0));
                            originDeskPile.pop(1);
                            movingCards=null;
                            return;
                        }
                    }
                    for(int i=0;i<movingCards.size();i++) {
                        movingCards.get(i).move(origin_x,origin_y+40*i);
                    }movingCards = null;
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(!startFlag)startFlag=true; //记录游戏开始
                if(movingCard!=null){
                    movingCard.setPosition_x(e.getX()-dis_x);
                    movingCard.setPosition_y(e.getY()-dis_y);
                }
                if(movingCards!=null){
                    for(int i=0;i<movingCards.size();i++){
                        movingCards.get(i).setPosition_x(e.getX()-dis_x);
                        movingCards.get(i).setPosition_y(e.getY()-dis_y+i*40);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // super.mouseMoved(e);
                if (restartButton.checkIn(e.getX(),e.getY()))restartButton.setStatus(1);
                else restartButton.setStatus(0);
            }}
        );

    }

    /**
     * repaint()调用的绘制方法
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //获胜后的弹窗
        if(isWin()){
            if(!showed) {
                showed = true;
                showCongratulations();
            }

        }
        else {
            //获胜后停止计时
            if (startFlag) timeCountS += 16;
            if (timeCountS > 60000) {
                timeCountS -= 60000;
                timeCountM++;
            }
        }
        Graphics2D gg = (Graphics2D) g;
       // gg.drawImage(table,0,0,width,height,null);
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //绘制背景颜色
        gg.setColor(new Color(129,194,214));
        gg.fillRect(0,0,width,height);
        //绘制最上方的工具栏
        gg.setColor(new Color(29,176,184));
        gg.fillRect(0,0,width,60);
        //绘制时间和restart按钮
        gg.setColor(Color.white);
        gg.setFont(new Font("Arial",Font.BOLD, 30));
        if (timeCountS/1000<10)
            gg.drawString("Time "+timeCountM+":0"+(timeCountS/1000)+"",200,40);
        else
            gg.drawString("Time "+timeCountM+":"+(timeCountS/1000)+"",200,40);
        restartButton.paintButton(gg);
        //绘制各个牌堆的牌
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
        //rankList.paintRankList(gg);
    }

    /**
     * 初始化，不重新洗牌
     */
    public void InitWithoutWashCards() {
        timeCountS=0;
        timeCountM=0;
        startFlag=false;
        int count =51;
        for(int i=3;i<7;i++)
            while (piles[i-3].getNumber()>0)
            piles[i-3].pop();
        for(int i=0;i<7;i++){
            while (deskPiles[i].getNumber()>0)
            deskPiles[i].pop();
        }
        drawPile.clear();
        for(int i=0;i<7;i++){
            for(int j=i;j>=0;j--){
                deskPiles[i].pushCard(cards[count--]);
                if(j!=0)deskPiles[i].peek().setSide(BACKSIDE);
            }
        }

        while (count>=0)drawPile.pushCard(cards[count--]);
    }

    /**
     * 初始化，洗牌
     */
    public void Init() {
        timeCountS=0;
        timeCountM=0;
        startFlag=false;
        restartButton=new Button(915,10,120,40);
        cards=new Card[52];
        int count =-1;
        for(int i=0;i<4;i++)
            for(int j=1;j<=13;j++){
                cards[++count]=new Card(type[i],j,UPSIDE);
            }
        Random generator = new Random();
        for (int k=0;k<10;k++)//洗牌10次
        for (int i = 0; i < 52; i++) {
            int j = Math.abs(generator.nextInt() % 52);
            Card temp = cards[i];
            cards[i]=cards[j];
            cards[j]=temp;
        }
        drawPile=new DrawPile(320,100,CARDWIDTH,CARDHEIGHT);
        for(int i=3;i<7;i++)
        piles[i-3]= new CardPile(200+120*i,100,CARDWIDTH,CARDHEIGHT,0);
        for(int i=0;i<7;i++){
            deskPiles[i]=new DeskPile(200+120*i,350,CARDWIDTH,CARDHEIGHT,0);
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

    /**
     * 从原来的牌堆中pop出移动的牌
     */
    private void originPilePOP(){
        switch (selectedPile){
            case DESKPILE : originDeskPile.pop();break;
            case CARDPILE : originPile.pop();break;
            case DRAWPILE : drawPile.pop();break;
        }
    }

    /**
     * 检测是否获胜
     * @return
     */
    private boolean isWin(){
        int sum=0;
        for(CardPile i:piles){
            sum+=i.getNumber();
        }
        if (sum==52)return true;
        return false;
    }

    /**
     * 显示获胜的提示框
     */
    private void showCongratulations(){
        //连接服务器 获取排行榜
//        String res =HttpClient4.doGet("http://119.29.52.224:8083/getRankList");
//        JSONArray jsonArray =JSONArray.fromObject(res);
        //System.out.println(jsonArray);
//        String mes ="您所用时间为："+timeCountM;
//        if (timeCountS/1000<10)
//            mes+=":0"+(timeCountS/1000)+"\n";
//        else
//            mes+=":"+(timeCountS/1000)+"\n";
//        mes+="排行榜：\n";
//        for(int i=0;i<jsonArray.size();i++){
//            JSONObject t =JSONObject.fromObject(jsonArray.get(i));
//            String time = t.get("time").toString();
//            int timeM= Integer.parseInt(time)/60;
//            int timeS= Integer.parseInt(time)%60;
//            mes+=(i+1)+". "+t.get("name")+"             ";
//            if (timeS<10)
//                mes+=timeM+":0"+timeS+"\n";
//            else
//                mes+=timeM+":"+timeS+"\n";
//        }
//        mes+="请输入您的名字：";
//        String name = JOptionPane.showInputDialog(this,mes ,"Congratulations!",JOptionPane.PLAIN_MESSAGE);
//        //如果名字不为空，上传记录到服务器
//        if(name!=null&&name.length()>0){
//            Map<String, Object> paramMap =new HashMap<String,Object>();
//            paramMap.put("name",name);
//            int timeSend = timeCountM*60+(timeCountS/1000);
//            paramMap.put("time",timeSend+"");
//            HttpClient4.doPost("http://119.29.52.224:8083/updateRankList",paramMap);
//        }
    }

    /**
     * 绘制线程
     */
    private class paintThread extends Thread{
        @Override
        public void run() {
          //  System.out.println(piles.toString());
            while(true){
                repaint();
                try {
                    Thread.sleep(16);//每秒约60帧
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
