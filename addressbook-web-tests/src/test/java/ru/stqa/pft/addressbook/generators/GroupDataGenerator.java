package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.FileParams;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

public class GroupDataGenerator extends TestBase {

  private final FilesHelper fileOperations = new FilesHelper();

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
    String fileName = null;


    if (validity.equals("true")) {
      if (modifying.equals("true")) {
        groups = generateGroupModificationValuesValid(count);
        fileName = app.properties.getProperty("group.modification.valid");
      } else if (modifying.equals("false")) {
        groups = generateGroupsValid(count);
        fileName = app.properties.getProperty("group.creation.valid");
      } else {
        throw new NoSuchMethodException("Unrecognized modifying flag: " + modifying);
      }
    } else if (validity.equals("false")) {
      if (modifying.equals("true")) {
        groups = generateGroupModificationValuesInvalid(count);
        fileName = app.properties.getProperty("group.modification.invalid");
      } else if (modifying.equals("false")) {
        groups = generateGroupsInvalid(count);
        fileName = app.properties.getProperty("group.creation.invalid");
      } else {
        System.out.println("Unrecognized modifying flag: " + modifying);
      }
    } else {
      throw new NoSuchMethodException("Unrecognized validity: " + validity);
    }

//    if (fileName != null) {
//      System.out.println(fileName);
//      groups = executeMethodByName(fileName, count);
//    } else {
//      throw new NoSuchMethodException("Метод не найден");
//    }
    //fileName = fileName.replaceAll("generate", "");

    FileParams file = new FileParams().inFileDir(dir).withName(fileName).withFormat(format);
    fileOperations.saveListOfGroupDataAsFile(groups, file);
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
    return String.format("%s%s", name + "'", randomInt());
  }

  public static String randomInvalidGroupName() {
    return String.format("%s%s", randomValidGroupName() + "'", randomInt());
  }

//  protected static List<GroupData> executeMethodByName(String methodName, int count) throws IOException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
////    String GroupDataGeneratorClass = "ru.stqa.pft.addressbook.generators.GroupDataGenerator";
////    Class<?> GeneratorClass = Class.forName(GroupDataGeneratorClass);
////    System.out.println(GeneratorClass);
//    Object Generator = new GroupDataGenerator();
//    Method setNameMethod = Generator.getClass().getMethod(methodName, int.class);
//    return (List<GroupData>) setNameMethod.invoke(Generator, count);
//  }

  public GroupDataGenerator() {

  }


}
