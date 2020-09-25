package engine.graphics.effects;

import engine.twinUtils.Dice;
import engine.twinUtils.TwinUtils;

import java.util.ArrayList;

public class Mix
{
    public static int[] randomizeInsideBox( int[] pixels, int pixelsWidth, int pixelsHeight, int boxWidth, int boxHeight )
    {
        int[] newPixels = new int[ pixels.length ];

        ArrayList<Integer> options = new ArrayList<>();

        for( int y = 0; y < pixelsHeight; y++ )
        {
            for( int x = 0; x < pixelsWidth; x++ )
            {
                // loop trough surrounding pixels
                for( int i = 0; i < boxWidth; i++ )
                {
                    for( int j = 0; j < boxHeight; j++ )
                    {
                        // if pixel exists, put its value in the list
                        int xCoord = x + i - boxWidth / 2;
                        int yCoord = y + j - boxHeight / 2;
                        if( TwinUtils.pixelExists( pixelsWidth, pixelsHeight, xCoord, yCoord ) )
                        {
                            options.add( pixels[ xCoord + yCoord * pixelsWidth ] );
                        }
                    }
                }

                newPixels[ x + y * pixelsWidth ] = options.get( Dice.rollInt( 0, options.size() - 1 ) );
                options.clear();
            }
        }

        return newPixels;
    }
}
