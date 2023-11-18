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
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame window = new JFrame("Analog Clock");
                window.setResizable(false);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Clock clock = new Clock(400, 400);
                window.add(clock);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }
}

class Clock extends JPanel implements ActionListener {
    double hAngle = 0;
    double mAngle = 0;
    double sAngle = 0;

    public Clock(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(123, 202, 175));

        Timer timer = new Timer(1000, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        Date currentDate = new Date();
        int hours = Integer.parseInt(new SimpleDateFormat("HH").format(currentDate));
        int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(currentDate));
        int seconds = Integer.parseInt(new SimpleDateFormat("ss").format(currentDate));

        sAngle = seconds * 6;
        mAngle = minutes * 6 + seconds * 0.1;
        hAngle = hours * 30 + minutes * 0.5;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("face.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        g2d.drawImage(img, 10, 10, null);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Line2D secondHand = new Line2D.Double(200, 200, 200, 200 - 200 * 0.9);
        Line2D minuteHand = new Line2D.Double(200, 200, 200, 200 - 200 * 0.7);
        Line2D hourHand = new Line2D.Double(200, 200, 200, 200 - 200 * 0.5);

        AffineTransform sTransform = AffineTransform.getRotateInstance(Math.toRadians(sAngle), hourHand.getX1(), hourHand.getY1());
        AffineTransform mTransform = AffineTransform.getRotateInstance(Math.toRadians(mAngle), minuteHand.getX1(), minuteHand.getY1());
        AffineTransform hTransform = AffineTransform.getRotateInstance(Math.toRadians(hAngle), secondHand.getX1(), secondHand.getY1());

        g2d.setStroke(new BasicStroke(4.0f));
        g2d.draw(hTransform.createTransformedShape(hourHand));
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.draw(mTransform.createTransformedShape(minuteHand));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(Color.RED);
        g2d.draw(sTransform.createTransformedShape(secondHand));

        Ellipse2D circle = new Ellipse2D.Double(195, 195, 10, 10);
        g2d.fill(circle);
    }
}