package RequestReader;

import CommandProcessing.Commands;
import SpaceMarineDataClient.SpaceMarine;
import SpaceMarineDataClient.SpaceMarineLists;
import javax.xml.bind.*;
import java.io.*;
import java.util.*;


/**
 * Класс считтывает XML-файл с коллекцией и преобразует его в коллекцию объектов
 */
public class FileReceiver {
    /**
     * Метод используется для получения переменной окружения uno, через которую программе передаётся имя файла с коллекцие
     * @return String
     */
    private static String getFilePath(){
        String path = System.getenv("uno");
        System.out.println(System.getProperty("user.dir"));
        if (path == null){
            return ("----\nПуть через переменную окружения uno не указан\n----");
        } else {
            return path;
        }
    }


    /**
     * Данным методом осущетсвляется работа с xml файлом, содержащим коллекцию
     * @throws FileNotFoundException выбрасывается, если не удаётся найти указанный файл
     * @throws JAXBException выбрасывается при неудачной сериализации
     */
     public void XMLConvertation() {
        try {
            getFilePath();
            String path =getFilePath();
            if (getFilePath() == null)
                System.out.println("----\nПуть не указан, дальнейшая работа не возможна.\n----");
            else {
                Scanner scan = new Scanner(new File(path));
                String stringData = new String();
                while (scan.hasNext()) {
                    stringData = stringData + scan.nextLine();
                }
                scan.close();
                try {
                    StringReader sr = new StringReader(stringData);
                    JAXBContext jaxbContext1 = JAXBContext.newInstance(SpaceMarineLists.class);
                    Unmarshaller unmarshaller = jaxbContext1.createUnmarshaller();
                    SpaceMarineLists lists1 = (SpaceMarineLists) unmarshaller.unmarshal(sr);
                    Commands.collection.addAll(lists1.SpaceMarineList); //заполняю коллекцию ArrayDeque (объявлена в Commands.java)
                    for (SpaceMarine s : Commands.collection) {
                        for (int i = 0; i < Commands.collection.size(); i++) {
                            try {
                                if ((Commands.collection.element().getCoordinates().getX() == null) || (Commands.collection.element().getChapter().getChapterName().trim().length() == 0) || (Commands.collection.element().getChapter().getChapterName() == null) || (Commands.collection.element().getChapter().getMarinesCount() <= 0) || (Commands.collection.element().getChapter().getMarinesCount() > 1000) || (Commands.collection.element().getCreationDate() == null) || (Commands.collection.element().getId() <= 0) || (Commands.collection.element().getName() == null) || (Commands.collection.element().getName().trim().length() == 0) || (Commands.collection.element().getHealth() <= 0) || (Commands.collection.element().getHealth() == null) || (Commands.collection.element().getHeight() == null) || (Commands.collection.element().getCoordinates() == null) || (Commands.collection.element().getMeleeWeapon() == null)) {
                                    throw new IllegalArgumentException("Данные в файле были испорчены ;((\n" +
                                            "Придётся запустить программу заново с корректными данными в файле");
                                }
                            } catch (NullPointerException e) {
                                System.out.println("некорректные данные в файле");
                            }
                        }
                    }
                } catch (UnmarshalException e) {
                    System.out.println("не удаётся считать файл ;(\n" +
                            "Укажите имя другого (?) файла с коллекцией повторно .xml");
                    getFilePath();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("Не удаётся найти указанный файл");
            getFilePath();
        }
    }


  /*  public void XMLConvertation() throws FileNotFoundException {

                Scanner scan = new Scanner(new File("file.xml"));
                String stringData = new String();
                while (scan.hasNext()) {
                    stringData = stringData + scan.nextLine();
                }
                scan.close();
                try {
                    StringReader sr = new StringReader(stringData); // преобразование xml строки в объект SpaceMarine
                    JAXBContext jaxbContext1 = JAXBContext.newInstance(SpaceMarineLists.class);
                    Unmarshaller unmarshaller = jaxbContext1.createUnmarshaller();
                    SpaceMarineLists lists1 = (SpaceMarineLists) unmarshaller.unmarshal(sr);
                    Commands.collection.addAll(lists1.SpaceMarineList); //заполняю коллекцию ArrayDeque (объявлена в Commands.java)
                    for (SpaceMarine s : Commands.collection) {
                        for (int i = 0; i < Commands.collection.size(); i++) {
                            try {
                                if ((Commands.collection.element().getCoordinates().getX() == null) || (Commands.collection.element().getChapter().getChapterName().trim().length() == 0) || (Commands.collection.element().getChapter().getChapterName() == null) || (Commands.collection.element().getChapter().getMarinesCount() <= 0) || (Commands.collection.element().getChapter().getMarinesCount() > 1000) || (Commands.collection.element().getCreationDate() == null) || (Commands.collection.element().getId() <= 0) || (Commands.collection.element().getName() == null) || (Commands.collection.element().getName().trim().length() == 0) || (Commands.collection.element().getHealth() <= 0) || (Commands.collection.element().getHealth() == null) || (Commands.collection.element().getHeight() == null) || (Commands.collection.element().getCoordinates() == null) || (Commands.collection.element().getMeleeWeapon() == null)) {
                                    throw new IllegalArgumentException("Данные в файле были испорчены ;((\n" +
                                            "Придётся запустить программу заново с корректными данными в файле");
                                }
                            } catch (NullPointerException e) {
                                System.out.println("некорректные данные в файле");
                            }
                        }
                    }
                } catch (UnmarshalException e) {
                    System.out.println("не удаётся считать файл ;(\n" +
                            "Укажите имя другого (?) файла с коллекцией повторно .xml");
                    e.printStackTrace();
                    //getFilePath();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }*/
}



