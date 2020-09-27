package engine.twinUtils;

public class StringUtils
{
    public static int countWords( String string )
    {
        int words = 0;

        if( string.length() > 0 )
        {
            words = 1;

            // startAt and stopAt variables to not count white spaces in front and end of string
            int startAt = 0;
            for( int i = 0; i < string.length(); i++ )
            {
                if( string.charAt( i ) == ' ' )
                    startAt++;
                else
                    break;
            }

            int stopAt = string.length();
            for( int i = string.length() - 1; i > 0; i-- )
            {
                if( string.charAt( i ) == ' ' )
                    stopAt--;
                else
                    break;
            }

            for( int i = startAt; i < stopAt    ; i++ )
            {
                if( string.charAt( i ) == ' ' )
                    words++;
            }
        }

        return words;
    }

    public static String[] getWords( String string )
    {
        String[] words = new String[ countWords( string ) ];

        StringBuilder stringBuilder = new StringBuilder();
        int currentWordIndex = 0;

        // startAt and stopAt variables to not count white spaces in front and end of string
        int startAt = 0;
        for( int i = 0; i < string.length(); i++ )
        {
            if( string.charAt( i ) == ' ' )
                startAt++;
            else
                break;
        }

        int stopAt = string.length();
        for( int i = string.length() - 1; i > 0; i-- )
        {
            if( string.charAt( i ) == ' ' )
                stopAt--;
            else
                break;
        }

        for( int i = startAt; i < stopAt; i++ )
        {
            if( string.charAt( i ) == ' ' )
            {
                words[ currentWordIndex ] = stringBuilder.toString();
                stringBuilder = new StringBuilder();
                currentWordIndex++;
            }
            else
            {
                stringBuilder.append( string.charAt( i ) );

                if( i == stopAt - 1 )
                    words[ currentWordIndex ] = stringBuilder.toString();
            }
        }

        return words;
    }
}
