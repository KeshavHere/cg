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



import java.util.Stack;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author champion
 */

    class Point_2{
    int x;
    int y;
    int color;
    
    Point_2(int x,int y){
        this.x=x;
        this.y=y;
        
    }
    
    int getX(){
        return this.x;
    }
    
    int getY(){
        return this.y;
    }
    
    int getColor(){
        return this.color;
    }
    
    void setColor(int color){
        this.color=color;
    }
}
class ScanLine implements GLEventListener {
    int buffer_matrix[][]=new int[640][480];
    private GLU glu;
    
    int BACKGROUND_VALUE=0;
    int BOUNDARY_VALUE=-1;
    int POLYGON_VALUE=1;
    
    public void init(GLAutoDrawable gld) {
    GL gl = gld.getGL();
    glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glViewport(0, 640, 0, 480);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluOrtho2D(0, 640, 0, 480);
   // glu.gluPerspective(fovy, aspect, zNear, zFar);
   
   initialize_Buffer();
   
}

void initialize_Buffer(){
    for(int i=0;i<buffer_matrix.length;i++){
        for(int j=0;j<buffer_matrix[i].length;j++){
            buffer_matrix[i][j]=0;      // 0 means background value
        } 
    }
}

int IntegerFunction(float temp){
    if (temp>0){
        return (int)(temp)-1;
    }
    else{
        return (int)(temp)-1;
    }
}

 void drawline(GL gl,int  x,int y){
    
    gl.glBegin(GL.GL_POINTS);
    gl.glColor3f(0.2f, 0.1f, 0.9f);
    gl.glVertex2i(x, y);
    gl.glEnd();
    
    
    }

private void DDA_1(GL gl,int x1,int y1,int x2,int y2){
    
    int delx=Math.abs(x2-x1);
    int dely=Math.abs(y2-y1);
    int lengthOfLine=(delx > dely ? delx:dely);
   // System.out.println("Length of linw with the coord ("+x1+" "+y1+") , ("+x2+" "+y2+") = "+lengthOfLine);
    
    
    float deltaX =(float) (x2-x1)/lengthOfLine;
    float deltaY =(float) (y2-y1)/lengthOfLine;
    
    //System.out.println("Del x= "+deltaX+" Del y = "+deltaY);
    
    //intital x and y values
    float x=(float) (x1+0.5);
    float y=(float) (y1+0.5);
    
    //algorithm starts here
    
     for(int i=0;i<lengthOfLine;i++){
        //plot the pixel here 
        
        {
        int tempx=IntegerFunction(x);
        int tempy=IntegerFunction(y);
        {
           {
              
                drawline(gl, tempx,tempy); 
                buffer_matrix[tempx][tempy]=-1;
            }

           
        }
        
        x=x+deltaX;
        y=y+deltaY;
        }          
    }    
    
}
      
void floodFill_ScanLine(GL gl,int x, int y)
{
                        /*
          value =0 => Background vlaue
          value =1 => Polygon Value
          value =-1 => Boundary Value
          */
    Stack<Point_2> arr=new Stack<Point_2>();
   //seed element is => x,y
    Point_2 temp=new Point_2(x,y);
    arr.push(temp);
    
    int saveX;
    int seedX,seedY;
    int count=0;
    count=1;
    while(!arr.isEmpty())
    {
        Point_2 seed=arr.peek();
        
        x=seed.getX();
        y=seed.getY();
        
        seedX=x;
        seedY=y;
        arr.pop();
         //fill the seed pixel
        if(buffer_matrix[x][y]==0){
            gl.glBegin(GL.GL_POINTS);
            gl.glVertex2i(x, y);
            buffer_matrix[x][y]=1;          // ser to Polygon value
            gl.glEnd();
        }
        
        // save the X- coordinate of the Pixel
        saveX=x;
        
        // span to right of the seed pixel
        x=x+1;
        while(buffer_matrix[x][y]!=BOUNDARY_VALUE){
            drawline(gl, x, y);
            buffer_matrix[x][y]=POLYGON_VALUE;
            x=x+1;
        }
        
        // save the Extreme right
        int Xright=x-1;
        
        //reset  X coordinate to Seed pixel
        
        x=saveX;
        
    //fill span to the Left of Seed pixel
        x=x-1;
        while(buffer_matrix[x][y]!=BOUNDARY_VALUE){
            drawline(gl, x, y);
            buffer_matrix[x][y]=POLYGON_VALUE;
            x=x-1;
        }
        
        //save the Extreme Left Pixel
        int Xleft=x+1;
        
        // reset the value of Seed pixel to extereme left
        x=saveX;
        
        // Cheking the Above Scan Line
        x=Xleft;
        y=seedY+1;
//             System.out.println("Above -> "+x+" ,"+y);
        while(x<=Xright)
        {
            //seed the scan line above
            int PFlag=0;
            while(buffer_matrix[x][y]!=BOUNDARY_VALUE && buffer_matrix[x][y]!=POLYGON_VALUE && x<Xright)
            {
                if(PFlag==0){
                    PFlag=1;
                }
                x=x+1;
            }
            // push extreme right pixel onto stack
            if(PFlag==1)
            {
                if(x==Xright && buffer_matrix[x][y]!=BOUNDARY_VALUE  && buffer_matrix[x][y]!=POLYGON_VALUE){
                        arr.push(new Point_2(x,y));
                  }
                else  {
                    // under the range boundary pixel
                    arr.push(new Point_2(x-1,y));
                }
                count+=1;
                PFlag=0;
           }
            
            //continue checking while Span is interuupted, CASE of HOLE
            int Xenter=x;
            while((buffer_matrix[x][y]== BOUNDARY_VALUE || buffer_matrix[x][y]== POLYGON_VALUE) && x<Xright)
            {
                x=x+1;
            }
            
            if(x==Xenter)
            {
                x=x+1;
            }
        }
        // Cheking the Below Scan Line
        x=Xleft;
        y=seedY-1;
//        System.out.println("Below -> "+x+" ,"+y);
        while(x<=Xright)
        {
            //seed the scan line below
            int PFlag=0;
            while(buffer_matrix[x][y]!=BOUNDARY_VALUE && buffer_matrix[x][y]!=POLYGON_VALUE && x<Xright){
                if(PFlag==0){
                    PFlag=1;
                }
                x=x+1;
            }
            // push extreme right pixel onto stack
            if(PFlag==1)
            {
                if(x==Xright && buffer_matrix[x][y]!=BOUNDARY_VALUE  && buffer_matrix[x][y]!=POLYGON_VALUE){
                        arr.push(new Point_2(x,y));
                  }
                else{
                    // under the range boundary pixel
                    arr.push(new Point_2(x-1,y));
                }
                PFlag=0;
                count+=1;
                      
            }
            
            //continue checking while Span is interuupted, CASE of HOLE
            int Xenter=x;
            while((buffer_matrix[x][y]== BOUNDARY_VALUE || buffer_matrix[x][y]== POLYGON_VALUE) && x<Xright){
                x=x+1;
            }
            if(x==Xenter){
                x=x+1;
            }
        }
                
    }
    
    
          System.out.println("No of Push Operation needed : "+count);
          
      }

