package com.lanzonprojects.noteskeeper.client;

import com.lanzonprojects.noteskeeper.domain.model.NoteResource;
import io.crnk.client.CrnkClient;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryV2;
import io.crnk.core.resource.list.ResourceList;
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
    private ResourceRepositoryV2<NoteResource, Long> resourceRepositoryV2;

    @PostConstruct
    public void init() {
        resourceRepositoryV2 = crnkClient.getRepositoryForType(NoteResource.class);
    }

    public ResourceList<NoteResource> findAll() {
        final ResourceList<NoteResource> noteResources = resourceRepositoryV2.findAll(new QuerySpec(NoteResource.class));

        LOGGER.info("found {}", noteResources.toString());
        return noteResources;
    }

    public NoteResource create(NoteResource entity) {
        NoteResource noteResource = resourceRepositoryV2.create(entity);

        LOGGER.info("created {}", noteResource);
        return noteResource;
    }


    public void delete(long id) {
        resourceRepositoryV2.delete(id);
        LOGGER.info("deleted note {}", id);
    }
}
