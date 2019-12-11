import org.apache.hadoop.hive.ql.optimizer.spark.SparkSkewJoinProcFactory.SparkSkewJoinJoinProcessor
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}

object StrucuredTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("StrucuredTest").master("local[2]").getOrCreate()
    val sparkLoad = spark.readStream.format("text").option("path", "H:\\out\\test1\\ccc").option("maxFilesPerTrigger", "10240").load()
    println(sparkLoad.isStreaming)
    println(sparkLoad.printSchema())
    val userSchaena = new StructType().add("name", "string").add("type", "string")
    val sparkread = spark.readStream.option("seq","""|""").schema(userSchaena).text("H:\\out\\test1\\ccc")
    sparkread.printSchema()
    sparkread.select("*").groupBy("name").count().writeStream.outputMode("complete").format("console").start()
  }
}
