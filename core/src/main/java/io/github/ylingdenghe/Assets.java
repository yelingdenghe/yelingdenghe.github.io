package io.github.ylingdenghe;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author 夜凌
 * @Description: TODO
 * @ClassName Assets
 * @Date 2025/12/12 18:05
 * @Version 1.0
 */
public class Assets implements Disposable {

    private final AssetManager am = new AssetManager();
    private boolean queued = false;
    private boolean finished = false;

    // [Assets.java] 新增：缓存 region
    public TextureRegion bg, ground, bird, pipeTop, pipeBottom;
    public Sound sfxJump, sfxHit, sfxScore;

    // [Assets.java] 修改：只加载 atlas + sound
    public void queueLoad() {
        if (queued) return;
        queued = true;

        // 图片
        am.load("atlas/game.atlas", TextureAtlas.class);

        // 音效
        am.load("jump.wav", Sound.class);
        am.load("hit.wav", Sound.class);
        am.load("score.wav", Sound.class);
    }

    /** 每帧调用一次：返回 true 表示加载完成 */
    public boolean update() {
        return am.update();
    }

    /** 0~1 的加载进度 */
    public float progress() {
        return am.getProgress();
    }

    public Texture tex(String path) {
        return am.get(path, Texture.class);
    }

    public Sound sound(String path) {
        return am.get(path, Sound.class);
    }

    // [Assets.java] 新增：加载完成后缓存 region（避免每帧 findRegion）
    public void finishLoading() {
        if (finished) return;
        finished = true;

        TextureAtlas atlas = am.get("atlas/game.atlas", TextureAtlas.class);
        bg = atlas.findRegion("bg");
        ground = atlas.findRegion("ground");
        bird = atlas.findRegion("bird");
        pipeTop = atlas.findRegion("pipe_top");
        pipeBottom = atlas.findRegion("pipe_bottom");

        sfxJump = am.get("jump.wav", Sound.class);
        sfxHit  = am.get("hit.wav", Sound.class);
        sfxScore= am.get("score.wav", Sound.class);
    }


    @Override
    public void dispose() {
        am.dispose();
    }
}
