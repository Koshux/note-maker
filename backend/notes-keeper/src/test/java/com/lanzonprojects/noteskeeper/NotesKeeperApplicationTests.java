package com.lanzonprojects.noteskeeper;

import com.lanzonprojects.noteskeeper.client.NoteClient;
import com.lanzonprojects.noteskeeper.domain.model.NoteResource;
import com.lanzonprojects.noteskeeper.jooq.generated.tables.Note;
import io.crnk.core.resource.list.ResourceList;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional("transactionManager")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = NotesKeeperApplication.class)
public class NotesKeeperApplicationTests {

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private NoteClient noteClient;

    @Test
    public void testFindAll() {
        ResourceList<NoteResource> notes = noteClient.findAll();
        Assert.assertEquals(notes.get(0).getDescription(), "Hello World!");
        Assert.assertEquals(notes.get(1).getDescription(), "This was a test note.");
        Assert.assertEquals(notes.get(2).getDescription(), "This note tool is prett neat.");
    }

    @Test
    public void testCreate_Success() {
        // Setup test.
        NoteResource noteResource = new NoteResource();
        noteResource.setTitle("testCreate_Success");
        noteResource.setDescription("testCreate_Success description.");

        NoteResource newNote = noteClient.create(noteResource);

        final int maxId = dslContext.select(DSL.max(Note.NOTE.ID)).from(Note.NOTE).fetchOneInto(Integer.class);
        Assert.assertEquals(newNote.getId(), maxId);
        Assert.assertEquals(newNote.getTitle(), "testCreate_Success");
        Assert.assertEquals(newNote.getDescription(), "testCreate_Success description.");

        // Teardown test
        noteClient.delete((long) maxId);
    }
}
