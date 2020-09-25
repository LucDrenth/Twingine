package engine.graphics.effects;

import java.awt.*;

public class Tint
{
    // tintChange should be a value from -20 to 20
    public static int[] change( int[] pixels, int tintChange )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );

            int newGreen = color.getGreen() + tintChange;
            if( newGreen > 255 ) newGreen = 255;
            if( newGreen < 0 ) newGreen = 0;

            newPixels[ i ] = new Color( color.getRed(), newGreen, color.getBlue() ).hashCode();
        }

        return newPixels;
    }
}
