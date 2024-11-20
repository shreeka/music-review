package com.musicreview;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/album-details")
public class AlbumController {

    @GetMapping("/{requestId}")
    private ResponseEntity<Album> findAlbumById(@PathVariable int requestId) {
        if (requestId == 1) {
            Album album = new Album(1,"Album example", "Artist example",2024,
                    Arrays.asList("Track 1", "Track 2"));
            return ResponseEntity.ok(album);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }
}
