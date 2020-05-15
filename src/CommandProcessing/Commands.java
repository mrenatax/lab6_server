package CommandProcessing;

import SpaceMarineDataClient.*;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс Commands содержит методы, используемы в классе CommandControl для выполнения команд пользователя
 */
public class Commands {
    public static ArrayDeque<SpaceMarine> collection = new ArrayDeque<>();
    public static Scanner slmmsk;
    private static ArrayList al = new ArrayList();
    private static int q=0;
    /**
     * Метод help выводит справку по доступным командам
     */
    public static String help(){
        String help = "Доступны следующие комманды, которые могут: \n" +
                "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) \n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении \n" +
                "add {element} : добавить новый элемент в коллекцию \n" +
                "update_id {element} : обновить значение элемента коллекции, id которого равен заданному \n" +
                "remove_by_id id : удалить элемент из коллекции по его id \n" +
                "clear : очистить коллекцию \n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме. \n" +
                "exit : завершить программу (без сохранения в файл) \n" +
                "head : вывести первый элемент коллекции \n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный \n" +
                "history : вывести последние 13 команд (без их аргументов) \n" +
                "sum_of_height : вывести сумму значений поля height для всех элементов коллекции \n" +
                "max_by_name : вывести любой объект из коллекции, значение поля name которого является максимальным \n" +
                "filter_greater_than_height height : вывести элементы, значение поля height которых больше заданного \n";
         return help;
    }
    /**
     * Метод info выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    public static String info(){
        String info;
        if (collection.size() != 0){
            info = "Размер коллекции: " + collection.stream().mapToInt((p)->1).sum() + "\n"
                    + "Тип коллекции: " + collection.getClass() + "\n"
                    + "Дата инициализации: " + Date.from(Instant.now()) + "\n"
                    + "Name первого элемента: " + collection.getFirst().getName();
        }
        else  info = "Коллекция пуста";
        return info;
    }
    /**
     * Метод show выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public static String show(){
        String show;
        if (collection.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            collection.stream().forEach((p) -> stringBuilder.append(p.toString()+","));
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            return String.valueOf(stringBuilder.append("]"));
        }
        else show = "Коллекция пуста";
        return show;
    }
    public static String add(SpaceMarine spaceMarine){
        collection.add(spaceMarine);
        String s = "Элемент успешно добавлен в коллецию.";
        return s;
    }

    /**
     * Метод updateId обновляет значение элемента коллекции, id которого равен заданному, работает по аналогии с методом add
     * @exception InputMismatchException при выбрасывании этого исключение выводится сообщение о том, что поле заполнено некорректно
     */
    public static String updateId(int x, SpaceMarine spaceMarine){
        String string = "";
        if (collection.size() != 0) {
                int i = 0;
            if (collection.stream().anyMatch((p)->p.getId()==x) == true){
                collection.stream().filter((p) -> p.getId() == x).forEach(collection::remove);
                i++;
                collection.add(spaceMarine);
                string = "Значения элемента успешно обновлены";
            }
                if (i == 0) {
                     string = "Элемент с таким id не найден";
                }
        }
        else string = "Коллекция пуста";
        return string;
    }
    /**
     * Метод remove_by_id удаляет элемент из коллекции по его id
     * @exception InputMismatchException выбрасывает исключение при некорректном пользовательском вводе
     */
    public static String removeById(int x){
        String k = " ";
        if (collection.size() != 0) {
            int i = 0;
            if (collection.stream().anyMatch((p)->p.getId()==x) == true){
                collection.stream().filter((p) -> p.getId() == x).forEach(collection::remove);
                        k = "Элемент успешно удалён";
                        i++;
                }
                if (i == 0) {
                    k = "Элемент с таким id не найден";
                }
        }
        else k ="Коллекция пуста";
        return k;
    }
       /**
     *  Метод head выводит первый элемент коллекции
     */
    public static String head(){
        String head;
        if (collection.size() != 0) {
            head = String.valueOf(collection.getFirst());
        }
        else head = "Коллекция пуста";
        return head;
    }
    /**
     * Метод SumOfHeight выводит сумму значений поля height для всех элементов коллекции
     */
    public static String SumOfHeight(){
            Long x = 0l;
            for (SpaceMarine s : collection) {
                x = x + s.getHeight();
            }
        String sumOfHeight = "Сумма значений поля height: " + x.toString();

        return sumOfHeight;
    }
    /**
     * Метод maxByName выводит максимальное по длине name в коллекции
     */
    public static String maxByName() { //метод для вывода самого длинного имени с использованием метода интерфейса Comparable - compareTo
       String maxByName = "";
        if (collection.size() != 0) {
            if (collection.size() == 1) {
                maxByName = collection.element().getName();
            } else {
                String max = "";
                SpaceMarine sl = collection.getFirst();
               // collection.stream().filter(p->p.compareTo(sl)>0).forEach(spaceMarine -> max = spaceMarine.getName());
                for (SpaceMarine s : collection) {
                    if(s.compareTo(sl)>0){
                        max = s.getName();
                    }
                    else {
                        max = sl.getName();
                    }

                }
                maxByName = max;
            }
        }
        else maxByName = "Коллекция пуста";
        return maxByName;
    }
    /**
     *  Метод filterGreater выводит элементы, значение поля height которых больше заданного
     * @exception InputMismatchException выбрасывает исключение при некорректном пользовательском вводе
     */
    public static String filterGreater(Long min){
        String filterGreater = " ";
        if (collection.size() != 0) {
                //    if((collection.stream().anyMatch((p)->p.getHeight().compareTo(min)>0))==true){
                  //  } //
                    for (SpaceMarine s : collection) {
                        if (s.getHeight().compareTo(min) > 0) {
                            filterGreater = filterGreater + " " + s.getName();
                        }
                    }
        }
        return filterGreater;
    }

