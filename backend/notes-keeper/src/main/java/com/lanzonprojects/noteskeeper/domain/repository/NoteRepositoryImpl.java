package com.lanzonprojects.noteskeeper.domain.repository;

import com.lanzonprojects.noteskeeper.domain.model.Note;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lanzon-projects
 */
@Component
@Transactional
public class NoteRepositoryImpl extends ResourceRepositoryBase<Note, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryImpl.class);

    private Map<Long, Note> notes = new HashMap<>();

    @Autowired
    private DSLContext dslContext;

    public NoteRepositoryImpl() {
        super(Note.class);
        LOGGER.info("found initialized repository.");

        for (long i = 0; i < 100; i++) {
            notes.put(i, new Note(i, "Welcome", "Hello World!"));
        }
    }

    @Override
    public synchronized ResourceList<Note> findAll(QuerySpec querySpec) {
        System.out.println("Found DSLContext" + dslContext);
        return querySpec.apply(notes.values());
    }
}
