package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;
  @Parameter(names = "-d", description = "File dir")
  public String dir;
  @Parameter(names = "-f", description = "Data format")
  public String format;
  @Parameter(names = "-v", description = "Data validity")
  public String validity;
  @Parameter(names = "-m", description = "Modifying flag")
  public String modifying;

  public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    GroupDataGenerator generator = new GroupDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();

  }


  public void run() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    List<GroupData> groups = null;
    String methodName = null;


    if (validity.equals("true")) {
      if (modifying.equals("true")) {
        methodName = "generateGroupModificationValuesValid";
      } else if (modifying.equals("false")) {
        methodName = "generateGroupsValid";
      } else {
        System.out.println("Unrecognized modifying flag: " + modifying);
      }
    } else if (validity.equals("false")) {
      if (modifying.equals("true")) {
        methodName = "generateGroupModificationValuesInvalid";
      } else if (modifying.equals("false")) {
        methodName = "generateGroupsInvalid";
      } else {
        System.out.println("Unrecognized modifying flag: " + modifying);
      }
    } else {
      System.out.println("Unrecognized validity: " + validity);
    }

    if (methodName != null) {
      System.out.println(methodName);
      groups = executeMethodByName(methodName, count);
    } else {
      throw new NoSuchMethodException("Название метода не определено");
    }

    String filePath = dir + methodName.replaceAll("generate","") + "." + format;
    if (format.equals("csv")) {
      saveAsCsv(groups, new File(filePath));
    } else if (format.equals("xml")) {
      saveAsXml(groups, new File(filePath));
    } else if (format.equals("json")) {
      saveAsJson(groups, new File(filePath));
    } else {
      System.out.println("Unrecognized format: " + format);
    }
  }

  private static void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(groups);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private static void saveAsCsv(List<GroupData> groups, File file) {
  }

  private static void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    String xml = xstream.toXML(groups);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }

  private void save(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (GroupData group : groups) {
      writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
    writer.close();
  }

  public static List<GroupData> generateGroupsValid(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(randomValidGroupName())
              .withHeader(String.format("header %s", randomInt())).withFooter(String.format("footer %s", randomInt())));
    }
    return groups;
  }

  public static List<GroupData> generateGroupsInvalid(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(randomInvalidGroupName() + "'")
              .withHeader(String.format("header %s", randomInt())).withFooter(String.format("footer %s", randomInt())));
    }
    return groups;
  }

  public static List<GroupData> generateGroupModificationValuesValid(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(randomValidGroupName("Modified group "))
              .withHeader(String.format("header %s", randomInt())).withFooter(String.format("footer %s", randomInt())));
    }
    return groups;
  }

  public static List<GroupData> generateGroupModificationValuesInvalid(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(randomInvalidGroupName("Modified group "))
              .withHeader(String.format("header %s", randomInt())).withFooter(String.format("footer %s", randomInt())));
    }
    return groups;
  }

  private static int randomInt() {
    return (int) (random() * 1000);
  }

  public static String randomValidGroupName() {
    return String.format("group%s", randomInt());
  }

  public static String randomValidGroupName(String name) {
    return String.format("%s%s", name.replaceAll("'", ""), randomInt());
  }

  public static String randomInvalidGroupName(String name) {
    return String.format("%s%s", name+"'", randomInt());
  }

  public static String randomInvalidGroupName() {
    return String.format("%s%s", randomValidGroupName()+"'", randomInt());
  }

  protected static List<GroupData> executeMethodByName(String methodName, int count) throws IOException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
    String GroupDataGeneratorClass = "ru.stqa.pft.addressbook.generators.GroupDataGenerator";
    Class<?> GeneratorClass = Class.forName(GroupDataGeneratorClass);
    Object Generator = new GroupDataGenerator();
    Method setNameMethod = Generator.getClass().getMethod(methodName, int.class);
    return (List<GroupData>) setNameMethod.invoke(Generator, count);
  }

  public GroupDataGenerator() {

  }


}
