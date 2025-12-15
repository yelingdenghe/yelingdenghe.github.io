package io.github.ylingdenghe.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.ylingdenghe.WorldConfig;

/**
 * @author 夜凌
 * @Description: TODO
 * @ClassName Bird
 * @Date 2025/12/10 18:16
 * @Version 1.0
 */
public class Bird {

    private static final float WIDTH = 34;
    private static final float HEIGHT = 24;

    private Texture texture;

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Rectangle bounds = new Rectangle();

    public Bird(Texture texture,float x, float y) {
        this.texture = texture;
        position.set(x, y);
        bounds.setSize(WIDTH, HEIGHT);
    }

    public void update(float delta) {
        velocity.y += WorldConfig.GRAVITY * delta;
        position.mulAdd(velocity, delta);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = WorldConfig.JUMP_VELOCITY;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, WIDTH, HEIGHT);
    }

}
