package main.java;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.MultiRowRangeFilter;
import org.apache.hadoop.hbase.filter.MultiRowRangeFilter.RowRange;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HbaseMultiFilter {

    private static final Log LOG = LogFactory.getLog(HbaseMultiFilter.class);
    private final HResourceSentry sentry;
    private final int cacheSize = 1000;


    private final String keytab = "/home/hadoop/.keytabs/hadoop.keytab";
    private final String principal = "hadoop@CORP.XXX.COM";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    static String table;
    static boolean debug;
    static String cmpgn;
    static String rundate;
    static String region;
    static String pageLimit;


    public static void main(String[] args) throws Throwable {


        table = args[0];
        cmpgn = args[1];
        rundate = args[2];
        region = args[3];
        pageLimit = args[4];
        LOG.info(String.format("table:%s, cmpgn:%s, rundate:%s, region:%s, pageLimit:%s", table, cmpgn, rundate, region, pageLimit));
        new HbaseMultiFilter().execute();

    }

    public HbaseMultiFilter() throws Exception {
        this.sentry = new HResourceSentry(keytab, principal);
    }

    /*
	 * WARNING - Removed try catching itself - possible behaviour change.
	 */
    public void execute() throws Throwable {
        sentry.init();
        long s = System.currentTimeMillis();
        Configuration config = HBaseConfiguration.create();
        config.setLong("hbase.rpc.timeout", 120000);
        config.setLong("hbase.client.scanner.caching", 1000);
        final AggregationClient aggregationClient = new AggregationClient(config);

        final String testDate = rundate;
        List<RowRange> ranges = new ArrayList<RowRange>();

        LOG.info("StartRow:" + region + "|20180117|12|TE78001|20180117,  StopRow:" + region + "|20180117|12|TE78001|20180118");
        ranges.add(new RowRange(Bytes.toBytes(region + "|20180117|12|TE78001|20180117"), true, Bytes.toBytes(region + "|20180117|12|TE78001|20180118"), false));


        Scan sendScan = new Scan();
        sendScan.setCaching(cacheSize);
        sendScan.setCacheBlocks(false);
        FilterList fl = new FilterList(new MultiRowRangeFilter(ranges), new PageFilter(Integer.parseInt(pageLimit)));
        sendScan.setFilter(fl);
        LOG.info("Send Scan: " + sendScan);
        LongColumnInterpreter ci = new LongColumnInterpreter();
        long rowCnt = aggregationClient.rowCount(TableName.valueOf(table), ci, sendScan);

        aggregationClient.close();
        long e = System.currentTimeMillis();
        LOG.info("***Send Raw table Scan Duration= " + (e - s) + "ms ***  Total rows scanned: " + rowCnt);
    }

}
