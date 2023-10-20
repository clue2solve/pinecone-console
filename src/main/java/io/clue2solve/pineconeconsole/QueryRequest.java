package io.clue2solve.pineconeconsole;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class QueryRequest {
    @Getter
    @Setter
    private String indexName;

    @Getter
    @Setter
    private List<Double> queryVector;

    // getters and setters
}
