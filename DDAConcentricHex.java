/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;


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

class ThirdGLEventListener3 implements GLEventListener {

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

        gl.glColor3f(1.0f, 0.0f, 0.0f);

        double x = 200,y=100,side = 100;
        int count =0;
        while(x>0 && y<300 &&side>0) 
        {
            if(count == 1)
                gl.glPointSize(3.0f);
            for (int i = 1; i <= 6; i++) 
            {
                drawLine(gl,x+side*Math.cos(i*2*Math.PI/6),y+side*Math.sin(i*2*Math.PI/6),
                         x+side*Math.cos((i+1)*2*Math.PI/6),y+side*Math.sin((i+1)*2*Math.PI/6),count);
               
            }
            count++;
            side -= 20;
       }

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    private void drawLine(GL gl,double x1,double y1,double x2, double y2,int cas) {
        //gl.glPointSize(3.0f);
        gl.glBegin(GL.GL_POINTS);// begin plotting points

//        System.out.println("enter the x and y of first coordinate");
//        Scanner sc = new Scanner(System.in);
//        double x1 = sc.nextDouble();
//        double y1 = sc.nextDouble();
//        System.out.println("enter the x and y of first coordinate");
//        double x2 = sc.nextInt();
//        double y2 = sc.nextDouble();
        
       if(cas == 4)
           gl.glPointSize(3.0f);
   
        double length;
        if(abs(x2-x1)>= abs(y2-y1))
            length = abs(x2-x1);
        else
            length = abs(y2-y1);
        
        double delx = (x2-x1)/length;
        double dely = (y2-y1)/length;
        
        int signx,signy;
        
        if(delx > 0)
            signx = 1;
        else if(delx<0)
            signx = -1;
        else
            signx = 0;
        
       
        if(dely > 0)
            signy = 1;
        else if(dely<0)
            signy = -1;
        else
            signy = 0;
        
        
        double x = x1 + (0.5)*signx;
        double y = y1 + (0.5)*signy;
        
        int i =1;
        while(i <= length)
        {
            if(cas == 2)
            {
            if(i%4 != 0)
            gl.glVertex2d((int)floor(x),(int)floor(y));
            }
            
            else if(cas == 3)
            {
                if(i%4 == 0)
                gl.glVertex2d((int)floor(x),(int)floor(y));
            }
            
            else if(cas == 1)
            {
                gl.glPointSize(3.0f);
                gl.glVertex2d((int)floor(x),(int)floor(y));
            }  
            else
                 gl.glVertex2d((int)floor(x),(int)floor(y));
            
            x = delx + x;
            y = dely + y;
            i = i+1;
        }
        
        gl.glEnd();//end drawing of points

        
    }

    public void dispose(GLAutoDrawable arg0) 
    {

    }
}

public class DDAConcentricHex
{

    public static void main(String args[]) 
    {
        //getting the capabilities object of GL2 profile
        //final GLProfile profile=GLProfile.get(GLProfile.GL);
        GLCapabilities capabilities = new GLCapabilities();
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener3 b = new ThirdGLEventListener3();
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
