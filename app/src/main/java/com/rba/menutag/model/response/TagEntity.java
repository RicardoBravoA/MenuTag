package com.rba.menutag.model.response;

/**
 * Created by Ricardo Bravo on 3/07/16.
 */

public class TagEntity {

    String id, description;

    public TagEntity(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
