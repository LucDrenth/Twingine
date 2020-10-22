package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.shapes.Line;
import engine.input.Input;
import engine.twinUtils.Coordinate;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Line line;
    private int points;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();
        points = 5;

        line = new Line( new Coordinate( 100, 100 ), new Coordinate( 200, 200 ), 0xff_f3c802 );
    }

    @Override
    public void update()
    {
        if( input.isKey( VK_UP ) )
            points++;
        if( input.isKey( VK_DOWN ) )
            points--;

        line = new Line( new Coordinate( engine.getWindow().getWidth() / 2, engine.getWindow().getHeight() / 2 ), 50, points, 0xff_f3c802 );
        line.setOffsets( engine.getWindow().getWidth() / 2 - line.getPixelData().getWidth() / 2, engine.getWindow().getHeight() / 2 - line.getPixelData().getHeight() / 2 );
    }

    @Override
    public void draw()
    {
        line.draw( renderer );
    }

}
