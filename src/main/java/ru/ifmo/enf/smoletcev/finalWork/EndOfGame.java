package ru.ifmo.enf.smoletcev.finalWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 26.05.14
 */
public class EndOfGame extends JFrame {
    private JFrame frame;

    public EndOfGame(final String score) {
        frame = this;
        setTitle("Угадай картинку");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 450);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);
        final Font myFont = new Font("Arial", Font.PLAIN, 15);

        putScore(score);
        List<String> scores = getScores();

        JPanel resultLabelPanel = new JPanel(new GridLayout(11, 1));
        final JLabel resultLabel = new JLabel("Лучшие результаты:");
        resultLabel.setFont(myFont);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabelPanel.add(resultLabel);

        for (int i = 0; i < scores.size(); i++) {
            JLabel currentLabel;
            if (i != 9) {
                currentLabel = new JLabel(Integer.toString(i + 1) +
                        ")                                                                     " + scores.get(i));
            } else {
                currentLabel = new JLabel(Integer.toString(i + 1) +
                        ")                                                                    " + scores.get(i));
            }
            currentLabel.setFont(myFont);
            resultLabelPanel.add(currentLabel);
        }


        final JPanel resultsPanel = new JPanel(new FlowLayout());
        resultLabelPanel.setPreferredSize(new Dimension(330, 350));
        resultsPanel.add(resultLabelPanel);
        final JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setPanel();
                frame.repaint();
                frame.revalidate();
            }
        });
        button.setFont(myFont);
        resultsPanel.add(button);

        setContentPane(resultsPanel);
    }

    private void setPanel() {
        final Font myFont = new Font("Arial", Font.PLAIN, 15);
        final JPanel newPanel = new JPanel(new FlowLayout());
        final JLabel label = new JLabel("Хотите сыграть еще раз?");
        label.setFont(myFont);
        label.setPreferredSize(new Dimension(200, 200));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        newPanel.add(label);
        final JButton yes = new JButton("Да");
        yes.setFont(myFont);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                JFrame newGame = new StartingFrame();
                newGame.setVisible(true);
            }
        });
        yes.setPreferredSize(new Dimension(200, 50));
        yes.setFont(myFont);
        newPanel.add(yes);
        final JButton no = new JButton("Нет");
        no.setFont(myFont);
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        no.setPreferredSize(new Dimension(200, 50));
        no.setFont(myFont);
        newPanel.add(no);
        frame.setContentPane(newPanel);
    }

    private void putScore(final String score) {
        if (!score.equals("Счет не ведется")) {
            List<String> scores = getScores();
            List<Integer> newScores = new ArrayList<Integer>();
            newScores.add(Integer.parseInt(score));
            for (String score1 : scores) {
                newScores.add(Integer.parseInt(score1));
            }
            Collections.sort(newScores, new Comparator<Integer>() {
                @Override
                public int compare(final Integer o1, final Integer o2) {
                    if (o1 < o2) {
                        return 1;
                    } else if (o1 > o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

            try {
                FileWriter writer = new FileWriter(".\\src\\main\\resources\\Results.txt", false);
                writer.write("");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            int counter = 0;
            for (Integer newScore : newScores) {
                if (counter < 10) {
                    try {
                        FileWriter writer = new FileWriter(".\\src\\main\\resources\\Results.txt", true);
                        writer.write(newScore.toString() + "\n");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                counter++;
            }
        }
    }

    private List<String> getScores() {
        List<String> scores = new ArrayList<String>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(".\\src\\main\\resources\\Results.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}