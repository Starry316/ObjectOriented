package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static util.Constant.*;

/**
 * Created by Starry on 2018/5/11.
 * 用于加载图片
 */
public class ImagePool {
    public static Image[][] cardImages=new Image[4][13];
    public static Image backimage;
    public static Image pileImage;
    static {
        String pictureUp = "picture/";
        for(int i=0;i<4;i++)
            for (int j=0;j<13;j++) {
                pictureUp += i + "-" + j + ".png";
                try {
                    cardImages[i][j] = ImageIO.read(new File(pictureUp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pictureUp = "picture/";
            }
        try {
            backimage= ImageIO.read(new File("picture/back.png"));
            pileImage=ImageIO.read(new File("picture/pile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
