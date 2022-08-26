package ru.stqa.pft.addressbook.tests;

import com.mysql.jdbc.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionTest{

  Connection conn = null;
  Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

  @Test
  public void testDBConnection() {
    try {
      conn =
              (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?" +
                      "user=root&password=");

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select group_id, group_header, group_footer from group_list ");
      Groups groups = new Groups();
      while (rs.next()){
       GroupData group =  new GroupData()
                .withId(rs.getInt("group_id"))
                .withHeader(rs.getString("group_header"))
                .withHeader(rs.getString("group_header"));
       logger.info(String.valueOf(group));
        groups.add(group);
      }
      rs.close();
      st.close();
      conn.close();
      logger.info(String.valueOf(groups));
    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}