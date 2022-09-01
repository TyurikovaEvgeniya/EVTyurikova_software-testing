package ru.stqa.pft.mantis.appmanager;

import com.mysql.jdbc.Connection;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.pft.mantis.model.UserData;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBHelper {
  protected WebDriver wd;
  Connection conn = null;
  Logger logger = LoggerFactory.getLogger(DBHelper.class);


  public UserData getUser() throws SQLException {

    ResultSet rs = null;
    UserData user = null;
    try {
      conn =
              (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?" +
                      "user=root&password=");
      Statement st = conn.createStatement();
        rs = st.executeQuery("select id, realname, username, email from mantis_user_table where realname <> ''");

        while (rs.next()) {
          user = new UserData()
                  .withId(rs.getInt("id"))
                  .withUsername(rs.getString("username"))
                  .withRealName(rs.getString("realname"))
                  .withEmail(rs.getString("email"))
                  .withPassword("password")
                  .withConfirmationLink("");
          System.out.println(String.valueOf(user));
        }
        rs.close();
        st.close();
        conn.close();

      } catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
      }
      return user;
    }
  }



