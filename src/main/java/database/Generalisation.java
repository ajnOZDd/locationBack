package database ;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Dictionary;
import java.util.List;
import java.util.stream.Collectors;

public class Generalisation {
    private ConnectionDb dbConnection;

    public Generalisation(ConnectionDb connection) {
        dbConnection = connection;
    }

    public String select(String tableName, String selectColumns, String whereClause) throws Exception {
        String columnValue;
        String query = "SELECT " + selectColumns + " FROM " + tableName + " ";
    
        if (whereClause != null && !whereClause.isEmpty()) {
            query += "WHERE " + whereClause;
        }
    
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            if (resultSet.next()) {
                columnValue = resultSet.getString(1);
                return columnValue;
            }
        } catch (SQLException ex) {
            throw new Exception("Error occurred during the query execution: " + ex.getMessage());
        }
    
        throw new Exception("No data found");
    }
    public void insert(String tableName, List<String> columns, Map<String, Object> parameters) throws Exception {
        String columnNames = String.join(", ", columns);
        String parameterPlaceholders = columns.stream().map(c -> "?").collect(Collectors.joining(", "));
        String query = "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + parameterPlaceholders + ")";
    
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
            int index = 1;
            for (String column : columns) {
                Object value = parameters.get(column);
                statement.setObject(index++, value);
            }
    
            statement.executeUpdate();
            dbConnection.commit();
        } catch (SQLException ex) {
            dbConnection.rollback();
            throw new Exception("Error occurred during the transaction: " + ex.getMessage());
        }
    }
}