package engine.graphics.text;

import engine.graphics.Renderer;

public class Text
{
    private Font font;
    private String string;

    private int offsetX;
    private int offsetY;

    private int color;

    public Text( String string, Font font, int color )
    {
        this.string = string;
        this.font = font;
        this.color = color;
    }

    public void draw( Renderer renderer )
    {

    }

    public void setOffsets( int offsetX, int offsetY )
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
