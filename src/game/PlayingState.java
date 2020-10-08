package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.effects.EffectBox;
import engine.graphics.effects.Flip;
import engine.graphics.effects.GrayScale;
import engine.graphics.image.AnimationMode;
import engine.graphics.image.Image;
import engine.graphics.image.Sprite;
import engine.input.Input;

import static java.awt.event.KeyEvent.VK_SPACE;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Sprite sprite;
    private Image image;
    private EffectBox effectBox;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();

        sprite = new Sprite( "/test/explosion.png", 50, 50 );
        sprite.setAnimationMode( AnimationMode.LOOPING );
        sprite.countXTilesPerRowWithEmptySpaces();
        sprite.setOffsets( 10, 10 );

        image = new Image( "/test/lion.png" );
        image.setOffsets( engine.getWindow().getWidth() / 2 - image.getWidth() / 2, engine.getWindow().getHeight() / 2 - image.getHeight() / 2 );

        effectBox = new EffectBox( renderer, engine.getWindow(), 200, 200 );
    }

    @Override
    public void update()
    {
        sprite.update();

        if( input.isKeyDown( VK_SPACE ) )
        {
            image.setPixelData( GrayScale.convert( image.getPixelData() ) );
        }

        effectBox.setOffsets( input.getMouseX() - effectBox.getWidth() / 2, input.getMouseY() - effectBox.getHeight() / 2 );
    }

    @Override
    public void draw()
    {
        sprite.draw( renderer );
        image.draw( renderer );

        effectBox.reset();
        effectBox.setPixelData( Flip.vertical( effectBox.getPixelData() ) );
        effectBox.draw();

        for( int x = 0; x < effectBox.getWidth(); x++ )
        {
            renderer.setPixel( effectBox.getOffsetX() + x, effectBox.getOffsetY() - 1, 0xff_ffffff );
            renderer.setPixel( effectBox.getOffsetX() + x, effectBox.getOffsetY() + effectBox.getHeight() + 1, 0xff_ffffff );
        }
    }

}
