/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;


import static java.lang.Math.abs;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
class point2
{
  int x =0, y =0,flag;
   point2()
  {
    x = 0;
    y = 0;
    flag =0;
  }
  point2(int a,int b,int c)
  {
    x = a;
    y= b;
    flag=c;
  }
}

class ThirdGLEventListener10 implements GLEventListener {

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
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        double x = 200,y=100,side = 100;
        int count =0;
        int[][] setit = new int[640][480];
      
        setit = drawLine(gl,100,100,100,110,setit);
        setit = drawLine(gl,100,110,110,110,setit);
        setit=  drawLine(gl,110,110,110,100,setit);
        setit=  drawLine(gl,110,100,100,100,setit);
        //xmax =110 ymax =110
        //all the points have been added to the Arraylist
        point2 seed = new point2(105,105,0);
   
        System.out.println("Done with printing p");
        seedfill2(gl,setit,seed);
    }

    public void seedfill2(GL gl,int[][] setit,point2 seed)
    {
        Stack<point2> stack = new Stack<point2>();
        stack.push(seed);

        System.out.println("Seed pixel\n x => "+seed.x+" y => "+seed.y);
        while(!stack.isEmpty())
        {
                System.out.println("In while");
                point2 popping = stack.pop();
                int x = popping.x;
                int y = popping.y;

                gl.glBegin(GL.GL_POINTS);
                gl.glVertex2d(x,y);
                gl.glEnd();
                
                point2 xx = new point2(x-1,y,0);
                if(setit[xx.x][xx.y] != 1 &&setit[xx.x][xx.y]!=2)
                {
                    setit[xx.x][xx.y] = 2;
                    System.out.println("pushing \n x => "+xx.x+" y => "+xx.y);
                    stack.push(xx);
                }
                 xx = new point2(x+1,y,0);
                if(setit[xx.x][xx.y] != 1 &&setit[xx.x][xx.y]!=2)
                {
                    setit[xx.x][xx.y] = 2;
                    System.out.println("pushing \n x => "+xx.x+" y => "+xx.y);
                    stack.push(xx);
                }
                 xx = new point2(x,y-1,0);
                if(setit[xx.x][xx.y] != 1 &&setit[xx.x][xx.y]!=2)
                {
                    setit[xx.x][xx.y] = 2;
                    System.out.println("pushing \n x => "+xx.x+" y => "+xx.y);
                    stack.push(xx);
                }
                xx = new point2(x,y+1,0);
                if(setit[xx.x][xx.y] != 1 &&setit[xx.x][xx.y]!=2)
                {
                    setit[xx.x][xx.y] = 2;
                    System.out.println("pushing \n x => "+xx.x+" y => "+xx.y);
                    stack.push(xx);
                }
               System.out.println("==============================================="); 
        }
    }
    
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    private int[][] drawLine(GL gl,double x1,double y1,double x2, double y2,int[][] setit) {
        //gl.glPointSize(3.0f);
        gl.glBegin(GL.GL_POINTS);// begin plotting points

//        System.out.println("enter the x and y of first coordinate");
//        Scanner sc = new Scanner(System.in);
//        double x1 = sc.nextDouble();
//        double y1 = sc.nextDouble();
//        System.out.println("enter the x and y of first coordinate");
//        double x2 = sc.nextInt();
//        double y2 = sc.nextDouble();
        
      
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
        
           setit[(int)floor(x)][(int)floor(y)] =1;
           gl.glVertex2d((int)floor(x),(int)floor(y));
            
            x = delx + x;
            y = dely + y;
            i = i+1;
        }
        
        gl.glEnd();//end drawing of points

       return setit; 
    }

    public void dispose(GLAutoDrawable arg0) 
    {

    }
}

public class seedfill_ad
{

    public static void main(String args[]) 
    {
        //getting the capabilities object of GL2 profile
        //final GLProfile profile=GLProfile.get(GLProfile.GL);
        GLCapabilities capabilities = new GLCapabilities();
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener10 b = new ThirdGLEventListener10();
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
