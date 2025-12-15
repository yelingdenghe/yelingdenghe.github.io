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

    private final Texture texTop;
    private final Texture texBottom;

    public final Rectangle boundsTop = new Rectangle();
    public final Rectangle boundsBottom = new Rectangle();

    public float x;
    private final  float gapY;
    public boolean passed = false; // 是否已经计过分

    public PipePair(Texture texTop, Texture texBottom, float startX, float gapY) {
        this.texTop = texTop;
        this.texBottom = texBottom;
        this.x = startX;
        this.gapY = gapY;

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
}
