package cn.hbwy.ftptohdfs.jdbcdeal;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * GP数据库连接池
 */
public class GpPool {
    private String url = "jdbc:postgresql://10.216.44.39/gpadmin";
    private String username = "gpadmin";
    private String password = "Hbwy_gp_20181024!";
    private String maxpoolsize = "10";
    private String minpoolsize = "5";
    private String gpProPath;
    private static ComboPooledDataSource comboPooledDataSource;

    /**
     * @param gpProPath 配置文件路径
     * @throws PropertyVetoException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GpPool(String gpProPath) throws PropertyVetoException, IOException, ClassNotFoundException {
        this.gpProPath = gpProPath;
        initPool();
    }

    /**
     * 初始化连接池
     *
     * @throws ClassNotFoundException
     * @throws PropertyVetoException
     * @throws IOException
     */
    private void initPool() throws ClassNotFoundException, PropertyVetoException, IOException {
        Class.forName("org.postgresql.Driver");
        comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("org.postgresql.Driver");
        Properties pro = new Properties();
        FileInputStream fi = new FileInputStream(gpProPath);
        pro.load(fi);
        url = pro.getProperty("url");
        username = pro.getProperty("username");
        password = pro.getProperty("password");
        maxpoolsize = pro.getProperty("maxpoolsize");
        minpoolsize = pro.getProperty("minpoolsize");
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(username);
        comboPooledDataSource.setPassword(password);
        //下面是设置连接池的一配置
        comboPooledDataSource.setMaxPoolSize(Integer.parseInt(maxpoolsize));
        comboPooledDataSource.setMinPoolSize(Integer.parseInt(minpoolsize));

    }

    public synchronized Connection getConnection() throws SQLException {
        return comboPooledDataSource.getConnection();
    }
}
