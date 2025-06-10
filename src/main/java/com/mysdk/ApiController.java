package com.mysdk;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiClient apiClient;

    public ApiController() {
        // Initialize with your base URL (adjust as needed)
        this.apiClient = new ApiClient("http://localhost:3002");
    }

    @GetMapping("/rules")
    public ApiResponse getRules() throws Exception {
        // Calls your existing getWithCache method
        return apiClient.getWithCache("/rule-engine/api/v1/groups/78/rules");
    }
}
