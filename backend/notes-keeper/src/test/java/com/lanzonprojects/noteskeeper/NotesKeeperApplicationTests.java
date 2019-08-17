package com.lanzonprojects.noteskeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lanzonprojects.noteskeeper.client.GreetingClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class NotesKeeperApplicationTests {

  @Autowired
  NoteClient noteClient;

  @Test
  public void testFindOne() {
    assertThat(noteClient.findOne(1L).getContent()).isEqualTo("Hello World!");
  }
}
