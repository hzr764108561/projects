package xyz.chengzi.aeroplanechess;/*java中设置图片自适应Jlable的大小*/
import javax.swing.*;
import java.awt.Image;

public class testImage extends JFrame {


    public testImage(String path,JLabel lable1) {
        ImageIcon image;
        image = new ImageIcon(path);
        // image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT));
        Image img = image.getImage();
        img = img.getScaledInstance(444,472, Image.SCALE_DEFAULT);
        image.setImage(img);
        lable1.setIcon(image);
    }

//    public static void start(String path,JLabel label1) {
//        new testImage(path,label1);
//    }
}