import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

public class LinkShortener 
{
    private static final String BASE_URL = "http://short.url/";
    private static final int SHORT_URL_LENGTH = 6;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private Map<String, String> urlMapping;
    private Map<String, String> shortToLongMapping;
    
    public LinkShortener() 
    {
        urlMapping = new HashMap<>();
        shortToLongMapping = new HashMap<>();
    }
    private String generateShortUrl() 
    {
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);
        Random random = new Random();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) 
        {
            shortUrl.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return shortUrl.toString();
    }
    public String shortenUrl(String longUrl) 
    {
        if (urlMapping.containsKey(longUrl)) 
        {
            return BASE_URL + urlMapping.get(longUrl);
        }
        String shortUrl = generateShortUrl();
        while (shortToLongMapping.containsKey(shortUrl)) 
        {
            shortUrl = generateShortUrl();
        }
        urlMapping.put(longUrl, shortUrl);
        shortToLongMapping.put(shortUrl, longUrl);
        return BASE_URL + shortUrl;
    }
    public String expandUrl(String shortUrl) 
    {
        String shortUrlId = shortUrl.replace(BASE_URL, "");
        return shortToLongMapping.getOrDefault(shortUrlId, "Invalid short URL");
    }
    
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        LinkShortener linkShortener = new LinkShortener();
        
        while (true) 
        {
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            
            switch (choice) 
            {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longUrl = scanner.nextLine();
                    String shortUrl = linkShortener.shortenUrl(longUrl);
                    System.out.println("Shortened URL: " + shortUrl);
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortUrlInput = scanner.nextLine();
                    String expandedUrl = linkShortener.expandUrl(shortUrlInput);
                    System.out.println("Expanded URL: " + expandedUrl);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}