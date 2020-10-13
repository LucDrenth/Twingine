package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
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

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();
        radius = 15;
        circle = new Circle( radius, 0xff_f3c802 );
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
            circle = new Circle( radius, 0xff_f3c802 );
        }
        else if( input.isKey( VK_DOWN ) )
        {
            radius++;
            circle = new Circle( radius, 0xff_f3c802 );
        }
    }

    @Override
    public void draw()
    {
        circle.draw( renderer );
    }

}
