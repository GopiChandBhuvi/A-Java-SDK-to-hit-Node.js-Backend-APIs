package com.mysdk;

import fi.iki.elonen.NanoHTTPD;

public class ApiServer extends NanoHTTPD {

    private final ApiClient apiClient;

    public ApiServer(int port, String backendBaseUrl) {
        super(port);
        this.apiClient = new ApiClient(backendBaseUrl);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        System.out.println("Request URI: " + uri);

        if ("/api/rules".equals(uri) && session.getMethod() == Method.GET) {
            try {
                ApiResponse response = apiClient.getWithCache("/rule-engine/api/v1/groups/78/rules");
                return newFixedLengthResponse(Response.Status.lookup(response.getStatusCode()),
                        "application/json",
                        response.getBody());
            } catch (Exception e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR,
                        "text/plain",
                        "Error: " + e.getMessage());
            }
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
    }

    public static void main(String[] args) {
        int port = 8080; // your API server port
        String backendUrl = "http://localhost:3002";

        ApiServer server = new ApiServer(port, backendUrl);
        try {
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            System.out.println("API Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
