package engine.graphics;

import engine.Engine;
import engine.graphics.color.ColorPalette;
import engine.graphics.pixeldata.PixelData;
import engine.twinUtils.Coordinate;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Renderer
{
    private Engine engine;
    private PixelData pixels;

    public Renderer( Engine engine )
    {
        this.engine = engine;

        int width = engine.getWindow().getWidth();
        int height = engine.getWindow().getHeight();
        int[] pixelsArray = ((DataBufferInt)engine.getWindow().getImage().getRaster().getDataBuffer()).getData();

        pixels = new PixelData( pixelsArray, width, height );
    }

    public void setPixel( int x, int y, int color )
    {
        int alpha = ((color >> 24) & 0xff);

        if( isWithinWindow( x, y ) && alpha > 0 )
        {
            if( alpha == 255 )
                pixels.setPixel( x, y, color );
            else
                pixels.setPixel( x, y, getBlendedPixelColor( x, y, color, alpha ) );
        }
    }

    /*
     * alphaPercentage ranging from 0 to 100, 0 being not visible and 100 being completely visible. If the color
     * variable holds alpha, the resulting color will still have alpha. So alphaPercentage amplifies the original color.
     */
    public void setPixel( int x, int y, int color, int alphaPercentage )
    {
        int alpha = ((color >> 24) & 0xff);
        alpha = (int)( (double)alphaPercentage / 100.0 * (double)alpha );

        if( isWithinWindow( x, y ) && alpha > 0 )
        {
            if( alpha == 255 )
                pixels.setPixel( x, y, color );
            else
                pixels.setPixel( x, y, getBlendedPixelColor( x, y, color, alpha ) );
        }
    }

    public void draw( PixelData pixelData, Coordinate coordinate )
    {
        for( int x = 0; x < pixelData.getWidth(); x++ )
        {
            for( int y = 0; y < pixelData.getHeight(); y++ )
            {
                setPixel( coordinate.getX() + x, coordinate.getY() + y, pixelData.getPixel( x, y ) );
            }
        }
    }

    private boolean isWithinWindow( int x, int y )
    {
        return !( x < 0 || x >= pixels.getWidth() || y < 0 || y >= pixels.getHeight() );
    }

    private int getBlendedPixelColor( int xPosition, int yPosition, int colorToBlendTo, int alpha )
    {
        int oldColor = pixels.getPixel( xPosition, yPosition );

        int newRed = ((oldColor >> 16) & 0xff) - (int)((((oldColor >> 16) & 0xff) - ((colorToBlendTo >> 16) & 0xff)) * (alpha / 255f));
        int newGreen = ((oldColor >> 8) & 0xff) - (int)((((oldColor >> 8) & 0xff) - ((colorToBlendTo >> 8) & 0xff)) * (alpha / 255f));
        int newBlue = (oldColor & 0xff) - (int)(((oldColor & 0xff) - (colorToBlendTo & 0xff)) * (alpha / 255f));

        return ( 255 << 24 | newRed << 16 | newGreen << 8 | newBlue );
    }

    public void clear()
    {
        Arrays.fill( pixels.getPixels(), ColorPalette.getBlack( ) );
    }

    public int getPixel( int x, int y )
    {
        return pixels.getPixel( x, y );
    }

}
