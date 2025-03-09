package com.example.tnr;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v133.network.Network;
import org.openqa.selenium.devtools.v133.network.model.ResponseReceived;
import org.openqa.selenium.net.NetworkUtils;

import java.util.Optional;

public class DevTools {
    public static void NetworkCheck (WebDriver driver){
        // Create a DevTools session
        org.openqa.selenium.devtools.DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        // Enable network tracking
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Add listener for network responses
        devTools.addListener(Network.responseReceived(), response -> {
            ResponseReceived networkResponse = response;
            System.out.println("Response URL: " + networkResponse.getResponse().getUrl());
            System.out.println("Status: " + networkResponse.getResponse().getStatus());
            System.out.println("Headers: " + networkResponse.getResponse().getHeaders());
        });
    }
}
