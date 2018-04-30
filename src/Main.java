import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame{
    private int height,width;
    private GameBoard game;
   // private Card test ;
    Main(int height, int width) {
        this.height = height;
        this.width = width;
        setTitle("纸牌游戏");
        //得到屏幕大小
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screensize.width;
        int screenHeight = screensize.height;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
        setLayout(null);
        setVisible(true);
        game=new GameBoard(width,height,this);
        game.Init();

        getContentPane().add(game);
        //game.setVisible(true);
//        getContentPane().add(heapSort);
//        heapSort.setVisible(false);

//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                System.out.println("鼠标位置: x "+e.getX() + " , y " + e.getY());
//            }
//        });

    }

    public static void main(String[] args) {

        new Main(900, 1600);
    }

}
