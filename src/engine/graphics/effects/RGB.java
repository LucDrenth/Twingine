package engine.graphics.effects;

import java.awt.*;

public class RGB
{
    public static int[] swapRedAndGreen( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            newPixels[ i ] = new Color( color.getGreen(), color.getRed(), color.getBlue() ).hashCode();
        }

        return newPixels;
    }

    public static int[] swapRedAndBlue( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            newPixels[ i ] = new Color( color.getBlue(), color.getGreen(), color.getRed() ).hashCode();
        }

        return newPixels;
    }

    public static int[] swapGreenAndBlue( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            newPixels[ i ] = new Color( color.getRed(), color.getBlue(), color.getGreen() ).hashCode();
        }

        return newPixels;
    }

    public static int[] increaseRed( int[] pixels, int percentage )
    {
        if( percentage > 100 )
            percentage = 100;

        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            Color newColor = new Color( increaseOneOfRGB( color.getRed(), percentage ), color.getGreen(), color.getBlue() );
            newPixels[ i ] = newColor.hashCode();
        }

        return newPixels;
    }

    public static int[] increaseGreen( int[] pixels, int percentage )
    {
        if( percentage > 100 )
            percentage = 100;

        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            Color newColor = new Color( color.getRed(), increaseOneOfRGB( color.getGreen(), percentage ), color.getBlue() );
            newPixels[ i ] = newColor.hashCode();
        }

        return newPixels;
    }

    public static int[] increaseBlue( int[] pixels, int percentage )
    {
        if( percentage > 100 )
            percentage = 100;

        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            Color newColor = new Color( color.getRed(), color.getGreen(), increaseOneOfRGB( color.getBlue(), percentage ) );
            newPixels[ i ] = newColor.hashCode();
        }

        return newPixels;
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
