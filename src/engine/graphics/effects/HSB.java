/*
 * NOTES
 * - Color.RGBtoHSB returns a float[] where [0] is hue, [1] is saturation and [2] is brightness
 * - Color.HSBtoRGB returns an int that can be directly used by the pixel array
 */

package engine.graphics.effects;

import java.awt.*;

public class HSB
{
    // shiftAmount should be a float between 0 and 1
    public static int[] shiftHue( int[] pixels, float shiftAmount )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color rgb = new Color( pixels[ i ] );
            float[] hsb = Color.RGBtoHSB( rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null );
            newPixels[ i ] = Color.HSBtoRGB( hsb[ 0 ] + shiftAmount, hsb[ 1 ], hsb[ 2 ] );
        }

        return newPixels;
    }

    // hue should be a float between 0 and 1
    public static int[] setHue( int[] pixels, float hue )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color rgb = new Color( pixels[ i ] );
            float[] hsb = Color.RGBtoHSB( rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null );
            newPixels[ i ] = Color.HSBtoRGB( hue, hsb[ 1 ], hsb[ 2 ] );
        }

        return newPixels;
    }

    // shiftAmount should be a float value between -1 and 1
    public static int[] addSaturation( int[] pixels, float shiftAmount )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            // get HSB color values
            Color rgb = new Color( pixels[ i ] );
            float[] hsb = Color.RGBtoHSB( rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null );
            float saturation = hsb[ 1 ];

            // add saturation
            saturation += saturation * shiftAmount;
            if( saturation > 1f )
                saturation = 1f;
            else if( saturation < 0f )
                saturation = 0f;

            // convert HSB color back
            newPixels[ i ] = Color.HSBtoRGB( hsb[ 0 ], saturation, hsb[ 2 ] );
        }

        return newPixels;
    }

    // saturation should be a float between 0 and 1
    public static int[] setSaturation( int[] pixels, float saturation )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color rgb = new Color( pixels[ i ] );
            float[] hsb = Color.RGBtoHSB( rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null );
            newPixels[ i ] = Color.HSBtoRGB( hsb[ 0 ], saturation, hsb[ 2 ] );
        }

        return newPixels;
    }

    // shiftAmount should be a float between -1 and 1
    public static int[] addBrightness( int[] pixels, float shiftAmount )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            // get HSB color values
            Color rgb = new Color( pixels[ i ] );
            float[] hsb = Color.RGBtoHSB( rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null );
            float brightness = hsb[ 2 ];

            // add brightness
            brightness += brightness * shiftAmount;
            if( brightness > 1f )
                brightness = 1f;
            else if( brightness < 0f )
                brightness = 0f;

            // convert HSB color back
            newPixels[ i ] = Color.HSBtoRGB( hsb[ 0 ], hsb[ 1 ], brightness );
        }

        return newPixels;
    }

    // brightness should be a float between 0 and 1
    public static int[] setBrightness( int[] pixels, float brightness )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color rgb = new Color( pixels[ i ] );
            float[] hsb = Color.RGBtoHSB( rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null );
            newPixels[ i ] = Color.HSBtoRGB( hsb[ 0 ], hsb[ 1 ], brightness );
        }

        return newPixels;
    }

}