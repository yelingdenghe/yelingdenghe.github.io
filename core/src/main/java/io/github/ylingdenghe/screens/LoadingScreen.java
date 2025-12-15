package io.github.ylingdenghe.screens;

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
 * @ClassName LoadingScreen
 * @Date 2025/12/15 09:59
 * @Version 1.0
 */
public class LoadingScreen implements Screen {

    private final FlappyGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    public LoadingScreen(FlappyGame game) {
        this.game = game;

        camera = new OrthographicCamera(); // 创建一个正交相机
        viewport = new FitViewport(WorldConfig.WORLD_WIDTH, WorldConfig.WORLD_HEIGHT, camera); // 创建一个适应窗口的视口
        camera.position.set(WorldConfig.WORLD_WIDTH / 2f, WorldConfig.WORLD_HEIGHT / 2f, 0); // 设置相机的位置
        camera.update(); // 更新相机
    }


    // 解释： 游戏窗口创建时调用
    @Override
    public void show() {
        // 入队（只会执行一次）
        game.assets.queueLoad();
    }

    // 解释： 游戏窗口绘制时调用
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // 加载完成 -> 进主菜单
        if (game.assets.update()) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        int percent = (int) (game.assets.progress() * 100);

        game.batch.begin();
        game.font.draw(game.batch, "Loading... " + percent + "%", 30, WorldConfig.WORLD_HEIGHT / 2f);
        game.batch.end();
    }


    // 解释： 游戏窗口大小改变时调用
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {} // 解释： 游戏暂停时调用
    @Override public void resume() {} // 解释： 游戏恢复时调用
    @Override public void hide() {} // 解释： 游戏切换到其他屏幕时调用
    @Override public void dispose() {} // 解释： 游戏退出时调用
}