    /**
     * Метод используется для получения переменной окружения dos, через которую программе передаётся имя файла co скриптом
     * @return String
     */
    private static String getFilePathForScript(){
        String path = System.getenv("dos");
        System.out.println(System.getProperty("user.dir"));
        if (path == null){
            return ("----\nПуть через переменную окружения dos не указан\n----");
        } else {
            return path;
        }
    }
    /**
     * Метод вызывается, если файл со скриптом содержит команду add
     * @throws IOException исключение выбрасывается, если в скрипте сожержатся некорректные данные
     */
    private static String addForScript() throws IOException {
        String scriptReceive = "";
        SpaceMarine sm =new SpaceMarine();
        sm.setNameF();
        sm.setHealthF();
        sm.setHeightF();
        sm.setWeaponTypeF();
        sm.setMeleeWeaponF();
        sm.setCoordinatesF();
        sm.setChapterF();
        sm.setId();
        sm.setCreationDate();
        if ((sm.getName() ==null)||(sm.getName().trim().length() ==0)||(sm.getChapter().getChapterName().trim().length()==0)||(sm.getChapter().getChapterName()==null)||((sm.getChapter().getMarinesCount())>1000)||((sm.getChapter().getMarinesCount())<=0)||(sm.getHealth() == null)||(sm.getCoordinates().getX() == null)||(sm.getHealth() <= 0)||(sm.getHeight() == null)||(sm.getMeleeWeapon() == null)||(sm.getCoordinates()==null)){
            scriptReceive = "некорректные поля в скрипте\n"+
                    "Такой объект в коллекцию не будет добавлен";
        }
        else {
            collection.add(sm);
            scriptReceive = "Объект успешно добавлен в коллекцию.";
        }
        return scriptReceive;
    }
    /**
     * Метод removeGreater удаляет из коллекции все элементы, которые превышают заданный
     * @exception InputMismatchException выбрасывает исключение при некорректном пользовательском вводе
     */
    public static String removeGreater(int lj){
        String removeGr = " ";
        if (collection.size() != 0) {
                Object[] arr = collection.toArray();
                for (int i = 0; i < arr.length; i++) {
                    if (i > lj) {
                        collection.remove(arr[i]);
                        removeGr = "Элемент успешно удалён";
                    }
                }
        }
        else removeGr = "Коллекция пуста";
        return removeGr;
    }
    public static String clear(){
        collection.clear();
        String clear = "Коллекция успешно отформатирована";
        return clear;
    }
    /**
     * Метод save сохраняет коллекцию в файл
     */
    public static void save(){
        try {
            XMLWorker.saveCollection();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace(); }
    }



