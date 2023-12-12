package com.example.demo;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsRelatedArtistsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GetArtist {
    public static String getArtistID(String accessToken, String artistName) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        try {
            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artistName).build(); // Fetch information about the specified artist
            Paging<Artist> artistPaging = searchArtistsRequest.execute();

            List<Artist> artists = List.of(artistPaging.getItems());

            if (!artists.isEmpty()) {
                return artists.get(0).getId();
            } else {
                System.out.println("Artist not found with the name: " + artistName);
                return null;
            }
            // Display retrieved artist information
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error getting artist ID: " + e.getMessage());
            return null;
        }
    }

    public static String getArtistInfo(String accessToken, String artistId) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        try {

            Artist artist = spotifyApi.getArtist(artistId).build().execute();
            /*
            // Return information about the artist
            return "" + artist.getName() + "\nGenres: " + String.join(", ", artist.getGenres());
            //return "" + artist.getName();

            */

            // Check if genres is not null before joining
            String genres = artist.getGenres() != null ? String.join(", ", artist.getGenres()) : "N/A";

            // Return information about the artist
            return "" + artist.getName() + "\nGenres: " + genres;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error getting artist information: " + e.getMessage());
            return "Error retrieving artist information";
        }
    }


    public static List<String> getSimilarArtists(String accessToken, String artistName) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        try {
            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artistName).build();
            Paging<Artist> artistPaging = searchArtistsRequest.execute();

            List<Artist> artists = List.of(artistPaging.getItems());
            List<String> similarArtistsNames = new ArrayList<>();

            if (!artists.isEmpty())
            {
                String seedArtistId = artists.get(0).getId();
                GetArtistsRelatedArtistsRequest artistsRelatedArtistsRequest = spotifyApi
                        .getArtistsRelatedArtists(seedArtistId)
                        .build();

                Artist[] artistPagingRec = artistsRelatedArtistsRequest.execute();

                for (Artist relatedArtist : artistPagingRec)
                {
                    similarArtistsNames.add(relatedArtist.getName());
                }

            }
            else
            {
                System.out.println("No artist found with the name: " + artistName);
            }

            return similarArtistsNames;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error getting similar artists: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static String getRandomArtist(String accessToken, String artistName)
    {
        List<String> similarArtistsNames = getSimilarArtists(accessToken, artistName);

        if (!similarArtistsNames.isEmpty())
        {
            // Get a random index from the list
            int randomIndex = new Random().nextInt(similarArtistsNames.size());

            // Return the artist at the random index
            return similarArtistsNames.get(randomIndex);
        }
        else
        {
            return "No similar artists found";
        }
    }

}
