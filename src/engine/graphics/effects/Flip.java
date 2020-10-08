package engine.graphics.effects;

import engine.graphics.PixelData;

public class Flip
{
    public static PixelData horizontal( PixelData pixels )
    {
        PixelData flippedPixels = new PixelData( pixels.getWidth(), pixels.getHeight() );

        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                flippedPixels.setPixel( ( pixels.getWidth() - x - 1 ), + y, pixels.getPixel( x, y ) );
            }
        }

        return flippedPixels;
    }

    public static PixelData vertical( PixelData pixels )
    {
        PixelData flippedPixels = new PixelData( pixels.getWidth(), pixels.getHeight() );

        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                flippedPixels.setPixel( x, pixels.getHeight() - y - 1, pixels.getPixel( x, y )  );
            }
        }

        return flippedPixels;
    }
}
