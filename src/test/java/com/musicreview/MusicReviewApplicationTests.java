package com.musicreview;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MusicReviewApplicationTests {

    private static RestClient restClient =  RestClient.create();

    @Test
    void shouldReturnAnAlbumDetailWithCorrectId() {
        ResponseEntity<String> response = restClient
                .get()
                .uri("http://localhost:8080/album-details/1")
                .retrieve()
                .toEntity(String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Album example");
        String artist = documentContext.read("$.artist");
        assertThat(artist).isEqualTo("Artist example");
        Number releaseYear = documentContext.read("$.releaseYear");
        assertThat(releaseYear).isEqualTo(2024);
        List<String> trackList = documentContext.read("$.trackList");
        assertThat(trackList).isEqualTo(Arrays.asList("Track 1", "Track 2"));

    }

    @Test
    void shouldReturnNotFoundWithWrongId() {
        String responseBody = restClient
                .get()
                .uri("http://localhost:8080/album-details/6000")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                })
                .body(String.class);

        // Assert that the body is empty
        assertThat(responseBody).isNull();
    }



}
