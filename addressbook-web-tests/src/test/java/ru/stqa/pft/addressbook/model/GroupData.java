package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("group")
@Entity
@Table(name="group_list")
public class GroupData {
  @XStreamOmitField
  @Id
  @Column(name="group_id")
  private int id;
  @Expose
  @Column(name="group_name")
  private String name;
  @Expose
  @Column(name="group_header")
  @Type(type="text")
  private String header;

  public Set<ContactData> getContacts() {
    return new Contacts( contacts);
  }

  @Expose
  @Column(name="group_footer")
  @Type(type="text")
  private String footer;


  @ManyToMany(mappedBy ="groups", fetch = FetchType.EAGER)
  private Set<ContactData> contacts = new HashSet<ContactData>();

  public GroupData withId(int id) {
    this.id = id;
    return this;
  }
  public GroupData withName(String name) {
    this.name = name;
    return this;
  }
  public GroupData withFooter(String footer) {
    this.footer = footer;
    return this;
  }

  public GroupData withHeader(String header) {
    this.header = header;
    return this;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupData groupData = (GroupData) o;
    return id == groupData.id &&
            Objects.equals(name, groupData.name) &&
            Objects.equals(header, groupData.header) &&
            Objects.equals(footer, groupData.footer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, header, footer);
  }

  public GroupData() {
    this.id = Integer.MAX_VALUE;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", header='" + header + '\'' +
            ", footer='" + footer + '\'' +
            '}';
  }
}
