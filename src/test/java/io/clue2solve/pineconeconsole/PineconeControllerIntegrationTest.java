package io.clue2solve.pineconeconsole;

import io.clue2solve.pinecone.javaclient.model.DeleteRequest;
import io.clue2solve.pinecone.javaclient.model.FetchRequest;
import io.clue2solve.pinecone.javaclient.model.QueryRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PineconeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${pinecone.indexName}")
    private String indexName;

    @Value("${pinecone.namespace}")
    private String namespace;
    private String uuidString = "b1ce7f35-41fc-4159-a9ab-a24c4de2abcd";

    @Test
    @Order(1)
    public void testDescribeIndexStats() throws Exception {
        mockMvc.perform(get("/describeIndexStats")
                .param("indexName", indexName))
                .andExpect(status().isOk());
    }

//    @Disabled
    @Test
    @Order(2)
    public void testUpsert() throws Exception {
        String upsertRequestJson = "{\n" +
                "  \"indexName\": \"dbclient-testing\",\n" +
                "  \"nameSpace\": \"simple\",\n" +
                "  \"upsertVectorsList\": [\n" +
                "    {\n" +
                "      \"id\": \"abcd12345\",\n" +
                "      \"values\": [0.94,0.69,0.43],\n" +
                "      \"metadata\": {\n" +
                "        \"page\": 460,\n" +
                "        \"source\": \"/tmp/tmpsa5b18gg/tmp.pdf\",\n" +
                "        \"text\": \"PooledDataBuffer  is an extension of DataBuffer\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"; // Replace with your UpsertRequest JSON
        mockMvc.perform(post("/upsert")
                .contentType("application/json")
                .content(upsertRequestJson))
                .andExpect(status().isOk());
    }

//    @Disabled
    @Test
    @Order(3)
    public void testQuery() throws Exception {
        String queryRequestJson = String.valueOf(new QueryRequest().builder()
                .indexName(indexName)
                .namespace(namespace)
                .top_k(10)
                .includeMetadata(true)
                .includeValues(true)
                .vector(Arrays.asList(0.94, 0.69, 0.23))
                .build());
        mockMvc.perform(post("/query")
                .contentType("application/json")
                .content(queryRequestJson))
                .andExpect(status().isOk());
    }

//    @Disabled
    @Test
    @Order(4)
    public void testFetch() throws Exception {
        //Create a Fetch Request with the values above
        FetchRequest fetchRequest = FetchRequest.builder()
            .indexName(indexName)
            .nameSpace(namespace)
            .ids(new String[]{uuidString})
            .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String fetchRequestJson = objectMapper.writeValueAsString(fetchRequest);

        System.out.println("Fetch Request: " + fetchRequestJson);

        mockMvc.perform(post("/fetch")
            .contentType("application/json")
            .content(fetchRequestJson))
            .andExpect(status().isOk());
    }

//    @Disabled
    @Test
    @Order(5)
    public void testDelete() throws Exception {
        String deleteRequestJson = String.valueOf(new DeleteRequest()
                .builder()
                .indexName(indexName)
                .namespace(namespace)
                .ids(new String[]{uuidString})
                .build()); // Replace with your DeleteRequest JSON
        mockMvc.perform(post("/delete")
                .contentType("application/json")
                .content(deleteRequestJson))
                .andExpect(status().isOk());
    }
}
