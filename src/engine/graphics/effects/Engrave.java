package engine.graphics.effects;

import engine.twinUtils.TwinUtils;

import java.awt.*;

public class Engrave
{
    public static int[] engrave( int[] pixels, int pixelsWidth, int pixelsHeight, int extraX, int extraY, int extraColor )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int x = 0; x < pixelsWidth; x++ )
        {
            for( int y = 0; y < pixelsHeight; y++ )
            {
                if( TwinUtils.pixelExists( pixelsWidth, pixelsHeight, x + extraX, y + extraY ) )
                {
                    Color color = new Color( pixels[ x + y * pixelsWidth ] );
                    Color neighbourColor = new Color( pixels[ ( x + extraX ) + ( y + extraY ) * pixelsWidth ] );

                    int newRed = color.getRed() + extraColor - neighbourColor.getRed();
                    if( newRed < 0 ) newRed = 0;
                    if( newRed > 255 ) newRed = 255;

                    int newGreen = color.getGreen() + extraColor - neighbourColor.getGreen();
                    if( newGreen < 0 ) newGreen = 0;
                    if( newGreen > 255 ) newGreen = 255;

                    int newBlue = color.getBlue() + extraColor - neighbourColor.getBlue();
                    if( newBlue < 0 ) newBlue = 0;
                    if( newBlue > 255 ) newBlue = 255;

                    newPixels[ x + y * pixelsWidth ] = new Color( newRed, newGreen, newBlue ).hashCode();
                }
            }
        }

        return newPixels;
    }
}
