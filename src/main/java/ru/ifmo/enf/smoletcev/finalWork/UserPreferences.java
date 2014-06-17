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
    private Preferences userPrefs;

    public UserPreferences() {
        userPrefs = Preferences.userRoot().node("result");
    }

    public void putScore(final Integer newScore) {
        List<Integer> newScores = getScores();
        newScores.add(newScore);
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
        for (Integer i = 0; i < newScores.size(); i++) {
            if (i <= 9) {
                userPrefs.putInt(i.toString() + " score", newScores.get(i));
            }
        }
    }

    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<Integer>();
        for (Integer i = 0; i < 10; i++) {
            scores.add(userPrefs.getInt(i.toString() + " score", 0));
        }
        return scores;
    }
}
