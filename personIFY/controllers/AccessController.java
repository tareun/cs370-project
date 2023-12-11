package com.example.demo.controllers;

import com.example.demo.Credentials;
import com.example.demo.GetArtist;

import com.example.demo.mysql.RecommendedArtist;
import com.example.demo.mysql.RecommendedArtistRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AccessController
{

    @Autowired
    private RecommendedArtistRepository recommendedArtistRepository;

    @PostConstruct
    public void clearTableOnStartup() {
        // This method will be called after the application context is initialized.
        // You can perform any initialization logic here.
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

                RecommendedArtist recommendedArtist = new RecommendedArtist();
                recommendedArtist.setArtistName(similarArtist);
                recommendedArtistRepository.save(recommendedArtist);

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
}
