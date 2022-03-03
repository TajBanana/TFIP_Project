package tajbanana.sudokuserver.services;

import jakarta.json.*;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class QuoteService {
    public static final String QUOTE_URL = "https://quotes.rest/qod";
    String quote;

    public String getQuote() {
        final String url = UriComponentsBuilder
                .fromUriString(QUOTE_URL)
                .queryParam("language","en")
                .toUriString();

        try {
            final RequestEntity<Void> requestEntity = RequestEntity
                    .get(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .build();

            final RestTemplate restTemplate = new RestTemplate();
            final ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            System.out.println(responseEntity);

            return jsonToQuote(responseEntity.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
//            String responseBody = "{ \"success\": { \"total\": 1 }, \"contents\": { \"quotes\": [ { \"quote\": \"You make a living by what you earn; you make a life by what you give.\", \"length\": \"69\", \"author\": \"Winston Churchill\", \"tags\": [ \"giving\", \"inspire\" ], \"category\": \"inspire\", \"language\": \"en\", \"date\": \"2022-03-03\", \"permalink\": \"https://theysaidso.com/quote/winston-churchill-you-make-a-living-by-what-you-earn-you-make-a-life-by-what-you\", \"id\": \"XZiOy4u9_g4Zmt7EdyxSIgeF\", \"background\": \"https://theysaidso.com/img/qod/qod-inspire.jpg\", \"title\": \"Inspiring Quote of the day\" } ] }, \"baseurl\": \"https://theysaidso.com\", \"copyright\": { \"year\": 2024, \"url\": \"https://theysaidso.com\" } }";
            return "Why do they call it a near miss and not a near hit?";
        }

    }

    public static String jsonToQuote(String responseBody) {
        InputStream inputStream = new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8));
        JsonObject responseJson = Json.createObjectBuilder(
                Json.createReader(inputStream)
                        .readObject())
                .build();

        JsonObject contents = responseJson.get("contents").asJsonObject();
        System.out.println("quote array>>>> " + contents);

        JsonArray quotesArray = contents.getJsonArray("quotes");
        System.out.println("quotesArray >>>> " + quotesArray);

        JsonObject quoteJson = quotesArray.get(0).asJsonObject();
        String quote = quoteJson.get("quote").toString().replaceAll("\"","");
        System.out.println(quote);
        return quote;
    }
}
