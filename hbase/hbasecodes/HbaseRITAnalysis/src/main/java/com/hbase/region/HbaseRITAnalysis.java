package com.hbase.region;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.master.RegionState;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.time.*;

public class HbaseRITAnalysis {

    protected static Log LOG = LogFactory.getLog(HbaseRITAnalysis.class);
    private long regionInTransitionMillis = 1800000;
    static String FLoca="/home/hadoop/RIToutput.txt";



    public static void main(String[] args) throws IOException {
        //init failed_region_number = 0;
        //long regionInTransitionMillis = 1800000;
         int failed_region_number = 0;

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        //ClusterStatus
        ClusterStatus status = admin.getClusterStatus();
        System.out.println("Cluster Status:\n--------------");
        System.out.println("HBase Version: " + status.getHBaseVersion());
        System.out.println("Version: " + status.getVersion());
        System.out.println("Cluster ID: " + status.getClusterId());
        System.out.println("Master: " + status.getMaster());
        System.out.println("No. Backup Masters: " +
                status.getBackupMastersSize());
        System.out.println("Backup Masters: " + status.getBackupMasters());

        System.out.println("No. Live Servers: " + status.getServersSize());
        System.out.println("Servers: " + status.getServers());
        System.out.println("No. Dead Servers: " + status.getDeadServers());
        System.out.println("Dead Servers: " + status.getDeadServerNames());
        System.out.println("No. Regions: " + status.getRegionsCount());
        System.out.println("Regions in Transition: " +
                status.getRegionsInTransition());
        System.out.println("No. Requests: " + status.getRequestsCount());
        System.out.println("Avg Load: " + status.getAverageLoad());
        System.out.println("Balancer On: " + status.getBalancerOn());
        System.out.println("Is Balancer On: " + status.isBalancerOn());
        System.out.println("Master Coprocessors: " +
                Arrays.asList(status.getMasterCoprocessors()));


        Map<String, RegionState> regionStateMap = status.getRegionsInTransition();
        StringBuilder sbFailed = new StringBuilder();
        int failedRegionNum = 0;
        for (Map.Entry<String, RegionState> entry: regionStateMap.entrySet()) {
            if (entry.getValue().isFailedClose() || entry.getValue().isFailedOpen()) {
                failedRegionNum += 1;
                sbFailed.append(entry.getKey()).append(": ").append(entry.getValue().getState().toString()).append("\n");
            }
        }

        if (failedRegionNum  > failed_region_number) {
            //Message msg = new Message(getMessage());
            //msg.setLevel(Message.Level.WARNING).setMessage(failedRegionNum + " failed region(s) found: \n" + sbFailed.toString());
            //offerMessage(msg);
            LOG.info("FailedRegions " + failedRegionNum + "failed region(s) found: \n"+ sbFailed.toString() );

        }
        failed_region_number = failedRegionNum;

        HbaseRITAnalysis vinsta = new HbaseRITAnalysis();
        //  Write to File
        //String FLoca="/home/hadoop/RIToutput.txt";
        Path path = Paths.get(FLoca);
        String content=" Hello Write to file";
        Files.write(Paths.get(String.valueOf(path)), content.getBytes());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            //writer.write("Hello World !!" + rs.getServerName().getHostname());
            //Files.write(Paths.get(String.valueOf(path)), status.getHBaseVersion());
            writer.write("Current Date & Time: "+ LocalDateTime.now() + "\n");
            writer.write(status.getHBaseVersion());
        }


        vinsta.checkRegionsInTransiton(status);
    }

    //checkRegionsInTransiton(status);



    public  void checkRegionsInTransiton(ClusterStatus status) throws IOException {
        Map<String, RegionState> rits = status.getRegionsInTransition();
        long currentTime = System.currentTimeMillis();
        boolean timeOutInTransition = false;
        if (rits != null && rits.size() > 0) {
            //Message msg = new Message(getMessage());
            //msg.setLevel(Message.Level.CRITICAL);
            for (RegionState rs: rits.values()) {
                if (currentTime - rs.getStamp() > regionInTransitionMillis) {
                    LOG.info(rs.toString());
                    timeOutInTransition = true;
                    //msg.appendMessage("Region " + rs.getRegion().getRegionNameAsString() +
                    //      " in transition for more than " + regionInTransitionMillis / 1000 + " seconds.\n");
                    LOG.info("Region" + rs.getRegion().getRegionNameAsString() + " in transition for more than " + regionInTransitionMillis / 1000 + " seconds.\n" );
                    //String FLoca="/home/hadoop/RIToutput.txt";
                    Path path = Paths.get(FLoca);
                    String content = "Hello World !!";
                    Files.write(Paths.get(String.valueOf(path)), content.getBytes());
                    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                        writer.write("Hello World !!" + rs.getServerName().getHostname());
                        //Files.write(Paths.get(String.valueOf(path)), status.getHBaseVersion());
                        writer.write(status.getHBaseVersion());
                    }


                    if (rs.getServerName() != null) {
                        //msg.setHost(rs.getServerName().getHostname());
                        LOG.info("ServerName" + rs.getServerName().getHostname());
                        List<String> lines = Arrays.asList("The first line", "The second line");

                        //Use try-with-resource to get auto-closeable writer instance
                        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                            writer.write("Hello World !!" + rs.getServerName().getHostname());
                            /*String content = "Hello World !!";
                            Files.write(Paths.get("c:/output.txt"), content.getBytes()); */
                        }
                    } else {
                        //msg.setHost("Unknown");
                        LOG.info("No Regionserver found \n");
                    }
                }
            }

        } else {
            System.out.println("No RITS found \n");
            LOG.info("NO RITS found\n");
        }
    }

}