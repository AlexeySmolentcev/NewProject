package ru.ifmo.enf.smoletcev.finalWork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 17.06.2014
 */
public class UserPreferences {
    //в этом классе хранится ифотрация о лучших результатах. точнее не совсем тут, а в поле, которое тут есть
    //а этот класс только предоставляет дступ к работе с этим полем

    //кстати, счет сохраняется из всех уровней сложности сразу

    private Preferences userPrefs;

    //в конструкторе создается файлик, в котром все и лежит. если этот файлик уже существует, то берется существующий
    //если я правильно понял принцип работы этой штуки, то файлик создается в реестре. соответственно на каждом
    //компьютере будет новый лучший счет
    public UserPreferences() {
        userPrefs = Preferences.userRoot().node("result");
    }

    //метод добавления нового счета
    public void putScore(final Integer newScore) {
        //берем все результаты, которые уже есть
        List<Integer> newScores = getScores();
        //добавляем туда новый счет
        newScores.add(newScore);
        //сортируем все это дело
        Collections.sort(newScores, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                if (o1 < o2) {
                    return 1;
                } else if (o1 > o2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        //и кладем все результаты по новой. это получается своеобразная мапа. то есть если счет уже был, то он
        //перезаписывается
        for (Integer i = 0; i < newScores.size(); i++) {
            if (i <= 9) {
                userPrefs.putInt(i.toString() + " score", newScores.get(i));
            }
        }
    }

    //метод, возвращающий лист результатов
    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<Integer>();
        for (Integer i = 0; i < 10; i++) {
            scores.add(userPrefs.getInt(i.toString() + " score", 0));
        }
        return scores;
    }
}
