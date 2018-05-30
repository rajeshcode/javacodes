import java.io.File

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row


object SparkSqlJob {

  def main(args: Array[String]): Unit = {
    val warehouseLocation = new File("spark-warehouse").getAbsolutePath

    val spark = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql


    sql("show tables").show()
    val dataDir = "/tmp/parquet_data"
    spark.range(10).write.parquet(dataDir)


    spark.stop()

  }

}
