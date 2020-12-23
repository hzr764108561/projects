package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.LoadGameprocess;
import xyz.chengzi.aeroplanechess.Play0;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class View extends JFrame {

    private String selected = "C://Users//Ld//Desktop//project1//src//xyz//chengzi//aeroplanechess//Gameprocess//stacked planes.txt";

    public void view()
    {
        ViewAction exwpAction=new ViewAction(this);//提前定义好动作对象//加载图片
        ImageIcon icon = new ImageIcon("fxq.jpg");//Image im=new Image(icon);//将图片放入label中
        JLabel label = new JLabel(icon);//设置label的大小
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        JFrame frame = new JFrame();//获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));//获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) frame.getContentPane();
        j.setOpaque(false);
        JPanel panel = new JPanel();
        JButton sg = new JButton("start game");
        JButton lg = new JButton("load game");
        GameprocessComponent gameprocessComponent = new GameprocessComponent();
        gameprocessComponent.setVisible(true);
        frame.add(gameprocessComponent);
        gameprocessComponent.setLocation(100,810);
        gameprocessComponent.setSize(1000,50);
        selected = gameprocessComponent.getSelectedprocess() + "";
        sg.addActionListener(exwpAction);
        lg.addActionListener(exwpAction);
        frame.add(sg);
        frame.add(lg);
        sg.setLocation(1100,630);
        sg.setSize(200,100);
        lg.setLocation(1100,740);
        lg.setSize(200,100);
        Font f = new Font("隶书",Font.PLAIN,30);
        sg.setFont(f);
        lg.setFont(f);
        panel.setOpaque(false);
        frame.add(panel);
        frame.add(panel);
        frame.setSize(icon.getIconWidth(), icon.getIconHeight());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        sg.addActionListener((e) -> {
            frame.setVisible(false);//关闭穿进来的那个类的视图
        });
        lg.addActionListener((e) ->{
            frame.setVisible(false);
                }

        );
    }
    public void view2(int n)
    {ViewAction exwpAction=new ViewAction(this);
        SwingUtilities.invokeLater(() -> {
            ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 13, 6);
            ChessBoard chessBoard = new ChessBoard(13, 6);
            GameController controller = new GameController(chessBoardComponent, chessBoard);
            GameFrame mainFrame = new GameFrame(controller);
            mainFrame.add(chessBoardComponent);
            if (n==1){
            mainFrame.setVisible(true);}
            if (n!=1){
                mainFrame.setVisible(false);
            }
            controller.initializeGame();
        });
    }
    public void view3(int n)
    {ViewAction exwpAction = new ViewAction(this);
        SwingUtilities.invokeLater(() -> {
            ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 13, 6);
            ChessBoard chessBoard = new ChessBoard(13, 6);
            GameController controller = new GameController(chessBoardComponent, chessBoard);
            GameFrame mainFrame = new GameFrame(controller);
            mainFrame.add(chessBoardComponent);
            if (n==1){
                mainFrame.setVisible(true);}
            if (n!=1){
                mainFrame.setVisible(false);
            }
            try {
                controller.LoadGame(gameloding(getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
    public ArrayList<int[]> gameloding(String info)throws FileNotFoundException {
        ArrayList<int[]> process = new ArrayList<>(4);
        try (Scanner gameprocess = new Scanner( new File(info));
        ){
            for (int i = 0; i < 4; i++){
                String x = gameprocess.nextLine();
                String[] processed = x.split(" ");
                int[] t = new int[processed.length];
                for (int j = 0; j < processed.length; j++){
                    t[j] = Integer.parseInt(processed[j]);
                }
                process.add(t);
            }
        }

        return process;
    }
    public String getPath(){
        return selected;
    }

}