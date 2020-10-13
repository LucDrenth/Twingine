package engine.graphics.effects;

import engine.graphics.pixeldata.PixelData;
import engine.twinUtils.Dice;
import engine.twinUtils.TwinUtils;

import java.util.ArrayList;

public class Mix
{
    public static PixelData randomizeInsideBox( PixelData pixels, int boxWidth, int boxHeight )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        ArrayList<Integer> options = new ArrayList<>();

        for( int y = 0; y < pixels.getHeight(); y++ )
        {
            for( int x = 0; x < pixels.getWidth(); x++ )
            {
                // loop trough surrounding pixels
                for( int i = 0; i < boxWidth; i++ )
                {
                    for( int j = 0; j < boxHeight; j++ )
                    {
                        // if pixel exists, put its value in the list
                        int xCoord = x + i - boxWidth / 2;
                        int yCoord = y + j - boxHeight / 2;
                        if( TwinUtils.pixelExists( pixels.getWidth(), pixels.getHeight(), xCoord, yCoord ) )
                        {
                            options.add( pixels.getPixel( xCoord, yCoord ) );
                        }
                    }
                }

                newPixels[ x + y * pixels.getWidth() ] = options.get( Dice.rollInt( 0, options.size() - 1 ) );
                options.clear();
            }
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }
}
