package com.genesis.android.network;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CheckUpdateModel {

    @SerializedName("name")
    private String name;

    /**
     * If login is successful, save this session id in shared preferences to use in subsequent executeSequence and getData API calls.
     */
    @SerializedName("versionId")
    private int versionId;

    @SerializedName("releasedate")
    private String releasedate;

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
}
