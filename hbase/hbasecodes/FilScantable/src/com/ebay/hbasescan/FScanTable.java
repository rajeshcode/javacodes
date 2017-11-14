package com.ebay.hbasescan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FScanTable {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("dss_mone:react_oc_event");
        Table table = connection.getTable(tableName);
        //HTable table = admin.getTableDescriptor("dss_mone:react_oc_event");

        // HTable table;
        //table = new HTable(conf, "dss_mone:react_oc_event");
        List<Filter> filters = new ArrayList<Filter>();

        SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("d"), Bytes.toBytes("c")
                , CompareFilter.CompareOp.NOT_EQUAL, new BinaryComparator(Bytes.toBytes("7")));
        colValFilter.setFilterIfMissing(false);
        filters.add(colValFilter);

        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes("36|20171112"));
        final Scan scan1 = scan.setStopRow(Bytes.toBytes("36|20171113"));
        scan.setMaxResultsPerColumnFamily(10);


        scan.setFilter(fl);

        ResultScanner scanner = table.getScanner(scan);
        System.out.println("Scanning table... ");

        for (Result result : scanner) {
            //System.out.println("getRow:"+Bytes.toString(result.getRow()));
            String key = "~";
            for (KeyValue kv : result.raw()) {

                //if (key.compareTo(keyFlag) == 0) {
                //  key = Bytes.toString(kv.getRow());
                System.out.print("Key: " + key);
                //}

                System.out.print(", " + Bytes.toString(kv.getFamilyArray()) + "." + Bytes.toString(kv.getQualifier()));
                System.out.print("=" + Bytes.toString(kv.getValue()));


            }

            System.out.println("");
            System.out.println("-------------------");
        }
        scanner.close();
        System.out.println("Completed ");


    }


}
