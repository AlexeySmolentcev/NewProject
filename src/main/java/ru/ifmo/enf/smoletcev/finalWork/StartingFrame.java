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
    //это окно открывается при запуске новой игры
    public StartingFrame() {
        //задаю нужные мне переменные: новый шрифт для красоты и этот фрейм, чтоб к нему обращаться
        final Font myFont = new Font("Arial", Font.PLAIN, 15);   //этот шрифт я буду ставить везде, шде есть текст
        final JFrame myFrame = this;


        //меняю иконку окна
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mainIcon.jpg")));
        //меняю заголовок окна
        setTitle("Угадай картинку");
        //меняю действие по нажатию на крестик
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //задаю размеры и делаю их фиксированными
        setSize(350, 450);
        setResizable(false);
        //помещаю окно по центру экрана
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - myFrame.getSize().width / 2, dim.height / 2 - myFrame.getSize().height / 2);
        //показываю пользователю это окно
        setVisible(true);


        //с настройками окна разобрались. переходим к отрисовке элементов


        //это будет моя панель, но которой я все распольжу. буду располагать рядами
        final JPanel mainPanel = new JPanel(new GridLayout(5, 1));


        //это будет первый ряд. можно назвать заголовком
        final JPanel row1 = new JPanel();
        final JLabel header = new JLabel("Выбор параметров игры");
        header.setFont(new Font("Arial", Font.BOLD, 20));
        row1.add(header);


        //это второй ряд. тут игрок решает, будет ли вестись счет
        final JPanel row2 = new JPanel(new FlowLayout());
        final JLabel pointsLabel = new JLabel("Подсчет очков");
        pointsLabel.setFont(myFont);
        row2.add(pointsLabel);
        //задяю группу radioButton'ов, чтобы из них можно было выбрать только один
        final ButtonGroup pointsRBGroup = new ButtonGroup();
        final JRadioButton pointsRB1 = new JRadioButton("есть");
        final JRadioButton pointsRB2 = new JRadioButton("нет");
        pointsRB1.setFont(myFont);
        pointsRB2.setFont(myFont);
        pointsRBGroup.add(pointsRB1);
        pointsRBGroup.add(pointsRB2);
        //кладу radioButton'ы в их отдельную панель для нужного мне расположения
        final JPanel pointsRBPanel = new JPanel(new GridLayout(2, 1));
        pointsRBPanel.add(pointsRB1);
        pointsRBPanel.add(pointsRB2);
        row2.add(pointsRBPanel);


        //третий ряд: игрок выбирает сложность игры - количество фрагментов картинки
        final JPanel row3 = new JPanel(new FlowLayout());
        final JLabel numberLabel = new JLabel("Число фрагментов картинки");
        numberLabel.setFont(myFont);
        row3.add(numberLabel);
        //снова задяю группу radioButton'ов. эти числа не с потолка, я посчитал их исходя из размеров картинки
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


        //в четвертом ряду игрок выбирает тематику картинок. сеществует три категории
        final JPanel row4 = new JPanel(new FlowLayout());
        final JLabel themeLabel = new JLabel("Тематика картинок");
        themeLabel.setFont(myFont);
        row4.add(themeLabel);
        //не знаю, почему я сделал тут comboBox, а не radioBitton'ы, как выше. но раз сделал, то уже оставлю
        final JComboBox<String> themeCB = new JComboBox<String>();
        themeCB.addItem("НЕ ВЫБРАНО");
        themeCB.addItem("Животные");
        themeCB.addItem("Марки автомобилей");
        themeCB.addItem("Рок-группы");
        themeCB.setFont(myFont);
        row4.add(themeCB);


        //пятый ряд: тут лежит одна кнопка. при нажатии на нее параметры игры подтверждаются и она начинается
        final JPanel row5 = new JPanel();
        final JButton confirmButton = new JButton("Подтвердить параметры");
        confirmButton.setSize(100, 80);
        confirmButton.setFont(myFont);
        row5.add(confirmButton);


        //добавляю созданные ряды на мою панель
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);


        //задаю действие при нажатии на кнопку
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                //условие: во всех параметрах что-то выбрано
                if ((pointsRB1.isSelected() || pointsRB2.isSelected()) &&
                        (numberRB1.isSelected() || numberRB2.isSelected() || numberRB3.isSelected()) &&
                        (themeCB.getSelectedIndex() != 0)) {
                    //флаг подсчета очков
                    boolean points = pointsRB1.isSelected();
                    //переменная с количеством фрагментов картинки (сложность)
                    int pictureFragments = 0;
                    //задаем ее в зависимости от того, какой radioButton нажат
                    if (numberRB1.isSelected()) {
                        pictureFragments = Integer.parseInt(numberRB1.getText());
                    }
                    if (numberRB2.isSelected()) {
                        pictureFragments = Integer.parseInt(numberRB2.getText());
                    }
                    if (numberRB3.isSelected()) {
                        pictureFragments = Integer.parseInt(numberRB3.getText());
                    }
                    //индекс тематики равняется индексу выбранной строки в comboBox'е
                    int themeCode = themeCB.getSelectedIndex();
                    //убираю эту форму и создаю модель с выбранными параметрами
                    myFrame.dispose();
                    new Model(points, pictureFragments, themeCode);
                } else { //иначе кидаем окошко с ошибкой, сообщающее о том, что не все параметры заданы
                    JOptionPane.showMessageDialog(new Frame(),
                            "Вы выбрали не все параметры.",
                            "Ошибка!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //меняю панель по умолчанию на ту, которую сейчас сделал
        setContentPane(mainPanel);
    }
}

