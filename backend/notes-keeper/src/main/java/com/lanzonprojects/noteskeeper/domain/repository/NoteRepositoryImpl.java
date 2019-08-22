package com.lanzonprojects.noteskeeper.domain.repository;

import com.google.common.base.Strings;
import com.lanzonprojects.noteskeeper.domain.model.NoteResource;
import com.lanzonprojects.noteskeeper.jooq.generated.tables.Note;
import io.crnk.core.exception.BadRequestException;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * @author lanzon-projects
 */
@Component
@Transactional
public class NoteRepositoryImpl extends ResourceRepositoryBase<NoteResource, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryImpl.class);

    @Autowired
    private DSLContext dslContext;

    public NoteRepositoryImpl() {
        super(NoteResource.class);
    }

    @Override
    public synchronized ResourceList<NoteResource> findAll(QuerySpec querySpec) {
        final List<NoteResource> noteResources = dslContext.select().from(Note.NOTE).fetchInto(NoteResource.class);
        LOGGER.debug("Found notes: {}", noteResources);

        return querySpec.apply(noteResources);
    }

    @Override
    public synchronized <S extends NoteResource> S create(S entity) {
        final Note noteTable = Note.NOTE;

        // Column-size validation against note-title input.
        boolean titleIsEmpty = Strings.isNullOrEmpty(entity.getTitle());
        if (!titleIsEmpty && entity.getTitle().length() > noteTable.TITLE.getDataType().length()) {
            LOGGER.error(String.format("Note title: '%s' length must be between 1 and 20.", entity.getTitle()));
            throw new BadRequestException("'title' length must be between 1 and 20.");
        }

        // Column-size validation against note-description input.
        final String description = entity.getDescription();
        if (description == null || description.length() > noteTable.DESCRIPTION.getDataType().length()) {
            LOGGER.error(String.format("Note description: '%s' length must be between 1 and 100.", entity.getTitle()));
            throw new BadRequestException("'description' length must be between 1 and 100.");
        }

        // Find the max ID from the `note` table and increment by 1 for the next note ID.
        final int nextNoteId = dslContext.select(DSL.max(noteTable.ID)).from(noteTable).fetchOneInto(Integer.class) + 1;
        final Timestamp creationDate = Timestamp.from(Instant.now());

        // Adds the new note.
        LOGGER.debug("Creating new note with ID: {}", nextNoteId);
        int inserted = dslContext
                .insertInto(noteTable)
                .columns(noteTable.ID, noteTable.TITLE, noteTable.DESCRIPTION, noteTable.CREATION_DATE)
                .values(nextNoteId, titleIsEmpty ? "Default" : entity.getTitle(), description, creationDate)
                .execute();

        // Discard failed attempts to insert and notify the user.
        if (inserted == 0) {
            LOGGER.error("Failed to create the new note with ID: {}", nextNoteId);
            LOGGER.error("Note Title: ", entity.getTitle() + "; Note Description: " + description);
            throw new InternalServerErrorException("Something went wrong while creating the note, please try again.");
        }

        // Update the entity `ID` and `creationDate` with what was persisted.
        entity.setId(nextNoteId);
        entity.setCreationDate(creationDate);

        LOGGER.debug("Note successfully created.");
        return entity;
    }

    @Override
    public synchronized void delete(Long id) {
        int execute = dslContext.deleteFrom(Note.NOTE).where(Note.NOTE.ID.equal(Math.toIntExact(id))).execute();

        if (execute == 0) {
            throw new InternalServerErrorException("Something went wrong while deleting the note, please try again.");
        }
    }
}
