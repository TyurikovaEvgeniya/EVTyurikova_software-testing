package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.FileParams;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator extends TestBase {

  public static final String ACCOUNT_IMG = "src/test/resources/OW.PNG";

  private final FilesHelper fileOperations = new FilesHelper();

  @Parameter(names = "-c", description = "Contact count")
  public int count;
  @Parameter(names = "-d", description = "File dir")
  public String dir;
  @Parameter(names = "-f", description = "Data format")
  public String format;
//  @Parameter(names = "-v", description = "Data validity")
//  public String validity;
  @Parameter(names = "-m", description = "Modifying flag")
  public String modifying;

  public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws NoSuchMethodException, IOException {

    List<ContactData> contacts = null;
    String fileName = null;

      if (modifying.equals("true")) {
        contacts = generateContactModificationValuesValid(count);
        fileName = "ContactModificationValuesValid";
      } else if (modifying.equals("false")) {
        contacts = generateContactValuesValid(count);
        fileName = "ContactValuesValid";
      } else {
        throw new NoSuchMethodException("Unrecognized modifying flag: " + modifying);
      }


    FileParams file = new FileParams().inFileDir(dir).withName(fileName).withFormat(format);
    fileOperations.saveListOfContactDataAsFile(contacts, file);
  }

  public List<ContactData> generateContactValuesValid(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    File photo = new File(ACCOUNT_IMG);
    for (int i = 0; i < count; i++) {

      contacts.add(new ContactData().withFirstName("Евгения")
              .withMiddleName("Вячеславовна")
              .withLastName("Тюрикова")
              .withMobilePhone(randomPhone())
              .withEmail("evgeniya.tyurikova@ligastavok.ru")
              .withBday("15")
              .withBmonth("May")
              .withByear("1988")
              .withPhoto(ACCOUNT_IMG));
    }
    return contacts;
  }

  private List<ContactData> generateContactModificationValuesValid(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    File photo = new File(ACCOUNT_IMG);
    for (int i = 0; i < count; i++) {

      contacts.add(  new ContactData()
              .withFirstName("Аглая")
              .withMiddleName("Степановна")
              .withLastName("Кий")
              .withMobilePhone(randomPhone())
              .withEmail("aglaya.Kiy@gmail.om")
              .withBday("17")
              .withBmonth("May")
              .withByear("1995")
              .withPhoto(ACCOUNT_IMG));
    }
    return contacts;
  }

  public ContactDataGenerator() {

  }

  public static String randomPhone() {
    return "+7777777" + (int) (100 + Math.random() * 1000) % 1000;
  }

}
