package com.example.demo;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.IOException;

@SpringBootTest
class GetArtistTest
{
    @Autowired
    private SpotifyApi spotifyApi;


    @Test
    void getArtistInfo() throws IOException, ParseException, SpotifyWebApiException {
        // Specify the artist ID for testing
        String artistId = "3TVXtAsR1Inumwj472S9r4"; //drake id

        // Make a request to get artist information
        GetArtistRequest getArtistRequest = spotifyApi.getArtist(artistId).build();
        Artist artist = getArtistRequest.execute();

        // Perform assertions on the retrieved artist information
        assertThat(artist).isNotNull();
        assertThat(artist.getName()).isEqualTo("Drake");
        // Add more assertions as needed
    }

    @Test
    void getArtistID()
    {

    }


    @Test
    void getSimilarArtists() {
    }

    @Test
    void getRandomArtist() {
    }


}