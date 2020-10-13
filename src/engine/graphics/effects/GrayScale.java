package engine.graphics.effects;

import engine.graphics.pixeldata.PixelData;

import java.awt.*;

public class GrayScale
{
    private static GrayScaleConversionType conversionType = GrayScaleConversionType.LUMA;

    public static PixelData convert( PixelData pixelData )
    {
        int[] newPixels = new int[ pixelData.getPixels().length ];

        switch( conversionType )
        {
            case AVERAGING:
                newPixels = convertUsingAveraging( pixelData.getPixels() );
                break;
            case LUMA:
                newPixels = convertUsingLuma( pixelData.getPixels() );
                break;
            case DESATURATION:
                newPixels = convertUsingDesaturation( pixelData.getPixels() );
                break;
            case DARKEST:
                newPixels = convertUsingDarkest( pixelData.getPixels() );
                break;
            case LIGHTEST:
                newPixels = convertUsingLightest( pixelData.getPixels() );
                break;
            case SINGLE_COLOR_CHANNEL_RED:
                newPixels = convertUsingSingleColorChannelRed( pixelData.getPixels() );
                break;
            case SINGLE_COLOR_CHANNEL_GREEN:
                newPixels = convertUsingSingleColorChannelGreen( pixelData.getPixels() );
                break;
            case SINGLE_COLOR_CHANNEL_BLUE:
                newPixels = convertUsingSingleColorChannelBlue( pixelData.getPixels() );
                break;
        }

        return new PixelData( newPixels, pixelData.getWidth(), pixelData.getHeight() );
    }

    // numberOfGrays is an integer that should be a minimum of 2 and a maximum of 256
    public static PixelData convertUsingCustomNumberOfGrays( PixelData pixelData, int numberOfGrays )
    {
        PixelData newPixelData = convert( pixelData );

        for( int i = 0; i < newPixelData.getPixels().length; i++ )
        {
            float conversionFactor = 255f / ( numberOfGrays - 1 );
            int oldGrey = new Color( newPixelData.getPixel( i ) ).getRed();
            int newGrey = (int)( (int)( oldGrey / conversionFactor ) * conversionFactor );
            newPixelData.setPixel( i, new Color( newGrey, newGrey, newGrey ).hashCode() );
        }

        return newPixelData;
    }

    private static int[] convertUsingAveraging( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int sum = color.getRed() + color.getGreen() + color.getBlue();
            int average = sum / 3;
            newPixels[ i ] = new Color( average, average, average ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingLuma( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = (int)( color.getRed() * 0.3 + color.getGreen() * 0.59 + color.getBlue() * 0.11 );
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingDesaturation( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = ( min( color ) + max( color ) ) / 2;
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingDarkest( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = min( color );
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingLightest( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = max( color );
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingSingleColorChannelRed( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = color.getRed();
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingSingleColorChannelGreen( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = color.getGreen();
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int[] convertUsingSingleColorChannelBlue( int[] pixels )
    {
        int[] newPixels = new int[ pixels.length ];

        for( int i = 0; i < pixels.length; i++ )
        {
            Color color = new Color( pixels[ i ] );
            int gray = color.getBlue();
            newPixels[ i ] = new Color( gray, gray, gray ).hashCode();
        }

        return newPixels;
    }

    private static int min( Color color )
    {
        int min = color.getRed();

        if( color.getGreen() < min )
            min = color.getGreen();
        if( color.getBlue() < min )
            min = color.getGreen();

        return min;
    }

    private static int max( Color color )
    {
        int max = color.getRed();

        if( color.getGreen() > max )
            max = color.getGreen();
        if( color.getBlue() > max )
            max = color.getGreen();

        return max;
    }

    public static void setConversionType( GrayScaleConversionType conversionType )
    {
        GrayScale.conversionType = conversionType;
    }
}
