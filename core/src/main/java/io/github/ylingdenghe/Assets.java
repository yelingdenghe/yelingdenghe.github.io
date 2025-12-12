package io.github.ylingdenghe;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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

    /** 把需要的资源全部加入加载队列（只入队一次） */
    public void queueLoad() {
        if (queued) return;
        queued = true;

        // 图片
        am.load("bg.png", Texture.class);
        am.load("ground.png", Texture.class);
        am.load("bird.png", Texture.class);
        am.load("pipe_top.png", Texture.class);
        am.load("pipe_bottom.png", Texture.class);

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

    @Override
    public void dispose() {
        am.dispose();
    }
}
