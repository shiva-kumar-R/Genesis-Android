package com.genesis.android;

public class ModelCheckUpdate {

    private String name;
    private int versionId;
    private String releasedate;

    private ModelCheckUpdate modelCheckUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public ModelCheckUpdate(){

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
