package org.scystl.comics.org.scystl.comics.model;

/**
 * Created by Andrea on 05/07/2015.
 */
public class ComicCharacter {
    private String id;
    private String name;
    private String alias;
    private String gender;
    private String realName;
    private String imageUrl;

    public ComicCharacter(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public ComicCharacter(String id, String name, String alias, String gender, String realName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.gender = gender;
        this.realName = realName;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getGender() {
        return gender;
    }

    public String getRealName() {
        return realName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
