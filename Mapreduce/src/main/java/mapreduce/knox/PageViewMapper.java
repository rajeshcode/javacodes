package mapreduce.knox;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageViewMapper extends Mapper<Object, Text, NullWritable, IntWritable>  {

    //public static String APACHE_ACCESS_LOGS_PATTERN = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+) (.+?) \"([^\"]+|(.+?))\"";
    public static String APACHE_ACCESS_LOGS_PATTERN = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.* (\\S+) (\\S+) (\\S+) - (\\w+) (\\S+).*\"";
    //pattern1 = Pattern.compile("\\s*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}.*\\sERROR\\s.*");
    //public static String APACHE_ACCESS_LOGS_PATTERN = "\\s*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}.*\\sERROR\\s.*";
    public static Pattern pattern = Pattern.compile(APACHE_ACCESS_LOGS_PATTERN);

    //private static final IntWritable one = new IntWritable(1);
    private NullWritable outKey = NullWritable.get();
    private Text url = new Text();

    //public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
    public void map(Object key, Text value, Mapper<Object, Text, NullWritable, Text>.Context context)
    //public void map(Object key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {
        Matcher matcher = pattern.matcher(value.toString());
        if (matcher.matches()) {
            // Group 6 as we want only Page URL
            //url.set(matcher.group(6));
            url.set(matcher.toString());
            System.out.println(url.toString());

            context.write(outKey , url);
        } else
        {
            url.set(matcher.toString());
            System.out.println(url.toString());
            context.write(outKey , url);
        }

    }


}
