package game;

import engine.Engine;

public class Main
{
    public static void main( String[] args )
    {
        Engine engine = new Engine();
        PlayingState playingState = new PlayingState( engine );
        engine.setGameState( playingState );
        engine.start();
    }
}
