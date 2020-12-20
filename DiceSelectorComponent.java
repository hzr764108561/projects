package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DiceSelectorComponent extends JComponent implements ItemListener {
    private JRadioButton manualDiceRadio;
    private JRadioButton randomDiceRadio;
    private JComboBox<Integer> diceComboBox;
    private JComboBox<Integer> diceComboBox2;
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private boolean randomDice = true;

    public DiceSelectorComponent() {
        setLayout(null); // Use absolute layout
        setSize(1000, 25);
        statusLabel1.setLocation(300, 0);
        statusLabel1.setFont(statusLabel1.getFont().deriveFont(18.0f));
        statusLabel1.setSize(400, 25);
        add(statusLabel1);
        statusLabel2.setLocation(500, 0);
        statusLabel2.setFont(statusLabel2.getFont().deriveFont(18.0f));
        statusLabel2.setSize(400, 25);
        add(statusLabel2);
        diceComboBox = new JComboBox<>();
        for (int i = 1; i <= 6; i++) {
            diceComboBox.addItem(i);
        }
        diceComboBox.setLocation(370, 0);
        diceComboBox.setSize(80, 25);
        diceComboBox.setVisible(false);
        add(diceComboBox);
        diceComboBox2 = new JComboBox<>();
        for (int i = 1; i <= 6; i++) {
            diceComboBox2.addItem(i);
        }
        diceComboBox2.setLocation(570, 0);
        diceComboBox2.setSize(80, 25);
        diceComboBox2.setVisible(false);
        add(diceComboBox2);

        manualDiceRadio = new JRadioButton("manual");
        randomDiceRadio = new JRadioButton("auto", true);

        manualDiceRadio.setLocation(200, 0);
        manualDiceRadio.setSize(100, 25);
        manualDiceRadio.setFont(manualDiceRadio.getFont().deriveFont(18.0f));
        randomDiceRadio.setLocation(90, 0);
        randomDiceRadio.setSize(70, 25);
        randomDiceRadio.setFont(randomDiceRadio.getFont().deriveFont(18.0f));
        manualDiceRadio.addItemListener(this);
        randomDiceRadio.addItemListener(this);
        add(manualDiceRadio);
        add(randomDiceRadio);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(manualDiceRadio);
        btnGroup.add(randomDiceRadio);
    }

    public boolean isRandomDice() {
        return randomDice;
    }

    public Object getSelectedDice() {
        return diceComboBox.getSelectedItem();
    }
    public Object getSelectedDice2() {
        return diceComboBox2.getSelectedItem();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (randomDiceRadio.isSelected()) {
            randomDice = true;
            diceComboBox.setVisible(false);
            diceComboBox2.setVisible(false);
            statusLabel1.setText("");
            statusLabel2.setText("");

        } else {
            randomDice = false;
            diceComboBox.setVisible(true);
            diceComboBox2.setVisible(true);
            statusLabel1.setText("dice1 is");
            statusLabel2.setText("dice2 is");
        }
    }
}
