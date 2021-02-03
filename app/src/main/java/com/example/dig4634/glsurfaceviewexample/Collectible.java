package com.example.dig4634.glsurfaceviewexample;

import gl.modeltypes.ShadedTexturedModel;

public class Collectible extends ShadedTexturedModel {

    public boolean shown=true;

    public float segments=0;

    public float positionX=0;
    public float positionY=0;
    public float positionZ=0;

    public float speedX=0;
    public float speedY=0;
    public float speedZ=0;

    public float accelarationX=0;
    public float accelarationY=0;
    public float accelarationZ=0;

    public float angleX=0;
    public float angleZ=0;

    Collectible(){
        super();

        setXYZ(new float[]{
                /*
                0,0,0,//lower left corner (id: 0)
                1,0,0,//lower right corner (id: 1)
                0,1,0//upper left corner (id: 2)
                */
                // 5 points in a pyramid:
                0,0.5f,0,       // Pyramid tip          (id: 0)
                -0.5f,0,-0.5f,  // Top left of base     (id: 1)
                0.5f,0,-0.5f,   // Top right of base    (id: 2)
                -0.5f,0,0.5f,   // Bottom left of base  (id: 3)
                0.5f,0,0.5f     // Bottom right of base (id: 4)
        });

        setTriangles(new short[]{
                //0,1,2 //my first triangle
                // 6 triangles in a pyramid
                1,0,2,  // North face
                2,0,4,  // East face
                4,0,3,  // South face
                3,0,1,  // West face
                3,1,2,  // Top left of base
                2,4,3   // Bottom right of base
        });

        setUV(new float[]{
                0.5f,0.5f, //This is the pixel of the texture that will be attached to vertex (id:0)
                0,0, //This is the pixel of the texture that will be attached to vertex (id:1)
                1,0, //This is the pixel of the texture that will be attached to vertex (id:2)
                0,1, //This is the pixel of the texture that will be attached to vertex (id:3)
                1,1, //This is the pixel of the texture that will be attached to vertex (id:4)
        });

        setNormals(new float[]{
                0,1,0,
                -1,-1,-1,
                1,-1,-1,
                -1,-1,1,
                1,-1,1,
        });
    }

    public void simulate(float perSec){

        angleX+=20*perSec;
        angleZ+=20*perSec;

        speedX+=accelarationX*perSec;
        speedY+=accelarationY*perSec;
        speedZ+=accelarationZ*perSec;

        positionX+=speedX*perSec;
        positionY+=speedY*perSec;
        positionZ+=speedZ*perSec;

        if(positionZ>-2){
            positionZ=-2-segments*4;
            shown=true;
        }


        localTransform.reset();
        localTransform.translate(positionX,positionY,positionZ);
        localTransform.rotateX(angleX);
        localTransform.rotateZ(angleZ);
        localTransform.scale(0.5f,0.5f,0.5f);
        localTransform.updateShader();
    }
}
