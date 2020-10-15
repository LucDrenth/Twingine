package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.shapes.Rectangle;
import engine.input.Input;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Rectangle rectangle;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();

        rectangle = new Rectangle( 200, 100, 0xff_f3c802 );
        rectangle.roundCorners( rectangle.getHeight() / 2 );
    }

    @Override
    public void update()
    {
        rectangle.setOffsets( input.getMouseX() - rectangle.getWidth() / 2, input.getMouseY() - rectangle.getHeight() / 2 );
    }

    @Override
    public void draw()
    {
        rectangle.draw( renderer );
    }

}
