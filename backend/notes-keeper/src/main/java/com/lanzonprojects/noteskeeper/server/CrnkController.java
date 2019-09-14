package com.lanzonprojects.noteskeeper.server;

import java.util.Map;
import java.util.stream.Collectors;

import io.crnk.core.engine.registry.ResourceRegistry;
import io.crnk.spring.boot.v3.CrnkConfigV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanzon-projects
 */
@RestController
@Import({CrnkConfigV3.class})
public class CrnkController {

    @Autowired
    private ResourceRegistry resourceRegistry;

    @RequestMapping("/resources-info")
    public Map<String, String> getResources() {
        return resourceRegistry
            .getResources()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getResourceInformation().getResourceType(),
                                      entry -> resourceRegistry.getResourceUrl(entry.getResourceInformation())));
    }
}
