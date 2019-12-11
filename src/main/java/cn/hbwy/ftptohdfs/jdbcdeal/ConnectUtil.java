package cn.hbwy.ftptohdfs.jdbcdeal;


import java.sql.*;

public class ConnectUtil {
    public static void main(String[] args) throws SQLException {
        Connection c = ConnectUtil.getConn();
        Statement s = c.createStatement();
        ResultSet r = s.executeQuery("select partition_time,table_name from hbwy_mid_monitor");
        while (r.next()) {
            System.out.println(r.getString("partition_time"));

        }
    }

    private static Connection conn = null;

    public static Connection getConn() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://10.216.44.39/gpadmin";
                conn = DriverManager.getConnection(url, "gpadmin", "Hbwy_gp_20181024!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
