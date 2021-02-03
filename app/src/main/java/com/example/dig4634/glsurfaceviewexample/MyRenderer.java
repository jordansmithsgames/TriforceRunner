package com.example.dig4634.glsurfaceviewexample;

import android.app.Activity;
import android.opengl.GLES30;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import gl.Texture;
import gl.renderers.ThirdEyeRenderer;

public class MyRenderer extends ThirdEyeRenderer implements View.OnTouchListener {

    Triangle my_triangle;
    Cube my_cube;
    Level my_level;

    Texture wooden_texture;
    Texture metal_texture;
    Texture brick_texture;

    Texture triforce_texture;
    Texture treadplate_texture;
    Texture grass_texture;

    double lastTime=0;

    public MyRenderer(Activity activity){
        super(activity);
    }

    @Override
    public void setup() {

        wooden_texture = new Texture(getContext(), "box.png");
        metal_texture = new Texture(getContext(), "metal.jpg");
        brick_texture = new Texture(getContext(), "bricks.jpg");

        triforce_texture = new Texture(getContext(), "triforce.png");
        treadplate_texture = new Texture(getContext(), "treadplate.png");
        grass_texture = new Texture(getContext(), "grass.jpg");

        my_triangle = new Triangle();
        my_triangle.setTexture(treadplate_texture);
        my_triangle.positionZ = -5;

        my_cube = new Cube();
        my_cube.setTexture(wooden_texture);
        my_cube.positionZ = -5;

        my_level = new Level(10, grass_texture, triforce_texture);

        //background(153/255f,	204/255f,	255/255f);
        setLightDir(0, -1, -1);
    }

    @Override
    public void simulate(double elapsedDisplayTime) {
        float perSec = (float)(elapsedDisplayTime-lastTime);
        lastTime=elapsedDisplayTime;

        my_triangle.simulate(perSec);
        //my_cube.simulate(perSec);
        my_level.simulate(perSec);

        //collision detection logic
        for (int i = 0; i < my_level.level_segments.length; i++) {
            if (my_level.level_segments[i].collectible != null) {

                if (Math.abs(my_level.level_segments[i].collectible.positionZ-my_triangle.positionZ) < 0.5 &&
                        Math.abs(my_level.level_segments[i].collectible.positionY-my_triangle.positionY) < 0.5)
                    my_level.level_segments[i].collectible.shown = false;
            }
        }
    }

    @Override
    public void draw() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT| GLES30.GL_DEPTH_BUFFER_BIT);

        my_triangle.draw();
        //my_cube.draw();
        my_level.draw();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            Log.d("Example","tap");
            //my_cube.speedY=3f;
            my_triangle.speedY=3f;
        }

        return false;
    }
}
