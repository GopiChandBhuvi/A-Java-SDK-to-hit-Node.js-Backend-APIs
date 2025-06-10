package com.mysdk;

public class CacheRefresher implements Runnable {
    private ApiClient client;
    private String endpoint;

    public CacheRefresher(ApiClient client, String endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Always hit the API for fresh data
                System.out.println("[Cache Refresh] Refreshing cache for " + endpoint);
                ApiResponse response = client.get(endpoint);
                
                String body = response.getBody();
                System.out.println("Response body size: " + body.length());

                if (body.length() > 8192) {
                    System.out.println("Warning: The response body is too large for caching.");
                }

                client.getRedis().setex(endpoint, 600, body);

                System.out.println("[Cache Refresh] Cache updated at " + System.currentTimeMillis());

                Thread.sleep(60000); // 60 seconds (1 minute)

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
