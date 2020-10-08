package engine.graphics.effects;

import engine.graphics.PixelData;

import java.awt.*;

public class BlackAndWhite
{
    // border should be a number between 0 and 255 and determines from which value the colors go black or white
    public static PixelData convert( PixelData pixelData, int border )
    {
        PixelData newPixelData = GrayScale.convert( pixelData );

        for( int i = 0; i < pixelData.getPixels().length; i++ )
        {
            int grey = new Color( newPixelData.getPixel( i ) ).getRed();
            if( grey <= border )
                newPixelData.setPixel( i, new Color( 0, 0, 0 ).hashCode() );
            else
                newPixelData.setPixel( i, new Color( 255, 255, 255 ).hashCode() );
        }

        return newPixelData;
    }
}
