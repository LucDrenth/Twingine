package engine.graphics.shapes;

import engine.graphics.PixelData;
import engine.graphics.Renderer;
import engine.twinUtils.Point;

public class Circle
{
    private PixelData pixelsTheirAlphaValues; // alpha values in percentages
    private Point offset;
    private int radius;
    private int color;

    public Circle( int radius, int color )
    {
        this.radius = radius;
        this.color = color;
        offset = new Point( 0, 0 );
        pixelsTheirAlphaValues = new PixelData( radius * 2, radius * 2 );
        pixelsTheirAlphaValues = generateCircle( radius );
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
        pixels.setPixel( x + radius, y + radius, 100 );
        pixels.setPixel( x + radius, -y + radius, 100 );
        pixels.setPixel( -x + radius, -y + radius, 100 );
        pixels.setPixel( -x + radius, y + radius, 100 );
        pixels.setPixel( y + radius, x + radius, 100 );
        pixels.setPixel( y + radius, -x + radius, 100 );
        pixels.setPixel( -y + radius, -x + radius, 100 );
        pixels.setPixel( -y + radius, x + radius, 100 );
    }

    public void draw( Renderer renderer )
    {
        for( int x = 0; x < pixelsTheirAlphaValues.getWidth(); x++ )
        {
            for( int y = 0; y < pixelsTheirAlphaValues.getHeight(); y++ )
            {
                if( pixelsTheirAlphaValues.getPixel( x, y ) > 0 )
                {
                    renderer.setPixel( x + offset.getX(), y + offset.getY(), color, pixelsTheirAlphaValues.getPixel( x, y ) );
                }
            }
        }
    }

    public void setOffset( int x, int y )
    {
        offset.set( x, y );
    }

    public PixelData getPixelsTheirAlphaValues()
    {
        return pixelsTheirAlphaValues;
    }

    public void setPixelsTheirAlphaValues( PixelData pixelsTheirAlphaValues )
    {
        this.pixelsTheirAlphaValues = pixelsTheirAlphaValues;
    }

    public int getRadius()
    {
        return radius;
    }
}
