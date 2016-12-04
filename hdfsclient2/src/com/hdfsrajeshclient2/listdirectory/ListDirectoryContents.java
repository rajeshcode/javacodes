/**
 * Created by rajec on 12/4/16.
 */

package com.hdfsrajeshclient2.listdirectory;

import java.io.IOException;
/* import java.io.PrintStream; */
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class ListDirectoryContents {
    public static void main(String[] args)
            throws IOException, URISyntaxException {
        Configuration configuration = new Configuration();

        FileSystem hdfs = FileSystem.get(new URI("hdfs://artemis-nn-2.vip.ebay.com:8020"), configuration);


        FileStatus[] fileStatus = hdfs.listStatus(new Path("hdfs://artemis-nn-2.vip.ebay.com:8020/user/b_um/ep_tables/exl_expt_trmt_bit_position_map/"));

        Path[] paths = FileUtil.stat2Paths(fileStatus);

        System.out.println("***** Contents of the Directory *****");
        for (FileStatus fileStatus2 : fileStatus) {
            if (fileStatus2.isDirectory()) {
                System.out.println("Directory :: ");
                System.out.println(fileStatus2);
            }
            if (fileStatus2.isFile()) {
                System.out.println("File :: ");
                System.out.println(fileStatus2.getPath());
                System.out.println(fileStatus2.toString());
            }
        }
    }
}


