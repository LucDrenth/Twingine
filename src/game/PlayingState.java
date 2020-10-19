package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.color.ColorPalette;
import engine.graphics.shapes.Line;
import engine.input.Input;
import engine.twinUtils.Point;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Point start;
    private Point end;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();

        start = new Point( engine.getWindow().getWidth() / 2, engine.getWindow().getHeight() / 2 );
        end = new Point( 0, 0 );
    }

    @Override
    public void update()
    {
        end = new Point( input.getMouseX(), input.getMouseY() );
    }

    @Override
    public void draw()
    {
        Line.draw( start, end, ColorPalette.getWhite(), renderer );
    }

}
