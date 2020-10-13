package engine.graphics.shapes;

import engine.graphics.PixelData;
import engine.graphics.Renderer;
import engine.twinUtils.Point;

public class Circle
{
    private PixelData pixels; // a grid of pixels with value 1 representing a pixel and value 0 representing no pixel
    private Point offset;
    private int radius;
    private int color;

    public Circle( int radius, int color )
    {
        this.radius = radius;
        this.color = color;
        offset = new Point( 0, 0 );
        pixels = generateCircle( radius );
    }

    private static PixelData generateCircle( int radius )
    {
        PixelData pixels = new PixelData( radius * 2 + 1, radius * 2 + 1 );

        int x = 0;
        int y = radius - 1;
        int decisionPoint = 3 - ( 2 * radius );
        setPixelsEightWaySymmetrical( radius, pixels, x, y ); // set initial points

        while( x <= y - 1 )
        {
            if( decisionPoint <= 0 ) // goes on same line
                decisionPoint += 4 * x + 6;
            else // goes on next line
            {
                decisionPoint += 4 * x - 4 * y + 10;
                y--;
            }

            x++;
            setPixelsEightWaySymmetrical( radius, pixels, x, y );
        }

        return pixels;
    }

    private static void setPixelsEightWaySymmetrical( int radius, PixelData pixels, int x, int y )
    {
        pixels.setPixel( x + radius, y + radius, 1 );
        pixels.setPixel( x + radius, -y + radius, 1 );
        pixels.setPixel( -x + radius, -y + radius, 1 );
        pixels.setPixel( -x + radius, y + radius, 1 );
        pixels.setPixel( y + radius, x + radius, 1 );
        pixels.setPixel( y + radius, -x + radius, 1 );
        pixels.setPixel( -y + radius, -x + radius, 1 );
        pixels.setPixel( -y + radius, x + radius, 1 );
    }

    public void draw( Renderer renderer )
    {
        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                if( pixels.getPixel( x, y ) == 1 )
                {
                    renderer.setPixel( x + offset.getX(), y + offset.getY(), color );
                }
            }
        }
    }

    public void setOffset( int x, int y )
    {
        offset.set( x, y );
    }

    public PixelData getPixels()
    {
        return pixels;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius( int radius )
    {
        this.radius = radius;
        pixels = generateCircle( radius );
    }
}
