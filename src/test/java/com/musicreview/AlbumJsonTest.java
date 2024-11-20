package com.musicreview;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class AlbumJsonTest {

    @Autowired
    private JacksonTester<Album> json;
    private int albumId;
    private String title ;
    private String artist;
    private int releaseYr;
    private List<String> trackList ;

    @BeforeEach
    void setUp() {
        albumId = 1;
        title = "Album example";
        artist = "Artist example";
        releaseYr = 2024;
        trackList = Arrays.asList("Track 1", "Track 2");
    }

    @Test
    void AlbumSerializationTest() throws IOException {
        Album album = new Album(albumId,title, artist, releaseYr, trackList);
        assertThat(json.write(album)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(album)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(album)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(albumId);
        assertThat(json.write(album)).hasJsonPathStringValue("@.title");
        assertThat(json.write(album)).extractingJsonPathStringValue("@.title")
                .isEqualTo(title);
        assertThat(json.write(album)).hasJsonPathStringValue("@.artist");
        assertThat(json.write(album)).extractingJsonPathStringValue("@.artist")
                .isEqualTo(artist);
        assertThat(json.write(album)).hasJsonPathNumberValue("@.releaseYear");
        assertThat(json.write(album)).extractingJsonPathNumberValue("@.releaseYear")
                .isEqualTo(releaseYr);
        assertThat(json.write(album)).hasJsonPathArrayValue("@.trackList");
        assertThat(json.write(album)).extractingJsonPathArrayValue("@.trackList")
                .isEqualTo(trackList);
    }

    @Test
    void AlbumDeserializationTest() throws IOException {
        String expected = """
           {
             "id" : 1,
             "title": "Album example",
             "artist": "Artist example",
             "releaseYear": 2024,
             "trackList": ["Track 1", "Track 2"]
           }
           """;
        assertThat(json.parse(expected))
                .isEqualTo(new Album(albumId,title, artist, releaseYr, trackList));
        assertThat(json.parseObject(expected).id()).isEqualTo(albumId);
        assertThat(json.parseObject(expected).title()).isEqualTo(title);
        assertThat(json.parseObject(expected).artist()).isEqualTo(artist);
        assertThat(json.parseObject(expected).releaseYear()).isEqualTo(releaseYr);
        assertThat(json.parseObject(expected).trackList()).isEqualTo(trackList);
    }
}
