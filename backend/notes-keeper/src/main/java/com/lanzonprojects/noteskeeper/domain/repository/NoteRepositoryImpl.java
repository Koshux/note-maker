package com.lanzonprojects.noteskeeper.domain.repository;

import com.lanzonprojects.noteskeeper.domain.model.Note;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lanzon-projects
 */
@Component
public class NoteRepositoryImpl extends ResourceRepositoryBase<Note, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryImpl.class);

    private Map<Long, Note> notes = new HashMap<>();

    public NoteRepositoryImpl() {
        super(Note.class);
        LOGGER.info("found initialized repository.");

        notes.put(1L, new Note(1L, "Welcome", "Hello World!"));
    }

    @Override
    public synchronized ResourceList<Note> findAll(QuerySpec querySpec) {
        return querySpec.apply(notes.values());
    }
}
