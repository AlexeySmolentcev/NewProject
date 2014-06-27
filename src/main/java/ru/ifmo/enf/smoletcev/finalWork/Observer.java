package ru.ifmo.enf.smoletcev.finalWork;

import javax.swing.*;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public interface Observer {  //интерфейс для изображения, чтобы реализовать наблюдателя
    void update(boolean[][] bricks, String score, String difficulty, String theme, ImageIcon picture, ImageIcon brick,
                boolean pictureChanged, boolean resultFromButton, boolean endOfTheGame);
                //метод принимает все необходимые данные
}
