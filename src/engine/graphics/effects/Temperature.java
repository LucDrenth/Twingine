package engine.graphics.effects;

import engine.graphics.pixeldata.PixelData;

import java.awt.*;

public class Temperature
{
    // temperatureChange should be a value from -50 to 50
    public static PixelData change( PixelData pixels, int temperatureChange )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int i = 0; i < pixels.getPixels().length; i++ )
        {
            Color color = new Color( pixels.getPixel( i ) );

            int newRed = color.getRed() + temperatureChange;
            if( newRed > 255 ) newRed = 255;
            else if( newRed < 0 ) newRed = 0;

            int newGreen = color.getGreen();

            int newBlue = color.getBlue() - temperatureChange;
            if( newBlue < 0 ) newBlue = 0;
            if( newBlue > 255 ) newBlue = 255;

            newPixels[ i ] = new Color( newRed, newGreen, newBlue ).hashCode();
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }
}
