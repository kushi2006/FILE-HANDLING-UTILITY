

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleWeatherClient {

    public static void main(String[] args) {
        try {
            // Public weather API (no key needed) - Bangalore
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=12.97&longitude=77.59&current_weather=true";

            // Create connection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            String json = jsonBuilder.toString();

            // Parse inside "current_weather" block
            String currentBlock = extract(json, "\"current_weather\":\\{", "}");
            String temperature = extract(currentBlock, "\"temperature\":", ",");
            String windspeed = extract(currentBlock, "\"windspeed\":", ",");
            String time = extract(currentBlock, "\"time\":\"", "\"");

            System.out.println("\n☀️ Weather in Bangalore:");
            System.out.println("Time       : " + time);
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Wind Speed : " + windspeed + " km/h");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Utility to extract value between a start and end marker
    private static String extract(String text, String start, String end) {
        try {
            int startIndex = text.indexOf(start);
            if (startIndex == -1) return "N/A";
            startIndex += start.length();
            int endIndex = text.indexOf(end, startIndex);
            if (endIndex == -1) return "N/A";
            return text.substring(startIndex, endIndex).trim();
        } catch (Exception e) {
            return "N/A";
        }
    }
}


