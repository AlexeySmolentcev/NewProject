package ru.ifmo.enf.smoletcev.finalWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public class View extends JFrame implements Observer {
    //это главное окно игры

    //тут будет лежать используемый контроллер
    private Strategy controller;

    //сюда я вынес все, что мне необходимо менять в update ну и шрифт
    private JFrame thisFrame = this;
    private JLabel themeLabel2;
    private JLabel levelLabel2;
    private JLabel scoreLabel2;
    private JLabel pictureLabel;
    private JLabel timeLabel2;
    private JPanel brickPanel;
    private JPanel answerPanel2;
    private JTextField answerTF;
    private final Font myFont = new Font("Arial", Font.PLAIN, 15);
    private int counter = 0;

    public View(final Strategy controller) {
        //конструктор View

        //принимаем контроллер
        this.controller = controller;
        //меняем иконку фрейма
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mainIcon.jpg")));
        //указываем заголовок
        setTitle("Угадай картинку");
        //говорим, что по крестику надо закрыться
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //указываем размер и делаем его неизменяемым
        setSize(550, 600);
        setResizable(false);
        //располагаем по центру экрана
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        //делаем окно видимым
        setVisible(true);


        //это будет гавная панель. тоже по рядам будем располагать
        final JPanel mainPanel = new JPanel(new FlowLayout());


        //первый ряд. тут будет лежать игровое поле с картинкой и параметры игры, чтоб не забыть
        final JPanel row1 = new JPanel(new FlowLayout());

        //создаю панель для картинки с нужными размерами и красивой границей. ну и добавляю на первый ряд
        final JPanel picturePanel = new JPanel();
        picturePanel.setPreferredSize(new Dimension(300, 400));
        picturePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        row1.add(picturePanel);

        //тут идет задание панели с параметрами (тема, сложность, очки, оставшееся время). она расположена справа от
        //панели с катринкой. думаю, тут нет ничего такого, что нужно пояснить
        final JPanel labelPanel = new JPanel(new GridLayout(4, 1));
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
        themePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 10, 22, 0),
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
        levelPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 10, 15, 0),
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
        scorePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 10, 8, 0),
                BorderFactory.createLoweredBevelBorder()));
        labelPanel.add(scorePanel);

        final JPanel timePanel = new JPanel(new GridLayout(2, 1));
        final JLabel timeLabel1 = new JLabel("Осталось времени:");
        timeLabel2 = new JLabel("5:00");
        timeLabel1.setFont(myFont);
        timeLabel2.setFont(myFont);
        final JPanel timePanel1 = new JPanel(new FlowLayout());
        timePanel1.add(timeLabel1);
        final JPanel timePanel2 = new JPanel(new FlowLayout());
        timePanel2.add(timeLabel2);
        timePanel.add(timePanel1);
        timePanel.add(timePanel2);
        timePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(22, 10, 0, 0),
                BorderFactory.createLoweredBevelBorder()));
        labelPanel.add(timePanel);

        //в первый ряд добавляю и эту панель с параметрами
        row1.add(labelPanel);


        //во втором ряду у меня лежит поле для ввода ответа и кнопка его подтверждения
        final JPanel row2 = new JPanel(new GridLayout(2, 1));
        row2.setPreferredSize(new Dimension(500, 180));

        //думаю, тут только про кнопку надо пояснить
        answerTF = new JTextField();
        answerTF.setPreferredSize(new Dimension(300, 25));
        final JPanel answerPanel1 = new JPanel(new FlowLayout());
        answerPanel1.add(answerTF);
        answerPanel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        final JButton answerButton = new JButton("Ответить");
        answerButton.setFont(myFont);
        //действия при нажатии на кнопку
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                //кидаю в контроллер мой ответ
                controller.buttonClicked(answerTF.getText());
                //очищаю поле для ввода ответа
                answerTF.setText("");
            }
        });
        answerPanel2 = new JPanel(new FlowLayout());
        answerPanel2.add(answerButton);
        row2.add(answerPanel1);
        row2.add(answerPanel2);

        //добавляю мои ряды на главную панель
        mainPanel.add(row1);
        mainPanel.add(row2);

        //обрабатываю дальше панель для картинки. не знаю, почему тут, а не выше, где задавал ее
        //переопределяю менеджкр распределения элементов
        picturePanel.setLayout(new OverlayLayout(picturePanel));

        //в этом label'е и будетнаходиться картинка
        pictureLabel = new JLabel();
        pictureLabel.setPreferredSize(new Dimension(300, 400));
        pictureLabel.setBorder(BorderFactory.createLoweredBevelBorder());

        //тут будут лежать кирпичики, покрывающие картинку
        brickPanel = new JPanel();
        //устанавливаю прозрачный фон
        brickPanel.setBackground(new Color(0, 0, 0, 0));

        //говорю элементам, чтоб они никуда не смещались
        brickPanel.setAlignmentX(0.0f);
        brickPanel.setAlignmentY(0.0f);
        pictureLabel.setAlignmentX(0.0f);
        pictureLabel.setAlignmentY(0.0f);

        //и добавляю их на картиночную панель
        picturePanel.add(brickPanel);
        picturePanel.add(pictureLabel);

        //меняю панель моего окна на только что созданную
        this.setContentPane(mainPanel);
    }

    @Override     //метод, обновляющий информацию в этом окне
    public void update(final boolean[][] bricks, final String score, final String difficulty, final String theme,
                       final ImageIcon picture, final ImageIcon brick, final boolean pictureChanged,
                       final boolean resultFromButton, final boolean endOfTheGame) {
        //перменная counter нужна мне только для того, чтобы понять надо мне запускать таймер или нет
        if (counter == 0) {
            //задаю новый таймер и говорю, что свое действие он будет выполнять раз в секунду
            Timer timer = new Timer(1000, new ActionListener() {
                //задаю время игры - 5 минут
                private int seconds = 300;

                @Override    //собственно, само действие таймера
                public void actionPerformed(final ActionEvent e) {
                    //уменьшаю время
                    seconds -= 1;
                    //это время преобразовываю в привычный формат строки
                    String time;
                    if ((seconds % 60) / 10 == 0) {
                        time = Integer.toString(seconds / 60) + ":0" + Integer.toString(seconds % 60);
                    } else {
                        time = Integer.toString(seconds / 60) + ":" + Integer.toString(seconds % 60);
                    }
                    //меняю это самое время, точнее заменяю его текстовое представление в окне
                    timeLabel2.setText(time);
                    //если время игры завончилось, то завершаю ее и перехожу в следующему окну (этапу)
                    if (seconds == 0) {
                        thisFrame.dispose();
                        JOptionPane.showMessageDialog(new Frame(),
                                "У Вас кончилось время. Игра окончена",
                                "Окончание игры",
                                JOptionPane.INFORMATION_MESSAGE);
                        JFrame end = new EndOfGame("0");
                        end.setVisible(true);
                    }
                }
            });
            //запускаю таймер
            timer.start();
            //говорю, что его больше не надо запускать
            counter = 10;
        }

        //говорю, что поле для ввода ответа теперь снова доступно
        answerTF.setEnabled(true);
        //отображаю тему игры
        themeLabel2.setText(theme);
        //отображаю сложность игры
        levelLabel2.setText(difficulty);
        //отображаю счет игры
        scoreLabel2.setText(score);
        //перезадаю кнопку для ответа. я сделал так, потому что дальше мне надо чтоб на этой кнопке был другой текст и
        //другое действие. но я не придумал, как сделать это не меняя кнопку
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

        //если мне пришла какая-то картинка, я ее показываю
        if (picture != null) {
            pictureLabel.setIcon(picture);
        } else { //а если мне не прислали картинку, то делаю следующее
            //эту форму закрываю
            this.dispose();
            //задаю переменную под новый счет. это набранные очки + бонус
            Integer newScore;
            //если счет велся
            if (!score.equals("Счет не ведется")) {
                //преобразую строку со времнем обратно в секунды
                String[] resultOfSplit = timeLabel2.getText().split(":");
                int secondsLeft = Integer.parseInt(resultOfSplit[0]) * 60 + Integer.parseInt(resultOfSplit[1]);
                //вычисляю новый счет
                newScore = Integer.parseInt(score) + controller.bonusPoints(secondsLeft);
                //сообщаю о том, что игра кончилась, говорю счет
                JOptionPane.showMessageDialog(new Frame(),
                        "Это была последняя картинка. Игра окончена. \n" +
                                "Ваш результат: " + newScore.toString(),
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);
                //и перехожу на следующее окно
                JFrame end = new EndOfGame(newScore.toString());
                end.setVisible(true);
            } else { //ну а если не велся, то просто говорю, что игра кончилась
                JOptionPane.showMessageDialog(new Frame(),
                        "Это была последняя картинка. Игра окончена.",
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);
                //и перехожу на следующее окно
                JFrame end = new EndOfGame(score);
                end.setVisible(true);
            }
        }

        //в любом случае мне приходит онформация об открытых и закрытых блоках
        //и я эти блоки отрисовываю на нужных местах
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
                //задаю действие при клике на label
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        controller.labelClicked(xCoordinate, yCoordinate);
                    }
                });
            }
        }

        //тут надо обновить окно, чтобы изменения нарисовались, и я это делаю
        revalidate();
        repaint();

        //тут идет обработка нажатия на кнопку в случае прихода какой-то картинки
        if (resultFromButton && picture != null) {
            //если эта картинка новая
            if (pictureChanged) {
                //задаю новую кнопку, при нажатии на которую игра продолжится
                answerPanel2.removeAll();
                final JButton nextButton = new JButton("Дальше");
                //по нажатию прошу следующую картинку
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        controller.nextPicture();
                    }
                });
                nextButton.setFont(myFont);
                answerPanel2.add(nextButton);
                //говорю, что поле ввода ответа недоступно в тот момент, когда игрок смотрит на угаданную картинку
                answerTF.setEnabled(false);
                //обновляю окно
                revalidate();
                repaint();

                //пишу сообщение, что игрок молодец
                JOptionPane.showMessageDialog(new Frame(),
                        "Вы угадали!",
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);

            } else { // а если старая, то я просто говорю, что надо попробовать снова
                JOptionPane.showMessageDialog(new Frame(),
                        "Пока неверно. Попробуйте еще раз",
                        "Результат проверки ответа",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        //если мне пришел флаг окончания игры
        if (endOfTheGame) {
            //то я закрываю это окно
            this.dispose();
            //и говорю, что игрок все очки проиграл
            JOptionPane.showMessageDialog(new Frame(),
                    "У Вас кончились очки. Игра окончена",
                    "Окончание игры",
                    JOptionPane.INFORMATION_MESSAGE);
            //перехожу на следующее окно
            JFrame end = new EndOfGame(score);
            end.setVisible(true);
        }
    }
}
