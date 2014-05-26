import javax.swing.*;

/**
 * Author: Alexey Smolentcev (alexsm95@mail.ru)
 * Date: 23.05.14
 */
public interface Observer {
    void update(boolean[][] bricks, String score, String difficulty, String theme, ImageIcon picture, ImageIcon brick,
                boolean pictureChanged, boolean resultFromButton, boolean endOfTheGame);
}
