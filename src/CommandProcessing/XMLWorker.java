package CommandProcessing;

import SpaceMarineDataClient.SpaceMarineLists;
import javax.xml.bind.*;
import java.io.*;
import java.util.*;
/**
 * Класс XMLWorker используется для работы команды save
 */
public class XMLWorker {
    /**
     * Метод saveCollection считывает при помощи метода getFilePathForSave введённое пользователем имя файла и сохраняет в него коллекцию в формате xml
     * В этом методе используется класс SpaceMarineLists и его метод setSpaceMarineLists
     * PrintWriter записывает данные в файл, Marshaller выполняет серализацию объектов
     */
    public static void saveCollection() throws JAXBException, IOException {
        try{
            String pathToFile = getFilePathForSave();
            if (pathToFile == null)
                System.out.println("----\nПуть не указан, дальнейшая работа не возможна.\n----");
            else {
                JAXBContext jaxbContext;
                SpaceMarineLists lists = new SpaceMarineLists();
                List lst = new ArrayList();
                lst.addAll(Commands.collection);
                lists.setSpaceMarineList(lst);
                try {
                    PrintWriter pw = new PrintWriter(new File(pathToFile));
                    jaxbContext = JAXBContext.newInstance(SpaceMarineLists.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(lists, pw);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e){
                    System.out.println("Не удалось сохранить коллекцию.");
                }
            }
        }
        catch (NoSuchElementException e){
            e.getMessage();
        }
    }

    /**
     * Метод используется для получения переменной окружения quatro, через которую программе передаётся имя файла для сохранения
     * @return String
     */
    private static String getFilePathForSave(){
        String path = System.getenv("quatro");
        System.out.println(System.getProperty("user.dir"));
        if (path == null){
            return ("----\nПуть через переменную окружения quatro не указан\n----");
        } else {
            return path;
        }
    }
}