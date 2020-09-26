package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.image.AnimationMode;
import engine.graphics.image.Image;
import engine.graphics.image.Sprite;
import engine.graphics.shapes.Rectangle;
import engine.graphics.text.BitmapFont;
import engine.input.Input;

import static java.awt.event.KeyEvent.*;

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

        image = new Image( "/test/tile.png" );
        image.setOffsets( engine.getWindow().getWidth() / 2 - image.getWidth() / 2, engine.getWindow().getHeight() / 2 - image.getHeight() / 2 );
        rectangle = new Rectangle( image.getWidth() + 2, image.getHeight() + 2, 0xff_FFFFFF );
        rectangle.setOffsets( image.getOffsetX() - 1, image.getOffsetY() - 1 );
        rotation = 0;

        sprite = new Sprite( "/test/explosion.png", 50, 50 );
        sprite.setAnimationMode( AnimationMode.LOOPING );
        sprite.setOffsets( 0, 0 );
        sprite.countXTilesPerRowWithEmptySpaces();

        BitmapFont.createBitmapFontFile( "Roboto-Regular", 14 );
    }


    @Override
    public void update()
    {
        sprite.update();

        if( input.isKey( VK_SPACE ) )
        {
            rotation++;
            if( rotation >= 360 )
                rotation = 0;
            image.reset();
            image.rotate( rotation );
            image.setOffsets( engine.getWindow().getWidth() / 2 - image.getWidth() / 2, engine.getWindow().getHeight() / 2 - image.getHeight() / 2 );
            rectangle = new Rectangle( image.getWidth() + 2, image.getHeight() + 2, 0xff_FFFFFF );
            rectangle.setOffsets( image.getOffsetX() - 1, image.getOffsetY() - 1 );
        }
    }

    @Override
    public void draw()
    {
        sprite.draw( renderer );
        image.draw( renderer );
    }

}
