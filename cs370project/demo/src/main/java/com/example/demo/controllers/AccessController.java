package com.example.demo.controllers;

import com.example.demo.Credentials;
import com.example.demo.GetArtist;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AccessController
{
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
