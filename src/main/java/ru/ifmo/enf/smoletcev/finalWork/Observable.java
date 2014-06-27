package ru.ifmo.enf.smoletcev.finalWork;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public interface Observable {     //интерфейс для модели, чтобы реализовать наблюдателя
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
