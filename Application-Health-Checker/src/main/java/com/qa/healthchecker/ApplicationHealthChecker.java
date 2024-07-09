package com.qa.healthchecker;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApplicationHealthChecker {

    public static void main(String[] args) {
        String url = "https://www.google.com/"; // Replace with your application's URL
        scheduleApplicationHealthCheck(url);
    }

    public static void scheduleApplicationHealthCheck(String urlString) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable healthCheckTask = () -> checkApplicationHealth(urlString);

        scheduler.scheduleAtFixedRate(healthCheckTask, 0, 5, TimeUnit.SECONDS);
    }

    public static void checkApplicationHealth(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int statusCode = connection.getResponseCode();

            if (statusCode == 200) {
                System.out.println("The application is up and running.");
            } else {
                System.out.println("The application is down. Status Code: " + statusCode);
            }

        } catch (IOException e) {
            System.out.println("Error checking application health: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
