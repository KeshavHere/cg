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
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;



/**
 * Clipping_Cohen_Sutherland.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class cohen  {

    public static void main(String[] args) {
        //getting the capabilities object of GL2 profile
    //final GLProfile profile=GLProfile.get(GLProfile.GL);
    GLCapabilities capabilities=new GLCapabilities();
    // The canvas
    final GLCanvas glcanvas=new GLCanvas(capabilities);
    ThirdGLEventListener13 b=new ThirdGLEventListener13();
    glcanvas.addGLEventListener(b);
    glcanvas.setSize(640, 400);
    //creating frame
    final JFrame frame=new JFrame("Basic frame");
    //adding canvas to frame
    frame.add(glcanvas);
    frame.setSize(640,480);
    frame.setVisible(true);
    }
}

class point{
    float x,y;
    int[] encode;
    point(float a,float b){
        x=a;
        y=b;
    }
    point(point p){
        x=p.x;
        y=p.y;
        encode=p.encode;
    }
}

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
    gl.glViewport(0,0,640,480);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluOrtho2D(0,640,0,480);
}

public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
  //  gl.glClear(GL.GL_COLOR_BUFFER_BIT);
  //  gl.glColor3f(1.0f, 0.0f, 0.0f );
    drawLine(gl);
}

public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
}

public void displayChanged(GLAutoDrawable drawable,
        boolean modeChanged, boolean deviceChanged) {
}

private void drawLine(GL gl) {
    gl.glPointSize(1.0f);
    gl.glColor3f(1.0f,0.0f,0.0f);
    //rectangular window
    gl.glBegin(GL.GL_QUADS);
    gl.glVertex2i(100, 100);
    gl.glVertex2i(200, 100);
    gl.glVertex2i(200, 200);
    gl.glVertex2i(100, 200);
    gl.glEnd();
  //  ndc(gl,new point(120,140),new point(150,150));
    gl.glColor3f(0.0f,0.0f,1.0f);
    clip(new point(120,140),new point(150,150),gl);
    clip(new point(80,80),new point(230,230),gl);
    clip(new point(80,80),new point(90,90),gl);
   
}

int[] encode(float x,float y){
    int winLeftX=100,winBottomY=100,winRightX=200,winTopY=200;
    int encode[]=new int[4];
    encode[0]=0;
    encode[1]=0;
    encode[2]=0;
    encode[3]=0;

      if(x<winLeftX){
          encode[3]=1;
      }
      if(x>winRightX){
          encode[2]=1;
      }
      if(y>winTopY){
          encode[0]=1;
      }
      if(y<winBottomY){
          encode[1]=1;
      }
    return encode; 
    }

  void clip(point p1,point p2,GL gl){
      p1.encode=encode(p1.x,p1.y);
      p2.encode=encode(p2.x,p2.y);
      int and[]=new int[4];
      float m;
      int leftX=100;
      int rightX=200;
      int topY=200;
      int bottomY=100;
      System.out.println(p1.encode.toString());
      System.out.println(p2.encode.toString());
      System.out.println("Encode of p1 "+p1.encode[0]+p1.encode[1]+p1.encode[2]+p1.encode[3]);
      System.out.println("Encode of p2 "+ p2.encode[0]+p2.encode[1]+p2.encode[2]+p2.encode[3]);
      if(p1.encode[0]==0 && p1.encode[1]==0 && p1.encode[2]==0 && p1.encode[3]==0 && p2.encode[0]==0 && p2.encode[1]==0 && p2.encode[2]==0 && p2.encode[3]==0 ){
        System.out.println("Totally Visible");
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(p1.x, p1.y);
        gl.glVertex2f(p2.x, p2.y);
        gl.glEnd();
        System.out.println("Clip points are  "+p1.x+","+p1.y+" and "+p2.x+","+p2.y);
       }
      else{
          for(int i=0;i<4;i++){
              and[i]=p1.encode[i] & p2.encode[i];
              System.out.println("And  "+and[i]);
          }
          if(and[0]==0 && and[1]==0 && and[2]==0 && and[3]==0 ){
              System.out.println("Partially Visible");
              //check p1 outside
             // System.out.println(!p1.encode.toString().equals("0000"));
                  if(p1.encode[3]==1){
                  m=(p2.y-p1.y)/(p2.x-p1.x);
                  p1.y=m*(leftX-p1.x)+p1.y;
                  p1.x=leftX;
                  clip(p1,p2,gl);
                 }
               else if(p1.encode[2]==1){
                      m=(p2.y-p1.y)/(p2.x-p1.x);
                      p1.y=m*(rightX-p1.x)+p1.y;
                      p1.x=rightX;
                      clip(p1,p2,gl);
                  }
               else if(p1.encode[1]==1){
                      m=(p2.y-p1.y)/(p2.x-p1.x);
                      p1.x=(1/m)*(bottomY-p1.y)+p1.x;
                      p1.y=bottomY;
                      clip(p1,p2,gl);
                  }
               else if( p1.encode[0]==1){
                     m=(p2.y-p1.y)/(p2.x-p1.x);
                     p1.x=(1/m)*(topY-p1.y)+p1.x;
                     p1.y=topY;
                     clip(p1,p2,gl);
                  }   
             //p1 not outside hence swap
              else{
                  point temp=new point(p2.x,p2.y);
                  temp.encode=p2.encode;
                  System.out.println(temp.encode.toString());
                  p2=p1;
                  p1=temp;
                  clip(p1,p2,gl);
              }
           }
          else{
              System.out.println("Totally Invisible");
          }
     }
   
  }

public void dispose(GLAutoDrawable arg0)
{
    
}
}
