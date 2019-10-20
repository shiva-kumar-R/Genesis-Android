package com.genesis.android.network;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CheckUpdateModel {

    @SerializedName("name")
    private String name;

    /**
     * If login is successful, save this session id in shared preferences to use in subsequent executeSequence and getData API calls.
     */
    @SerializedName("version")
    private String version;

    @SerializedName("releaseDate")
    private String release_date;

    @SerializedName("patchId")
    private String patch_id;

    @SerializedName("patchFile")
    private String patch_file;

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
}
