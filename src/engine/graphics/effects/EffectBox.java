package engine.graphics.effects;

import engine.graphics.PixelData;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.twinUtils.Point;
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

    private PixelData pixelData;
    private Point offset;

    private int blur;

    public EffectBox( Renderer renderer, Window window, int width, int height )
    {
        this.renderer = renderer;
        this.window = window;

        pixelData = new PixelData( width, height );
        offset = new Point( 0, 0 );
    }

    public void reset()
    {
        for( int x = 0; x < pixelData.getWidth(); x++ )
        {
            for( int y = 0; y < pixelData.getHeight(); y++ )
            {
                if( TwinUtils.pixelExists( window.getWidth(), window.getHeight(), x + offset.getX(), y + offset.getY() ) )
                    pixelData.setPixel( x, y, renderer.getPixel( x + offset.getX(), y + offset.getY() ) );
            }
        }

    }

    public void draw()
    {
        renderer.draw( pixelData, offset );
    }

    public void setOffsets( int offsetX, int offsetY )
    {
        offset.set( offsetX, offsetY );
    }

    public int getWidth()
    {
        return pixelData.getWidth();
    }

    public int getHeight()
    {
        return pixelData.getHeight();
    }

    public PixelData getPixelData()
    {
        return pixelData;
    }

    public void setPixelData( PixelData pixelData )
    {
        this.pixelData = pixelData;
    }

    public int getOffsetX()
    {
        return offset.getX();
    }

    public int getOffsetY()
    {
        return offset.getY();
    }
}
