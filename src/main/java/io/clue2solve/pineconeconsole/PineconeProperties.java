package io.clue2solve.pineconeconsole;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

//import LOMBOK
import lombok.Data;
@ConfigurationProperties(prefix = "pinecone")
public class PineconeProperties {
    @Getter
    @Setter
    private String environment;

    @Getter
    @Setter
    private String projectId;

    @Getter
    @Setter
    private String apiKey;

    // getters and setters
}

