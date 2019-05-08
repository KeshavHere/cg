/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class prcat7 implements GLEventListener {

    int tx=1;
    int ty=0;
    int tz=0;
    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new prcat7());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        System.out.println(width);
        System.out.println(height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        int choice;
        Scanner in =new Scanner(System.in);
        System.out.println("Enter choice");
        choice=in.nextInt();
        if(choice==1)
            glu.gluPerspective(45.0f, h, 0.0, 20.0);
        else    
            gl.glOrtho(-10, 10,-10, 10, -10, 10);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        gl.glRotatef(45.0f, 1.0f, 1.0f, 0);
        
        gl.glBegin(GL.GL_LINES);
            gl.glColor3f(0.5f,0.75f,0.8f);
            gl.glVertex3f(-10.0f,0.0f,0.0f);
            gl.glVertex3f(10.0f,0.0f,0.0f);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
            gl.glColor3f(0.5f,0.5f,0.8f);
            gl.glVertex3f(0.0f,10.0f,0.0f);
            gl.glVertex3f(0.0f,-10.0f,0.0f);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
            gl.glColor3f(0.5f,0.75f,0.0f);
            gl.glVertex3f(0.0f,0.0f,10.0f);
            gl.glVertex3f(0.0f,0.0f,-10.0f);
        gl.glEnd();
        
        gl.glTranslatef(tx, ty, tz);
        gl.glRotatef(45.0f, 0, 1, 0);

        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(1.0f, 0.0f, 0.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, -1.0f, -1.0f);  // Top Left
            gl.glVertex3f(-1.0f, -1.0f, 1.0f);   // Top Right
            gl.glVertex3f(1.0f, -1.0f, 1.0f);  // Bottom Right
            gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(0.0f, 1.0f, 0.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(1.0f, -1.0f, 1.0f);  // Top Left
            gl.glVertex3f(1.0f, -1.0f, -1.0f);   // Top Right
            gl.glVertex3f(1.0f, 1.0f, -1.0f);  // Bottom Right
            gl.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 1.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, 1.0f, -1.0f);  // Top Left
            gl.glVertex3f(1.0f, 1.0f, -1.0f);   // Top Right
            gl.glVertex3f(1.0f, 1.0f, 1.0f);  // Bottom Right
            gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(1.0f, 0.0f, 1.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, 1.0f, -1.0f);  // Top Left
            gl.glVertex3f(1.0f, 1.0f, -1.0f);   // Top Right
            gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Bottom Right
            gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(0.0f, 1.0f, 1.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Top Left
            gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Top Right
            gl.glVertex3f(1.0f, 1.0f, 1.0f);  // Bottom Right
            gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(1.0f, 1.0f, 1.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Top Left
            gl.glVertex3f(-1.0f, 1.0f, 1.0f);   // Top Right
            gl.glVertex3f(-1.0f, 1.0f, -1.0f);  // Bottom Right
            gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
}
