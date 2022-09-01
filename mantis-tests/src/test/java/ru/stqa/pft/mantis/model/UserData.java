package ru.stqa.pft.mantis.model;

public class UserData {

  public int id;
  public String username;
  public String email;
  public String realName;
  public String confirmationLink;
  public String password;
  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", realName='" + realName + '\'' +
            ", confirmationLink='" + confirmationLink + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

  public UserData withUsername(String username) {
    this.username = username;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserData withRealName(String realName) {
    this.realName = realName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserData withPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getConfirmationLink() {
    return confirmationLink;
  }

  public UserData withConfirmationLink(String confirmationLink) {
    this.confirmationLink = confirmationLink;
    return this;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public int getId() {
    return id;
  }
}
