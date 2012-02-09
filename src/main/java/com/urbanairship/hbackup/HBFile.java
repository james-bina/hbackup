package com.urbanairship.hbackup;

import java.io.IOException;
import java.io.InputStream;

public abstract class HBFile {
    public abstract InputStream getFullInputStream() throws IOException;
    
    public abstract InputStream getPartialInputStream(long offset, long len) throws IOException;
    
    /**
     * @return The filename used by both the source and the target. This is relative 
     * to the base directory of the source. For example, if the source file was 
     * "hdfs://localhost:7080/base/mypics/pony.png", and the configured source was 
     * "hdfs://localhost:7080/base", the relativePath would be "/mypics/pony.png"
     */
    public abstract String getRelativePath();
    
    public abstract long getMTime();
    
    public abstract long getLength();
}