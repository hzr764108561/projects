package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;

public class GameFrame extends JFrame implements GameStateListener {
    private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};

    private final JLabel statusLabel = new JLabel();

    public GameFrame(GameController controller) {
        controller.registerListener(this);

        setTitle("2020 CS102A Project Demo");
        setSize(1300, 825);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        statusLabel.setLocation(780, 30);
        statusLabel.setFont(statusLabel.getFont().deriveFont(18.0f));
        statusLabel.setSize(400, 20);
        add(statusLabel);

        DiceSelectorComponent diceSelectorComponent = new DiceSelectorComponent();
        diceSelectorComponent.setLocation(396, 758);
        add(diceSelectorComponent);
        JButton button = new JButton("roll");
        JButton button1 = new JButton("+");
        JButton button2 = new JButton("-");
        JButton button3 = new JButton("*");
        JButton button4 = new JButton("÷");
        button.addActionListener((e) -> {
            int dice = controller.rollDice();
            int dice1 = controller.rollDice1();
            if (diceSelectorComponent.isRandomDice()) {
                if (dice != -1||dice1!=-1) {
                    button1.setLocation(780, 90);
                    button1.setFont(button.getFont().deriveFont(18.0f));
                    button1.setSize(90, 30);
                    add(button1);
                    button2.setLocation(780, 130);
                    button2.setFont(button.getFont().deriveFont(18.0f));
                    button2.setSize(90, 30);
                    add(button2);
                    button3.setLocation(780, 170);
                    button3.setFont(button.getFont().deriveFont(18.0f));
                    button3.setSize(90, 30);
                    add(button3);
                    button4.setLocation(780, 210);
                    button4.setFont(button.getFont().deriveFont(18.0f));
                    button4.setSize(90, 30);
                    add(button4);
                    statusLabel.setText(String.format("[%s] Rolled a \n %c (%d) and a %c (%d)",
                            PLAYER_NAMES[controller.getCurrentPlayer()], '\u267F' + dice, dice, '\u267F' + dice1, dice1));
                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
            }
        });
        button.setLocation(780, 0);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        add(button);
    }


    @Override
    public void onPlayerStartRound(int player) {
        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
