package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.color.ColorPalette;
import engine.graphics.shapes.Star;
import engine.input.Input;
import engine.twinUtils.Coordinate;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private int points;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();
        points = 5;
    }

    @Override
    public void update()
    {
        if( input.isKeyDown( VK_UP ) )
            points++;
        if( input.isKeyDown( VK_DOWN ) )
            points--;
    }

    @Override
    public void draw()
    {
        Coordinate offset = new Coordinate( input.getMouseX(), input.getMouseY() );
        Star.draw( points, 150, 90, offset, ColorPalette.getWhite(), renderer );
    }

}
