package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.effects.Scale;
import engine.graphics.image.Image;
import engine.graphics.shapes.Circle;
import engine.input.Input;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Circle circle;
    private int radius;

    private Image image;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();
        radius = 15;
        circle = new Circle( radius, 0xff_f3c802 );

        image = new Image( "/test/tile.png" );
        image.setPixelData( Scale.scale( image.getPixelData(), 3 ) );
        image.setOffsets( engine.getWindow().getWidth() / 2 - image.getWidth() / 2, engine.getWindow().getHeight() / 2 - image.getHeight() / 2 );
        image.roundCorners( image.getWidth() / 2 );
    }

    @Override
    public void update()
    {
        updateCircleSize();
        circle.setOffset( input.getMouseX() - circle.getPixels().getWidth() / 2, input.getMouseY() - circle.getPixels().getHeight() / 2 );
    }

    private void updateCircleSize()
    {
        if( input.isKey( VK_UP ) )
        {
            radius--;
            if( radius <= 1 ) radius = 1;
            circle.setRadius( radius );
        }
        else if( input.isKey( VK_DOWN ) )
        {
            radius++;
            circle.setRadius( radius );
            circle.fill();
        }
    }

    @Override
    public void draw()
    {
        circle.draw( renderer );
        image.draw( renderer );
    }

}
