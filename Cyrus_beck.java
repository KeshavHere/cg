/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;


import java.util.ArrayList;
import java.util.Collections;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

class ThirdGLEventListener12 implements GLEventListener{
    private GLU glu;
    Vertex v1 = new Vertex(100,100);
    Vertex v2 = new Vertex(700,100);
    Vertex v3 = new Vertex(500,300);
    Vertex v4 = new Vertex(300,300);
    
    Edge v1v2 = new Edge(v1,v2);
    Edge v2v3 = new Edge(v2,v3);
    Edge v3v4 = new Edge(v3,v4);
    Edge v4v1 = new Edge(v4,v1);
    Edge[] edges = new Edge[4];
    
    ArrayList<Float> tl = new ArrayList<Float>();
    ArrayList<Float> tu= new ArrayList<Float>();
    
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        glu = new GLU();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glViewport(0,0,800,800);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0,800,0,800);
    }

    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(0.0f, 0.0f, 0.0f );
        drawLine(gl,v1.x,v1.y,v2.x,v2.y);
        drawLine(gl,v2.x,v2.y,v3.x,v3.y);
        drawLine(gl,v3.x,v3.y,v4.x,v4.y);
        drawLine(gl,v4.x,v4.y,v1.x,v1.y);
        gl.glColor3f(1.0f, 0.0f, 0.0f );
        clip(gl, 50, 10, 600, 300);
        clip(gl, 300, 700, 300, 100);
    }
    
    public void clip (GL gl, int x0, int y0, int x1, int y1)
    {
        tl.clear();
        tu.clear();
        int dx = x1-x0;
        int dy = y1-y0;
        edges[0] = v1v2 ;
        edges[1] = v2v3 ;
        edges[2] = v3v4;
        edges[3] = v4v1;
        System.out.println("   Normal   |   F   |   Wi   |   Wi.ni   |   D.ni   ");
        for(int i = 0; i<4; i++)
        {
            edges[i].wi[0] = x0 - edges[i].f[0];
            edges[i].wi[1] = y0 - edges[i].f[1];
            edges[i].wini = (edges[i].wi[0] * edges[i].normal[0]) + (edges[i].wi[1] * edges[i].normal[1]);
            edges[i].dni = (dx * edges[i].normal[0]) + (dy * edges[i].normal[1]);
            if (edges[i].dni > 0)
                tl.add(( -(float)edges[i].wini /(float) edges[i].dni));
            else
                tu.add( -((float)edges[i].wini /(float) edges[i].dni));
            System.out.println(edges[i].normal[0]+" "+edges[i].normal[1]+" | "+edges[i].f[0]+" "+edges[i].f[1]+" | "+edges[i].wi[0]+" "+edges[i].wi[1]+" | "+edges[i].wini+" | "+edges[i].dni+" | ");
        }
        Collections.sort(tl);
        Collections.sort(tu);
        Collections.reverse(tu);
        System.out.println("tl = "+tl.get(0));
        System.out.println("tu = "+tu.get(0));
        int fx1 = (int) (x0 + (dx*tl.get(0)));
        int fx2 = (int) (x0 + (dx*tu.get(0)));
        int fy1 = (int) (y0 + (dy*tl.get(0)));
        int fy2 = (int) (y0 + (dy*tu.get(0)));
        drawLine( gl, fx1, fy1, fx2, fy2);
    }
   
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }

    public void displayChanged(GLAutoDrawable drawable,boolean modeChanged, boolean deviceChanged) { }

    private void drawLine(GL gl,int x1,int y1,int x2,int y2)
    {
        int x,y;
        x=x1;
        y=y1;
        int dx,dy,interchange;
        int e;
        dx=Math.abs(x2-x1);
        dy=Math.abs(y2-y1);
        int s1=1,s2=1;
        if(x2-x1<0)
            s1=-1;
        if(y2-y1<0)
            s2=-1;
        if(dy>dx)
        {
            int temp=dx;
            dx=dy;
            dy=temp;
            interchange=1;
        }
        else
            interchange=0;
        e=(2*dy)-dx;
        for(int i=1;i<=dx;i++)
        {
                gl.glPointSize(0.3f);
                gl.glBegin(GL.GL_POINTS);
                gl.glVertex2i(x,y); 
                gl.glFlush();   
                while(e>0)
                {
                    if(interchange==1)
                        x=x+s1;     
                    else
                        y=y+s2;
                    e=e-(2*dx);
                }
                if(interchange==1)
                    y=y+s2;
                else
                    x=x+s1;
                e=e+(2*dy);
        }
        gl.glEnd();
    }

    
    public void dispose(GLAutoDrawable arg0){

    }
}

class Vertex{
    int x, y;
    Vertex(int x, int y){
        this.x=x;
        this.y=y;
    }
}

class Edge{
    Vertex a1,a2;
    int [] normal = new int[2];
    int [] f = new int[2];
    int [] wi = new int[2];
    int wini;
    int dni;
    Edge(Vertex a1, Vertex a2){
        this.a1=a1;
        this.a2=a2;
        normal[0] = (a2.y - a1.y);
        normal[1] = - (a2.x - a1.x);
        f[0] = a1.x;
        f[1] = a1.y;
    }
}

public class Cyrus_beck{
    public static void main(String args[]){
        GLCapabilities capabilities=new GLCapabilities();
        final GLCanvas glcanvas=new GLCanvas(capabilities);
        ThirdGLEventListener12 b=new ThirdGLEventListener12();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(1000, 1000);
        final JFrame frame=new JFrame("Basic frame");
        frame.add(glcanvas);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

