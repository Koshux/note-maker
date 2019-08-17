package com.lanzonprojects.noteskeeper;

import com.lanzonprojects.noteskeeper.client.NoteClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NotesKeeperApplicationTests {

    @Autowired
    NoteClient noteClient;

    @Test
    public void testFindOne() {
        Assert.assertEquals(noteClient.findOne(1L).getDescription(), "Hello World!");
    }
}
