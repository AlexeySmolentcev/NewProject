package ru.ifmo.enf.smoletcev.finalWork;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public class Controller implements Strategy{
    //это мой контроллер

    //тут нет ничего интересного. он просто прокидывает все методы в модель, принятую в конструкторе

    private Model model;

    public Controller(final Model model) {
        this.model = model;
    }


    @Override
    public void labelClicked(final int xCoordinate, final int yCoordinate) {
        model.labelClicked(xCoordinate, yCoordinate);
    }

    @Override
    public void buttonClicked(final String answer) {
        model.buttonClicked(answer);
    }

    @Override
    public void nextPicture() {
        model.nextPicture();
    }

    @Override
    public int bonusPoints(final int secondsLeft) {
        return model.bonusPoints(secondsLeft);
    }
}
