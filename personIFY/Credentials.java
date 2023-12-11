package com.example.demo;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class Credentials{
    private static final String clientId = "46002b30975c4fb4b1bcd20aa0884acb";
    private static final String clientSecret = "04982263a5d241b6aa0d277a68a1e513";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public static String getAccessToken() {
        try {
            // Retrieve client credentials
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Access token expires in: " + clientCredentials.getExpiresIn() + " seconds");

            return clientCredentials.getAccessToken();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error getting client credentials: " + e.getMessage());
            return null;
        }
    }
}
