import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 26.05.14
 */
public class EndOfGame extends JFrame {
    private JFrame frame;

    public EndOfGame() {
        frame = this;
        setTitle("Угадай картинку");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 450);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);
        final Font myFont = new Font("Arial", Font.PLAIN, 15);

        final JPanel resultsPanel = new JPanel(new FlowLayout());
        final JLabel resultLabel = new JLabel("Лучшие результаты:");
        resultLabel.setFont(myFont);
        resultLabel.setPreferredSize(new Dimension(350, 350));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setVerticalAlignment(SwingConstants.NORTH);
        resultsPanel.add(resultLabel);
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
}
