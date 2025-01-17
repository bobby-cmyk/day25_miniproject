package vttp.batchb.paf.day25.mini.project.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GPTService {

    @Value("${OPENAI.API.KEY}")
    private String OPENAI_API_KEY;

    private final String GPT4OMINI = "gpt-4o-mini";

    public String generateSQLquery(String userPrompt) {

        String systemPrompt = """
            Create an sql query based on the user prompt. Only allow SELECT queries.

            Answer with the raw SQL query only; no markdown or other punctuation.

            Based it on the DDL to provided:
            CREATE TABLE author (
                id INT NOT NULL AUTO_INCREMENT,
                first_name VARCHAR(128),
                last_name VARCHAR(128),
                location VARCHAR (128),
                
                CONSTRAINT pk_author_id PRIMARY KEY (id)
            );

            CREATE TABLE book (
                id INT NOT NULL AUTO_INCREMENT,
                title VARCHAR(128),
                pages INT,
                date_released DATE,
                language VARCHAR(128),
                author_id INT,
                
                CONSTRAINT pk_book_id PRIMARY KEY (id),
                CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES author(id)
            );

            INSERT INTO author (first_name, last_name, location)
            VALUES
            ('George', 'Orwell', 'Motihari, India'),
            ('Jane', 'Austen', 'Steventon, England'),
            ('Mark', 'Twain', 'Florida, Missouri, USA'),
            ('J.K.', 'Rowling', 'Yate, England'),
            ('Chinua', 'Achebe', 'Ogidi, Nigeria');

            INSERT INTO book (title, pages, date_released, language, author_id)
            VALUES
            ('1984', 328, '1949-06-08', 'English', 1),
            ('Animal Farm', 112, '1945-08-17', 'English', 1),
            ('Pride and Prejudice', 432, '1813-01-28', 'English', 2),
            ('Sense and Sensibility', 368, '1811-10-30', 'English', 2),
            ('The Adventures of Tom Sawyer', 274, '1876-04-30', 'English', 3),
            ('Harry Potter and the Philosopher\'s Stone', 223, '1997-06-26', 'English', 4),
            ('Harry Potter and the Chamber of Secrets', 251, '1998-07-02', 'English', 4),
            ('Things Fall Apart', 209, '1958-06-17', 'English', 5);
        """;

        try {
            return promptGPT(systemPrompt, userPrompt, GPT4OMINI);
        }

        catch (Exception e) {
            return "hehe";
        }
    }

    public String generateFriendlyResponse(String databaseResults) {
        String systemPrompt = """
            Convert the results from the database into user-friendly answer. Exclude any formatting.
        """;

        try {
            return promptGPT(systemPrompt, databaseResults, GPT4OMINI);
        }

        catch(Exception e) {
            return "hehe";
        }  
    }

    private String promptGPT(String systemPrompt, String userPrompt, String model) throws Exception{

        String gptReponse = "";
        
        String url = "https://api.openai.com/v1/chat/completions";

        JsonObject reqBody = Json.createObjectBuilder()
            .add("model", model)
            .add("messages", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                    .add("role", "system")
                    .add("content", systemPrompt))
                .add(Json.createObjectBuilder()
                    .add("role", "user")
                    .add("content", userPrompt)))
            .build();

        RequestEntity<String> req = RequestEntity
            .post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + OPENAI_API_KEY)
            .body(reqBody.toString(), String.class);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        String payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));

        JsonObject result = reader.readObject();

        JsonArray choicesArr = result.getJsonArray("choices");

        JsonObject firstChoiceObj = choicesArr.getJsonObject(0);

        JsonObject messageObj = firstChoiceObj.getJsonObject("message");

        gptReponse = messageObj.getString("content");

        return gptReponse;
    }
}
