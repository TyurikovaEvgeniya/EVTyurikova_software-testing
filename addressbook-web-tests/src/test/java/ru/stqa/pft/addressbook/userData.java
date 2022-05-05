package ru.stqa.pft.addressbook;

public class userData {
  private final String firstname;
  private final String middlename;
  private final String lastname;
  private final String mobilePhone;
  private final String email;
  private final String bday;
  private final String bmonth;
  private final String byear;

  public userData(String firstname, String middlename, String lastname, String mobilePhone, String email, String bday, String bmonth, String byear) {
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.bday = bday;
    this.bmonth = bmonth;
    this.byear = byear;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public String getEmail() {
    return email;
  }

  public String getBday() {
    return bday;
  }

  public String getBmonth() {
    return bmonth;
  }

  public String getByear() {
    return byear;
  }
}
