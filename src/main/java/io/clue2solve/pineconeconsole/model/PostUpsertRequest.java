package io.clue2solve.pineconeconsole.model;


import io.clue2solve.pinecone.javaclient.model.UpsertVector;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpsertRequest {
    private String indexName;
    private String nameSpace;
    private List<UpsertVector> upsertVectorsList;

}
