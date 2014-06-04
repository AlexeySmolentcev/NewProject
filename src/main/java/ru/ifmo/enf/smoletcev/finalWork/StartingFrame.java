package ru.ifmo.enf.smoletcev.finalWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public class StartingFrame extends JFrame {
    public StartingFrame() {
        final Font myFont = new Font("Arial", Font.PLAIN, 15);
        final JFrame myFrame = this;

        setTitle("Угадай картинку");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 450);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);

        final JPanel mainPanel = new JPanel(new GridLayout(5, 1));

        final JPanel row1 = new JPanel();
        final JLabel header = new JLabel("Выбор параметров игры");
        header.setFont(new Font("Arial", Font.BOLD, 20));
        row1.add(header);

        final JPanel row2 = new JPanel(new FlowLayout());
        final JLabel pointsLabel = new JLabel("Подсчет очков");
        pointsLabel.setFont(myFont);
        row2.add(pointsLabel);
        final ButtonGroup pointsRBGroup = new ButtonGroup();
        final JRadioButton pointsRB1 = new JRadioButton("есть");
        final JRadioButton pointsRB2 = new JRadioButton("нет");
        pointsRB1.setFont(myFont);
        pointsRB2.setFont(myFont);
        pointsRBGroup.add(pointsRB1);
        pointsRBGroup.add(pointsRB2);
        final JPanel pointsRBPanel = new JPanel(new GridLayout(2, 1));
        pointsRBPanel.add(pointsRB1);
        pointsRBPanel.add(pointsRB2);
        row2.add(pointsRBPanel);

        final JPanel row3 = new JPanel(new FlowLayout());
        final JLabel numberLabel = new JLabel("Число фрагментов картинки");
        numberLabel.setFont(myFont);
        row3.add(numberLabel);
        final ButtonGroup numberRBGroup = new ButtonGroup();
        final JRadioButton numberRB1 = new JRadioButton("8");
        final JRadioButton numberRB2 = new JRadioButton("15");
        final JRadioButton numberRB3 = new JRadioButton("48");
        numberRB1.setFont(myFont);
        numberRB2.setFont(myFont);
        numberRB3.setFont(myFont);
        numberRBGroup.add(numberRB1);
        numberRBGroup.add(numberRB2);
        numberRBGroup.add(numberRB3);
        final JPanel numberRBPanel = new JPanel(new GridLayout(3, 1));
        numberRBPanel.add(numberRB1);
        numberRBPanel.add(numberRB2);
        numberRBPanel.add(numberRB3);
        row3.add(numberRBPanel);

        final JPanel row4 = new JPanel(new FlowLayout());
        final JLabel themeLabel = new JLabel("Тематика картинок");
        themeLabel.setFont(myFont);
        row4.add(themeLabel);
        final JComboBox<String> themeCB = new JComboBox<String>();
        themeCB.addItem("НЕ ВЫБРАНО");
        themeCB.addItem("Животные");
        themeCB.addItem("Марки автомобилей");
        themeCB.addItem("Рок-группы");
        themeCB.setFont(myFont);
        row4.add(themeCB);

        final JPanel row5 = new JPanel();
        final JButton confirmButton = new JButton("Подтвердить параметры");
        confirmButton.setSize(100, 80);
        confirmButton.setFont(myFont);
        row5.add(confirmButton);

        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if ((pointsRB1.isSelected() || pointsRB2.isSelected()) &&
                        (numberRB1.isSelected() || numberRB2.isSelected() || numberRB3.isSelected()) &&
                        (themeCB.getSelectedIndex() != 0)) {
                    boolean points = pointsRB1.isSelected();

                    int pictureFragments = 0;
                    if (numberRB1.isSelected()) {
                        pictureFragments = Integer.parseInt(numberRB1.getText());
                    }
                    if (numberRB2.isSelected()) {
                        pictureFragments = Integer.parseInt(numberRB2.getText());
                    }
                    if (numberRB3.isSelected()) {
                        pictureFragments = Integer.parseInt(numberRB3.getText());
                    }

                    int themeCode = themeCB.getSelectedIndex();

                    myFrame.setVisible(false);
                    new Model(points, pictureFragments, themeCode);
                } else {
                    JOptionPane.showMessageDialog(new Frame(),
                            "Вы выбрали не все параметры.",
                            "Ошибка!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setContentPane(mainPanel);
    }
}

