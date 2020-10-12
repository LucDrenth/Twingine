package engine.graphics.effects;

import engine.graphics.PixelData;
import engine.twinUtils.TwinUtils;

public class Rotate
{
    public static PixelData rotate( PixelData pixels, int degreesToRotate )
    {
        // handle rotation below 0
        if( degreesToRotate < 0 )
        {
            degreesToRotate *= -1;
            degreesToRotate %= 360;
            degreesToRotate = 360 - degreesToRotate;
        }

        if( degreesToRotate > 360 )
            degreesToRotate %= 360;

        switch( degreesToRotate )
        {
            case 0:
                return pixels;
            case 90:
                return rotate90degrees( pixels );
            case 180:
                return rotate180degrees( pixels );
            case 270:
                return rotate270degrees( pixels );
            default:
                return rotateUncleanAngle( pixels, degreesToRotate );
        }

    }

    private static PixelData rotate90degrees( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                newPixels[ (pixels.getHeight() - y - 1) + x * pixels.getHeight() ] = pixels.getPixel( x, y );
            }
        }

        return new PixelData( newPixels, pixels.getHeight(), pixels.getWidth() );
    }

    private static PixelData rotate180degrees( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                newPixels[ (pixels.getWidth() - x - 1) + (pixels.getHeight() - y - 1) * pixels.getWidth() ] = pixels.getPixel( x, y );
            }
        }

        return new PixelData( newPixels, pixels.getWidth(), pixels.getHeight() );
    }

    private static PixelData rotate270degrees( PixelData pixels )
    {
        int[] newPixels = new int[ pixels.getPixels().length ];

        for( int x = 0; x < pixels.getWidth(); x++ )
        {
            for( int y = 0; y < pixels.getHeight(); y++ )
            {
                newPixels[ y + (pixels.getWidth() - x - 1) * pixels.getHeight() ] = pixels.getPixel( x, y );
            }
        }

        return new PixelData( newPixels, pixels.getHeight(), pixels.getWidth() );
    }

    private static PixelData rotateUncleanAngle( PixelData pixels, int degreesToRotate )
    {
        // calculate the new dimensions of the array after rotation
        int newWidth = getWidthAfterRotation( pixels.getWidth(), pixels.getHeight(), degreesToRotate );
        int newHeight = getHeightAfterRotation( pixels.getWidth(), pixels.getHeight(), degreesToRotate );
        int[] rotatedPixels = new int[ newWidth * newHeight ];

        // calculate cos and sin
        double rotationAsRadiant = Math.toRadians( 360 - degreesToRotate ); // subtract degreesToRotate from 360 to rotate clockwise
        double cos = Math.cos( rotationAsRadiant );
        double sin = Math.sin( rotationAsRadiant );

        // rotation goes around the top left corner. Calculate the lowestX and lowestY of the rotated pixels in order
        // adjust the offset of the pixels to put them in an array.
        int lowestX = getLowestX( pixels.getWidth(), pixels.getHeight(), cos, sin );
        int lowestY = getLowestY( pixels.getWidth(), pixels.getHeight(), cos, sin );

        // calculate values of pixels in the rotatedPixels array
        int widthAfterRotation = getWidthAfterRotation( pixels.getWidth(), pixels.getHeight(), degreesToRotate );
        int heightAfterRotation = getHeightAfterRotation( pixels.getWidth(), pixels.getHeight(), degreesToRotate );
        fillPixels( rotatedPixels, pixels, widthAfterRotation, degreesToRotate, lowestX, lowestY );

        return new PixelData( rotatedPixels, widthAfterRotation, heightAfterRotation );
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

    private static void fillPixels( int[] rotatedPixels, PixelData originalPixels, int newWidth, int degreesToRotate, int lowestX, int lowestY )
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
                int originalPixelPosition = xAfterRotateBack + yAfterRotateBack * originalPixels.getWidth();

                if( TwinUtils.pixelExists( originalPixels.getWidth(), originalPixels.getPixels().length / originalPixels.getWidth(), xAfterRotateBack, yAfterRotateBack ) )
                    rotatedPixels[ i ] = originalPixels.getPixel( xAfterRotateBack, yAfterRotateBack );
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
