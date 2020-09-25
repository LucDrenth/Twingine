package engine.twinUtils;

import java.util.ArrayList;

public class Dice
{
    public static int rollInt( int min, int max )
    {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public static int[] randomlyOrderedArray( int length, int start )
    {
        // initialize the list of numbers that need to be put in a random order
        ArrayList<Integer> numbersToRandomize = new ArrayList<>();
        for( int i = start; i < length; i++ )
        {
            numbersToRandomize.add( i );
        }

        // create an array that the numbers will be put in
        int[] randomlyOrderedNumbers = new int[length];

        // put the numbers of the list in the array in a random order
        for( int i = 0; i < length; i++ )
        {
            int indexToPutInArray = Dice.rollInt( 0, numbersToRandomize.size() - 1 );
            randomlyOrderedNumbers[ i ] = numbersToRandomize.get( indexToPutInArray );
            numbersToRandomize.remove( indexToPutInArray );
        }

        return randomlyOrderedNumbers;
    }
}
