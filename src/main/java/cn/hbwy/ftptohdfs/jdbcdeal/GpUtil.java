package cn.hbwy.ftptohdfs.jdbcdeal;

import cn.hbwy.ftptohdfs.orm.FtpHis;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * gp表hbwy_ftp_his_v3的增删CURD工具
 */
public class GpUtil {
    private String GpPath;
    private GpPool gpPool;

    public GpUtil(String GpPath) throws ClassNotFoundException, SQLException, PropertyVetoException, IOException {
        this.GpPath = GpPath;
        init();
    }

    private void init() throws PropertyVetoException, IOException, ClassNotFoundException {
        gpPool = new GpPool(GpPath);

    }

    public void insertFtpHis(String insql) throws SQLException {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = gpPool.getConnection();
            stat = conn.createStatement();
            stat.execute(insql);
        } finally {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public ArrayList<FtpHis> selectFtpHis(String selsql) throws SQLException {
        Connection conn = null;
        Statement stat = null;
        ArrayList<FtpHis> ftpHiss = new ArrayList<FtpHis>();
        try {
            conn = gpPool.getConnection();
            stat = conn.createStatement();
            ResultSet rset = stat.executeQuery(selsql);
            while (rset.next()) {
                FtpHis ftpHis = new FtpHis();
                //ftpHis.setId(rset.getInt("id"));
                ftpHis.setHis_file(rset.getString("his_file"));
                ftpHis.setPartition_time(rset.getString("partition_time"));
                ftpHiss.add(ftpHis);
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
            return ftpHiss;
        }

    }
}
