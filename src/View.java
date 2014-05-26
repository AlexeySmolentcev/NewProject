import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public class View extends JFrame implements Observer {
    private Strategy controller;

    private JLabel themeLabel2;
    private JLabel levelLabel2;
    private JLabel scoreLabel2;
    private JLabel pictureLabel;
    private JPanel brickPanel;
    private JPanel answerPanel2;
    private JTextField answerTF;
    private final Font myFont = new Font("Arial", Font.PLAIN, 15);

    public View(final Strategy controller) {
        this.controller = controller;
        setTitle("Угадай картинку");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(550, 600);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);


        final JPanel mainPanel = new JPanel(new FlowLayout());


        final JPanel row1 = new JPanel(new FlowLayout());

        final JPanel picturePanel = new JPanel();
        picturePanel.setPreferredSize(new Dimension(300, 400));
        picturePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        row1.add(picturePanel);

        final JPanel labelPanel = new JPanel(new GridLayout(3, 1));
        labelPanel.setPreferredSize(new Dimension(200, 400));
        final JPanel themePanel = new JPanel(new GridLayout(2, 1));
        final JLabel themeLabel1 = new JLabel("Выбранная тематика:");
        themeLabel2 = new JLabel();
        themeLabel1.setFont(myFont);
        themeLabel2.setFont(myFont);
        final JPanel themePanel1 = new JPanel(new FlowLayout());
        themePanel1.add(themeLabel1);
        final JPanel themePanel2 = new JPanel(new FlowLayout());
        themePanel2.add(themeLabel2);
        themePanel.add(themePanel1);
        themePanel.add(themePanel2);
        themePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 10, 40, 0),
                BorderFactory.createLoweredBevelBorder()));
        labelPanel.add(themePanel);
        final JPanel levelPanel = new JPanel(new GridLayout(2, 1));
        final JLabel levelLabel1 = new JLabel("Выбранная сложность:");
        levelLabel2 = new JLabel();
        levelLabel1.setFont(myFont);
        levelLabel2.setFont(myFont);
        final JPanel levelPanel1 = new JPanel(new FlowLayout());
        final JPanel levelPanel2 = new JPanel(new FlowLayout());
        levelPanel1.add(levelLabel1);
        levelPanel2.add(levelLabel2);
        levelPanel.add(levelPanel1);
        levelPanel.add(levelPanel2);
        levelPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0),
                BorderFactory.createLoweredBevelBorder()));
        labelPanel.add(levelPanel);
        final JPanel scorePanel = new JPanel(new GridLayout(2, 1));
        final JLabel scoreLabel1 = new JLabel("Текущий счет:");
        scoreLabel2 = new JLabel();
        scoreLabel1.setFont(myFont);
        scoreLabel2.setFont(myFont);
        final JPanel scorePanel1 = new JPanel(new FlowLayout());
        final JPanel scorePanel2 = new JPanel(new FlowLayout());
        scorePanel1.add(scoreLabel1);
        scorePanel2.add(scoreLabel2);
        scorePanel.add(scorePanel1);
        scorePanel.add(scorePanel2);
        scorePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(40, 10, 0, 0),
                BorderFactory.createLoweredBevelBorder()));
        labelPanel.add(scorePanel);
        row1.add(labelPanel);


        final JPanel row2 = new JPanel(new GridLayout(2, 1));
        row2.setPreferredSize(new Dimension(500, 180));

        answerTF = new JTextField();
        answerTF.setPreferredSize(new Dimension(300, 25));
        final JPanel answerPanel1 = new JPanel(new FlowLayout());
        answerPanel1.add(answerTF);
        answerPanel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        final JButton answerButton = new JButton("Ответить");
        answerButton.setFont(myFont);
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.buttonClicked(answerTF.getText());
                answerTF.setText("");
            }
        });
        answerPanel2 = new JPanel(new FlowLayout());
        answerPanel2.add(answerButton);
        row2.add(answerPanel1);
        row2.add(answerPanel2);

        mainPanel.add(row1);
        mainPanel.add(row2);

        picturePanel.setLayout(new OverlayLayout(picturePanel));

        pictureLabel = new JLabel();
        pictureLabel.setPreferredSize(new Dimension(300, 400));
        pictureLabel.setBorder(BorderFactory.createLoweredBevelBorder());

        brickPanel = new JPanel();
        brickPanel.setBackground(new Color(0, 0, 0, 0));

        brickPanel.setAlignmentX(0.0f);
        brickPanel.setAlignmentY(0.0f);
        pictureLabel.setAlignmentX(0.0f);
        pictureLabel.setAlignmentY(0.0f);

        picturePanel.add(brickPanel);
        picturePanel.add(pictureLabel);

        this.setContentPane(mainPanel);
    }

    @Override
    public void update(final boolean[][] bricks, final String score, final String difficulty, final String theme,
                       final ImageIcon picture, final ImageIcon brick, final boolean pictureChanged,
                       final boolean resultFromButton, final boolean endOfTheGame) {
        answerTF.setEnabled(true);
        themeLabel2.setText(theme);
        levelLabel2.setText(difficulty);
        scoreLabel2.setText(score);
        answerPanel2.removeAll();
        final JButton answerButton = new JButton("Ответить");
        answerButton.setFont(myFont);
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.buttonClicked(answerTF.getText());
                answerTF.setText("");
            }
        });
        answerPanel2.add(answerButton);

        if (picture != null) {
            pictureLabel.setIcon(picture);
        } else {
            if (!score.equals("Счет не ведется")) {
                JOptionPane.showMessageDialog(new Frame(),
                        "Это была последняя картинка. Игра окончена. \n" +
                                "Ваш результат: " + score,
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(new Frame(),
                        "Это была последняя картинка. Игра окончена.",
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
            JFrame end = new EndOfGame(score);
            end.setVisible(true);
        }

        brickPanel.removeAll();
        brickPanel.setLayout(new GridLayout(bricks.length, bricks[0].length));
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                final JLabel label = new JLabel();
                label.setIcon(brick);
                brickPanel.add(label);
                label.setVisible(bricks[i][j]);
                final int xCoordinate = i;
                final int yCoordinate = j;
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        controller.labelClicked(xCoordinate, yCoordinate);
                    }

                    @Override
                    public void mousePressed(final MouseEvent e) {
                        //do nothing
                    }

                    @Override
                    public void mouseReleased(final MouseEvent e) {
                        //do nothing
                    }

                    @Override
                    public void mouseEntered(final MouseEvent e) {
                        //do nothing
                    }

                    @Override
                    public void mouseExited(final MouseEvent e) {
                        //do nothing
                    }
                });
            }
        }

        revalidate();
        repaint();

        if (resultFromButton && picture != null) {
            if (pictureChanged) {
                answerPanel2.removeAll();
                final JButton nextButton = new JButton("Дальше");
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        controller.nextPicture();
                    }
                });
                nextButton.setFont(myFont);
                answerPanel2.add(nextButton);
                answerTF.setEnabled(false);
                revalidate();
                repaint();

                JOptionPane.showMessageDialog(new Frame(),
                        "Вы угадали!",
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(new Frame(),
                        "Пока неверно. Попробуйте еще раз",
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (endOfTheGame) {
            JOptionPane.showMessageDialog(new Frame(),
                    "Неверно. У Вас кончились очки. Игра окончена",
                    "Результат проверки ответа",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            JFrame end = new EndOfGame(score);
            end.setVisible(true);
        }
    }
}
