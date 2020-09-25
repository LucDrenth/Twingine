package engine.twinUtils;

public class TwinUtils
{
    /*
     * get the pixel position from an x and y coordinate in a one dimensional array that represents 2d data
     */
    public static int getPositionForPixel( int x, int y, int width )
    {
        return x + y * width;
    }

    /*
     * checks if a position exists in a one dimensional array that represents 2d data based of x and y coordinate
     */
    public static boolean pixelExists( int pixelsWidth, int pixelsHeight, int x, int y )
    {
        return x >= 0 && x < pixelsWidth && y >= 0 && y < pixelsHeight;
    }

    public static float linearInterpolate( int point1, int point2, int totalSteps, int stepsFromPoint1 )
    {
        return ( point1 + (float)(point2 - point1) / (float)totalSteps * (float)stepsFromPoint1 );
    }

    public static float linearInterpolate( float point1, float point2, int totalSteps, int stepsFromPoint1 )
    {
        return ( point1 + (point2 - point1) / (float)totalSteps * (float)stepsFromPoint1 );
    }

    public static float bilinearInterpolate( int pointTopLeft, int pointTopRight, int pointBottomLeft, int pointBottomRight, int totalStepsX, int totalStepsY, int stepsFromX, int stepsFromY )
    {
        float linearInterpolationLeft = linearInterpolate( pointTopLeft, pointBottomLeft, totalStepsY, stepsFromY );
        float linearInterpolationRight = linearInterpolate( pointTopRight, pointBottomRight, totalStepsY, stepsFromY );
        return linearInterpolate( linearInterpolationLeft, linearInterpolationRight, totalStepsX, stepsFromX );
    }

    public static float bilinearInterpolate( float pointTopLeft, float pointTopRight, float pointBottomLeft, float pointBottomRight, int totalStepsX, int totalStepsY, int stepsFromX, int stepsFromY )
    {
        float linearInterpolationLeft = linearInterpolate( pointTopLeft, pointBottomLeft, totalStepsY, stepsFromY );
        float linearInterpolationRight = linearInterpolate( pointTopRight, pointBottomRight, totalStepsY, stepsFromY );
        return linearInterpolate( linearInterpolationLeft, linearInterpolationRight, totalStepsX, stepsFromX );
    }

    // get the lowest value in an int array
    public static int getMin( int[] inputArray )
    {
        int minValue = inputArray[0];
        for( int i = 1; i < inputArray.length; i++ )
        {
            if( inputArray[i] < minValue )
                minValue = inputArray[ i ];
        }
        return minValue;
    }

    /*
     * get the highest value in an int array
     */
    public static int getMax( int[] inputArray )
    {
        int maxValue = inputArray[0];
        for( int i = 1; i < inputArray.length; i++ )
        {
            if( inputArray[i] > maxValue )
                maxValue = inputArray[ i ];
        }
        return maxValue;
    }
}
