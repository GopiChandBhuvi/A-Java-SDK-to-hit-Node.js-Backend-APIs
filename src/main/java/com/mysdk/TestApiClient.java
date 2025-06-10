package com.mysdk;

public class TestApiClient {
    public static void main(String[] args) throws Exception {
        ApiClient apiClient = new ApiClient("http://localhost:3002");
        ApiResponse response = apiClient.getWithCache("/rule-engine/api/v1/groups/78/rules");
        System.out.println(response);
    }
}
