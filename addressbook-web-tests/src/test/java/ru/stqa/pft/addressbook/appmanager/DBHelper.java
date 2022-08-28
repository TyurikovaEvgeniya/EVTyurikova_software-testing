package ru.stqa.pft.addressbook.appmanager;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Order;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class DBHelper {
  private final SessionFactory sessionFactory;

  public DBHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

  }

  public Contacts contacts() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery("from ContactData where deprecated = 0").list();
    for (ContactData contact : result) {
      System.out.println(contact);
    }
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public int maxContactId() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    ContactData maxId = null;
    maxId = (ContactData) session.createCriteria(ContactData.class)
            .addOrder(Order.desc("id"))
            .setMaxResults(1)
            .uniqueResult();

    System.out.println(maxId);
    session.getTransaction().commit();
    session.close();
    assert maxId != null;
    return maxId.getId();
  }


  public ContactData getContactById(int id) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query result = session.createQuery(String.format("from ContactData where id = %s", id));
    ContactData contact = (ContactData) result.uniqueResult();
    System.out.println(contact);
    session.getTransaction().commit();
    session.close();
    return contact;
  }

  public Groups groups() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    for (GroupData group : result) {
      System.out.println(group);
    }
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }


}
