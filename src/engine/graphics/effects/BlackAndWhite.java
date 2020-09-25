package engine.graphics.effects;

import java.awt.*;

public class BlackAndWhite
{
    // border should be a number between 0 and 255 and determines from which value the colors go black or white
    public static int[] convert( int[] pixels, int border )
    {
        int[] newPixels = GrayScale.convert( pixels );

        for( int i = 0; i < pixels.length; i++ )
        {
            int grey = new Color( newPixels[ i ] ).getRed();
            if( grey <= border )
                newPixels[ i ] = new Color( 0, 0, 0 ).hashCode();
            else
                newPixels[ i ] = new Color( 255, 255, 255 ).hashCode();
        }

        return newPixels;
    }
}
