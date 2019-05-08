/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

/**
 *
 * @author Rakshita Joshi
 */

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

class ThirdGLEventListener11 implements GLEventListener {
private GLU glu;
int[] origin={320,240};
    
public void init(GLAutoDrawable gld) {
    GL gl = gld.getGL();
    glu = new GLU();
    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); //background color
    gl.glViewport(0,0,640,480);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluOrtho2D(0,640,0,480);
}

public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    int[][]controlpts={{100,200},{150,300},{200,200},{250,150},{300,100},{350,200}};
    
    gl.glPointSize(6.0f);
    gl.glColor3f(1.0f, 0.0f, 0.0f);
    gl.glBegin(GL.GL_POINTS);
    for(int i=0;i<controlpts.length;i++)
        gl.glVertex2i(controlpts[i][0], controlpts[i][1]);
    gl.glEnd();
    gl.glPointSize(2.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glColor3f(0.0f, 0.0f, 0.0f);
    pu(gl,controlpts);
    gl.glEnd();
}


public void pu(GL gl,int [][]controlpts){
    int n=controlpts.length-1;
    double sumx=0,sumy=0;
    for(double u=0;u<=1;u=u+0.005){
        for(int i=0;i<controlpts.length;i++){
            //System.out.println("i 0 "+controlpts[i][0]);
            sumx=sumx+controlpts[i][0]*bin(n,i,u);
            //System.out.println("i 1 "+controlpts[i][1]);
            sumy=sumy+controlpts[i][1]*bin(n,i,u);
        }
        
        System.out.println("sumx "+sumx); //x(u)
        System.out.println("sumy "+sumy); //y(u)
        System.out.println();
        gl.glVertex2d(sumx,sumy);
        sumx=0;sumy=0;
    }
}

public double bin(int n,int i,double u){
    //System.out.println("");
    return c(n,i)*Math.pow(u,i)*Math.pow(1-u,n-i);
}

public double c(int n,int i){
    return factorial(n)/(factorial(n-i)*factorial(i));
}

public double factorial(int n){
    if(n==0)
        return 1;
    else
        return n*factorial(n-1);
}

public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
}

public void displayChanged(GLAutoDrawable drawable,
        boolean modeChanged, boolean deviceChanged) {
}

public void dispose(GLAutoDrawable arg0)
{
}
}
public class BeizerCurve
{
public static void main(String args[])
{
    GLCapabilities capabilities=new GLCapabilities();
    final GLCanvas glcanvas=new GLCanvas(capabilities);
    ThirdGLEventListener11 b=new ThirdGLEventListener11();
    glcanvas.addGLEventListener(b);
    glcanvas.setSize(400, 400);
    final JFrame frame=new JFrame("Basic frame");
    frame.add(glcanvas);
    frame.setSize(640,480);
    frame.setVisible(true);
}
}