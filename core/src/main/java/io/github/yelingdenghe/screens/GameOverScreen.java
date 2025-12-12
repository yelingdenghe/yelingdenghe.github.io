package io.github.yelingdenghe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.yelingdenghe.FlappyGame;
import io.github.yelingdenghe.WorldConfig;

/**
 * @author 夜凌
 * @Description: 结算界面
 * @ClassName GameOverScreen
 * @Date 2025/12/10 18:19
 * @Version 1.0
 */
public class GameOverScreen implements Screen {

    private final FlappyGame game;
    private final int score;

    private OrthographicCamera camera;
    private Viewport viewport;

    public GameOverScreen(FlappyGame game, int score) {
        this.game = game;
        this.score = score;

        camera = new OrthographicCamera();
        viewport = new FitViewport(
            WorldConfig.WORLD_WIDTH,
            WorldConfig.WORLD_HEIGHT,
            camera
        );
        camera.position.set(
            WorldConfig.WORLD_WIDTH / 2f,
            WorldConfig.WORLD_HEIGHT / 2f,
            0
        );
        camera.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "游戏结束", 180, 500);
        game.font.draw(game.batch, "分数: " + score, 190, 450);
        game.font.draw(game.batch, "最高分: " + game.getHighScore(), 190, 420);
        game.font.draw(game.batch, "点击重试", 180, 360);
        game.font.draw(game.batch, "按 M 键进入菜单", 160, 330);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { viewport.update(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
