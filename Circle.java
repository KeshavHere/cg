package org.yourorghere;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import java.util.Scanner;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

class ThirdGLEventListener5 implements GLEventListener {

    private GLU glu;

    
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
         Scanner sc = new Scanner(System.in);
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        double h=120,k=300,r=100;
        
//        System.out.println("enter the x coordinate of center");
//        h = sc.nextInt();
//        System.out.println("enter the y coordinate of center");
//        k = sc.nextInt();
//        System.out.println("Enter the radius of the circle");
//        r = sc.nextInt();
          gl.glColor3f(0.0f, 0.0f, 1.0f);
          drawLine(gl,h,k,r);
                    gl.glColor3f(1.0f, 1.0f, 1.0f);
          drawLine(gl,h+200,k,r);
                    gl.glColor3f(1.0f, 0.0f, 0.0f);
          drawLine(gl,h+400,k,r);
                    gl.glColor3f(0.0f, 1.0f, 1.0f);
          drawLine(gl,h+100,k-100,r);
                    gl.glColor3f(0.0f, 1.0f, 0.0f);
          drawLine(gl,h+300,k-100,r);
          
  
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    private void drawLine(GL gl,double h,double k,double r) 
    {
        //h and k are the centers.
        gl.glBegin(GL.GL_POINTS);

        int xi = 0;
        double yi = r;
        
        double deli ;
        deli = 2*(1-r);
        
        while(yi >= 0)
        {
           gl.glVertex2d(xi+h,yi+k);
           gl.glVertex2d(-xi+h,yi+k);
           gl.glVertex2d(xi+h,-yi+k);
           gl.glVertex2d(-xi+h,-yi+k);
           if(deli< 0)
           {
               double delta = 2*deli + 2*yi-1;
               if(delta <= 0)
               {
                    // mh(xi,yi,deli);
                    xi = xi + 1;
                    deli = deli +2*xi +1;
                }
               else
               { //md(xi,yi,deli);
                 xi = xi + 1;
                 yi = yi-1;
                 deli = deli + (2*xi) -(2*yi) + 2;
               }
           }
           else if(deli > 0)
           {
               double deltadash = (2*deli) - (2*xi) -1;
               if(deltadash <= 0)
                { //md(xi,yi,deli);
                  xi = xi + 1;
                  yi = yi-1;
                  deli = deli + (2*xi) -(2*yi) + 2;
                }
               else 
               {  
                   //mv(xi,yi,deli);
                   yi =yi -1;
                   deli = deli-(2*yi)+1;
               }
               
           }
           else if(deli == 0)
            {      
                   //md(xi,yi,deli);
                   xi = xi + 1;
                   yi = yi-1;
                   deli = deli + (2*xi) -(2*yi) + 2;
            }
        }
        
        gl.glEnd();//end drawing of points

    }
    
    

    
    public void dispose(GLAutoDrawable arg0) 
    {

    }
}

public class Circle
{

    public static void main(String args[]) 
    {
        //getting the capabilities object of GL2 profile
        //final GLProfile profile=GLProfile.get(GLProfile.GL);
        GLCapabilities capabilities = new GLCapabilities();
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener5 b = new ThirdGLEventListener5();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(400, 400);
        //creating frame
        final JFrame frame = new JFrame("Basic frame");
        //adding canvas to frame
        frame.add(glcanvas);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}

