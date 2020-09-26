package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.image.Image;
import engine.graphics.image.Sprite;
import engine.graphics.shapes.Rectangle;
import engine.graphics.text.BitmapFont;
import engine.input.Input;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Image image;
    private Sprite sprite;
    private Rectangle rectangle;

    private int rotation;

    public PlayingState( Engine engine )
    {
        this.engine = engine;

        input = engine.getInput();
        renderer = engine.getRenderer();

        BitmapFont.createBitmapFontFile( "Roboto-Regular", 14 );
        BitmapFont.createBitmapFontFile( "Josefin_Sans", 40 );
    }


    @Override
    public void update()
    {

    }

    @Override
    public void draw()
    {

    }

}
