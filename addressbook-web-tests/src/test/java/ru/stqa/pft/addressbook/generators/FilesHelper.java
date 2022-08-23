package ru.stqa.pft.addressbook.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.FileParams;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class FilesHelper {

  private void save(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (GroupData group : groups) {
      writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
    writer.close();
  }

  public void saveListOfGroupDataAsFile(List<GroupData> group, FileParams fileParams) throws IOException {
    String filePath = fileParams.getDir() + fileParams.getName()+ "."+ fileParams.getFormat();
    switch (fileParams.getFormat()) {
      case "csv":
        saveGroupAsCsv(group, new File(filePath));
        break;
      case "xml":
        saveGroupAsXml(group, new File(filePath));
        break;
      case "json":
        saveGroupAsJson(group, new File(filePath));
        break;
      default:
        System.out.println("Unrecognized file format: " + fileParams.getFormat());
        break;
    }
    System.out.println(filePath);
  }

  private static void saveGroupAsJson(List<GroupData> group, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(group);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }


  private static void saveContactAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }


  private static void saveContactAsCsv(List<ContactData> group, File file) {
  }
  private static void saveGroupAsCsv(List<GroupData> contact, File file) {
  }

  private static void saveGroupAsXml(List<GroupData> group, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    String xml = xstream.toXML(group);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }

  private static void saveContactAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }

  public void saveListOfContactDataAsFile(List<ContactData> contacts, FileParams file) throws IOException {
    String filePath = file.getDir() + file.getName()+ "."+ file.getFormat();
    switch (file.getFormat()) {
      case "csv":
        saveContactAsCsv(contacts, new File(filePath));
        break;
      case "xml":
        saveContactAsXml(contacts, new File(filePath));
        break;
      case "json":
        saveContactAsJson(contacts, new File(filePath));
        break;
      default:
        System.out.println("Unrecognized file format: " + file.getFormat());
        break;
    }
    System.out.println(filePath);
  }
}
