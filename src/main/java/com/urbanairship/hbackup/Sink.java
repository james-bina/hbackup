package com.urbanairship.hbackup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.urbanairship.hbackup.datasinks.HdfsSink;
import com.urbanairship.hbackup.datasinks.Jets3tSink;

/**
 * A "sink" is a place to store data. Each type of Sink is an implementation of this abstract class. New
 * Sinks can easily be added by providing a Sink implementation and modifying forUri() below.
 */
public abstract class Sink {
    public static Sink forUri(URI uri, HBackupConfig conf, Stats stats) 
            throws IOException, URISyntaxException {
        String scheme = uri.getScheme();
        
        if(scheme.equals("s3")) {
            return new Jets3tSink(uri, conf, stats);
        } else if (scheme.equals("hdfs")) {
            return new HdfsSink(uri, conf, stats);
        } else {
            throw new IllegalArgumentException("Unknown protocol \"" + scheme + "\" in  URI " + uri);
        }
    }
    
    /**
     * Check whether the target has sourceFile and if it's up to date.
     * @return false if 
     *  - the target does not have sourceFile
     *  - the target was copied from a sourceFile with a different modification time. Each sink
     *    should remember the mtime of the source when it receives a file.
     *  - the target's version of sourceFile has a different length
     *  Otherwise true (the target is up to date). 
     */
    public abstract boolean existsAndUpToDate(HBFile file) throws IOException;
    
//    public abstract void write(HBFile file) throws IOException;
    
    public abstract List<Runnable> getChunks(HBFile file);
}