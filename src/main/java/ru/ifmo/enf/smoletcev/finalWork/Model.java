package ru.ifmo.enf.smoletcev.finalWork;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public class Model implements Observable {
    //собственно, класс модели. он Observable, чтобы можно было обновлять Viev через наблюдателя

    //лист слушателей этой модели. у меня слушатель один, но по паттерну вроде надо чтоб был лист. понятно, что если я
    //оставлю не лист, а одного наблидателя, то ничего не изменится. но вдруг я захочу добавить второго? поэтому
    //оставляю лист
    private List<Observer> observerList = new ArrayList<Observer>();
    //объект класса состояния (класс приписан ниже в конце кода)
    private ModelState state;
    //пулл правильных ответов
    private Map<String, List<String>> rightAnswers = new HashMap<String, List<String>>();
    //это текущие привильные ответы. лист потому что в некоторых местх я задал несколько правильных ответов
    private List<String> currentRightAnswers;
    //сюда я складываю сначала все картинки, а потом питихлньку их забираю
    //структора такая: {"тема" -> int'овый лист с номерами картинок}
    private Map<String, List<Integer>> usedPictures = new HashMap<String, List<Integer>>();


    //конструктор класса модели, принимающий параметры игры
    public Model(final boolean score, final int level, final int theme) {
        //метод, который задает праувильные ответы на все картинки
        fillRightAnswers();
        //метод, который складывает в мапу все картинки
        fillUsedPictures();
        //запускаем View и передаем ему контроллер, в который кладем текущую модель
        View view = new View(new Controller(this));
        //регистрируем его как наблюдателя
        registerObserver(view);
        //в состояние модели складываем параметры игры
        this.state = new ModelState(score, level, theme);
        //говорим ему, что надо отобразить
        notifyObservers();
    }

    @Override //добавление наблюдателя
    public void registerObserver(final Observer o) {
        observerList.add(o);
    }

    @Override //исключение наблюдателя
    public void removeObserver(final Observer o) {
        observerList.remove(o);
    }

    @Override //оповещение наблюдателей
    public void notifyObservers() {
        //идем по листу наблюдателей и оповещаем их
        for (final Observer anObserverList : observerList) {
            //из состояния моделт берем все, что надо знать наблюдателю и передаем ему
            anObserverList.update(state.getBricks(), state.getScore(), state.getDifficulty(), state.getTheme(),
                    state.getPicture(), getBrick(state.getDifficulty()), state.getPictureChanged(),
                    state.getResultFromButton(), state.getEndOfTheGame());
        }
    }

    //выбор случайной картинки. возможность повторного выбора исключена
    private ImageIcon chooseRandomPicture(final String theme) {
        // задаем имя новой картинки. сначала оно пустое
        String fileName = "";
        //добавляем в имя тему, выбранную игроком (в ресурсах все по темам лежит)
        if (theme.equals("Животные")) {
            fileName += "/animals/";
        } else if (theme.equals("Марки автомобилей")) {
            fileName += "/cars/";
        } else {
            fileName += "/music/";
        }
        //берем из всех картинок интересующую нас тему
        List<Integer> currentUsedPictures = usedPictures.get(theme);
        //и если там еще есть картинки, которые можно взять, то выбираем любую
        if (currentUsedPictures.size() > 0) {
            //собственно, исключение повтора или невзятия при наличии картинки
            Integer number = (int) (Math.random() * 10);
            while (!currentUsedPictures.contains(number)) {
                number = (int) (Math.random() * 10);
            }
            //добавляем к имени номер картинки и расширение. теперь имя готово
            fileName += number.toString() + ".jpg";
            //удаляем картинку и пулла картинок, т.к. мы ее взяли
            currentUsedPictures.remove(number);
            //обновляем пулл картинок с учетом удаления текущей
            usedPictures.put(theme, currentUsedPictures);
            //говорим состоянию, какие ответы правильные
            if (state != null) {
                state.setRightAnswers(rightAnswers.get(fileName));
            } else {
                currentRightAnswers = rightAnswers.get(fileName);
            }
            //выкидаваем взятую картинку
            return new ImageIcon(getClass().getResource(fileName));
        } else {
            return null;
        }
    }


    //тут задается то, каким кирпичиком закрывать картинку, в зависимости от сложности
    private ImageIcon getBrick(final String difficulty) {
        //думаю, тут все понятно. просто смотрим на сложность и берем соответствующий кирпичик. плюем его
        ImageIcon img;
        if (difficulty.equals("Легко")) {
            img = new ImageIcon(getClass().getResource("/bricks/big.jpg"));
        } else if (difficulty.equals("Средне")) {
            img = new ImageIcon(getClass().getResource("/bricks/medium.jpg"));
        } else {
            img = new ImageIcon(getClass().getResource("/bricks/small.jpg"));
        }
        return img;
    }

    //этот метод задает правилные ответы
    private void fillRightAnswers() {
        //создаю лтст с названием: answerТемаНомеркартинки
        List<String> answerAnimals0 = new ArrayList<String>();
        //кидаю в лист ответы, которые считаю правильными
        answerAnimals0.add("утка");
        answerAnimals0.add("селезень");
        answerAnimals0.add("duck");
        //кладу в мапу имя картинки и лист сообветствующих ей правильных ответов
        rightAnswers.put("/animals/0.jpg", answerAnimals0);

        //дальше все так же по аналогии

        List<String> answerAnimals1 = new ArrayList<String>();
        answerAnimals1.add("фламинго");
        answerAnimals1.add("розовый фламинго");
        answerAnimals1.add("flamingo");
        rightAnswers.put("/animals/1.jpg", answerAnimals1);

        List<String> answerAnimals2 = new ArrayList<String>();
        answerAnimals2.add("лиса");
        answerAnimals2.add("лис");
        answerAnimals2.add("лисица");
        answerAnimals2.add("fox");
        rightAnswers.put("/animals/2.jpg", answerAnimals2);

        List<String> answerAnimals3 = new ArrayList<String>();
        answerAnimals3.add("кенгуру");
        answerAnimals3.add("kangaroo");
        rightAnswers.put("/animals/3.jpg", answerAnimals3);

        List<String> answerAnimals4 = new ArrayList<String>();
        answerAnimals4.add("панда");
        answerAnimals4.add("panda");
        rightAnswers.put("/animals/4.jpg", answerAnimals4);

        List<String> answerAnimals5 = new ArrayList<String>();
        answerAnimals5.add("попугай");
        answerAnimals5.add("ара");
        answerAnimals5.add("попугай ара");
        answerAnimals5.add("parrot");
        answerAnimals5.add("ara");
        answerAnimals5.add("ara parrot");
        rightAnswers.put("/animals/5.jpg", answerAnimals5);

        List<String> answerAnimals6 = new ArrayList<String>();
        answerAnimals6.add("павлин");
        answerAnimals6.add("peacock");
        rightAnswers.put("/animals/6.jpg", answerAnimals6);

        List<String> answerAnimals7 = new ArrayList<String>();
        answerAnimals7.add("носорог");
        answerAnimals7.add("rhino");
        rightAnswers.put("/animals/7.jpg", answerAnimals7);

        List<String> answerAnimals8 = new ArrayList<String>();
        answerAnimals8.add("белка");
        answerAnimals8.add("белочка");
        answerAnimals8.add("squirrel");
        rightAnswers.put("/animals/8.jpg", answerAnimals8);

        List<String> answerAnimals9 = new ArrayList<String>();
        answerAnimals9.add("черепаха");
        answerAnimals9.add("морская черепаха");
        answerAnimals9.add("turtle");
        rightAnswers.put("/animals/9.jpg", answerAnimals9);

        List<String> answerCars0 = new ArrayList<String>();
        answerCars0.add("опель");
        answerCars0.add("opel");
        rightAnswers.put("/cars/0.jpg", answerCars0);

        List<String> answerCars1 = new ArrayList<String>();
        answerCars1.add("пежо");
        answerCars1.add("peugeot");
        rightAnswers.put("/cars/1.jpg", answerCars1);

        List<String> answerCars2 = new ArrayList<String>();
        answerCars2.add("понтиак");
        answerCars2.add("pontiac");
        rightAnswers.put("/cars/2.jpg", answerCars2);

        List<String> answerCars3 = new ArrayList<String>();
        answerCars3.add("рено");
        answerCars3.add("renault");
        rightAnswers.put("/cars/3.jpg", answerCars3);

        List<String> answerCars4 = new ArrayList<String>();
        answerCars4.add("сеат");
        answerCars4.add("seat");
        rightAnswers.put("/cars/4.jpg", answerCars4);

        List<String> answerCars5 = new ArrayList<String>();
        answerCars5.add("шкода");
        answerCars5.add("skoda");
        rightAnswers.put("/cars/5.jpg", answerCars5);

        List<String> answerCars6 = new ArrayList<String>();
        answerCars6.add("субару");
        answerCars6.add("subaru");
        rightAnswers.put("/cars/6.jpg", answerCars6);

        List<String> answerCars7 = new ArrayList<String>();
        answerCars7.add("сузуки");
        answerCars7.add("suzuki");
        rightAnswers.put("/cars/7.jpg", answerCars7);

        List<String> answerCars8 = new ArrayList<String>();
        answerCars8.add("тойота");
        answerCars8.add("toyota");
        rightAnswers.put("/cars/8.jpg", answerCars8);

        List<String> answerCars9 = new ArrayList<String>();
        answerCars9.add("фольксваген");
        answerCars9.add("volkswagen");
        rightAnswers.put("/cars/9.jpg", answerCars9);

        List<String> answerMusic0 = new ArrayList<String>();
        answerMusic0.add("black sabbath");
        rightAnswers.put("/music/0.jpg", answerMusic0);

        List<String> answerMusic1 = new ArrayList<String>();
        answerMusic1.add("iron maiden");
        rightAnswers.put("/music/1.jpg", answerMusic1);

        List<String> answerMusic2 = new ArrayList<String>();
        answerMusic2.add("led zeppelin");
        rightAnswers.put("/music/2.jpg", answerMusic2);

        List<String> answerMusic3 = new ArrayList<String>();
        answerMusic3.add("linkin park");
        rightAnswers.put("/music/3.jpg", answerMusic3);

        List<String> answerMusic4 = new ArrayList<String>();
        answerMusic4.add("metallica");
        rightAnswers.put("/music/4.jpg", answerMusic4);

        List<String> answerMusic5 = new ArrayList<String>();
        answerMusic5.add("nirvana");
        rightAnswers.put("/music/5.jpg", answerMusic5);

        List<String> answerMusic6 = new ArrayList<String>();
        answerMusic6.add("queen");
        rightAnswers.put("/music/6.jpg", answerMusic6);

        List<String> answerMusic7 = new ArrayList<String>();
        answerMusic7.add("rammstein");
        rightAnswers.put("/music/7.jpg", answerMusic7);

        List<String> answerMusic8 = new ArrayList<String>();
        answerMusic8.add("the rolling stones");
        rightAnswers.put("/music/8.jpg", answerMusic8);

        List<String> answerMusic9 = new ArrayList<String>();
        answerMusic9.add("slipknot");
        rightAnswers.put("/music/9.jpg", answerMusic9);
    }

    //пихаю все картинки. которые у меня есть в пулл
    private void fillUsedPictures() {
        //лист под каждую тему
        List<Integer> animalsList = new ArrayList<Integer>();
        List<Integer> carsList = new ArrayList<Integer>();
        List<Integer> musicList = new ArrayList<Integer>();
        //добавление картинок в виде их номеров
        for (int i = 0; i < 10; i++) {
            animalsList.add(i);
            carsList.add(i);
            musicList.add(i);
        }
        //составление мапыц Тема -> лист картинок
        usedPictures.put("Животные", animalsList);
        usedPictures.put("Марки автомобилей", carsList);
        usedPictures.put("Рок-группы", musicList);
    }

    //метод, увеличивающий чисоло очков. срабатывает, когда игрок угадал картинку
    private void increaseScore() {
        //тут в зависимости от сложности начисляются очки
        if (state.getDifficulty().equals("Легко")) {
            state.setScore(Integer.parseInt(state.getScore()) + 400);
        } else if (state.getDifficulty().equals("Средне")) {
            state.setScore(Integer.parseInt(state.getScore()) + 800);
        } else {
            state.setScore(Integer.parseInt(state.getScore()) + 2400);
        }
    }

    //метод, уменьшающий число очков. срабатывает, когда игрок ввел неверный ответ и когда открыл новую секцию картинки
    private void decreaseScore() {
        state.setScore(Integer.parseInt(state.getScore()) - 100);
        if (Integer.parseInt(state.getScore()) <= 0) {
            //если очки кончились - завершаем игру
            state.setEndOfTheGame(true);
        }
    }

    //метод, который говорит, что картику надо открыть целиком. срабатывет после того, как игрок угадал ответ
    private void showPicture() {
        boolean[][] bricks = state.getBricks();
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j] = false;
            }
        }
    }

    //метод, срабатывающий, когда игрок открывает новую область
    void labelClicked(final int xCoordinate, final int yCoordinate) {
        //открываем эту новую область
        boolean[][] currentBricks = state.getBricks();
        currentBricks[xCoordinate][yCoordinate] = false;
        state.setBricks(currentBricks);
        state.setResultFromButton(false);
        //уменьшаем счет
        if (state.getIfScore()) {
            decreaseScore();
        }
        //оповещаем слушателей
        notifyObservers();
    }

    //по сути тут лежит проверка ответа
    void buttonClicked(final String answer) {
        state.setPictureChanged(false);
        state.setResultFromButton(true);
        List<String> currentRightAnswers = state.getRightAnswers();
        //если верно: увеличиваем счет и показывем картинку. если неверно: уменьшаем счет
        if (currentRightAnswers.contains(answer.toLowerCase())) {
            state.setPictureChanged(true);
            showPicture();
            if (state.getIfScore()) {
                increaseScore();
            }
        } else {
            if (state.getIfScore()) {
                decreaseScore();
            }
        }
        //оповещаем слушателей
        notifyObservers();
    }

    //метод, плюющий изображению новую картинку
    void nextPicture() {
        state.setPicture();
        state.setResultFromButton(false);
        notifyObservers();
    }

    //расчет бонусных очков в конце игры
    int bonusPoints(final int secondsLeft) {
        return (secondsLeft / 100) * 1500;
    }

    //класс состояния модели
    private class ModelState {
        //флаг ведется счет или нет
        private boolean ifScore;
        //см счет, если он ведется
        private Integer score;
        //сложность игры
        private int difficulty;
        //в этом массиве лежит информация об открытых и закрытых областях картинки
        private boolean[][] bricks;
        //тема игры
        private int theme;
        //текущая картинка
        private ImageIcon picture;
        //правильные ответы к текущей картинке
        private List<String> rightAnswers;
        //флаг была ли картинка изменена. true когда игрок угадал овет. false в остальных случаях
        private boolean pictureChanged;
        //флаг была ли нажата кнопка и открыта новая область
        private boolean resultFromButton;
        //флаг завершения игры (когда счет кончился)
        private boolean endOfTheGame;

        //конструктор, принимающий параметры заданные пользователем в стартовом окошке
        public ModelState(final boolean score, final int level, final int theme) {
            this.ifScore = score;
            this.difficulty = level;
            this.theme = theme;
            endOfTheGame = false;
            //назначаем стартовые очки
            if (level == 8) {
                bricks = new boolean[4][2];
                this.score = 1000;
            } else if (level == 15) {
                bricks = new boolean[5][3];
                this.score = 1700;
            } else {
                bricks = new boolean[8][6];
                this.score = 5000;
            }
            //закрываем всю картинку
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    bricks[i][j] = true;
                }
            }
            //задаем картинку и текущие правильные ответы
            setPicture();
            rightAnswers = currentRightAnswers;
        }

        //спрашиваем, ведется ли счет
        public boolean getIfScore() {
            return ifScore;
        }

        //просим дать счет
        public String getScore() {
            if (ifScore) {
                return score.toString();
            } else {
                return "Счет не ведется";
            }
        }

        //изменяем счет
        public void setScore(final int score) {
            this.score = score;
        }

        //получаем сложность в виде слова
        public String getDifficulty() {
            if (difficulty == 8) {
                return "Легко";
            } else if (difficulty == 15) {
                return "Средне";
            } else {
                return "Сложно";
            }
        }

        //получаем информацию об открытых и закрытых кусках картинки
        public boolean[][] getBricks() {
            return bricks;
        }

        //изменяем информацию об открытых и закрытых кусках картинки
        public void setBricks(final boolean[][] bricks) {
            this.bricks = bricks;
        }

        //получам тематику игры в виде картинки
        public String getTheme() {
            if (theme == 1) {
                return "Животные";
            } else if (theme == 2) {
                return "Марки автомобилей";
            } else {
                return "Рок-группы";
            }
        }

        //получаем текущую картинку
        public ImageIcon getPicture() {
            return picture;
        }

        //изменяем картинку на новую
        public void setPicture() {
            //тянем случайную картинку
            picture = chooseRandomPicture(getTheme());
            //говорим, что она новая
            pictureChanged = true;
            //закрываем ее
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    bricks[i][j] = true;
                }
            }
        }

        //тут все методы с понятными названиями. думаю, можно не пояснять

        public List<String> getRightAnswers() {
            return rightAnswers;
        }

        public void setRightAnswers(final List<String> rightAnswers) {
            this.rightAnswers = rightAnswers;
        }

        public boolean getPictureChanged() {
            return pictureChanged;
        }

        public void setPictureChanged(final boolean pictureChanged) {
            this.pictureChanged = pictureChanged;
        }

        public boolean getResultFromButton() {
            return resultFromButton;
        }

        public void setResultFromButton(final boolean resultFromButton) {
            this.resultFromButton = resultFromButton;
        }

        public boolean getEndOfTheGame() {
            return endOfTheGame;
        }

        public void setEndOfTheGame(final boolean endOfTheGame) {
            this.endOfTheGame = endOfTheGame;
        }
    }
}

