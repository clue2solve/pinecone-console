package io.clue2solve.pineconeconsole;

import io.clue2solve.pinecone.javaclient.PineconeDBClient;
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
        Response response = client.query(payload.getIndexName(), true, true, payload.getQueryVector());
        return ResponseEntity.ok(response.body().string());
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


