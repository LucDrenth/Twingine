package engine.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window
{
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private int width;
    private int height;
    private String title;

    public Window( int width, int height, String title )
    {
        this.width = width;
        this.height = height;
        this.title = title;

        image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        canvas = new Canvas( );
        Dimension dimension = new Dimension( width, height );
        canvas.setPreferredSize( dimension );
        canvas.setMaximumSize( dimension );
        canvas.setMinimumSize( dimension );

        frame = new JFrame( title );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( new BorderLayout( ) );
        frame.add( canvas, BorderLayout.CENTER );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setResizable( false );
        frame.setVisible( true );

        canvas.createBufferStrategy( 2 );
        bufferStrategy = canvas.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void update()
    {

        graphics.drawImage( image, 0, 0, canvas.getWidth(), canvas.getHeight(), null );
        bufferStrategy.show();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public void setTitle( String title )
    {
        frame.setTitle( title );
    }
}
