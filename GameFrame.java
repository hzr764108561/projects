package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.Play0;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFrame extends JFrame implements GameStateListener {
    private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};
    private ChessBoard chessBoard;
    private final JLabel statusLabel = new JLabel();
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private JLabel statusLabel3 = new JLabel();
    private JLabel statusLabel4 = new JLabel();
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private Button button0;
    private Button button7;
    private Button button8;
    private Button button9;

    public GameFrame(GameController controller) {
        controller.registerListener(this);
        chessBoard=controller.getModel();
        setTitle("2020 CS102A Project Demo");
        setSize(1300, 880);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        statusLabel.setLocation(780, 60);
        statusLabel.setFont(statusLabel.getFont().deriveFont(18.0f));
        statusLabel.setSize(400, 20);
        add(statusLabel);
        statusLabel1.setLocation(780, 330);
        statusLabel1.setFont(statusLabel1.getFont().deriveFont(18.0f));
        statusLabel1.setSize(400, 20);
        add(statusLabel1);
        statusLabel2.setLocation(780, 360);
        statusLabel2.setFont(statusLabel2.getFont().deriveFont(18.0f));
        statusLabel2.setSize(400, 20);
        add(statusLabel2);
        statusLabel4.setLocation(780, 400);
        statusLabel4.setFont(statusLabel1.getFont().deriveFont(18.0f));
        statusLabel4.setSize(400, 20);
        add(statusLabel4);
        DiceSelectorComponent diceSelectorComponent = new DiceSelectorComponent();
        diceSelectorComponent.setLocation(396, 788);
        add(diceSelectorComponent);
        button0 = new Button("restart");
        button0.setLocation(0,0);
        button0.setSize(90, 30);
        button0.setFocusable(false);
        add(button0);
        button0.setVisible(true);
        button7 = new Button("home");
        button7.setLocation(100,0);
        button7.setSize(90, 30);
        button7.setFocusable(false);
        add(button7);
        button7.setVisible(true);
        button8 = new Button("lucky song 1");
        button8.setLocation(193,0);
        button8.setSize(90, 30);
        button8.setFocusable(false);
        add(button8);
        button8.setVisible(true);
        button9 = new Button("lucky song 2");
        button9.setLocation(286,0);
        button9.setSize(90, 30);
        button9.setFocusable(false);
        add(button9);
        button9.setVisible(true);
        JButton button = new JButton("roll");
        button.setLocation(780, 30);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        button.setFocusable(false);
        add(button);
        button1 = new JButton("+");
        button2 = new JButton("-");
        button3 = new JButton("*");
        button4 = new JButton("÷");
        button1.setLocation(780, 150);
        button1.setFont(button.getFont().deriveFont(18.0f));
        button1.setSize(90, 30);
        add(button1);
        button2.setLocation(780, 190);
        button2.setFont(button.getFont().deriveFont(18.0f));
        button2.setSize(90, 30);
        add(button2);
        button3.setLocation(780, 230);
        button3.setFont(button.getFont().deriveFont(18.0f));
        button3.setSize(90, 30);
        add(button3);
        button4.setLocation(780, 270);
        button4.setFont(button.getFont().deriveFont(18.0f));
        button4.setSize(90, 30);
        add(button4);
        button5 = new JButton("fight");
        button5.setLocation(780, 430);
        button5.setFont(button.getFont().deriveFont(18.0f));
        button5.setSize(90, 30);
        add(button5);
        button6 = new JButton("fly");
        button6.setLocation(780, 100);
        button6.setFont(button.getFont().deriveFont(18.0f));
        button6.setSize(90, 30);
        add(button6);
        AtomicInteger add = new AtomicInteger();
        AtomicInteger subtract = new AtomicInteger();
        AtomicInteger multiply= new AtomicInteger();
        AtomicInteger divide= new AtomicInteger();
        AtomicInteger yushu = new AtomicInteger();
        AtomicInteger ys2 = new AtomicInteger();
        button0.addActionListener((e) -> {
            controller.setN(1);
            controller.initializeGame();
        });
        button7.addActionListener((e) -> {
            View v = new View();
            new View().view();//new一个View类并调用里面的view函数
            setVisible(false);//关闭穿进来的那个类的视图
        });
        button8.addActionListener((e) -> {//关闭穿进来的那个类的视图
            Play0 play1 = new Play0("fxq.mp3"); //开启
            play1.stop();
            new Play0("zg1.mp3").start();//关闭穿进来的那个类的视图
        });
        button9.addActionListener((e) -> {//关闭穿进来的那个类的视图
            Play0 play1 = new Play0("zg1.mp3");  //开启
            play1.stop();
            new Play0("zg2.mp3").start();
        });
        button.addActionListener((e) -> {
            int dice = controller.rollDice();
            int dice1 = controller.rollDice1();
            if (diceSelectorComponent.isRandomDice()) {
                if (dice != -1 || dice1 != -1) {
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                    button4.setVisible(true);
                    if (dice == 6 || dice1 == 6) {
                        button6.setVisible(true);
                    }
                    add.set(dice + dice1);
                    subtract.set(Math.abs(dice - dice1));
                    multiply.set(dice * dice1);
                    yushu.set(dice % dice1);
                    ys2.set(dice1 % dice);
                    if (dice / dice1 == (double) dice / dice1) divide.set(dice / dice1);
                    if (dice1 / dice == (double) dice1 / dice) divide.set(dice1 / dice);
                    statusLabel.setText(String.format("[%s] Rolled a %d and a %d", PLAYER_NAMES[controller.getCurrentPlayer()], dice, dice1));
                    if (dice != 6 && dice1 != 6) {
                        if (chessBoard.home(controller.getCurrentPlayer()) == null) {
                            JOptionPane.showMessageDialog(this, "You have no chess to move");
                            controller.nextPlayer();
                            statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[controller.getCurrentPlayer()]));
                        }
                        if (chessBoard.home(controller.getCurrentPlayer()) != null){
                        button1.addActionListener((a) -> {
                            statusLabel1.setText(String.format("you choose %d steps,", add.get()));
                            statusLabel2.setText(String.format("please choose a chess to move"));
                            controller.setSteps(add.get());
                        });
                        button2.addActionListener((b) -> {
                            if (subtract.get() == 0) {
                                statusLabel1.setText(String.format("it's 0."));
                                statusLabel2.setText(String.format("please choose another button"));
                            }
                            if (subtract.get() != 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", subtract.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(subtract.get());
                            }
                        });
                        button3.addActionListener((c) -> {
                            if (multiply.get() <= 12) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", multiply.get()));
                                controller.setSteps(multiply.get());
                            } else {
                                statusLabel1.setText(String.format("you choose to move 12 steps,"));
                                controller.setSteps(12);
                            }
                            statusLabel2.setText(String.format("please choose a chess to move"));
                        });
                        button4.addActionListener((d) -> {
                            if (yushu.get() == 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(divide.get());
                            }
                            if (ys2.get() == 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(divide.get());
                            }
                            if (yushu.get() != 0 && ys2.get() != 0) {
                                statusLabel1.setText(String.format("you can't move like this"));
                                statusLabel2.setText(String.format("please choose another button"));
                            }

                        });
                    }
                    }
                    else {
                        button1.addActionListener((a) -> {
                            statusLabel1.setText(String.format("you choose %d steps,", add.get()));
                            statusLabel2.setText(String.format("please choose a chess to move"));
                            controller.setSteps(add.get());
                        });
                        button2.addActionListener((b) -> {
                            if (subtract.get() == 0) {
                                statusLabel1.setText(String.format("it's 0."));
                                statusLabel2.setText(String.format("please choose another button"));
                            }
                            if (subtract.get() != 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", subtract.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(subtract.get());
                            }
                        });
                        button3.addActionListener((c) -> {
                            if (multiply.get() <= 12) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", multiply.get()));
                                controller.setSteps(multiply.get());
                            } else {
                                statusLabel1.setText(String.format("you choose to move 12 steps,"));
                                controller.setSteps(12);
                            }
                            statusLabel2.setText(String.format("please choose a chess to move"));
                        });
                        button4.addActionListener((d) -> {
                            if (yushu.get() == 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(divide.get());
                            }
                            if (ys2.get() == 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(divide.get());
                            }
                            if (yushu.get() != 0 && ys2.get() != 0) {
                                statusLabel1.setText(String.format("you can't move like this"));
                                statusLabel2.setText(String.format("please choose another button"));
                            }

                        });
                    }
                    button6.addActionListener((d) -> {
                        controller.setSteps(13);
                    });

                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice() + "and" + diceSelectorComponent.getSelectedDice2());
                int g = Integer.parseInt(diceSelectorComponent.getSelectedDice().toString());
                int h = Integer.parseInt(diceSelectorComponent.getSelectedDice2().toString());
                statusLabel.setText(String.format("[%s] Rolled a %d and a %d", PLAYER_NAMES[controller.getCurrentPlayer()],g,h));
                if (g != 6 && h != 6) {
                    if (chessBoard.home(controller.getCurrentPlayer()) == null) {
                        JOptionPane.showMessageDialog(this, "You have no chess to move");
                        controller.nextPlayer();
                        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[controller.getCurrentPlayer()]));
                    }
                    if (chessBoard.home(controller.getCurrentPlayer()) != null) {
                        button1.setVisible(true);
                        button2.setVisible(true);
                        button3.setVisible(true);
                        button4.setVisible(true);
                        controller.setRolledNumber(g);
                        controller.setRolledNumber1(h);
                        add.set(g + h);
                        subtract.set(Math.abs(g - h));
                        multiply.set(g * h);
                        yushu.set(g % h);
                        ys2.set(h % g);
                        if (g / h == (double) g / h) divide.set(g / h);
                        if (h / g == (double) h / g) divide.set(h / g);
                        if (g == 6 || h == 6) {
                            button6.setVisible(true);
                            button6.addActionListener((a)-> {
                                controller.setSteps(13);
                            });
                        }
                        button1.addActionListener((a) -> {
                            statusLabel1.setText(String.format("you choose %d steps,", add.get()));
                            statusLabel2.setText(String.format("please choose a chess to move"));
                            controller.setSteps(add.get());
                        });
                        button2.addActionListener((b) -> {
                            if (subtract.get() == 0) {
                                statusLabel1.setText(String.format("it's 0."));
                                statusLabel2.setText(String.format("please choose another button"));
                            }
                            if (subtract.get() != 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", subtract.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(subtract.get());
                            }
                        });
                        button3.addActionListener((c) -> {
                            if (multiply.get() <= 12) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", multiply.get()));
                                controller.setSteps(multiply.get());
                            } else {
                                statusLabel1.setText(String.format("you choose to move 12 steps,"));
                                controller.setSteps(12);
                            }
                            statusLabel2.setText(String.format("please choose a chess to move"));
                        });
                        button4.addActionListener((d) -> {
                            if (yushu.get() == 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(divide.get());
                            }
                            if (ys2.get() == 0) {
                                statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                                statusLabel2.setText(String.format("please choose a chess to move"));
                                controller.setSteps(divide.get());
                            }
                            if (yushu.get() != 0 && ys2.get() != 0) {
                                statusLabel1.setText(String.format("you can't move like this"));
                                statusLabel2.setText(String.format("please choose another button"));
                            }

                        });
                    }
                }

                else{button1.setVisible(true);
                button2.setVisible(true);
                button3.setVisible(true);
                button4.setVisible(true);
                controller.setRolledNumber(g);
                controller.setRolledNumber1(h);
                add.set(g + h);
                subtract.set(Math.abs(g - h));
                multiply.set(g * h);
                yushu.set(g % h);
                ys2.set(h % g);
                if (g / h == (double) g / h) divide.set(g / h);
                if (h / g == (double) h / g) divide.set(h / g);
                if (g == 6 || h == 6) {
                    button6.setVisible(true);
                    button6.addActionListener((a)-> {
                        controller.setSteps(13);
                    });
                }
                button1.addActionListener((a) -> {
                    statusLabel1.setText(String.format("you choose %d steps,", add.get()));
                    statusLabel2.setText(String.format("please choose a chess to move"));
                    controller.setSteps(add.get());
                });
                button2.addActionListener((b) -> {
                    if (subtract.get() == 0) {
                        statusLabel1.setText(String.format("it's 0."));
                        statusLabel2.setText(String.format("please choose another button"));
                    }
                    if (subtract.get() != 0) {
                        statusLabel1.setText(String.format("you choose to move %d steps,", subtract.get()));
                        statusLabel2.setText(String.format("please choose a chess to move"));
                        controller.setSteps(subtract.get());
                    }
                });
                button3.addActionListener((c) -> {
                    if (multiply.get() <= 12) {
                        statusLabel1.setText(String.format("you choose to move %d steps,", multiply.get()));
                        controller.setSteps(multiply.get());
                    } else {
                        statusLabel1.setText(String.format("you choose to move 12 steps,"));
                        controller.setSteps(12);
                    }
                    statusLabel2.setText(String.format("please choose a chess to move"));
                });
                button4.addActionListener((d) -> {
                    if (yushu.get() == 0) {
                        statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                        statusLabel2.setText(String.format("please choose a chess to move"));
                        controller.setSteps(divide.get());
                    }
                    if (ys2.get() == 0) {
                        statusLabel1.setText(String.format("you choose to move %d steps,", divide.get()));
                        statusLabel2.setText(String.format("please choose a chess to move"));
                        controller.setSteps(divide.get());
                    }
                    if (yushu.get() != 0 && ys2.get() != 0) {
                        statusLabel1.setText(String.format("you can't move like this"));
                        statusLabel2.setText(String.format("please choose another button"));
                    }

                });
            }
            }

        });
    }
    @Override
    public void fight(ChessBoardLocation src,ChessBoardLocation dest2,int m,int n){
        button5.setVisible(false);
        statusLabel3.setLocation(780, 350);
        statusLabel3.setFont(statusLabel1.getFont().deriveFont(18.0f));
        statusLabel3.setSize(400, 20);
        add(statusLabel3);
            statusLabel3.setText(String.format("[%s] Rolled a %d", PLAYER_NAMES[chessBoard.getGridAt(src).getPiece().getPlayer()], m));
            statusLabel4.setText(String.format("[%s] Rolled a %d", PLAYER_NAMES[chessBoard.getGridAt(dest2).getPiece().getPlayer()], n));

            chessBoard.setM(m);
            chessBoard.setN(n);
    }
    @Override
    public void onPlayerStartRound(int player) {
        if (player<12){
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        button6.setVisible(false);
        statusLabel1.setText("");
        statusLabel2.setText("");
        if (player>4&&player<10){
            statusLabel.setText(String.format("[%s] roll more then 10,you can roll again", PLAYER_NAMES[player-4]));
        }
        if (player<4)statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
        if (player==11) JOptionPane.showMessageDialog(this, "You have no chess to move");
        }
        if (player==12) JOptionPane.showMessageDialog(this, "You can't move this chess");
        if (player==13) JOptionPane.showMessageDialog(this, "You should choice a chess at home");
        if (player==14)JOptionPane.showMessageDialog(this, "You are victory");
        if (player==15){
            JOptionPane.showMessageDialog(this, "the first"+PLAYER_NAMES[chessBoard.getWinner(0)]+"\nthe second"+PLAYER_NAMES[chessBoard.getWinner(1)]+"\nthe third"+PLAYER_NAMES[chessBoard.getWinner(2)]+"\nthe forth"+PLAYER_NAMES[chessBoard.getWinner(3)]);
            JOptionPane.showMessageDialog(this, "The game will restart");
        }
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
