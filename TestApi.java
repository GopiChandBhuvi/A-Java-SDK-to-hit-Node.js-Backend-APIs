import com.mysdk.ApiClient;
import com.mysdk.ApiResponse;

public class TestApi {

    public static void main(String[] args) {
        String baseUrl = "http://localhost:3002";
        String endpoint = "/rule-engine/api/v1/groups/78/rules";

        ApiClient client = new ApiClient(baseUrl);

        while (true) {
            try {
                ApiResponse response = client.getWithCache(endpoint);
                System.out.println("Response Code: " + response.getStatusCode());
                System.out.println("Response Body: " + response.getBody());

                Thread.sleep(10000);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