    @Override
    public void display(GLAutoDrawable drawable) {
           GL gl = drawable.getGL();
        /*   
     DDA_1(gl,100,100,200,100); 
    DDA_1(gl,200,100,200,200);
    DDA_1(gl,200,200,100,200);
    DDA_1(gl,100,200,100,100);
    
        floodFill_ScanLine(gl, 150, 150);

       */  
           int scaleFactor=20;
           //outer polygon
           DDA_1(gl,1*scaleFactor,1*scaleFactor,4*scaleFactor,1*scaleFactor);
           DDA_1(gl,4*scaleFactor,1*scaleFactor,4*scaleFactor,3*scaleFactor);
           DDA_1(gl,4*scaleFactor,3*scaleFactor,9*scaleFactor,3*scaleFactor);
           DDA_1(gl,9*scaleFactor,3*scaleFactor,9*scaleFactor,1*scaleFactor);
           DDA_1(gl,9*scaleFactor,1*scaleFactor,11*scaleFactor,1*scaleFactor);
               DDA_1(gl,11*scaleFactor,1*scaleFactor,11*scaleFactor,6*scaleFactor);
           DDA_1(gl,11*scaleFactor,6*scaleFactor,8*scaleFactor,9*scaleFactor);
           DDA_1(gl,8*scaleFactor,9*scaleFactor,1*scaleFactor,9*scaleFactor);
           DDA_1(gl,1*scaleFactor,9*scaleFactor,1*scaleFactor,1*scaleFactor);
           
           //Hole Polygon
           
           DDA_1(gl,4*scaleFactor,4*scaleFactor,9*scaleFactor,4*scaleFactor);
           DDA_1(gl,9*scaleFactor,4*scaleFactor,9*scaleFactor,5*scaleFactor);
             DDA_1(gl,9*scaleFactor,5*scaleFactor,7*scaleFactor,7*scaleFactor);
              DDA_1(gl,7*scaleFactor,7*scaleFactor,4*scaleFactor,7*scaleFactor);
               DDA_1(gl,4*scaleFactor,7*scaleFactor,4*scaleFactor,4*scaleFactor);
               
               floodFill_ScanLine(gl, 5*scaleFactor, 7*scaleFactor);


    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

public class ScanSeed {
    public static void main(String[] args) {
            //getting the capabilities object of GL2 profile
    //final GLProfile profile=GLProfile.get(GLProfile.GL);
    GLCapabilities capabilities=new GLCapabilities();
    // The canvas
    final GLCanvas glcanvas=new GLCanvas(capabilities);
    ScanLine b=new ScanLine();
    glcanvas.addGLEventListener(b);
    glcanvas.setSize(640, 480);
    //creating frame
    final JFrame frame=new JFrame("******SEES FILL"
            + "*****");
    //adding canvas to frame
    frame.add(glcanvas);
    frame.setSize(640,480);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


/*
No of Push Operation needed : 99
*/
