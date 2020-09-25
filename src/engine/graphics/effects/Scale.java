package engine.graphics.effects;

import engine.twinUtils.TwinUtils;

import java.awt.*;
import java.util.Arrays;

public class Scale
{
    /*
     * scaleFactor should be higher than 0
     */
    public static int[] scale( int[] pixels, int width, int height, float scaleFactor )
    {
        int newWidth = (int)( width * scaleFactor );
        int newHeight = (int)( height * scaleFactor );
        return getScaledImage( width, height, newWidth, newHeight, pixels );
    }

    public static int[] scale( int[] pixels, int width, int height, float scaleFactorX, float scaleFactorY )
    {
        int newWidth = (int)( width * scaleFactorX );
        int newHeight = (int)( height * scaleFactorY );
        return getScaledImage( width, height, newWidth, newHeight, pixels );
    }

    private static int[] getScaledImage( int width, int height, int newWidth, int newHeight, int[] pixels )
    {
        int[] scaledPixels = new int[ newWidth * newHeight ];

        // set all pixels to -1 to indicate they still need to be calculated
        Arrays.fill( scaledPixels, -1 );

        // set all known pixel values from the original pixels array
        float stepSizeX = (float)newWidth / (float)(width - 1);
        float stepSizeY = (float)newHeight / (float)(height - 1);
        for( int x = 0; x < width - 1; x++ )
        {
            for( int y = 0; y < height - 1; y++ )
            {
                int xPositionInScaledPixels = (int)( x * stepSizeX );
                int yPositionInScaledPixels = (int)( y * stepSizeY );
                int pixelValue = getPixel( pixels, width, x, y );
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
                    int topLeft = pixels[ TwinUtils.getPositionForPixel( topLeftArrayPositionX, topLeftArrayPositionY, width ) ];
                    int topRight = pixels[ TwinUtils.getPositionForPixel( topLeftArrayPositionX + 1, topLeftArrayPositionY, width ) ];
                    int bottomLeft = pixels[ TwinUtils.getPositionForPixel( topLeftArrayPositionX, topLeftArrayPositionY + 1, width ) ];
                    int bottomRight = pixels[ TwinUtils.getPositionForPixel( topLeftArrayPositionX + 1, topLeftArrayPositionY + 1, width ) ];

                    int totalStepsX = (int)(( topLeftArrayPositionX + 1 ) * stepSizeX) - (int)( topLeftArrayPositionX * stepSizeX );
                    int totalStepsY = (int)(( topLeftArrayPositionY + 1 ) * stepSizeY) - (int)( topLeftArrayPositionY * stepSizeY );

                    int stepsX = x - (int)( topLeftArrayPositionX * stepSizeX );
                    int stepsY = y - (int)( topLeftArrayPositionY * stepSizeY );

                    scaledPixels[ arrayPosition ] = getBilinearInterpolatedColor( topLeft, topRight, bottomLeft, bottomRight, totalStepsX, totalStepsY, stepsX, stepsY );
                }
            }
        }
        return scaledPixels;
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

    private static int getPixel( int[] pixels, int pixelsWidth, int x, int y )
    {
        return pixels[ x + y * pixelsWidth ];
    }

}
