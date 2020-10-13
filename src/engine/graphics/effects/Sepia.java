package engine.graphics.effects;

import engine.graphics.pixeldata.PixelData;

import java.awt.*;

public class Sepia
{
    public static PixelData sepia( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );

            int newRed = (int)( ( color.getRed() * 0.393 ) + ( color.getGreen() * 0.769 ) + ( color.getBlue() * 0.189 ) );
            if( newRed < 0 ) newRed = 0;
            if( newRed > 255 ) newRed = 255;

            int newGreen = (int)( ( color.getRed() * 0.349 ) + ( color.getGreen() * 0.686 ) + ( color.getBlue() * 0.168 ) );
            if( newGreen < 0 ) newGreen = 0;
            if( newGreen > 255 ) newGreen = 255;

            int newBlue = (int)( ( color.getRed() * 0.272 ) + ( color.getGreen() * 0.534 ) + ( color.getBlue() * 0.131 ) );
            if( newBlue < 0 ) newBlue = 0;
            if( newBlue > 255 ) newBlue = 255;

            newPixels[ i ] = new Color( newRed, newGreen, newBlue ).hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }
}
