package com.genesis.android;

public class ModelCheckUpdate {

    private String name;
    private String version;
    private String release_date;
    private String patch_id;
    private String patch_file;

    private ModelCheckUpdate modelCheckUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPatch_id() {
        return patch_id;
    }

    public void setPatch_id(String patch_id) {
        this.patch_id = patch_id;
    }

    public String getPatch_file() {
        return patch_file;
    }

    public void setPatch_file(String patch_file) {
        this.patch_file = patch_file;
    }

    public ModelCheckUpdate() {

    }

    public ModelCheckUpdate getInstance() {
        if (modelCheckUpdate == null) {
            modelCheckUpdate = new ModelCheckUpdate();
            return modelCheckUpdate;
        }
        return modelCheckUpdate;
    }
//
//    public String toString() {
//        String string = "N: " + name + ", id: " + id;
//        return string;
//    }
}
