package com.lanzonprojects.noteskeeper;

import com.lanzonprojects.noteskeeper.client.NoteClient;
import org.jooq.DSLContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional("transactionManager")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = NotesKeeperApplication.class)
public class NotesKeeperApplicationTests {

    @Autowired
    private NoteClient noteClient;

    @Autowired
    private DSLContext dslContext;

    @Test
    public void testFindOne() {
        Assert.assertEquals(noteClient.findOne(1L).getDescription(), "Hello World!");
    }
}
