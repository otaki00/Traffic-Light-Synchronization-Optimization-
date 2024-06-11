package model.hadoop;

import org.apache.hadoop.conf.Configuration;

public class HadoopConfigurator {

    public static Configuration getConfiguration() {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://namenode:9000"); // URL of your Hadoop namenode
        conf.set("mapreduce.framework.name", "yarn"); // Using YARN
        conf.set("yarn.resourcemanager.hostname", "resourcemanager"); // ResourceManager address
        return conf;
    }
}
