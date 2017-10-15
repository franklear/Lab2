package bms;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DBHelper {
    static public final int ISBN_maxlen = 16;
    static public final int Title_maxlen = 256;
    static public final int Publisher_maxlen = 256;
    static public final int Name_maxlen = 64;
    static public final int Country_maxlen = 64;
    static private Connection mConnection;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB?useUnicode=true&characterEncoding=UTF-8", "root", "1qaz@WSX");
            mConnection.setAutoCommit(false);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    static private Connection getConnection() {
        try {
            if (mConnection.isValid(1))
                return mConnection;
            mConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB?useUnicode=true&characterEncoding=UTF-8", "root", "1qaz@WSX");
            mConnection.setAutoCommit(false);
            return mConnection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    static private Map<String, Object> getBookInfomation(ResultSet rs) throws SQLException {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ISBN", rs.getString("ISBN"));
        result.put("Title", rs.getString("Title"));
        result.put("AuthorID", rs.getInt("AuthorID"));
        result.put("Publisher", rs.getString("Publisher"));
        result.put("PublishDate", rs.getDate("PublishDate"));
        result.put("Price", rs.getDouble("Price"));
        return result;
    }
    static private Map<String, Object> getAuthorInfomation(ResultSet rs) throws SQLException {
        HashMap<String, Object> result = new HashMap<>();
        result.put("AuthorID", rs.getInt("AuthorID"));
        result.put("Name", rs.getString("Name"));
        result.put("Age", rs.getInt("Age"));
        result.put("Country", rs.getString("Country"));
        return result;
    }
    static public Map<String, Object> findBookByISBN(String ISBN) {
        if (ISBN == null) return Collections.EMPTY_MAP;
        ISBN.replaceAll("[^0-9xX]", "");
        Connection connection = getConnection();
        String sql = "select * from Book where ISBN = ?";
        try (PreparedStatement psFindBookByISBN = connection.prepareStatement(sql)) {
            psFindBookByISBN.setString(1, ISBN);
            ResultSet rs = psFindBookByISBN.executeQuery();
            if (!rs.next()) return Collections.EMPTY_MAP;
            return getBookInfomation(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_MAP;
        }
    }
    static public List<Map<String, Object>> findBookByAuthorID(Integer AuthorID) {
        Connection connection = getConnection();
        String sql = "select * from Book where AuthorID = ?";
        try (PreparedStatement psFindBookByAuthorID = connection.prepareStatement(sql)) {
            psFindBookByAuthorID.setInt(1, AuthorID);
            ResultSet rs = psFindBookByAuthorID.executeQuery();
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            while (rs.next())
                result.add(getBookInfomation(rs));
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
    static public List<Map<String, Object>> findBookByAuthorNameFuzzily(String authorName) {
        if (authorName == null) return Collections.EMPTY_LIST;
        Connection connection = getConnection();
        String sql = "select * from Book left join Author on Book.AuthorID = Author.AuthorID where Author.Name like concat('%', ?, '%') order by ISBN asc";
        try (PreparedStatement psFindBookByAuthorNameFuzzily = connection.prepareStatement(sql)) {
            psFindBookByAuthorNameFuzzily.setString(1, authorName);
            ResultSet rs = psFindBookByAuthorNameFuzzily.executeQuery();
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> entry = getBookInfomation(rs);
                entry.putAll(getAuthorInfomation(rs));
                result.add(entry);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
    static public Map<String, Object> findAuthorByAuthorID(Integer AuthorID) {
        Connection connection = getConnection();
        String sql = "select * from Author where AuthorID = ?";
        try (PreparedStatement psFindAuthorByAuthorID = connection.prepareStatement(sql)) {
            psFindAuthorByAuthorID.setInt(1, AuthorID);
            ResultSet rs = psFindAuthorByAuthorID.executeQuery();
            if (!rs.next()) return Collections.EMPTY_MAP;
            return getAuthorInfomation(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_MAP;
        }
    }
    static public List<Map<String, Object>> findAuthorByNameFuzzily(String Name) {
        Connection connection = getConnection();
        if (Name == null) Name = "";
        String sql = "select * from Author where Name like concat('%', ?, '%') order by convert(Name using gbk) asc";
        try (PreparedStatement psFindAuthorByNameFuzzily = connection.prepareStatement(sql)) {
            psFindAuthorByNameFuzzily.setString(1, Name);
            ResultSet rs = psFindAuthorByNameFuzzily.executeQuery();
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            while (rs.next())
                result.add(getAuthorInfomation(rs));
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
    static public boolean addBook(String ISBN, String Title, Integer AuthorID, String Publisher, Date PublishDate, Double Price) {
        if (ISBN == null) return false;
        ISBN.replaceAll("[^0-9xX]", "");
        Connection connection = getConnection();
        String sql = "insert into Book (ISBN, Title, AuthorID, Publisher, PublishDate, Price) values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psAddBook = connection.prepareStatement(sql)) {
            psAddBook.setString(1, ISBN);
            psAddBook.setString(2, Title);
            psAddBook.setInt(3, AuthorID);
            psAddBook.setString(4, Publisher);
            psAddBook.setDate(5, PublishDate);
            psAddBook.setDouble(6, Price);
            int result = psAddBook.executeUpdate();
            connection.commit();
            return result == 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException er) {
                er.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
    static public boolean addAuthor(Integer AuthorID, String Name, Integer Age, String Country) {
        Connection connection = getConnection();
        String sql = "insert into Author (AuthorID, Name, Age, Country) values (?, ?, ?, ?)";
        try (PreparedStatement psAddAuthor = connection.prepareStatement(sql)) {
            psAddAuthor.setInt(1, AuthorID);
            psAddAuthor.setString(2, Name);
            if (Age == null)
                psAddAuthor.setString(3, null);
            else
                psAddAuthor.setInt(3, Age);
            psAddAuthor.setString(4, Country);
            int result = psAddAuthor.executeUpdate();
            connection.commit();
            return result == 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException er) {
                er.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
    static public boolean deleteBookByISBN(String ISBN) {
        if (ISBN == null) return false;
        ISBN.replaceAll("[^0-9xX]", "");
        Connection connection = getConnection();
        String sql = "delete from Book where ISBN = ?";
        try (PreparedStatement psDeleteBookByISBN = connection.prepareStatement(sql)) {
            psDeleteBookByISBN.setString(1, ISBN);
            int result = psDeleteBookByISBN.executeUpdate();
            connection.commit();
            return result == 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException er) {
                er.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
    static public boolean updateBookByISBN(String ISBN, String Title, Integer AuthorID, String Publisher, Date PublishDate, Double Price) {
        if (ISBN == null) return false;
        ISBN.replaceAll("[^0-9xX]", "");
        Connection connection = getConnection();
        String sql = "update Book set Title = ?, AuthorID = ?, Publisher = ?, PublishDate = ?, Price = ? where ISBN = ?";
        try (PreparedStatement psUpdateBookByISBN = connection.prepareStatement(sql)) {
            psUpdateBookByISBN.setString(1, Title);
            psUpdateBookByISBN.setInt(2, AuthorID);
            psUpdateBookByISBN.setString(3, Publisher);
            psUpdateBookByISBN.setDate(4, PublishDate);
            psUpdateBookByISBN.setDouble(5, Price);
            psUpdateBookByISBN.setString(6, ISBN);
            int result = psUpdateBookByISBN.executeUpdate();
            connection.commit();
            return result == 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException er) {
                er.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
}
