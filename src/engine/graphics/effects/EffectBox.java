package engine.graphics.effects;

import engine.graphics.Window;
import engine.graphics.Renderer;
import engine.twinUtils.TwinUtils;

/*
 * How To Use
 *
 * Don't apply effects in the update() method of the GameStateManagers. Instead, do it in the draw() method. This is
 * because the Renderer its pixels[] gets cleared right before calling the GameStateManager.draw() method. So when you
 * reset and apply filters in the update method it will use the old pixels[] and thus it will use EffectBox its own
 * (previous) filter to put a filter over (so the filters stack over time).
 * Use the following order in the draw method:
 * 1. reset()
 * 2. apply effects and filters (such as blur() or/and toGrayScale())
 * 3. draw()
 */
public class EffectBox
{
    private Renderer renderer;
    private Window window;

    private int[] pixels;

    private int width;
    private int height;
    private int offsetX;
    private int offsetY;

    private int blur;

    public EffectBox( Renderer renderer, Window window, int width, int height )
    {
        this.renderer = renderer;
        this.window = window;
        this.width = width;
        this.height = height;

        pixels = new int[ width * height ];
        offsetX = 0;
        offsetY = 0;
    }

    public void reset()
    {
        pixels = new int[ width * height ];
        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                if( TwinUtils.pixelExists( window.getWidth(), window.getHeight(), x + offsetX, y + offsetY ) )
                    pixels[ x + y * width ] = renderer.getPixel( x + offsetX, y + offsetY );
            }
        }

    }

    public void draw()
    {
        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                renderer.setPixel( x + offsetX, y + offsetY, pixels[ TwinUtils.getPositionForPixel( x, y, width ) ] );
            }
        }
    }

    // EFFECTS (look in the Class of the effects to see the proper values for the parameters or look in the Image class at the same function)
    public void blur( int blurPercentage, boolean blurInbound ){ pixels = Blur.blur( pixels, width, height, blurPercentage, blurInbound ); }
    public void toGrayScale(){ pixels = GrayScale.convert( pixels ); }
    public void toGrayScale( int numberOfGreys ){ pixels = GrayScale.convertUsingCustomNumberOfGrays( pixels, numberOfGreys ); }
    public void toBlackAndWhite( int border ){ pixels = BlackAndWhite.convert( pixels, border ); }
    public void shiftHue     ( float shiftAmount     ){ pixels = HSB.shiftHue     ( pixels, shiftAmount     ); }
    public void addSaturation( float extraSaturation ){ pixels = HSB.addSaturation( pixels, extraSaturation ); }
    public void addBrightness( float extraBrightness ){ pixels = HSB.addBrightness( pixels, extraBrightness ); }
    public void setHue       ( float hue        ){ pixels = HSB.setHue( pixels, hue               ); }
    public void setSaturation( float saturation ){ pixels = HSB.setSaturation( pixels, saturation ); }
    public void setBrightness( float brightness ){ pixels = HSB.setBrightness( pixels, brightness ); }
    public void flipHorizontal(){ pixels = Flip.horizontal( pixels, width, height ); }
    public void flipVertical(){ pixels = Flip.vertical( pixels, width, height ); }
    public void swapRedAndGreen (){ pixels = RGB.swapRedAndGreen( pixels ); }
    public void swapRedAndBlue  (){ pixels = RGB.swapRedAndBlue( pixels ); }
    public void swapGreenAndBlue(){ pixels = RGB.swapGreenAndBlue( pixels ); }
    public void increaseRed  ( int percentage ){ pixels = RGB.increaseRed  ( pixels, percentage ); }
    public void increaseGreen( int percentage ){ pixels = RGB.increaseGreen( pixels, percentage ); }
    public void increaseBlue ( int percentage ){ pixels = RGB.increaseBlue ( pixels, percentage ); }
    public void changeTemperature( int temperatureChange ){ pixels = Temperature.change( pixels, temperatureChange ); }
    public void changeTint( int tintChange )
    {
        pixels = Tint.change( pixels, tintChange );
    }
    public void mix( int spreadWidth, int spreadHeight ){ pixels = Mix.randomizeInsideBox( pixels, width, height, spreadWidth, spreadHeight ); }
    public void engrave( int shiftX, int shiftY, int extraColor ){ pixels = Engrave.engrave( pixels, width, height, shiftX, shiftY, extraColor ); }
    public void sepia(){ pixels = Sepia.sepia( pixels ); }

    public void scale( float scaleX, float scaleY )
    {
        int[] scaledPixels = Scale.scale( pixels, width, height, scaleX, scaleY );
        int newWidth = (int)( width * scaleX );
        int newHeight = (int)( height * scaleY );

        int startX = ( newWidth - width ) / 2;
        int startY = ( newHeight - height ) / 2;
        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                pixels[ x + y * width ] = scaledPixels[ (x + startX) + (y + startY) * newWidth ];
            }
        }
    }

    public void setOffsets( int offsetX, int offsetY )
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
