/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.Scanner;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_LINE_LOOP;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

class ThirdGLEventListener13 implements GLEventListener {

    /**
     * Interface to the GLU library.
     */
    private GLU glu;

    /**
     * Take care of initialization here.
     */
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 640, 480);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 640, 0, 480);
    }

    /**
     * Take care of drawing here.
     */
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        /*
     * put your code here
         */
        //drawLine(gl, 0, 0, 100, 100);
        gl.glColor3f(1.0f, 0.0f, 1.0f);
        drawLine(gl);

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    private void drawLine(GL gl) {
        gl.glPointSize(3.0f);
        int ox = 300, oy = 250;

        //int [][]input={{50,50,1},{100,100,1},{50,150,1}};
        int[][] input = {{50, 50, 1}, {100, 100, 1}, {200, 100, 1}, {300, 200, 1}};
        int[][] Reflectioncor = {{1, 0, 0}, {0, -1, 0}, {0, 0, 1}};
        int[][] Reflection1cor = {{-1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        int[][] output = new int[input.length][input[0].length];

        gl.glBegin(GL.GL_LINES);
        gl.glVertex2i(ox, 0);
        gl.glVertex2i(ox, ox + oy);
        gl.glVertex2i(0, oy);
        gl.glVertex2i(ox + oy, oy);
        gl.glEnd();

//      gl.glBegin(GL.GL_TRIANGLE_FAN);
//      gl.glVertex2i(50+ox,50+oy);
//      gl.glVertex2i(100+ox,100+oy);
//      gl.glVertex2i(200+ox,100+oy);
//      gl.glVertex2i(300+ox,200+oy);
//      gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2i(input[0][0] + ox, input[0][1] + oy);
        gl.glVertex2i(input[1][0] + ox, input[1][1] + oy);
        gl.glVertex2i(input[2][0] + ox, input[2][1] + oy);
        gl.glVertex2i(input[3][0] + ox, input[3][1] + oy);
        gl.glEnd();

//    gl.glEnd();       //end drawing of points
        System.out.println("Original done");
//    System.out.println("Enter Your Choice "+"\n"+ "1.Water Image"+"2.Miror Image");
//    Scanner sc=new Scanner(System.in);
//    int choice=sc.nextInt();
        //switch(choice)
        //{
        // case 1:{
        int sum = 0;
        System.out.println("input => " + input.length);
        System.out.println("input[0] => " + input[0].length);
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < Reflectioncor.length; j++) {
                for (int k = 0; k < Reflectioncor.length; k++) {
                    sum = sum + (input[i][k] * Reflectioncor[k][j]);
                    System.out.println("The ouput =>\n " + output[i][j]);
                }

                output[i][j] = sum;
                sum = 0;
            }
        }

        System.out.println("Outside for loop");
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        System.out.println("After begin");
        gl.glVertex2i(output[0][0] + ox, output[0][1] + oy);
        gl.glVertex2i(output[1][0] + ox, output[1][1] + oy);
        gl.glVertex2i(output[2][0] + ox, output[2][1] + oy);
        gl.glVertex2i(output[3][0] + ox, output[3][1] + oy);
        System.out.println("Before end");
        gl.glEnd();
        System.out.println("After end");

        //end drawing of points
//                            break;
//        }
//        case 2:
//        {
        int sum1 = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                for (int k = 0; k < Reflection1cor.length; k++) {
                    sum1 = sum1 + (input[i][k] * Reflection1cor[k][j]);
                    System.out.print(output[i][j] + "  ");
                }

                output[i][j] = sum1;
                sum1 = 0;
            }
        }

        gl.glBegin(GL.GL_TRIANGLE_FAN);
        System.out.println("After begin");
        gl.glVertex2i(output[0][0] + ox, output[0][1] + oy);
        gl.glVertex2i(output[1][0] + ox, output[1][1] + oy);
        gl.glVertex2i(output[2][0] + ox, output[2][1] + oy);
        gl.glVertex2i(output[3][0] + ox, output[3][1] + oy);
        System.out.println("Before end");
        gl.glEnd();   //end drawing of point  
        // }
//}    
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}

public class Reflection_ori {

    public static void main(String args[]) {
        //getting the capabilities object of GL2 profile
        //final GLProfile profile=GLProfile.get(GLProfile.GL);
        GLCapabilities capabilities = new GLCapabilities();
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener13 b = new ThirdGLEventListener13();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(400, 400);
        //creating frame
        final JFrame frame = new JFrame("Basic frame");
        //adding canvas to frame
        frame.add(glcanvas);
        frame.setSize(640, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
