package model.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class GADriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Traffic Light Optimization GA");
        job.setJarByClass(GADriver.class);
        job.setMapperClass(GAMapper.class);
        job.setReducerClass(GAReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0])); // Input path from command line
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output path from command line
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
