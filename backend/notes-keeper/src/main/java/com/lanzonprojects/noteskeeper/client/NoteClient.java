package com.lanzonprojects.noteskeeper.client;

import com.lanzonprojects.noteskeeper.domain.model.Note;
import io.crnk.client.CrnkClient;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lanzon-projects
 */
@Component
public class NoteClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteClient.class);

    private CrnkClient crnkClient = new CrnkClient("http://localhost:8081/lanzonprojects/api");
    private ResourceRepositoryV2<Note, Long> resourceRepositoryV2;

    @PostConstruct
    public void init() {
        resourceRepositoryV2 = crnkClient.getRepositoryForType(Note.class);
    }

    public Note findOne(long id) {
        final Note note = resourceRepositoryV2.findOne(id, new QuerySpec(Note.class));

        LOGGER.info("found {}", note.toString());
        return note;
    }
}
