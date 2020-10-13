package engine.graphics.effects;

import engine.graphics.pixeldata.PixelData;
import engine.twinUtils.TwinUtils;

import java.awt.*;
import java.util.Arrays;

public class Scale
{
    /*
     * scaleFactor should be higher than 0
     */
    public static PixelData scale( PixelData pixels, float scaleFactor )
    {
        int newWidth = (int)( pixels.getWidth() * scaleFactor );
        int newHeight = (int)( pixels.getHeight() * scaleFactor );
        return getScaledImage( pixels, newWidth, newHeight );
    }

    public static PixelData scale( PixelData pixels, int scaleFactor )
    {
        int newWidth = pixels.getWidth() * scaleFactor;
        int newHeight = pixels.getHeight() * scaleFactor;
        PixelData pixelData = new PixelData( newWidth, newHeight );

        for( int x = 0; x < newWidth; x++ )
        {
            for( int y = 0; y < newHeight; y++ )
            {
                int newPixelValue = pixels.getPixel( x / scaleFactor, y / scaleFactor );
                pixelData.setPixel( x, y, newPixelValue );
            }
        }

        return pixelData;
    }

    public static PixelData scale( PixelData pixels, float scaleFactorX, float scaleFactorY )
    {
        int newWidth = (int)( pixels.getWidth() * scaleFactorX );
        int newHeight = (int)( pixels.getHeight() * scaleFactorY );
        return getScaledImage( pixels, newWidth, newHeight );
    }

    public static PixelData scale( PixelData pixels, int scaleFactorX, int scaleFactorY )
    {
        int newWidth = pixels.getWidth() * scaleFactorX;
        int newHeight = pixels.getHeight() * scaleFactorY;
        PixelData pixelData = new PixelData( newWidth, newHeight );

        for( int x = 0; x < newWidth; x++ )
        {
            for( int y = 0; y < newHeight; y++ )
            {
                int newPixelValue = pixels.getPixel( x / scaleFactorX, y / scaleFactorY );
                pixelData.setPixel( x, y, newPixelValue );
            }
        }

        return pixelData;
    }

    private static PixelData getScaledImage( PixelData pixels, int newWidth, int newHeight )
    {
        int[] scaledPixels = new int[ newWidth * newHeight ];
        int oldWidth = pixels.getWidth();
        int oldHeight = pixels.getHeight();

        // set all pixels to -1 to indicate they still need to be calculated
        Arrays.fill( scaledPixels, -1 );

        // set all known pixel values from the original pixels array
        float stepSizeX = (float)newWidth / (float)(oldWidth - 1);
        float stepSizeY = (float)newHeight / (float)(oldHeight - 1);
        for( int x = 0; x < oldWidth - 1; x++ )
        {
            for( int y = 0; y < oldHeight - 1; y++ )
            {
                int xPositionInScaledPixels = (int)( x * stepSizeX );
                int yPositionInScaledPixels = (int)( y * stepSizeY );
                int pixelValue = pixels.getPixel( x, y );
                scaledPixels[ xPositionInScaledPixels + yPositionInScaledPixels * newWidth ] = pixelValue;
            }
        }

        // set the color of the remaining pixels (that are at value -1) by bilinear interpolating between the known pixels
        for( int y = 0; y < newHeight; y++ )
        {
            for( int x = 0; x < newWidth; x++ )
            {
                int arrayPosition = x + y * newWidth;

                if( scaledPixels[ arrayPosition ] == - 1 )
                {
                    int topLeftArrayPositionX = (int)( x / stepSizeX );
                    int topLeftArrayPositionY = (int)( y / stepSizeY );
                    int topLeft = pixels.getPixel( topLeftArrayPositionX, topLeftArrayPositionY );
                    int topRight = pixels.getPixel( topLeftArrayPositionX + 1, topLeftArrayPositionY );
                    int bottomLeft = pixels.getPixel( topLeftArrayPositionX, topLeftArrayPositionY + 1 );
                    int bottomRight = pixels.getPixel( topLeftArrayPositionX + 1, topLeftArrayPositionY + 1 );

                    int totalStepsX = (int)(( topLeftArrayPositionX + 1 ) * stepSizeX) - (int)( topLeftArrayPositionX * stepSizeX );
                    int totalStepsY = (int)(( topLeftArrayPositionY + 1 ) * stepSizeY) - (int)( topLeftArrayPositionY * stepSizeY );

                    int stepsX = x - (int)( topLeftArrayPositionX * stepSizeX );
                    int stepsY = y - (int)( topLeftArrayPositionY * stepSizeY );

                    scaledPixels[ arrayPosition ] = getBilinearInterpolatedColor( topLeft, topRight, bottomLeft, bottomRight, totalStepsX, totalStepsY, stepsX, stepsY );
                }
            }
        }
        return new PixelData( scaledPixels, newWidth, newHeight );
    }

    private static int getBilinearInterpolatedColor( int topLeft, int topRight, int bottomLeft, int bottomRight, int totalStepsX, int totalStepsY, int stepsX, int stepsY )
    {
        Color colorTopLeft = new Color( topLeft );
        Color colorTopRight = new Color( topRight );
        Color colorBottomLeft = new Color( bottomLeft );
        Color colorBottomRight = new Color( bottomRight );

        int bilinearInterpolatedRed = (int) TwinUtils.bilinearInterpolate( colorTopLeft.getRed(), colorTopRight.getRed(), colorBottomLeft.getRed(), colorBottomRight.getRed(), totalStepsX, totalStepsY, stepsX, stepsY );
        int bilinearInterpolatedGreen = (int) TwinUtils.bilinearInterpolate( colorTopLeft.getGreen(), colorTopRight.getGreen(), colorBottomLeft.getGreen(), colorBottomRight.getGreen(), totalStepsX, totalStepsY, stepsX, stepsY );
        int bilinearInterpolatedBlue = (int) TwinUtils.bilinearInterpolate( colorTopLeft.getBlue(), colorTopRight.getBlue(), colorBottomLeft.getBlue(), colorBottomRight.getBlue(), totalStepsX, totalStepsY, stepsX, stepsY );

        return new Color( bilinearInterpolatedRed, bilinearInterpolatedGreen, bilinearInterpolatedBlue ).hashCode();
    }

}
