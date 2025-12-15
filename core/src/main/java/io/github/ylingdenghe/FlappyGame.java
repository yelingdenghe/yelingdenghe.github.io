package io.github.ylingdenghe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import io.github.ylingdenghe.screens.LoadingScreen;
import io.github.ylingdenghe.screens.MainMenuScreen;

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
    public Assets assets;

    // 游戏中使用的所有中文字符
    private static final String CHINESE_CHARS = "弹鸟点击按空格开始最高分游戏结束数重试进入菜单已暂停/:M键 0123456789";

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        prefs = Gdx.app.getPreferences("flappy_prefs");
        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
            prefs.flush();
        }

        assets = new Assets();

        // 先去 LoadingScreen
        setScreen(new LoadingScreen(this));
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

        // 注意：Assets 统一在这里释放（Screen/Entity 不要 dispose 这些共享资源）
        if (assets != null) assets.dispose();
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
