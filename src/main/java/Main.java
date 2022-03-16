import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String URL = "https://reqres.in/api/users?page=2";

        // Creating a http request.
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .build();

        // Creating client for sending the request.
        HttpClient client = HttpClient.newBuilder().build();

        // Creating a https response.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        String body = response.body();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readValue(body, JsonNode.class);

        JsonNode data = node.get("data");

        List<Person> persons = convertToPerson(data);

        persons.forEach(System.out::println);
    }


    private static List<Person> convertToPerson(JsonNode arrayData) {
        List<Person> persons = new ArrayList<>();

        for(int i=0; i < arrayData.size(); i++) {
            
            persons.add(new Person(
                arrayData.get(i).get("id").asLong(), 
                arrayData.get(i).get("email").toString(), 
                arrayData.get(i).get("first_name").toString(), 
                arrayData.get(i).get("last_name").toString(), 
                arrayData.get(i).get("avatar").toString()
            ));
        }

        return persons;
        
    }
}
