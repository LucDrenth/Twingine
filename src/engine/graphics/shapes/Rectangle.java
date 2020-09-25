package engine.graphics.shapes;

import engine.graphics.Renderer;

public class Rectangle
{
    private int[][] pixels;

    private int color;
    private int alphaPercentage;

    private int width;
    private int height;
    private int offsetX;
    private int offsetY;

    public Rectangle( int width, int height, int color )
    {
        this.width = width;
        this.height = height;
        this.color = color;

        alphaPercentage = 100;
        pixels = new int[width][height];

        for( int x = 0; x < pixels.length; x++ )
        {
            for( int y = 0; y < pixels[0].length; y++ )
            {
                pixels[x][y] = color;
            }
        }
    }

    public void draw( Renderer renderer )
    {

        for( int x = 0; x < pixels.length; x++ )
        {
            for( int y = 0; y < pixels[0].length; y++ )
            {
                renderer.setPixel( x + offsetX, y + offsetY, color, alphaPercentage );
            }
        }
    }

    public void setOffsets( int x, int y )
    {
        this.offsetX = x;
        this.offsetY = y;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor( int color )
    {
        this.color = color;
    }

    public int getOffsetX()
    {
        return offsetX;
    }

    public int getOffsetY()
    {
        return offsetY;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getAlphaPercentage()
    {
        return alphaPercentage;
    }

    public void setAlphaPercentage( int alphaPercentage )
    {
        this.alphaPercentage = alphaPercentage;
    }
}
