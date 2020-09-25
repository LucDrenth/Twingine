package engine.graphics.effects;

import java.awt.*;

public class Blur
{
    private static final int maxBlurPercentagePerIteration = 100;
    private static int blurBoxSize = 5; // should be an uneven number, at least 3
    private static int loopStart = -( ( blurBoxSize - 1 ) / 2 );
    private static int loopEnd = loopStart * -1;

    public static int[] blur( int[] pixels, int pixelsWidth, int pixelsHeight, int blurPercentage, boolean blurInbound )
    {
        int[] newPixels = new int[ pixels.length ];
        System.arraycopy( pixels, 0, newPixels, 0, pixels.length );

        // keep track of a currentBlurPercentage variable that is never higher than maxBlurPercentagePerIteration to
        // handle a blurPercentage above 100
        int currentBlurPercentage;
        if( blurPercentage > maxBlurPercentagePerIteration )
            currentBlurPercentage = maxBlurPercentagePerIteration;
        else
            currentBlurPercentage = blurPercentage;

        while( blurPercentage > 0 )
        {
            // loop trough all pixels to calculate and set the blurred pixel value to a copied array of pixels
            for( int x = 0; x < pixelsWidth; x++ )
            {
                for( int y = 0; y < pixelsHeight; y++ )
                {
                    int averageColorOfSurroundingPixel = getAverageColorOfSurroundingPixel( pixels, pixelsWidth, pixelsHeight, x, y );
                    int originalColorPercentage = 100 - currentBlurPercentage;

                    if( averageColorOfSurroundingPixel != 0 && ( !blurInbound || getPixel( pixels, pixelsWidth, x, y ) != 0 ) )
                    {
                        int newRed = ( new Color( averageColorOfSurroundingPixel ).getRed() * currentBlurPercentage + new Color( getPixel( pixels, pixelsWidth, x, y ) ).getRed() * originalColorPercentage ) / 100;
                        int newGreen = ( new Color( averageColorOfSurroundingPixel ).getGreen() * currentBlurPercentage + new Color( getPixel( pixels, pixelsWidth, x, y ) ).getGreen() * originalColorPercentage ) / 100;
                        int newBlue = ( new Color( averageColorOfSurroundingPixel ).getBlue() * currentBlurPercentage + new Color( getPixel( pixels, pixelsWidth, x, y ) ).getBlue() * originalColorPercentage ) / 100;
                        newPixels[ x + y * pixelsWidth ] = new Color( newRed, newGreen, newBlue ).hashCode();
                    }
                    else
                        newPixels[ x + y * pixelsWidth ] = 0;
                }
            }

            // copy the newPixel array to the pixels array to set up for a next iteration
            System.arraycopy( newPixels, 0, pixels, 0, pixels.length );

            // set the new currentBlurPercentage
            if( blurPercentage > maxBlurPercentagePerIteration )
            {
                blurPercentage -= maxBlurPercentagePerIteration;
                if( blurPercentage > maxBlurPercentagePerIteration )
                    currentBlurPercentage = maxBlurPercentagePerIteration;
                else
                    currentBlurPercentage = blurPercentage;
            }
            else
            {
                blurPercentage = 0;
            }

        }

        return newPixels;
    }

    private static int getAverageColorOfSurroundingPixel( int[] pixels, int pixelsWidth, int pixelsHeight, int x, int y )
    {
        int totalRedOfSurroundingPixels = 0;
        int totalGreenOfSurroundingPixels = 0;
        int totalBlueOfSurroundingPixels = 0;
        int amountOfSurroundingPixels = 0;

        // loop trough all surrounding pixels of the size of the blurBox to get the sum of surrounding colors
        for( int i = loopStart; i <= loopEnd; i++ )
        {
            for( int j = loopStart; j <= loopEnd; j++ )
            {
                if( pixelExists( pixelsWidth, pixelsHeight, x + i, y + j ) &&
                    getPixel( pixels, pixelsWidth, x + i, y + j ) != 0 &&
                    !( i == 0 && j == 0 ) ) // don't add the pixel that needs to be blurred
                {
                    amountOfSurroundingPixels++;
                    Color color = new Color( getPixel( pixels, pixelsWidth, x + i, y + j ) );
                    totalRedOfSurroundingPixels += color.getRed();
                    totalGreenOfSurroundingPixels += color.getGreen();
                    totalBlueOfSurroundingPixels += color.getBlue();
                }
            }
        }

        // average and return the summed up colors
        if( amountOfSurroundingPixels > 0 )
        {
            // get the average color of the surrounding pixels and return it
            int averageRed = totalRedOfSurroundingPixels / amountOfSurroundingPixels;
            int averageGreen = totalGreenOfSurroundingPixels / amountOfSurroundingPixels;
            int averageBlue = totalBlueOfSurroundingPixels / amountOfSurroundingPixels;
            return new Color( averageRed, averageGreen, averageBlue ).hashCode();
        }
        else
            return 0;
    }

    private static boolean pixelExists( int pixelsWidth, int pixelsHeight, int x, int y )
    {
        return x >= 0 && x < pixelsWidth && y >= 0 && y < pixelsHeight;
    }

    private static int getPixel( int[] pixels, int pixelsWidth, int x, int y )
    {
        return pixels[ x + y * pixelsWidth ];
    }

    /*
     * sets the size of the box that surrounds to-be-blurred pixels and takes an average value from
     *
     * should be at least 3 and should be an uneven number
     */
    private static void setBlurBoxSize( int newBlurBoxSize )
    {
        if( blurBoxSize % 2 == 1 && blurBoxSize >= 3 )
        {
            blurBoxSize = newBlurBoxSize;
            loopStart = -( ( blurBoxSize - 1 ) / 2 );
            loopEnd = loopStart * -1;
        }
        else
            System.out.println( "Warning: did not set new blurBoxSize " + newBlurBoxSize + ". blurBoxSize should always be an uneven number and should be at least 3" );
    }
}
