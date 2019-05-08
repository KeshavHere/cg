/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.util.Scanner;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLCapabilities;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;  

class Coordinate
{
    int x,y;

    public Coordinate() {
    }
    

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    Boolean[] code = {true,true,true,true};
}

class ThirdGLEventListener implements GLEventListener {
private GLU glu;
GL gl;

/**
 * Take care of initialization here.
 */
int visibility (Coordinate p1,Coordinate p2)
{
int i,flag=0;
for(i=0;i<4;i++)
{
    if((p1.code[i])||(p2.code[i]))
    flag=1;
}
if(flag==0)
return(0); // completely visible
for(i=0;i<4;i++)
{
if(p1.code[i]&p2.code[i])
flag=0;
}
if(flag==0)
return(1);
return(2);
}

public static int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix, int r1, int c1, int c2) {
        int[][] product = new int[r1][c2];
        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }


   public void drawMatrix(int[][] mat,int no_rows)
    {
        int i;
        for(i =0;i<no_rows-1;i++)
        {
            drawLine(gl,500+mat[i][0],500+mat[i][1],500+mat[i+1][0],500+mat[i+1][1]);
        }
        drawLine(gl,500+mat[i][0],500+mat[i][1],500+mat[0][0],500+mat[0][1]); 
    }
 

Coordinate setcode(Coordinate p, int[] poly)
{
    //int[] poly = {150,100,450,400}; 
    Coordinate ptemp = new Coordinate();
    if(p.y<=poly[1])
    ptemp.code[0]=true; 
    else
    ptemp.code[0]=false;
    if(p.y>=poly[3])
    ptemp.code[1]=true; 
    else
    ptemp.code[1]=false;
    if (p.x>=poly[2])
    ptemp.code[2]=true; 
    else
    ptemp.code[2]=false;
    if (p.x<=poly[0])      
    ptemp.code[3]=true;
    else
    ptemp.code[3]=false;
    ptemp.x=p.x;
    ptemp.y=p.y;
    return(ptemp);
}



void midsub(GL gl,Coordinate p1,Coordinate p2, int[] poly)
{
 Coordinate mid =  new Coordinate();
 int v;
 p1=setcode(p1,poly);
 p2=setcode(p2,poly);//dono ka visibility nikaal lia.
 v=visibility(p1,p2);
 switch(v)
  {
  case 0:  /* Line completely visible */
    drawLine(gl,p1.x,p1.y,p2.x,p2.y);
//    Coordinate[] c = new Coordinate[2];
//    c[0] = new Coordinate(p1.x,p1.y);
//    c[1] = new Coordinate(p2.x,p2.y);
//    if(p1.x == p2.x && p1.y==p2.y)
//        return c;
  case 1:  /* Line completely invisible */
    break;
  case 2:  /* line partly visible */
    mid.x = p1.x + (p2.x-p1.x)/2;
    mid.y = p1.y + (p2.y-p1.y)/2;
    midsub(gl,p1,mid,poly);
    mid.x = mid.x+1;
    mid.y = mid.y+1;
    midsub(gl,mid,p2,poly);
    break;
  }
}

public void init(GLAutoDrawable gld) {
    GL gl = gld.getGL();
    this.gl = gl;
    glu = new GLU();

    
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glViewport(0,0,2500,1500);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluOrtho2D(0,2500,0,1500);
}

/**
 * Take care of drawing here.
 */
public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glColor3f(1.0f, 0.0f, 0.0f );
    Scanner s = new Scanner(System.in);
    Coordinate p1,p2,ptemp;
    drawPolygon(gl,150,100,450,400);
    gl.glColor3f(1.0f, 1.0f, 0.0f );
    System.out.println("\n\nENTER END-POINT 1 (x,y):");
    p1 = new Coordinate();
    p2 = new Coordinate();
    p1.x=s.nextInt();
    p1.y=s.nextInt();
    System.out.println("\nENTER END-POINT 2 (x,y):");
    p2.x=s.nextInt();
    p2.y=s.nextInt();
    int[] poly = {150,100,450,400};
//    drawLine(gl,p1.x,p1.y,p2.x,p2.y);
    midsub(gl,p1,p2,poly);

    gl.glColor3f(1.0f, 0.5f, 0.5f );
    drawLine(gl, 500, 0, 500, 1500);
    
    int[][] firstMatrix = {{150,100,1}, {450,400,1}, {p1.x,p1.y,1}, {p2.x,p2.y,1}};
    int[][] secondMatrix = { {4,0,0}, {0,3,0},  {0,0,2} }; //X-AXIS

    // Mutliplying Two matrices
    int[][] product = multiplyMatrices(firstMatrix, secondMatrix, 4, 3, 3);
    gl.glColor3f(0.0f, 1.0f, 0.0f );
    
    drawPolygon(gl,product[0][0],product[0][1],product[1][0],product[1][1]);
    int[] poly2 = {product[0][0],product[0][1],product[1][0],product[1][1]};
    gl.glColor3f(0.4f, 0.0f, 1.0f );
    midsub(gl,new Coordinate(product[2][0], product[2][1]), new Coordinate(product[3][0], product[3][1]),poly2);

//    drawMatrix(firstMatrix,4);            
}

public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
}

public void displayChanged(GLAutoDrawable drawable,
        boolean modeChanged, boolean deviceChanged) {
}

private void drawLine(GL gl,int h,int k,int r,int q) {
    //gl.glPointSize(3.0f);
      gl.glBegin(GL.GL_LINES);
      gl.glVertex2i(h, k);
      gl.glVertex2i(r, q);
    gl.glEnd();
}
private void drawPolygon(GL gl,int h,int k,int r,int q) {
    gl.glPointSize(3.0f);
    drawLine(gl,h,k,h,q);
    drawLine(gl,r,k,r,q);
    drawLine(gl,h,k,r,k);
    drawLine(gl,h,q,r,q);
}


public void dispose(GLAutoDrawable arg0)
{
    
}
}
public class midpoint
{
public static void main(String args[])
{
    //getting the capabilities object of GL2 profile
    //final GLProfile profile=GLProfile.get(GLProfile.GL);
    GLCapabilities capabilities=new GLCapabilities();
    // The canvas
    final GLCanvas glcanvas=new GLCanvas(capabilities);
    ThirdGLEventListener b=new ThirdGLEventListener();
    glcanvas.addGLEventListener(b);
    glcanvas.setSize(2500, 1000);
    //creating frame
    final JFrame frame=new JFrame("Basic frame");
    //adding canvas to frame
    frame.add(glcanvas);
    frame.setSize(2500,1000);
    frame.setVisible(true);
}
}