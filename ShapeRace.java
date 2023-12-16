//NAVAMI SHENOY
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;

//Infinite Shape Race - restarts after the end of the race
//Moving obstacles block the path of the shapes; if a shape
//collides with an obstacle it has to start from the beginning 
//of the race

public class ShapeRace extends JComponent{
    static JFrame f;
    //circle
    static int x = 100; 
    static int y = 100;
    //square 
    static int leftX = 100;
    static int topY = 600;
    
    static int finish_line_x = 1100; //finish line coordinates
    static boolean game_over = true; 

    //boolean values to check if the shapes are colliding with the obstacles
    static boolean circle_collision = false;
    static boolean square_collision = false;

    //boolean values to check if the obstacles are moving up (true)
    //or down (false)
    static boolean up1 = true;
    static boolean up2 = true;
    static boolean up3 = true;
    static boolean up4 = false;

    //x and y coordinates for the obstacles 
    static int[] x1 = {230,235,255,235,230,225,205,225}; 
    static int[] y1 = {110,130,135,140,160,140,135,130};
    static int[] x2 = {530,535,555,535,530,525,505,525};
    static int[] y2 = {510,530,535,540,560,540,535,530};
    static int[] x3 = {730,735,755,735,730,725,705,725}; 
    static int[] y3 = {310,330,335,340,360,340,335,330};
    static int[] x4 = {1030,1035,1055,1035,1030,1025,1005,1025}; 
    static int[] y4 = {410,430,435,440,460,440,435,430};
    

    public void paintComponent(Graphics g){
        setSize(f.getSize());

        Graphics2D g2 = (Graphics2D) g;

        //draw the circle
        g2.setColor(Color.RED);
        Shape circle = new Ellipse2D.Double(x, y, 25, 25);
        g2.draw(circle);
        g2.fill(circle);

        //draw the square
        double width = 25;
        double height = 25;
        Rectangle2D square = new Rectangle2D.Double(leftX, topY, width, height);
        g2.setPaint(Color.BLUE);
        g2.fill(square);

        //draw the finish line
        Stroke stroke = new BasicStroke(3f);
        g2.setStroke(stroke); //set line thickness
        g2.setColor(Color.BLACK);
        g2.drawLine(finish_line_x, 30, finish_line_x, 730); 


        //add obstacles  
        Shape ob1 = new Polygon(x1, y1, x1.length);
        g2.fill(ob1);
        Shape ob2 = new Polygon(x2, y2, x2.length);
        g2.fill(ob2);
        Shape ob3 = new Polygon(x3, y3, x3.length);
        g2.fill(ob3);
        Shape ob4 = new Polygon(x4, y4, x4.length);
        g2.fill(ob4);

        //if a shape collides with the obstacles, take it back
        //to the start line
        if(collision(circle, ob1) || collision(circle, ob2)
         || collision(circle, ob3) || collision (circle, ob4)){
            circle_collision = true;
        }

        if(collision(square, ob1) || collision(square, ob2) 
        || collision(square, ob3) || collision (square, ob4)){
            square_collision = true;
        }

        //print winner message
        if(x >= finish_line_x & leftX < x){
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("The Circle won!!", 400, 50);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString("Restarting game in 5s...", 500, 80);  
        }

        if(leftX >= finish_line_x & leftX > x){
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("The Square won!!", 400, 50);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString("Restarting game in 5s...", 500, 80);
        }

    }

    public static void main(String[] args){
        f = new JFrame();
        //add component to the frame
        ShapeRace shapes = new ShapeRace();
        f.add(shapes);
    
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Infinite Shape Race!");
        f.setSize(1200, 800);

        //loop keeps moving the shapes and the obstacles
        while(true){
            move(f);
        }

    }


    //changes coordinates of a polygon by adding or subtracting by n
    public static int[] multiply(int[] coords, int n, boolean add){
        for(int i = 0; i < 8; i++){
            if(add){
                coords[i] = coords[i] + n;
            } else {
                coords[i] = coords[i] - n;
            }  
        }
        return coords;
    }

    //detect collision
    public static boolean collision(Shape shape1, Shape shape2){
        return shape1.getBounds2D().intersects(shape2.getBounds2D());
    }



    //move: makes the shapes move
    public static void move(JFrame f){
        try{
            TimeUnit.MILLISECONDS.sleep(10);
            if(game_over){
                Random speed = new Random();
                //move circle
                if(circle_collision == false){
                    x= x + speed.nextInt(6);
                } else {
                    x = 100;
                    circle_collision = false;
                }

                //move square
                if(square_collision == false){
                    leftX = leftX + speed.nextInt(6);
                } else {
                    leftX = 100;
                    square_collision = false;
                }
                

                //move obstacle1
                if(up1 == false){
                    y1 = multiply(y1, speed.nextInt(10) + 10, true);
                    if(y1[4] > f.getHeight() - 100){
                        up1 = true; 
                    } 
                } else {
                    y1 = multiply(y1, speed.nextInt(10) + 3, false);
                    if(y1[4] < 100){
                        up1 = false;
                    }
                }

                //move obstacle2
                if(up2 == false){
                    y2 = multiply(y2, speed.nextInt(10) + 2, true);
                    if(y2[4] > f.getHeight() - 100){
                        up2 = true; 
                    } 
                } else {
                    y2 = multiply(y2, speed.nextInt(10) + 2, false);
                    if(y2[4] < 100){
                        up2 = false;
                    }
                }                

                //move obstacle3
                if(up3 == false){
                    y3 = multiply(y3, speed.nextInt(10) + 1, true);
                    if(y3[4] > f.getHeight() - 100){
                        up3 = true; 
                    } 
                } else {
                    y3 = multiply(y3, speed.nextInt(10) + 3, false);
                    if(y3[4] < 100){
                        up3 = false;
                    }
                }  
                
                //move obstacle4
                if(up4 == false){
                    y4 = multiply(y4, speed.nextInt(10) + 15, true);
                    if(y4[4] > f.getHeight() - 100){
                        up4 = true; 
                    } 
                } else {
                    y4 = multiply(y4, speed.nextInt(10) + 15, false);
                    if(y4[4] < 100){
                        up4 = false;
                    }
                } 
                
                //if one of the shapes reaches the finish line
                //stop the movement
                if(x >= finish_line_x || leftX >= finish_line_x){
                    game_over = false;
                }
                f.repaint();
            } else {
                //restarts the race
                TimeUnit.SECONDS.sleep(5);
                x = 100;
                y = 100;
                leftX = 100;
                topY = 600;
                game_over = true;
                circle_collision = false;
                square_collision = false;
                boolean up1 = true;
                boolean up2 = true;
                boolean up3 = true;
                boolean up4 = false; 
                move(f);
            }
        } 
        catch (Exception e){
        }
    }

}