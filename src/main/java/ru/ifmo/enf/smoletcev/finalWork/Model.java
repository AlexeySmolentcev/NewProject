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
    private List<Observer> observerList = new ArrayList<Observer>();
    private ModelState state;
    private Map<String, List<String>> rightAnswers = new HashMap<String, List<String>>();
    private List<String> currentRightAnswers;
    private Map<String, List<Integer>> usedPictures = new HashMap<String, List<Integer>>();

    public Model(final boolean score, final int level, final int theme) {
        fillRightAnswers();
        fillUsedPictures();
        View view = new View(new Controller(this));
        registerObserver(view);
        this.state = new ModelState(score, level, theme);
        notifyObservers();
    }

    @Override
    public void registerObserver(final Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(final Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (final Observer anObserverList : observerList) {
            anObserverList.update(state.getBricks(), state.getScore(), state.getDifficulty(), state.getTheme(),
                    state.getPicture(), getBrick(state.getDifficulty()), state.getPictureChanged(),
                    state.getResultFromButton(), state.getEndOfTheGame());
        }
    }

    private ImageIcon chooseRandomPicture(final String theme) {
        String fileName = "";
        if (theme.equals("Животные")) {
            fileName += "/animals/";
        } else if (theme.equals("Марки автомобилей")) {
            fileName += "/cars/";
        } else {
            fileName += "/music/";
        }
        List<Integer> currentUsedPictures = usedPictures.get(theme);
        if (currentUsedPictures.size() > 0) {
            Integer number = (int) (Math.random() * 10);
            while (!currentUsedPictures.contains(number)) {
                number = (int) (Math.random() * 10);
            }
            fileName += number.toString() + ".jpg";
            currentUsedPictures.remove(number);
            usedPictures.put(theme, currentUsedPictures);
            if (state != null) {
                state.setRightAnswers(rightAnswers.get(fileName));
            } else {
                currentRightAnswers = rightAnswers.get(fileName);
            }
            return new ImageIcon(getClass().getResource(fileName));
        } else {
            return null;
        }
    }

    private ImageIcon getBrick(final String difficulty) {
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

    private void fillRightAnswers() {
        List<String> answerAnimals0 = new ArrayList<String>();
        answerAnimals0.add("утка");
        answerAnimals0.add("селезень");
        answerAnimals0.add("duck");
        rightAnswers.put("/animals/0.jpg", answerAnimals0);

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

    private void fillUsedPictures() {
        List<Integer> animalsList = new ArrayList<Integer>();
        List<Integer> carsList = new ArrayList<Integer>();
        List<Integer> musicList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            animalsList.add(i);
            carsList.add(i);
            musicList.add(i);
        }
        usedPictures.put("Животные", animalsList);
        usedPictures.put("Марки автомобилей", carsList);
        usedPictures.put("Рок-группы", musicList);
    }

    private void increaseScore() {
        if (state.getDifficulty().equals("Легко")) {
            state.setScore(Integer.parseInt(state.getScore()) + 400);
        } else if (state.getDifficulty().equals("Средне")) {
            state.setScore(Integer.parseInt(state.getScore()) + 800);
        } else {
            state.setScore(Integer.parseInt(state.getScore()) + 2400);
        }
    }

    private void decreaseScore() {
        state.setScore(Integer.parseInt(state.getScore()) - 100);
        if (Integer.parseInt(state.getScore()) <= 0) {
            state.setEndOfTheGame(true);
        }
    }

    private void showPicture() {
        boolean[][] bricks = state.getBricks();
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j] = false;
            }
        }
    }

    void labelClicked(final int xCoordinate, final int yCoordinate) {
        boolean[][] currentBricks = state.getBricks();
        currentBricks[xCoordinate][yCoordinate] = false;
        state.setBricks(currentBricks);
        state.setResultFromButton(false);
        if (state.getIfScore()) {
            decreaseScore();
        }
        notifyObservers();
    }

    void buttonClicked(final String answer) {
        state.setPictureChanged(false);
        state.setResultFromButton(true);
        List<String> currentRightAnswers = state.getRightAnswers();
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
        notifyObservers();
    }

    void nextPicture() {
        state.setPicture();
        state.setResultFromButton(false);
        notifyObservers();
    }

    int bonusPoints(final int secondsLeft) {
        return (secondsLeft / 100) * 1500;
    }

    private class ModelState {
        private boolean ifScore;
        private Integer score;
        private int difficulty;
        private boolean[][] bricks;
        private int theme;
        private ImageIcon picture;
        private List<String> rightAnswers;
        private boolean pictureChanged;
        private boolean resultFromButton;
        private boolean endOfTheGame;

        public ModelState(final boolean score, final int level, final int theme) {
            this.ifScore = score;
            this.difficulty = level;
            this.theme = theme;
            endOfTheGame = false;
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
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    bricks[i][j] = true;
                }
            }
            setPicture();
            rightAnswers = currentRightAnswers;
        }

        public boolean getIfScore() {
            return ifScore;
        }

        public String getScore() {
            if (ifScore) {
                return score.toString();
            } else {
                return "Счет не ведется";
            }
        }

        public void setScore(final int score) {
            this.score = score;
        }

        public String getDifficulty() {
            if (difficulty == 8) {
                return "Легко";
            } else if (difficulty == 15) {
                return "Средне";
            } else {
                return "Сложно";
            }
        }

        public boolean[][] getBricks() {
            return bricks;
        }

        public void setBricks(final boolean[][] bricks) {
            this.bricks = bricks;
        }

        public String getTheme() {
            if (theme == 1) {
                return "Животные";
            } else if (theme == 2) {
                return "Марки автомобилей";
            } else {
                return "Рок-группы";
            }
        }

        public ImageIcon getPicture() {
            return picture;
        }

        public void setPicture() {
            picture = chooseRandomPicture(getTheme());
            pictureChanged = true;
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    bricks[i][j] = true;
                }
            }
        }

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

