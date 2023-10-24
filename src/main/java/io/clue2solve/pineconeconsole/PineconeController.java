package io.clue2solve.pineconeconsole;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.clue2solve.pinecone.javaclient.PineconeDBClient;
import io.clue2solve.pinecone.javaclient.model.QueryRequest;
import io.clue2solve.pinecone.javaclient.model.QueryResponse;
import io.clue2solve.pinecone.javaclient.model.UpsertRequest;
import io.clue2solve.pinecone.javaclient.model.UpsertVector;
import lombok.extern.java.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import okhttp3.Response;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



@RestController
public class PineconeController {
    private static final Logger LOG = LoggerFactory.getLogger(PineconeController.class);
    private PineconeDBClient client;
    private final PineconeProperties properties;

    @Autowired
    public PineconeController(PineconeProperties properties) {
        this.properties = properties;
        this.client = new PineconeDBClient(properties.getEnvironment(), properties.getProjectId(), properties.getApiKey());
    }

    @GetMapping("/describeIndexStats")
    public ResponseEntity<String> describeIndexStats(@RequestParam String indexName) throws IOException {
        Response response = client.describeIndexStats(indexName);
        return ResponseEntity.ok(response.body().string());
    }

    @PostMapping("/query")
    public ResponseEntity<String> query(@RequestBody QueryRequest payload) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRequest = mapper.readTree(payload.toString());
        LOG.info("jsonRequest: " + jsonRequest.toString());
        JsonNode values = jsonRequest.get("queryVector");
        List<Double> valuesList = new ArrayList<Double>();
        for(JsonNode value : values) {
            valuesList.add(value.asDouble());
        }

        LOG.info("valuesList: " + valuesList);
        QueryRequest queryRequest = QueryRequest.builder()
                .indexName(payload.getIndexName())
                .includeMetadata(true)
                .includeValues(true)
                .top_k(payload.getTop_k())
                .queryVector(valuesList)
                .build();

        List<QueryResponse> queryResponses = client.query(queryRequest);

        return ResponseEntity.ok(convertQueryResponsesToJson(queryResponses).toString());
    }

    @PostMapping("/upsert")
    public ResponseEntity<String> upsert(@RequestBody String payload) throws IOException {
        //TODO: Need to change the String type to the PostUpsertRequest type, using a string looks hacky

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRequest = mapper.readTree(payload.toString());
        JsonNode vectors = jsonRequest.get("upsertVectorsList");
        List<UpsertVector> upsertVectorsList = new ArrayList<UpsertVector>();
        for (JsonNode vector : vectors) {
            JsonNode values = vector.get("values");
            List<Double> valuesList = new ArrayList<Double>();
            for(JsonNode value : values) {
                valuesList.add(value.asDouble());
            }

            UpsertVector upsertVector = UpsertVector.builder()
                    .id(vector.get("id").asText())
                    .metadata(String.valueOf(vector.get("metadata")))
                    .values(valuesList)
                    .build();
            upsertVectorsList.add(upsertVector);
        }

        //TODO: moving to payload of type PostUpsertRequest will allow us to remove this hacky code for remoding double quotes
        UpsertRequest upsertRequest = UpsertRequest.builder()
                .indexName(jsonRequest.get("indexName").toString().substring(1, jsonRequest.get("indexName").toString().length() - 1))
                .nameSpace(jsonRequest.get("nameSpace").toString())
                .upsertVectorsList(upsertVectorsList)
                .build();

        String response = client.upsert(upsertRequest);
        return ResponseEntity.ok(response);
    }


    public static JSONObject convertQueryResponsesToJson(List<QueryResponse> responses) {
        JSONArray jsonArray = new JSONArray();

        for (QueryResponse response : responses) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", response.getId().toString());
            jsonObject.put("score", response.getScore());
            jsonObject.put("values", response.getValues());
            jsonObject.put("metadata", new JSONObject(response.getMetadata())); // assuming metadata is a stringified JSON
            jsonArray.put(jsonObject);
        }

        JSONObject result = new JSONObject();
        result.put("matches", jsonArray);

        return result;
    }

}


