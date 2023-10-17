package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;

public class Zegar{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame okno= new JFrame("Zegar analogowy");
                okno.setResizable(false);
                okno.setDefaultCloseOperation(3);



                Zegarek czas = new Zegarek (400,400);
                okno.add(czas);
                okno.pack();
                okno.setLocationRelativeTo(null);
                okno.setVisible(true);


            }
        });
    }

}

class Zegarek extends JPanel implements ActionListener{
    double hKat=0;
    double mKat=0;
    double sKat=0;

    public Zegarek (int w, int h) {
        setPreferredSize(new Dimension(w,h));
        setBackground(new Color(123,202,175));

        Timer t=new Timer(1000, this);
        t.start();
    }

    /* public void kwadrat (int x, int y, int a){
         Line2D bok1= new Line2D.Double(x,y,x+a,y);
     }*/
    public void actionPerformed(ActionEvent e){
        Date d = new Date();
        int h=Integer.parseInt(new SimpleDateFormat("HH").format(d));
        int m=Integer.parseInt(new SimpleDateFormat("mm").format(d));
        int s=Integer.parseInt(new SimpleDateFormat("ss").format(d));


        sKat=s*6;
        mKat=m*6+s*0.1;
        hKat=h*30+m*0.5;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage img=null;
        try { img = ImageIO.read(new File("face.png"));}
        catch (IOException e){}
        g2d.drawImage(img,10,10,null);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Line2D secound = new Line2D.Double(200,200,200,200-200*0.9);
        Line2D minute = new Line2D.Double(200,200,200,200-200*0.7);
        Line2D hour = new Line2D.Double(200,200,200,200-200*0.5);


        AffineTransform sT =AffineTransform.getRotateInstance(Math.toRadians(sKat), hour.getX1(), hour.getY1());
        AffineTransform mT =AffineTransform.getRotateInstance(Math.toRadians(mKat), minute.getX1(), minute.getY1());
        AffineTransform hT =AffineTransform.getRotateInstance(Math.toRadians(hKat), secound.getX1(), secound.getY1());

        // Line2D Linia1 = new Line2D.Double(100,100,300,100);
        // Line2D Linia2 = new Line2D.Double(300,300,300,100);
        // Line2D Linia3 = new Line2D.Double(300,300,100,300);
        g2d.setStroke(new BasicStroke(4.0f));
        g2d.draw(hT.createTransformedShape(hour));
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.draw(mT.createTransformedShape(minute));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(Color.RED);
        g2d.draw(sT.createTransformedShape(secound));

        // g2d.draw(Linia1);
        // g2d.draw(Linia2);
        //g2d.draw(Linia3);



        Ellipse2D kolo = new Ellipse2D.Double(195,195,10,10);
        g2d.fill(kolo);
    }
}