package com.example.demo.controllers;

import com.example.demo.Credentials;
import com.example.demo.GetArtist;

import com.example.demo.mysql.RecommendedArtist;
import com.example.demo.mysql.RecommendedArtistRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AccessController
{

    @Autowired
    private RecommendedArtistRepository recommendedArtistRepository;

    @PostConstruct
    public void clearTableOnStartup() {
        // This method will be called after the application context is initialized.
        System.out.println("Clearing the table on startup...");

        recommendedArtistRepository.clearTable();
        recommendedArtistRepository.resetAutoIncrement();

    }

    @GetMapping("/api/retrieve-data")
    public String getData(@RequestParam String artistName)
    {
        String accessToken = Credentials.getAccessToken();

        if(accessToken != null)
        {
            //String artistID = "7n2Ycct7Beij7Dj7meI4X0";
            //String artistID = GetArtist.getArtistID(accessToken,artistName);

            //String similarArtist = GetArtist.getRandomArtist(accessToken, artistName);

            String similarArtist = GetArtist.getRandomArtist(accessToken, artistName);


            if(!similarArtist.isEmpty())
            {
                String artistID = GetArtist.getArtistID(accessToken,similarArtist);
                String artistInfo = GetArtist.getArtistInfo(accessToken, artistID);
                //String artistGenre = GetArtist.getArtistGenre(accessToken, artistID);

                /*
                RecommendedArtist recommendedArtist = new RecommendedArtist();
                recommendedArtist.setArtistName(similarArtist);
                recommendedArtistRepository.save(recommendedArtist);
                */
                return artistInfo;
            }
            else
            {
                return "Failed to get artistID";
            }
        }
        else
        {
            return "Failed to get token";
        }
        //return Credentials.getAccessToken();
    }

    @Transactional
    @PostMapping("/api/store-artist")
    public String storeArtist(@RequestParam String artistName)
    {

        // Split the received data into artist name and genres
        String[] parts = artistName.split("\nGenres: ");

        if (parts.length == 2) {
            String artName = parts[0];
            String genres = parts[1];

            // Check if the artist already exists in the database
            if (recommendedArtistRepository.existsByArtistName(artName)) {
                return "Artist already exists in the database.";
            }

            // Save the artist to the database
            RecommendedArtist recommendedArtist = new RecommendedArtist();
            recommendedArtist.setArtistName(artName);
            recommendedArtist.setGenres(genres);
            recommendedArtistRepository.save(recommendedArtist);



            return "Artist stored in the database.";
        }else
        {
            return "invalid data format";
        }
    }

    @GetMapping("/api/get-stored-artists")
    public List<RecommendedArtist> getStoredArtists() {
        List<RecommendedArtist> storedArtists = recommendedArtistRepository.findAll();

        // Log the retrieved data
        storedArtists.forEach(artist -> System.out.println("Artist: " + artist.getArtistName() + ", Genres: " + artist.getGenres()));

        return storedArtists;

        //return recommendedArtistRepository.findAll();
    }
}
