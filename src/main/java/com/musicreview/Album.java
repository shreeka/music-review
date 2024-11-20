package com.musicreview;

import java.util.List;

record Album(int id, String title, String artist, int releaseYear, List<String> trackList) {
}
