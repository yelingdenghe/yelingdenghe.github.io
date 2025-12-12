package io.github.ylingdenghe.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.ylingdenghe.WorldConfig;

/**
 * @author 夜凌
 * @Description: TODO
 * @ClassName PipePair
 * @Date 2025/12/10 18:17
 * @Version 1.0
 */
public class PipePair {

    private static final float WIDTH = 52;

    private Texture texTop;
    private Texture texBottom;

    public Rectangle boundsTop;
    public Rectangle boundsBottom;

    public float x;
    private float gapY;
    public boolean passed = false; // 是否已经计过分

    public PipePair(float startX, float gapY) {
        this.x = startX;
        this.gapY = gapY;

        texTop = new Texture("pipe_top.png");
        texBottom = new Texture("pipe_bottom.png");

        boundsTop = new Rectangle();
        boundsBottom = new Rectangle();

        updateBounds();
    }

    private void updateBounds() {
        // 上管道的底端 y = gapY + gap/2
        float halfGap = WorldConfig.PIPE_GAP / 2f;

        boundsTop.set(
            x,
            gapY + halfGap,
            WIDTH,
            texTop.getHeight()
        );

        boundsBottom.set(
            x,
            gapY - halfGap - texBottom.getHeight(),
            WIDTH,
            texBottom.getHeight()
        );
    }

    public void update(float delta) {
        x -= WorldConfig.PIPE_SPEED * delta;
        updateBounds();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texTop, boundsTop.x, boundsTop.y);
        batch.draw(texBottom, boundsBottom.x, boundsBottom.y);
    }

    public boolean isOffScreen() {
        return x + WIDTH < 0;
    }

    public void dispose() {
        texTop.dispose();
        texBottom.dispose();
    }
}
