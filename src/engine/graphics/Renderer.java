package engine.graphics;

import engine.Engine;
import engine.graphics.color.ColorPalette;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Renderer
{
    private Engine engine;

    private int pixelsWidth;
    private int pixelsHeight;
    private int pixels[];

    public Renderer( Engine engine )
    {
        this.engine = engine;

        pixelsWidth = engine.getWindow().getWidth();
        pixelsHeight = engine.getWindow().getHeight();
        pixels = ((DataBufferInt)engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void setPixel( int x, int y, int color )
    {
        int alpha = ((color >> 24) & 0xff);

        if( isWithinWindow( x, y ) && alpha > 0 )
        {
            if( alpha == 255 )
                pixels[ x + y * pixelsWidth ] = color;
            else
                pixels[ x + y * pixelsWidth ] = getBlendedPixelColor( x, y, color, alpha );
        }
    }

    /*
     * alphaPercentage ranging from 0 to 100, 0 being not visible and 100 being completely visible. If the color
     * variable holds alpha, the resulting color will still have alpha. So alphaPercentage amplifies the original color.
     * For example; if color variable has
     */
    public void setPixel( int x, int y, int color, int alphaPercentage )
    {
        int alpha = ((color >> 24) & 0xff);
        alpha = (int)( (double)alphaPercentage / 100.0 * (double)alpha );

        if( isWithinWindow( x, y ) && alpha > 0 )
        {
            if( alpha == 255 )
                pixels[ x + y * pixelsWidth ] = color;
            else
                pixels[ x + y * pixelsWidth ] = getBlendedPixelColor( x, y, color, alpha );
        }
    }

    private boolean isWithinWindow( int x, int y )
    {
        return !( x < 0 || x >= pixelsWidth || y < 0 || y >= pixelsHeight );
    }

    private int getBlendedPixelColor( int xPosition, int yPosition, int colorToBlendTo, int alpha )
    {
        int oldColor = pixels[ xPosition + yPosition * pixelsWidth ];

        int newRed = ((oldColor >> 16) & 0xff) - (int)((((oldColor >> 16) & 0xff) - ((colorToBlendTo >> 16) & 0xff)) * (alpha / 255f));
        int newGreen = ((oldColor >> 8) & 0xff) - (int)((((oldColor >> 8) & 0xff) - ((colorToBlendTo >> 8) & 0xff)) * (alpha / 255f));
        int newBlue = (oldColor & 0xff) - (int)(((oldColor & 0xff) - (colorToBlendTo & 0xff)) * (alpha / 255f));

        return ( 255 << 24 | newRed << 16 | newGreen << 8 | newBlue );
    }

    public void clear()
    {
        Arrays.fill( pixels, ColorPalette.getBlack( ) );
    }

    public int getPixel( int x, int y )
    {
        return pixels[ x + y * pixelsWidth ];
    }

}
