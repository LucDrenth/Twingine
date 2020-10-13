package engine.graphics.shapes;

import engine.graphics.color.ColorPalette;
import engine.graphics.pixeldata.PixelData;
import engine.graphics.Renderer;
import engine.twinUtils.Point;

public class Circle
{
    private PixelData pixels; // a grid of pixels with value 1 representing a pixel and value 0 representing no pixel
    private Point offset;

    private int radius;
    private int diameter;
    private int color;

    public Circle( int radius, int color )
    {
        this.radius = radius;
        this.color = color;
        calculateDiameter();
        offset = new Point( 0, 0 );
        pixels = generateCircle( radius );
    }

    public Circle( int radius )
    {
        this.radius = radius;
        calculateDiameter();
        offset = new Point( 0, 0 );
        pixels = generateCircle( radius );
    }

    private void calculateDiameter()
    {
        diameter = radius * 2 - 1;
    }

    private static PixelData generateCircle( int radius )
    {
        int diameter = calculateDiameter( radius );
        PixelData pixels = new PixelData( diameter, diameter );

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

    private static int calculateDiameter( int radius )
    {
        return radius * 2 - 1;
    }

    private static void setPixelsEightWaySymmetrical( int radius, PixelData pixels, int x, int y )
    {
        pixels.setPixel( x + radius - 1, y + radius - 1, 1 );
        pixels.setPixel( x + radius - 1, -y + radius - 1, 1 );
        pixels.setPixel( -x + radius - 1, -y + radius - 1, 1 );
        pixels.setPixel( -x + radius - 1, y + radius - 1, 1 );
        pixels.setPixel( y + radius - 1, x + radius - 1, 1 );
        pixels.setPixel( y + radius - 1, -x + radius - 1, 1 );
        pixels.setPixel( -y + radius - 1, -x + radius - 1, 1 );
        pixels.setPixel( -y + radius - 1, x + radius - 1, 1 );
    }

    public void fill()
    {
        for( int y = 0; y < pixels.getHeight(); y++ )
        {
            int mostLeftPixelPosition = getMostLeftPixelPosition( y );
            int mostRightPixelPosition = getMostRightPixelPosition( y );
            if( mostLeftPixelPosition < mostRightPixelPosition )
            {
                for( int x = mostLeftPixelPosition + 1; x < mostRightPixelPosition; x++ )
                {
                    pixels.setPixel( x, y, 1 );
                }
            }
        }
    }

    private int getMostLeftPixelPosition( int y )
    {
        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            if( pixels.getPixel( x, y ) == 1 )
                return x;
        }
        return 0;
    }

    private int getMostRightPixelPosition( int y )
    {
        for( int x = pixels.getWidth() - 1; x >= 0; x-- )
        {
            if( pixels.getPixel( x, y ) == 1 )
                return x;
        }
        return 0;
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
                else
                {
                    renderer.setPixel( x + offset.getX(), y + offset.getY(), ColorPalette.getWhite() );
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

    public void setPixels( PixelData pixels )
    {
        this.pixels = pixels;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius( int radius )
    {
        this.radius = radius;
        pixels = generateCircle( radius );
        calculateDiameter();
    }

    public int getDiameter()
    {
        return diameter;
    }

    public void setDiameter( int diameter )
    {
        this.diameter = diameter;
        radius = ( diameter + 1 ) / 2;
        pixels = generateCircle( radius );

    }

    public int getColor()
    {
        return color;
    }

    public void setColor( int color )
    {
        this.color = color;
    }
}
