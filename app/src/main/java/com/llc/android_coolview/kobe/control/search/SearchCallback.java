package com.llc.android_coolview.kobe.control.search;

import java.util.List;

public interface SearchCallback {
    // runs in background
    void onSearchResult(String query, long hits, List<String> result);
}
