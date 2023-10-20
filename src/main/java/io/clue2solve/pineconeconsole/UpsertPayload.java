package io.clue2solve.pineconeconsole;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UpsertPayload {
    @Getter
    @Setter
    private String indexName;

    @Getter
    @Setter
    private List<Double> vector;

    @Getter
    @Setter
    private String id;

    // getters and setters
}
