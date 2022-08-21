package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private String firstname;
  private String middlename;
  private String lastname;
  private String mobilePhone;
  private String email;
  private String email2;
  private String email3;
  private String address;
  private String address2;
  private String bday;
  private String bmonth;
  private String byear;
  private String group;
  private String homePhone;
  private String workPhone;
  private String allPhones;
  private String allAddresses;
  private String allEmails;



  public void setId(int id) {
    this.id = id;
  }

  public ContactData withMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

  public ContactData withHomePhone(String home) {
    this.homePhone = home;
    return this;
  }

  public ContactData withWorkPhone(String work) {
    this.workPhone = work;
    return this;
  }


  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }


  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withAddress(String address) {
    this.allAddresses = address;
    return this;
  }

  public ContactData withAddress2(String address2) {
    this.address2 = address2;
    return this;
  }


  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstName(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withMiddleName(String middlename) {
    this.middlename = middlename;
    return this;
  }

  public ContactData withLastName(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withBday(String bday) {
    this.bday = bday;
    return this;
  }

  public ContactData withBmonth(String bmonth) {
    this.bmonth = bmonth;
    return this;
  }

  public ContactData withByear(String byear) {
    this.byear = byear;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(mobilePhone, that.mobilePhone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname, mobilePhone);
  }

  public ContactData() {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.email2 = email2;
    this.email3 = email3;
    this.address = address;
    this.address2 = address2;
    this.bday = bday;
    this.bmonth = bmonth;
    this.byear = byear;
    this.group = group;
    this.homePhone = homePhone;
    this.workPhone = workPhone;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", middlename='" + middlename + '\'' +
            ", lastname='" + lastname + '\'' +
            ", mobilePhone='" + mobilePhone + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            ", address='" + address + '\'' +
            ", address2='" + address2 + '\'' +
            ", bday='" + bday + '\'' +
            ", bmonth='" + bmonth + '\'' +
            ", byear='" + byear + '\'' +
            ", group='" + group + '\'' +
            ", homePhone='" + homePhone + '\'' +
            ", workPhone='" + workPhone + '\'' +
            '}';
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstname;
  }

  public String getMiddleName() {
    return middlename;
  }

  public String getLastName() {
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

  public String getGroup() {
    return group;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
  }

  public String getAddress() {
    return address;
  }

  public String getAddress2() {
    return address2;
  }

  public String getAllAddresses() {
    return allAddresses;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public String getHomePhone() {return homePhone;
  }

}
