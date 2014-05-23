/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
