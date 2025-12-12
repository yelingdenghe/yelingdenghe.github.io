package io.github.yelingdenghe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.yelingdenghe.screens.MainMenuScreen;

/**
 * @author 夜凌
 * @Description: TODO
 * @ClassName FlappyGame
 * @Date 2025/12/10 18:13
 * @Version 1.0
 */
public class FlappyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Preferences prefs;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // 简单字体：先用默认 BitmapFont，后面可以自制 .fnt
        font = new BitmapFont();

        // 初始化 Preferences
        prefs = Gdx.app.getPreferences("flappy_prefs");
        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
            prefs.flush();
        }

        setScreen(new MainMenuScreen(this));
    }

    public int getHighScore() {
        return prefs.getInteger("highscore", 0);
    }

    // 更新最高分
    public void setHighScore(int score) {
        int old = getHighScore();
        if (score > old) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
