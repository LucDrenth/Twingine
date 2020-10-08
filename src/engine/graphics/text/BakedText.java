package engine.graphics.text;

import engine.graphics.PixelData;
import engine.graphics.Renderer;
import engine.twinUtils.Point;

public class BakedText
{
    private PixelData pixelData;
    private Point offset;

    public BakedText( Text text )
    {
        pixelData = bake( text );
    }

    public void draw( Renderer renderer )
    {
        renderer.draw( pixelData, offset );
    }

    private PixelData bake( Text text )
    {
        int width = calculateBakedWidth();
        int height = calculateBakedHeight();
        int[] pixels = new int[ width * height ];

        return new PixelData( pixels, width, height );
    }

    private int calculateBakedWidth()
    {
        return 0;
    }

    private int calculateBakedHeight()
    {
        return 0;
    }
}
