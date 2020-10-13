package engine.graphics.pixeldata;

public class PixelData
{
    private int[] pixels;
    private int width;
    private int height;

    public PixelData( int width, int height )
    {
        pixels = new int[ width * height ];
        this.width = width;
        this.height = height;
    }

    public PixelData( int[] pixels, int width, int height )
    {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int[] getPixels()
    {
        return pixels;
    }

    public int getPixel( int x, int y )
    {
        return pixels[ x + y * width ];
    }

    public int getPixel( int arrayIndex )
    {
        return pixels[ arrayIndex ];
    }

    public void setPixels( int[] pixels )
    {
        this.pixels = pixels;
    }

    public void setPixel( int x, int y, int value )
    {
        pixels[ x + y * width ] = value;
    }

    public void setPixel( int arrayIndex, int value )
    {
        pixels[ arrayIndex ] = value;
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
