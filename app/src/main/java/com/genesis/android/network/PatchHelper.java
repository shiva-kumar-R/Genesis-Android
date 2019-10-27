package com.genesis.android.network;

import com.genesis.android.bsdiff.BSPatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class PatchHelper {

    /**
     * @param oldApkFile
     * @param patchFile
     * @return 0 on success
     * @throws PatchException
     */
    public static int applyPatch(File oldApkFile, File patchFile, File newTempFile) throws PatchException {
        try {
            newTempFile.createNewFile();
            return BSPatch.patchFast(new FileInputStream(oldApkFile), new FileInputStream(patchFile), newTempFile);
        } catch (IOException e) {
            throw new PatchException(e.getMessage());
        }
    }

}
