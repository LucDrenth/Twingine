package engine.graphics.effects;

public class Flip
{
    public static int[] horizontal( int[] pixels, int pixelsWidth, int pixelsHeight )
    {
        int[] flippedPixels = new int[ pixels.length ];

        for( int x = 0; x < pixelsWidth; x++ )
        {
            for( int y = 0; y < pixelsHeight; y++ )
            {
                flippedPixels[ ( pixelsWidth - x - 1 ) + y * pixelsWidth ] = pixels[ x + y * pixelsWidth ];
            }
        }

        return flippedPixels;
    }

    public static int[] vertical( int[] pixels, int pixelsWidth, int pixelsHeight )
    {
        int[] flippedPixels = new int[ pixels.length ];

        for( int x = 0; x < pixelsWidth; x++ )
        {
            for( int y = 0; y < pixelsHeight; y++ )
            {
                flippedPixels[ x + ( pixelsHeight - y - 1 ) * pixelsWidth ] = pixels[ x + y * pixelsWidth ];
            }
        }

        return flippedPixels;
    }
}
