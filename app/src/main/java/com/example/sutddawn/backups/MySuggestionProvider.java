package com.example.sutddawn.backups;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider(){
        //passes the name of the search authority and a database mode
        setupSuggestions(AUTHORITY, MODE);
    }
}
