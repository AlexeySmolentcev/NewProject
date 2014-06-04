package ru.ifmo.enf.smoletcev.finalWork;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public interface Strategy {
    void labelClicked(int xCoordinate, int yCoordinate);
    void buttonClicked(String answer);
    void nextPicture();
    int bonusPoints(int secondsLeft);
}
