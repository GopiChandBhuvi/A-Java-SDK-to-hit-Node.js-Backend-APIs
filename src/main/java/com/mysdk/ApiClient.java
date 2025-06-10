package com.mysdk;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private String baseUrl;
    private Jedis redis;
    private Cache<String, String> nearCache;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.redis = new Jedis("localhost", 6379);

        this.nearCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
    }

    public ApiResponse getWithCache(String endpoint) throws Exception {
        // Try near cache (fastest)
        String cached = nearCache.getIfPresent(endpoint);
        if (cached != null) {
            System.out.println("[Near Cache Hit]");
            return new ApiResponse(200, cached);
        }

        // Try Redis (fallback)
        cached = redis.get(endpoint);
        if (cached != null) {
            System.out.println("[Redis Cache Hit]");
            nearCache.put(endpoint, cached); 
            return new ApiResponse(200, cached);
        }

        System.out.println("[Cache Miss] Calling API...");
        ApiResponse response = get(endpoint);
        redis.setex(endpoint, 600, response.getBody()); 
        nearCache.put(endpoint, response.getBody());    
        return response;
    }

    public ApiResponse get(String endpoint) throws Exception {
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new ApiResponse(responseCode, response.toString());
    }

    public Jedis getRedis() {
        return redis;
    }
}
