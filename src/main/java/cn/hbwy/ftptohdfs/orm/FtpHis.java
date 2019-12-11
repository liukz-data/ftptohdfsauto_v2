package cn.hbwy.ftptohdfs.orm;

/**
 * gp表hbwy_ftp_his_v3的映射类
 */
public class FtpHis {
    private int id;
    private String his_file;
    private String partition_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHis_file() {
        return his_file;
    }

    public void setHis_file(String his_file) {
        this.his_file = his_file;
    }

    public String getPartition_time() {
        return partition_time;
    }

    public void setPartition_time(String partition_time) {
        this.partition_time = partition_time;
    }

    @Override
    public String toString() {
        return "FtpHis{" +
                "id=" + id +
                ", his_file='" + his_file + '\'' +
                ", partition_time='" + partition_time + '\'' +
                '}';
    }
}
