package model.hadoop;


import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;

public class HDFSManager {
    private Configuration conf;
    private FileSystem fs;

    public HDFSManager() throws IOException {
        this.conf = HadoopConfigurator.getConfiguration();
        this.fs = FileSystem.get(conf);
    }

    public void writeToHDFS(String data, String pathString) throws IOException {
        Path path = new Path(pathString);
        try (FSDataOutputStream outputStream = fs.create(path, true)) {
            outputStream.writeUTF(data);
        }
    }

    public String readFromHDFS(String pathString) throws IOException {
        Path path = new Path(pathString);
        try (FSDataInputStream inputStream = fs.open(path)) {
            return inputStream.readUTF();
        }
    }

    public void closeFileSystem() throws IOException {
        fs.close();
    }
}
