package ru.ifmo.enf.smoletcev.finalWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 26.05.14
 */
public class EndOfGame extends JFrame {
    //это и есть то следующее окно, на которое я перехожу из представления

    //опять же задаю переменные, нужные мне дальше
    private JFrame frame;
    private UserPreferences prefs = new UserPreferences();
    private Font myFont = new Font("Arial", Font.PLAIN, 15);


    public EndOfGame(final String score) {  //конструктор принимает счет
        //если честно, мне уже надоело эту шапку расписывать, поэтому не буду
        frame = this;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mainIcon.jpg")));
        setTitle("Угадай картинку");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 450);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);


        //вызыываю метод, который сохранит текущий результат
        putScore(score);
        //получаю все результаты с учетом сохраненного
        List<String> scores = getScores();

        //это панель с результатами
        JPanel resultLabelPanel = new JPanel(new GridLayout(11, 1));
        final JLabel resultLabel = new JLabel("Лучшие результаты:");
        resultLabel.setFont(myFont);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabelPanel.add(resultLabel);

        //тут я эти результаты отображаю
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

        //кнопка, при нажатии на которую происходит переход на следующий этап
        final JPanel resultsPanel = new JPanel(new FlowLayout());
        resultLabelPanel.setPreferredSize(new Dimension(330, 350));
        resultsPanel.add(resultLabelPanel);
        final JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(100, 30));
        //при нажатии заменяю панель на другую
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

        //меняю панель окна на тоьлко что созданную
        setContentPane(resultsPanel);
    }

    //метод замены панели по зажатию на кнопку
    private void setPanel() {
        //задаю новую панель
        final JPanel newPanel = new JPanel(new FlowLayout());
        final JLabel label = new JLabel("Хотите сыграть еще раз?");
        label.setFont(myFont);
        label.setPreferredSize(new Dimension(200, 200));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        newPanel.add(label);
        //создаю кнопку начала новой игры
        final JButton yes = new JButton("Да");
        yes.setFont(myFont);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                //это окно закрывается
                frame.dispose();
                //начинается новая игра с задания параметров новой игры
                JFrame newGame = new StartingFrame();
                newGame.setVisible(true);
            }
        });
        yes.setPreferredSize(new Dimension(200, 50));
        yes.setFont(myFont);
        newPanel.add(yes);
        //кнопка выхода из игры
        final JButton no = new JButton("Нет");
        no.setFont(myFont);
        //просто закрываем приложение
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        no.setPreferredSize(new Dimension(200, 50));
        no.setFont(myFont);
        newPanel.add(no);

        //меняем панель окна
        frame.setContentPane(newPanel);
    }

    //если счет велся, то говорю, что его надо сохранить
    private void putScore(final String score) {
        if (!score.equals("Счет не ведется")) {
            prefs.putScore(Integer.parseInt(score));
        }
    }

    //получаю список лучших результатов в виде int'ов и преобразую в строки
    private List<String> getScores() {
        List<Integer> scoresInInteger = prefs.getScores();
        List<String> scores = new ArrayList<String>();
        for (Integer aScoreInInteger : scoresInInteger) {
            scores.add(aScoreInInteger.toString());
        }
        return scores;
    }
}
