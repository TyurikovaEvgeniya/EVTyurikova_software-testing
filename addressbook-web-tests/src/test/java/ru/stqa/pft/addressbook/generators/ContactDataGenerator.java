package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.FileParams;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator extends TestBase {

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

  public static void main(String[] args) throws IOException, NoSuchMethodException {
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

    app.properties.load(new FileReader("addressbook-web-tests/src/test/resources/local.properties"));
      if (modifying.equals("true")) {
        contacts = generateContactModificationValuesValid(count);
        fileName = app.properties.getProperty("gen.contact.modification.valid");
      } else if (modifying.equals("false")) {
        contacts = generateContactValuesValid(count);
        fileName = app.properties.getProperty("gen.contact.creation.valid");
      } else {
        throw new NoSuchMethodException("Unrecognized modifying flag: " + modifying);
      }


    FileParams file = new FileParams().inFileDir(app.properties.getProperty("gen.TestDataDir")).withName(fileName).withFormat(format);
    fileOperations.saveListOfContactDataAsFile(contacts, file);
  }

  public List<ContactData> generateContactValuesValid(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    File photo = new File(app.getPhotoPath());
    for (int i = 0; i < count; i++) {

      contacts.add(new ContactData().withFirstName("Евгения")
              .withMiddleName("Вячеславовна")
              .withLastName("Тюрикова")
              .withMobilePhone(randomPhone())
              .withWorkPhone(randomPhone())
              .withHomePhone(randomPhone())
              .withFax(randomPhone())
              .withPhone2(randomPhone())
              .withEmail("evgeniya.tyurikova@ligastavok.ru")
              .withEmail2("evgeniya.fgjfg@ligastavok.ru")
              .withEmail3("evgeniya.tfdghjdgyurikova@ligastavok.ru")
              .withAddress("Первый адрес")
              .withAddress2("Второй адрес")
              .withBday("15")
              .withBmonth("May")
              .withByear("1988")
              .withPhoto(app.getPhotoPath()));
    }
    return contacts;
  }

  private List<ContactData> generateContactModificationValuesValid(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    File photo = new File(app.getPhotoPath());
    for (int i = 0; i < count; i++) {

      contacts.add(  new ContactData()
              .withFirstName("Аглая")
              .withMiddleName("Степановна")
              .withLastName("Кий")
              .withMobilePhone(randomPhone())
              .withWorkPhone(randomPhone())
              .withHomePhone(randomPhone())
              .withFax(randomPhone())
              .withPhone2(randomPhone())
              .withEmail("aglaya.Kiy@gmail.om")
              .withEmail2("evgeniya.fgjfg@ligastavok.ru")
              .withEmail3("evgeniya.tfdghjdgyurikova@ligastavok.ru")
              .withAddress("Первый адрес")
              .withAddress2("Второй адрес")
              .withBday("17")
              .withBmonth("May")
              .withByear("1995")
              .withPhoto(app.getPhotoPath()));
    }
    return contacts;
  }

  public ContactDataGenerator() {

  }

  public static String randomPhone() {
    return "+777777" + (int) (1000 + Math.random() * 10000) % 1000;
  }

}
