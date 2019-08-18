package com.lanzonprojects.noteskeeper.domain.repository;

import com.lanzonprojects.noteskeeper.domain.model.NoteResource;
import com.lanzonprojects.noteskeeper.jooq.generated.tables.Note;
import io.crnk.core.exception.InternalServerErrorException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
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
public class NoteRepositoryImpl extends ResourceRepositoryBase<NoteResource, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryImpl.class);

    private Map<Long, NoteResource> notes = new HashMap<>();

    @Autowired
    private DSLContext dslContext;

    public NoteRepositoryImpl() {
        super(NoteResource.class);
        LOGGER.info("found initialized repository.");

        for (long i = 0; i < 100; i++) {
            notes.put(i, new NoteResource(i, "Welcome", "Hello World!"));
        }
    }

    @Override
    public synchronized ResourceList<NoteResource> findAll(QuerySpec querySpec) {
        System.out.println("Found DSLContext" + dslContext);
        return querySpec.apply(notes.values());
    }

    @Override
    public synchronized <S extends NoteResource> S create(S entity) {
        System.out.println("Found DSLContext" + dslContext);
        Note noteTable = Note.NOTE;

        // Find the max ID from the `note` table.
        final Integer maxId = dslContext.select(DSL.max(noteTable.ID)).from(noteTable).fetchOneInto(Integer.class);

        // Increment max-id by 1 and add the new note.
        int inserted = dslContext
            .insertInto(noteTable)
            .columns(noteTable.ID, noteTable.TITLE, noteTable.DESCRIPTION)
            .values(maxId + 1, entity.getTitle(), entity.getDescription())
            .execute();

        // Discard failed attempts to insert and notify the user.
        if (inserted == 0) {
            throw new InternalServerErrorException("Something went wrong while trying to save the note," +
                                                       " please try again.");
        }

        // Return the note that was just persisted.
        /*
        dslContext.select()
                  .from(noteTable)
                  .where(noteTable.ID.equal(Math.toIntExact(entity.getId()) + 1))
                  .and(noteTable.TITLE.equal(entity.getTitle()))
                  .fetchOneInto(NoteResource.class);
        */
        return entity;
    }
}
