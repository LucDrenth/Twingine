package engine.graphics.effects;

import engine.graphics.PixelData;
import engine.twinUtils.TwinUtils;

import java.awt.*;

public class Engrave
{
    public static PixelData engrave( PixelData pixels, int extraX, int extraY, int extraColor )
    {
        PixelData newPixels = new PixelData( pixels.getWidth(), pixels.getHeight() );

        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                if( TwinUtils.pixelExists( pixels.getWidth(), pixels.getHeight(), x + extraX, y + extraY ) )
                {
                    Color color = new Color( pixels.getPixel( x, y ) );
                    Color neighbourColor = new Color( pixels.getPixel( x + extraX, y + extraY ) );

                    int newRed = color.getRed() + extraColor - neighbourColor.getRed();
                    if( newRed < 0 ) newRed = 0;
                    if( newRed > 255 ) newRed = 255;

                    int newGreen = color.getGreen() + extraColor - neighbourColor.getGreen();
                    if( newGreen < 0 ) newGreen = 0;
                    if( newGreen > 255 ) newGreen = 255;

                    int newBlue = color.getBlue() + extraColor - neighbourColor.getBlue();
                    if( newBlue < 0 ) newBlue = 0;
                    if( newBlue > 255 ) newBlue = 255;

                    newPixels.setPixel( x, y, new Color( newRed, newGreen, newBlue ).hashCode() );
                }
            }
        }

        return newPixels;
    }
}
