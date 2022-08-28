package ru.stqa.pft.addressbook.model;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "addressbook")
public class ContactData {
  @Id
  @Column(name = "id")
  private int id;

  @Expose
  @Column(name = "firstname")
  private String firstname;
  @Expose
  private String middlename;
  @Expose
  @Column(name = "lastname")
  private String lastname;
  @Expose
  @Type(type = "text")
  @Column(name = "mobile")
  private String mobilePhone;
  @Expose
  @Type(type = "text")
  @Column(name = "home")
  private String homePhone;
  @Expose
  @Type(type = "text")
  @Column(name = "fax")
  private String fax;
  @Expose
  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;
  @Expose
  @Column(name = "phone2")
  @Type(type = "text")
  private String phone2;
  @Expose
  @Column(name = "email")
  @Type(type = "text")
  private String email;
  @Expose
  @Column(name = "email2")
  @Type(type = "text")
  private String email2;
  @Expose
  @Column(name = "email3")
  @Type(type = "text")
  private String email3;
  @Column(name = "address")
  @Type(type = "text")
  @Expose
  private String address;
  @Column(name = "address2")
  @Type(type = "text")
  @Expose
  private String address2;
  @Expose
  @Column(name = "bday")
  @Type(type = "byte")
  private byte bday;
  @Expose
  @Column(name = "bmonth", length = 65535, columnDefinition = "TEXT")
  private String bmonth;
  @Expose
  @Column(name = "byear", length = 65535, columnDefinition = "TEXT")
  private String byear;
  @Transient
  private String group;
  @Transient
  private String allPhones;
  @Transient
  private String allEmails;
  @Expose
  @Transient
  @Column(name = "photo")
  @Type(type = "text")
  private String photo;


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

  public String getPhone2() {
    return phone2;
  }

  public ContactData withPhone2(String phone2) {
    this.phone2 = phone2;
    return this;
  }

  public ContactData withFax(String fax) {
    this.fax = fax;
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
      this.address = address;
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
    this.bday = Byte.parseByte(bday);
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

  public ContactData withPhoto(String photo) {
    this.photo = photo;
    return this;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="address_in_groups",joinColumns = @JoinColumn(name= "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();

  public Set<GroupData> getGroups() {
    return new Groups(groups);
  }

  public ContactData() {
    this.id = Integer.MAX_VALUE;
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

  public String getWorkPhone() {
    return workPhone;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getFax() {
    return fax;
  }

  public String getEmail() {
    return email;
  }

  public String getBday() {
    return String.valueOf(bday);
  }

  public String getBmonth() {
    return bmonth;
  }

  public String getByear() {
    return byear;
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

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", middlename='" + middlename + '\'' +
            ", lastname='" + lastname + '\'' +
            ", mobilePhone='" + mobilePhone + '\'' +
            ", homePhone='" + homePhone + '\'' +
            ", fax='" + fax + '\'' +
            ", workPhone='" + workPhone + '\'' +
            ", phone2='" + phone2 + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            ", address='" + address + '\'' +
            ", address2='" + address2 + '\'' +
            ", bday=" + bday +
            ", bmonth='" + bmonth + '\'' +
            ", byear='" + byear + '\'' +
            '}';
  }

  public String getAddress() {
    return address;
  }

  public String getAddress2() {
    return address2;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public String getPhoto() {
    return photo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            bday == that.bday &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(middlename, that.middlename) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(mobilePhone, that.mobilePhone) &&
            Objects.equals(homePhone, that.homePhone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(workPhone, that.workPhone) &&
            Objects.equals(phone2, that.phone2) &&
            Objects.equals(email, that.email) &&
            Objects.equals(email2, that.email2) &&
            Objects.equals(email3, that.email3) &&
            Objects.equals(address, that.address) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(bmonth, that.bmonth) &&
            Objects.equals(byear, that.byear);
  }

  public ContactData toDBTypes() {
    this.withFirstName(Optional.ofNullable(this.getFirstName()).orElse(""))
            .withMiddleName(Optional.ofNullable(this.getMiddleName()).orElse(""))
            .withLastName(Optional.ofNullable(this.getLastName()).orElse(""))
            .withWorkPhone(Optional.ofNullable(this.getWorkPhone()).orElse(""))
            .withMobilePhone(Optional.ofNullable(this.getMobilePhone()).orElse(""))
            .withHomePhone(Optional.ofNullable(this.getHomePhone()).orElse(""))
            .withFax(Optional.ofNullable(this.getFax()).orElse(""))
            .withAddress(Optional.ofNullable(this.getAddress()).orElse(""))
            .withAddress2(Optional.ofNullable(this.getAddress2()).orElse(""))
            .withBday(Optional.ofNullable(this.getBday()).orElse(""))
            .withBmonth(Optional.ofNullable(this.getBmonth()).orElse(""))
            .withByear(Optional.ofNullable(this.getByear()).orElse(""))
            .withEmail(Optional.ofNullable(this.getEmail()).orElse(""))
            .withEmail2(Optional.ofNullable(this.getEmail2()).orElse(""))
            .withEmail3(Optional.ofNullable(this.getEmail3()).orElse(""));
    return this;

  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, middlename, lastname, mobilePhone, homePhone, fax, workPhone, phone2, email, email2, email3, address, address2, bday, bmonth, byear);
  }


  public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }
}
