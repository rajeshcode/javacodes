package mapreduce.knox;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.compress.GzipCodec;
//import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.security.UserGroupInformation;
//import org.apache.hadoop.hbase.security.token ;





public class PageViewCounter {


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapred.job.queue.name","hdlq-data-default" );
        if (args.length != 2) {
            System.err.println("Usage: PageViewCounter <in><out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "Page View Counter");
        //Job job = new Job(getConf(), "ExtractKnoxLog");
        job.setJarByClass(PageViewCounter.class);
        job.setMapperClass(PageViewMapper.class);
        //job.setCombinerClass(IntSumReducer.class);
        //job.setReducerClass(IntSumReducer.class);
        //job.setOutputKeyClass(LongWritable.class);
        job.setOutputKeyClass(NullWritable.class);
        //job.setOutputValueClass(IntWritable.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //FileOutputFormat.setCompressOutput(job, true);
        //FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
        //SequenceFileOutputFormat.setOutputCompressionType(job, SequenceFile.CompressionType.BLOCK);
        //FileInputFormat.addInputPath(job, new Path(args[0]));

        job.setNumReduceTasks(0);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
