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

class ThirdGLEventListener4 implements GLEventListener {

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
                
            for (int i = 0; i <= 6; i++) 
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
      
        gl.glBegin(GL.GL_POINTS);

        double s1,s2,temp,delx,dely,interchange,e1,x=x1,y=y1;
        delx = abs(x2-x1);
        dely = abs(y2-y1);
        
        if(x2-x1>0)
            s1 = 1;
        else if(x2-x1 <0)
            s1 = -1;
        else
            s1=0;
        
        if(y2-y1>0)
            s2 = 1;
        else if(y2-y1<0)
            s2 = -1;
        else
            s2=0;
        //interchanging
        if(dely>delx)
        {
          temp = delx;
          delx = dely;
          dely = temp;
          interchange = 1;
        }
        else
        {
          interchange = 0;
        }
        //initialise error term
        e1 = (2*dely) -delx;
        int count =0;
        //main loop
        for (int i = 0; i < delx; i++) 
        {
            gl.glVertex2d(x,y);
            while(e1>0)
            {
                if(interchange==1)
                    x = x + s1;
                else
                    y = y+s2;

                e1 = e1-(2*delx);
            }
           
            if(interchange ==1)
               y = y+s2;
            else
               x = x+s1;
           
            e1 = e1 + (2*dely);
        }
        
        gl.glEnd();//end drawing of points

    }

    public void dispose(GLAutoDrawable arg0) 
    {

    }
}

public class BrehConcentricHex
{

    public static void main(String args[]) 
    {
        //getting the capabilities object of GL2 profile
        //final GLProfile profile=GLProfile.get(GLProfile.GL);
        GLCapabilities capabilities = new GLCapabilities();
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener4 b = new ThirdGLEventListener4();
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
