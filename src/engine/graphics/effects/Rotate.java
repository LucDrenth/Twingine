package engine.graphics.effects;

import engine.twinUtils.TwinUtils;

public class Rotate
{
    public static int[] rotate( int[] pixels, int width, int height, int degreesToRotate )
    {
        switch( degreesToRotate )
        {
            case 0:
                return pixels;
            case 90:
                return rotate90degrees( pixels, width, height );
            case 180:
                return rotate180degrees( pixels, width, height );
            case 270:
                return rotate270degrees( pixels, width, height );
            default:
                return rotateUncleanAngle( pixels, width, height, degreesToRotate );
        }

    }

    private static int[] rotate90degrees( int[] pixels, int width, int height )
    {
        int[] newPixels = new int[ width * height ];

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                newPixels[ (height - y - 1) + x * height ] = pixels[ x + y * width ];
            }
        }

        return newPixels;
    }

    private static int[] rotate180degrees( int[] pixels, int width, int height )
    {
        int[] newPixels = new int[ width * height ];

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                newPixels[ (width - x - 1) + (height - y - 1) * width ] = pixels[ x + y * width ];
            }
        }

        return newPixels;
    }

    private static int[] rotate270degrees( int[] pixels, int width, int height )
    {
        int[] newPixels = new int[ width * height ];

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                newPixels[ y + (width - x - 1) * height ] = pixels[ x + y * width ];
            }
        }

        return newPixels;
    }

    private static int[] rotateUncleanAngle( int[] pixels, int width, int height, int degreesToRotate )
    {
        // calculate the new dimensions of the array after rotation
        int newWidth = getWidthAfterRotation( width, height, degreesToRotate );
        int newHeight = getHeightAfterRotation( width, height, degreesToRotate );
        int[] rotatedPixels = new int[ newWidth * newHeight ];

        // calculate cos and sin
        double rotationAsRadiant = Math.toRadians( 360 - degreesToRotate ); // subtract degreesToRotate from 360 to rotate clockwise
        double cos = Math.cos( rotationAsRadiant );
        double sin = Math.sin( rotationAsRadiant );

        // rotation goes around the top left corner. Calculate the lowestX and lowestY of the rotated pixels in order
        // adjust the offset of the pixels to put them in an array.
        int lowestX = getLowestX( width, height, cos, sin );
        int lowestY = getLowestY( width, height, cos, sin );

        // calculate values of pixels in the rotatedPixels array
        fillPixels( rotatedPixels, pixels, width, getWidthAfterRotation( width, height, degreesToRotate ), degreesToRotate, lowestX, lowestY );

        return rotatedPixels;
    }

    private static int getLowestX( int width, int height, double cos, double sin )
    {
        int[] xCoordinatesAfterRotation = new int[ width * height ];

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                xCoordinatesAfterRotation[ x + y * width ] = (int)( x * cos + y * sin );
            }
        }

        return TwinUtils.getMin( xCoordinatesAfterRotation );
    }

    private static int getLowestY( int width, int height, double cos, double sin )
    {
        int[] yCoordinatesAfterRotation = new int[ width * height ];

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                yCoordinatesAfterRotation[ x + y * width ] = (int)( -x * sin + y * cos );
            }
        }

        return TwinUtils.getMin( yCoordinatesAfterRotation );
    }

    private static void fillPixels( int[] rotatedPixels, int[] originalPixels, int originalPixelsWidth, int newWidth, int degreesToRotate, int lowestX, int lowestY )
    {
        double rotationAsRadiant = Math.toRadians( degreesToRotate );
        double cos = Math.cos( rotationAsRadiant );
        double sin = Math.sin( rotationAsRadiant );

        for( int i = 0; i < rotatedPixels.length; i++ )
        {
            if( rotatedPixels[ i ] == 0 )
            {
                int x = getXAfterLowestXAdjustment( i % newWidth, lowestX, degreesToRotate );
                int y = getYAfterLowestYAdjustment( i / newWidth, lowestY, degreesToRotate );

                int xAfterRotateBack = (int)( x * cos + y * sin );
                int yAfterRotateBack = (int)( -x * sin + y * cos );
                int originalPixelPosition = xAfterRotateBack + yAfterRotateBack * originalPixelsWidth;

                if( TwinUtils.pixelExists( originalPixelsWidth, originalPixels.length / originalPixelsWidth, xAfterRotateBack, yAfterRotateBack ) )
                    rotatedPixels[ i ] = originalPixels[ xAfterRotateBack + yAfterRotateBack * originalPixelsWidth ];
            }
        }
    }

    private static int getXAfterLowestXAdjustment( int x, int lowestX, int degreesToRotate )
    {
        if( degreesToRotate < 270 )
            return x + lowestX;
        else
            return x;
    }

    private static int getYAfterLowestYAdjustment( int y, int lowestY, int degreesToRotate )
    {
        if( degreesToRotate > 90 )
            return y + lowestY;
        else
            return y;
    }

    public static int getWidthAfterRotation( int width, int height, int rotationDegrees )
    {
        rotationDegrees %= 360;
        switch( rotationDegrees )
        {
            case 0: return width;
            case 90: return height;
            case 180: return width;
            case 270: return height;
            default:
                rotationDegrees %= 180;
                if( rotationDegrees < 90 )
                    return (int)( width * Math.cos( Math.toRadians( rotationDegrees ) ) + height * Math.sin( Math.toRadians( rotationDegrees ) ) );
                else
                    return (int)( height * Math.cos( Math.toRadians( (rotationDegrees - 90) ) ) + width * Math.sin( Math.toRadians( (rotationDegrees - 90) ) ) );
        }
    }

    public static int getHeightAfterRotation( int width, int height, int rotationDegrees )
    {
        rotationDegrees %= 360;
        switch( rotationDegrees )
        {
            case 0:
                return height;
            case 90:
                return width;
            case 180:
                return height;
            case 270:
                return width;
            default:
                rotationDegrees %= 180;
                if( rotationDegrees < 90 )
                    return (int) ( width * Math.sin( Math.toRadians( rotationDegrees ) ) + height * Math.cos( Math.toRadians( rotationDegrees ) ) );
                else
                    return (int) ( height * Math.sin( Math.toRadians( ( rotationDegrees - 90 ) ) ) + width * Math.cos( Math.toRadians( ( rotationDegrees - 90 ) ) ) );
        }
    }
}
