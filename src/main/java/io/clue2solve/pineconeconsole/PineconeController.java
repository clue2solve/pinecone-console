package io.clue2solve.pineconeconsole;

import io.clue2solve.pinecone.javaclient.PineconeDBClient;
import io.clue2solve.pinecone.javaclient.model.QueryResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import okhttp3.Response;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        List<QueryResponse> queryResponses = client.query(payload.getIndexName(), true, true, payload.getQueryVector());


        return ResponseEntity.ok(convertQueryResponsesToJson(queryResponses).toString());
    }

    public static JSONObject convertQueryResponsesToJson(List<QueryResponse> responses) {
        JSONArray jsonArray = new JSONArray();

        for (QueryResponse response : responses) {
//            LOG.info(response.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", response.getId().toString());
            jsonObject.put("score", response.getScore());
            jsonObject.put("values", response.getValues());
            LOG.info("Metadata : {}", response.getMetadata());
            jsonObject.put("metadata", new JSONObject(response.getMetadata())); // assuming metadata is a stringified JSON
//
            jsonArray.put(jsonObject);
        }

        JSONObject result = new JSONObject();
        result.put("matches", jsonArray);

        return result;
    }

//    @PostMapping("/upsert")
//    public ResponseEntity<String> upsert(@RequestBody UpsertPayload payload) throws IOException {
//        String indexName = payload.getIndexName();
//        List<Double> vector = payload.getVector();
//        String id = payload.getId();
//
//        Response response = client.upsert(indexName, vector, id);
//        return ResponseEntity.ok(response.body().string());
//    }



}


