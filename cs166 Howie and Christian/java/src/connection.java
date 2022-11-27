import java.sql.*;

public class connection {

    Connection _connection = null;
    Retail esql;

    public connection() {
        try {
            String url = "jdbc:postgresql://localhost:" + esql.getdbport() + "/" + esql.getdbname();
            this._connection = DriverManager.getConnection(url, esql.getUser(), esql.getPassword());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
