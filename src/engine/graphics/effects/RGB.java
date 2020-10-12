package engine.graphics.effects;

import engine.graphics.PixelData;

import java.awt.*;

public class RGB
{
    public static PixelData swapRedAndGreen( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );
            newPixels[ i ] = new Color( color.getGreen(), color.getRed(), color.getBlue() ).hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    public static PixelData swapRedAndBlue( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );
            newPixels[ i ] = new Color( color.getBlue(), color.getGreen(), color.getRed() ).hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    public static PixelData swapGreenAndBlue( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );
            newPixels[ i ] = new Color( color.getRed(), color.getBlue(), color.getGreen() ).hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    // increases red by a certain percentage. Percentage is the percentage of red left to the maximum amount of red (255)
    public static PixelData increaseRed( PixelData pixels, int percentage )
    {
        if( percentage > 100 )
            percentage = 100;

        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );
            Color newColor = new Color( increaseOneOfRGB( color.getRed(), percentage ), color.getGreen(), color.getBlue() );
            newPixels[ i ] = newColor.hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    // increases green by a certain percentage. Percentage is the percentage of green left to the maximum amount of red (255)
    public static PixelData increaseGreen( PixelData pixels, int percentage )
    {
        if( percentage > 100 )
            percentage = 100;

        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );
            Color newColor = new Color( color.getRed(), increaseOneOfRGB( color.getGreen(), percentage ), color.getBlue() );
            newPixels[ i ] = newColor.hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    // increases blue by a certain percentage. Percentage is the percentage of blue left to the maximum amount of red (255)
    public static PixelData increaseBlue( PixelData pixels, int percentage )
    {
        if( percentage > 100 )
            percentage = 100;

        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );
            Color newColor = new Color( color.getRed(), color.getGreen(), increaseOneOfRGB( color.getBlue(), percentage ) );
            newPixels[ i ] = newColor.hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    // takes in an r, g or b value and increases it with the percentage of the difference to its max (255) or min (0)
    // percentage can be between reach from -100 to 100
    private static int increaseOneOfRGB( int color, int percentage )
    {
        if( percentage >= 0 )
            return color + (int)( (float)percentage / 100 * ( 255 - color ) );
        else
            return color - (int)( (float)-percentage / 100 * color );
    }
}