    /**
     * Метод execute_script считывает и исполняет скрипт из указанного файла
     * @throws IOException выбрасывает исключения при некорректном пользовавтельском вводе
     * @exception FileNotFoundException выбрасывает исключения если указанный пользователем файл не существует
     */
    public static ArrayList<String> executeScript() throws IOException {
        ArrayList<String> scriptoData = new ArrayList<>();
        ArrayList<String> scriptoHistory = new ArrayList<>();
        al.add(q);
        q++;
        if (al.size() > 4){
            System.out.println("скрипт может зациклиться");
        }
        else {
            try {
                String pathToFile = getFilePathForScript();
                if (pathToFile == null)
                    System.out.println("----\nПуть не указан, дальнейшая работа не возможна.\n----");
                else {
                    String[] ImpData = null;
                    try {
                        String str = null;
                        //slmmsk = new Scanner(new File("script.txt"));
                        slmmsk = new Scanner(new InputStreamReader(new FileInputStream(pathToFile)));
                        while ((str = slmmsk.nextLine()) != null) {
                            String[] list = str.split(" ");
                            for (int i = 0; i < list.length; i++) {
                                str = list[i];
                                str = str.trim();
                                list = str.trim().split(" ", 2);
                                try {
                                    switch (list[0]) {
                                        case "info":
                                            info();
                                            scriptoData.add(info());
                                            scriptoHistory.add("info");
                                            break;
                                        case "help":
                                            help();
                                            scriptoData.add(help());
                                            scriptoHistory.add("help");
                                            break;
                                        case "head":
                                            if (collection.size() != 0) {
                                                scriptoData.add(head());
                                            } else scriptoData.add("Коллекция пуста");
                                            scriptoHistory.add("head");
                                            break;
                                        case "clear":
                                            scriptoData.add(clear());
                                            scriptoHistory.add("clear");
                                            break;
                                        case "show":
                                            scriptoData.add(show());
                                            String show = "show";
                                            scriptoHistory.add(show);
                                            break;
                                        case "add":
                                            scriptoData.add(addForScript());
                                            scriptoHistory.add("add");
                                            break;
                                        case "update_id":
                                            String update = "";
                                            if (collection.size() != 0) {
                                                try {
                                                    int x = slmmsk.nextInt();
                                                    int k = 0;
                                                    for (SpaceMarine s : collection) {
                                                        if (x == s.getId()) {
                                                            s.setNewId();
                                                            k++;
                                                            update = "Команда update_id успешно выполнена.";
                                                        }
                                                    }
                                                    if (k == 0) {
                                                        update = "Элемент с таким id не найден";
                                                    }
                                                } catch (InputMismatchException e) {
                                                    update ="Поле в скрипте заполнено некорректно";
                                                }
                                            } else update = "Коллекция пуста";
                                            scriptoHistory.add("update_id");
                                            scriptoData.add(update);
                                            break;
                                        case "remove_by_id":
                                            String remove = "";
                                            if (collection.size() != 0) {
                                                try {
                                                    int x = slmmsk.nextInt();
                                                    int ki = 0;
                                                    for (SpaceMarine s : collection) {
                                                        if (x == s.getId()) {
                                                            collection.remove(s);
                                                            ki++;
                                                        }
                                                    }
                                                    if (ki == 0) {
                                                        remove = "Элемент с таким id не найден";
                                                    }
                                                } catch (InputMismatchException e) {
                                                    remove = "Поле id в скрипте заполнено некорректно";
                                                }
                                            } else remove = "Коллекция пуста";
                                            scriptoHistory.add("remove_by_id");
                                            scriptoData.add(remove);
                                            break;
                                        case "sum_of_height":
                                            if (collection.size() != 0) {
                                                scriptoData.add(SumOfHeight());
                                            } else scriptoData.add("Коллекция пуста");
                                            scriptoHistory.add("sum_of_height");
                                            break;
                                        case "max_by_name":
                                            if (collection.size() != 0) {
                                                scriptoData.add(maxByName());
                                            } else scriptoData.add("Коллекция пуста");
                                            scriptoHistory.add("max_by_name");
                                            break;
                                        case "filter_greater_than_height":
                                            if (collection.size() != 0) {
                                                try {
                                                    Long min = slmmsk.nextLong();
                                                    for (SpaceMarine s : collection) {
                                                        if (s.getHeight() > min) {
                                                            scriptoData.add(s.getName());
                                                        }
                                                    }
                                                } catch (InputMismatchException e) {
                                                    scriptoData.add("Поле в скрипте заполнено некорректно");
                                                }
                                            } else scriptoData.add("Коллекция пуста");
                                            scriptoHistory.add("filter_greater_than_height");
                                            break;
                                        case "remove_greater":
                                            if (collection.size() != 0) {
                                                try {
                                                    int z = slmmsk.nextInt() - 1;
                                                    if (z < 0) {
                                                        scriptoData.add("при вызове команды remove_greater аргумент должен быть целым и больше нуля");
                                                    }
                                                    Object[] arr = collection.toArray();
                                                    for (int p = 0; p < arr.length; p++) {
                                                        if (p > z) {
                                                            collection.remove(arr[p]);
                                                            scriptoData.add("Команда remove_greater успешно выполнена");
                                                        }
                                                    }
                                                } catch (InputMismatchException e) {
                                                    scriptoData.add("Некорректные данные в скрипте для команды remove_greater: должно быть целое положительное число");
                                                }
                                            } else scriptoData.add("Коллекция пуста");
                                            scriptoHistory.add("remove_greater");
                                            break;
                                        case "history":
                                            scriptoData.add(scriptoHistory.toString());
                                            scriptoHistory.add("history");
                                            break;
                                        case "exit":
                                            break;
                                        case "execute_script":
                                            executeScript();
                                            //CommandControl.history.add(CommandControl.commandName);
                                            break;
                                    }
                                } catch (NoSuchElementException e) {
                                    System.out.println("Программа завершена");
                                }
                            }
                        }
                        slmmsk.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден ;(");
                    }

               }
            } catch (NoSuchElementException e) {
                e.getMessage();
            }

        }
        return scriptoData;
}
}
