package mtylenda.polishroadsurface.service;

import android.util.Log;

import java.io.File;

/**
 * Created by milosz on 15.12.17.
 */
public class FileFinder {

    public File findFile(File file) {


            if (file.isDirectory()) {

                for (File childFile: file.listFiles()) {
                    File matchedFile = findFile(childFile);
                    if (matchedFile != null) {
                        return  matchedFile;
                    }
                }
                return null;

            } else {
                Log.i("MIS", "LENGTH = " + file.length());
                if (file.length() > 500_000) {
                    return file;
                } else {
                    return null;
                }

            }
    }
}
