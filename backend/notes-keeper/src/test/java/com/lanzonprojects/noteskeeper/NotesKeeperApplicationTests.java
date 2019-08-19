package com.lanzonprojects.noteskeeper;

import com.lanzonprojects.noteskeeper.client.NoteClient;
import com.lanzonprojects.noteskeeper.domain.model.NoteResource;
import com.lanzonprojects.noteskeeper.jooq.generated.tables.Note;
import io.crnk.core.exception.BadRequestException;
import io.crnk.core.resource.list.ResourceList;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional("transactionManager")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = NotesKeeperApplication.class)
public class NotesKeeperApplicationTests {

    @Rule
    public ExpectedException expectedException;

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private NoteClient noteClient;

    @Before
    public void setupTests() {
        expectedException = ExpectedException.none();
    }

    @Test
    public void testFindAll() {
        final ResourceList<NoteResource> notes = noteClient.findAll();
        Assert.assertEquals(notes.get(0).getDescription(), "Hello World!");
        Assert.assertEquals(notes.get(1).getDescription(), "This was a test note.");
        Assert.assertEquals(notes.get(2).getDescription(), "This note tool is prett neat.");
    }

    @Test
    public void testCreate_Success() {
        // Setup test.
        final NoteResource noteResource = new NoteResource();
        noteResource.setTitle("testCreate_Success");
        noteResource.setDescription("testCreate_Success description.");

        final NoteResource newNote = noteClient.create(noteResource);

        final int maxId = dslContext.select(DSL.max(Note.NOTE.ID)).from(Note.NOTE).fetchOneInto(Integer.class);
        Assert.assertEquals(newNote.getId(), maxId);
        Assert.assertEquals(newNote.getTitle(), "testCreate_Success");
        Assert.assertEquals(newNote.getDescription(), "testCreate_Success description.");

        // Teardown test
        noteClient.delete(maxId);
    }

    @Test(expected = BadRequestException.class)
    public void testCreate_Failed_TitleTooLong() {
        // Setup resource with the length of the title attribute being larger than what is defined in the DDL.
        final NoteResource noteResource = new NoteResource();
        noteResource.setTitle("testCreate_Failed_InvalidTitle");
        noteResource.setDescription("testCreate_Failed_InvalidTitle description.");

        // Find the current Max ID before the new note is requested to be added.
        final int currentMaxId = dslContext.select(DSL.max(Note.NOTE.ID)).from(Note.NOTE).fetchOneInto(Integer.class);

        // Method throws exception due to title.length() is greater than DDL max-length.
        noteClient.create(noteResource);

        // Most recent Max ID after an attempt to add a new note was made.
        final int newMaxId = dslContext.select(DSL.max(Note.NOTE.ID)).from(Note.NOTE).fetchOneInto(Integer.class);

        expectedException.expectMessage("'title' length must be between 1 and 20.");

        // The IDs should match since the attempt to add a new note failed.
        Assert.assertEquals(currentMaxId, newMaxId);
    }

    @Test(expected = BadRequestException.class)
    public void testCreate_Failed_DescriptionTooLong() {
        // Setup resource with the length of the title attribute being larger than what is defined in the DDL.
        final NoteResource noteResource = new NoteResource();
        noteResource.setTitle("description too long");
        noteResource.setDescription("testCreate_Failed_DescriptionTooLong description, " +
                "testCreate_Failed_Description TooLong has failed miserably.");

        // Find the current Max ID before the new note is requested to be added.
        final int currentMaxId = dslContext.select(DSL.max(Note.NOTE.ID)).from(Note.NOTE).fetchOneInto(Integer.class);

        // Method throws exception due to title.length() is greater than DDL max-length.
        noteClient.create(noteResource);

        // Most recent Max ID after an attempt to add a new note was made.
        final int newMaxId = dslContext.select(DSL.max(Note.NOTE.ID)).from(Note.NOTE).fetchOneInto(Integer.class);

        expectedException.expectMessage("'description' length must be between 1 and 100.");

        // The IDs should match since the attempt to add a new note failed.
        Assert.assertEquals(currentMaxId, newMaxId);
    }

    @Test
    public void testDelete() {
        // Setup test.
        final NoteResource noteResource = new NoteResource();
        noteResource.setTitle("testCreate_Success");
        noteResource.setDescription("testCreate_Success description.");

        final NoteResource newNote = noteClient.create(noteResource);

        // Teardown test
        noteClient.delete(newNote.getId());

        final Long noteId = dslContext
                .select(Note.NOTE.ID)
                .from(Note.NOTE)
                .where(Note.NOTE.ID.equal(Math.toIntExact(newNote.getId())))
                .fetchOneInto(Long.class);

        Assert.assertNull(noteId);
    }
}
