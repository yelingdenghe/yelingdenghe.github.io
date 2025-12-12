package io.github.ylingdenghe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ylingdenghe.FlappyGame;
import io.github.ylingdenghe.WorldConfig;

/**
 * @author 夜凌
 * @Description: TODO
 * @ClassName MainMenuScreen
 * @Date 2025/12/10 18:14
 * @Version 1.0
 */
public class MainMenuScreen implements Screen {

    private final FlappyGame game;
    private OrthographicCamera camera;
    private Viewport viewport;

    public MainMenuScreen(FlappyGame game) {
        this.game = game;

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
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(
            game.batch,
            "弹鸟",
            150,
            500
        );
        game.font.draw(
            game.batch,
            "点击/按空格开始",
            100,
            400
        );
        game.font.draw(
            game.batch,
            "最高分: " + game.getHighScore(),
            130,
            350
        );
        game.batch.end();

        if (Gdx.input.justTouched() ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
    }

    // 其他方法保持空实现即可
    @Override public void show() {}
    @Override public void resize(int width, int height) { viewport.update(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
