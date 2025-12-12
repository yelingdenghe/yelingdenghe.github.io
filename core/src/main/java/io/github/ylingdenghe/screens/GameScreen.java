package io.github.ylingdenghe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ylingdenghe.FlappyGame;
import io.github.ylingdenghe.WorldConfig;
import io.github.ylingdenghe.entities.Bird;
import io.github.ylingdenghe.entities.PipePair;

import java.util.Iterator;

/**
 * @author 夜凌
 * @Description: 游戏主逻辑
 * @ClassName GameScreen
 * @Date 2025/12/10 18:15
 * @Version 1.0
 */
public class GameScreen implements Screen {

    private enum State {
        READY, RUNNING, PAUSED, GAME_OVER
    }

    private State state = State.READY;

    private final FlappyGame game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Bird bird;
    private Array<PipePair> pipes;

    private float pipeTimer = 0f;
    private int score = 0;

    private Texture bgTexture;
    private Texture groundTexture;

    private Sound sfxJump;
    private Sound sfxHit;
    private Sound sfxScore;

    // 用于碰撞检测的地面矩形
    private Rectangle groundBounds;

    public GameScreen(FlappyGame game) {
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

        // 加载资源（后面可以重构到 Assets 管理类）
        bgTexture = new Texture("bg.png");
        groundTexture = new Texture("ground.png");

        sfxJump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        sfxHit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        sfxScore = Gdx.audio.newSound(Gdx.files.internal("score.wav"));

        groundBounds = new Rectangle(
            0,
            0,
            WorldConfig.WORLD_WIDTH,
            groundTexture.getHeight()
        );

        resetGame();
    }

    private void resetGame() {
        bird = new Bird(100, 400);
        pipes = new Array<PipePair>();
        pipeTimer = 0f;
        score = 0;
        state = State.READY;
    }

    @Override
    public void render(float delta) {
        handleInput();

        if (state == State.RUNNING) {
            update(delta);
        }

        draw();
    }

    private void handleInput() {
        boolean justTouched = Gdx.input.justTouched() ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE);

        if (state == State.READY) {
            if (justTouched) {
                state = State.RUNNING;
                sfxJump.play();
                bird.jump();
            }
        } else if (state == State.RUNNING) {
            if (justTouched) {
                sfxJump.play();
                bird.jump();
            }

            // 暂停键（示例：P）
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                state = State.PAUSED;
            }
        } else if (state == State.PAUSED) {
            // 任意按键继续
            if (justTouched || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                state = State.RUNNING;
            }
        } else if (state == State.GAME_OVER) {
            // 在 GameOverScreen 处理，这里不管
        }
    }

    private void update(float delta) {
        bird.update(delta);

        // 管道生成
        pipeTimer += delta;
        if (pipeTimer > WorldConfig.PIPE_INTERVAL / WorldConfig.PIPE_SPEED) {
            pipeTimer = 0f;
            spawnPipe();
        }

        // 更新管道，检查是否离开屏幕
        for (Iterator<PipePair> it = pipes.iterator(); it.hasNext(); ) {
            PipePair pipe = it.next();
            pipe.update(delta);

            // 通过管道计分（以水平位置判断）
            if (!pipe.passed && pipe.x + 52 < bird.position.x) {
                pipe.passed = true;
                score++;
                sfxScore.play();
            }

            if (pipe.isOffScreen()) {
                pipe.dispose();
                it.remove();
            }

            // 碰撞检测
            if (pipe.boundsTop.overlaps(bird.bounds)
                || pipe.boundsBottom.overlaps(bird.bounds)) {
                onGameOver();
            }
        }

        // 撞到地面
        if (bird.bounds.overlaps(groundBounds)) {
            onGameOver();
        }
    }

    private void spawnPipe() {
        // 简单随机一下管道中间高度
        float minY = 250f;
        float maxY = WorldConfig.WORLD_HEIGHT - 250f;
        float gapY = MathUtils.random(minY, maxY);

        float startX = WorldConfig.WORLD_WIDTH + 50f;
        pipes.add(new PipePair(startX, gapY));
    }

    private void onGameOver() {
        if (state == State.GAME_OVER) return;
        state = State.GAME_OVER;
        sfxHit.play();

        // 更新最高分
        game.setHighScore(score);

        // 切到结算界面
        game.setScreen(new GameOverScreen(game, score));
    }

    private void draw() {
        ScreenUtils.clear(0.2f, 0.6f, 1f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // 背景
        game.batch.draw(bgTexture, 0, 0,
            WorldConfig.WORLD_WIDTH,
            WorldConfig.WORLD_HEIGHT);

        // 管道
        for (PipePair pipe : pipes) {
            pipe.draw(game.batch);
        }

        // 地面
        game.batch.draw(groundTexture,
            0,
            0,
            WorldConfig.WORLD_WIDTH,
            groundTexture.getHeight());

        // 小鸟
        bird.draw(game.batch);

        // 分数
        game.font.draw(
            game.batch,
            "分数: " + score,
            10,
            WorldConfig.WORLD_HEIGHT - 20
        );

        if (state == State.READY) {
            game.font.draw(
                game.batch,
                "点击开始",
                160,
                450
            );
        } else if (state == State.PAUSED) {
            game.font.draw(
                game.batch,
                "已暂停",
                200,
                450
            );
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        bgTexture.dispose();
        groundTexture.dispose();
        bird.dispose();
        for (PipePair p : pipes) {
            p.dispose();
        }
        sfxJump.dispose();
        sfxHit.dispose();
        sfxScore.dispose();
    }
}
