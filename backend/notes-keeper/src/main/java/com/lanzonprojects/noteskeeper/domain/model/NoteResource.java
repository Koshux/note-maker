package com.lanzonprojects.noteskeeper.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import org.hibernate.validator.constraints.Length;

/**
 * @author lanzon-projects
 */
@JsonApiResource(type = "notes")
public class NoteResource {

    @JsonApiId
    private long id;

    @JsonProperty
    @Length(max = 20)
    private String title;

    @JsonProperty
    @Length(max = 100)
    private String description;

    public NoteResource() {
        super();
    }

    public NoteResource(long id, String title) {
        this.id = id;
        this.title = title;
    }


    public NoteResource(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Note{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}