package CommandProcessing;

import AnswersSender.CommandToObjectServer;
import AnswersSender.ServerAnswer;
import java.io.IOException;

import static CommandProcessing.Commands.*;
import static ReceivingConnections.Receiver.*;

public class Control {
    public void processing() throws IOException, ClassNotFoundException {
            ServerAnswer sa = new ServerAnswer();
            sarray = s.trim().split(" ", 2);
            switch (sarray[0]) {
                case "help":
                    CommandToObjectServer help = new CommandToObjectServer("help", help());
                    sa.sendAnswer(help);
                    break;
                case "info":
                    CommandToObjectServer info = new CommandToObjectServer("info", info());
                    sa.sendAnswer(info);
                    break;
                case "show":
                    CommandToObjectServer show = new CommandToObjectServer("show", show());
                    sa.sendAnswer(show);
                    break;
                case "head":
                    CommandToObjectServer head = new CommandToObjectServer("head", head());
                    sa.sendAnswer(head);
                    break;
                case "sum_of_height":
                    CommandToObjectServer sumOfHeight = new CommandToObjectServer("sum_of_height", SumOfHeight());
                    sa.sendAnswer(sumOfHeight);
                    break;
                case "max_by_name":
                    CommandToObjectServer maxByName = new CommandToObjectServer("max_by_name", maxByName());
                    sa.sendAnswer(maxByName);
                    break;
                case "filter_greater_than_height":
                    CommandToObjectServer filterGr = new CommandToObjectServer("filter_greater_than_height", filterGreater(j));
                    sa.sendAnswer(filterGr);
                    break;
                case "update_id":
                    CommandToObjectServer updateId = new CommandToObjectServer("update_id", String.valueOf(updateId(g, spaceMarine)));//эти переменные считываются программой как нулевые
                    sa.sendAnswer(updateId);
                    break;
                case "add":
                    CommandToObjectServer add = new CommandToObjectServer("add", add(spaceMarine));
                    sa.sendAnswer(add);
                    break;
                case "remove_by_id":
                    CommandToObjectServer remove = new CommandToObjectServer("remove_by_id", removeById(p));
                    sa.sendAnswer(remove);
                    break;
                case "remove_greater":
                    CommandToObjectServer removeGr = new CommandToObjectServer("remove_greater", removeGreater(p));
                    sa.sendAnswer(removeGr);
                    break;
                case "clear":
                    CommandToObjectServer clear = new CommandToObjectServer("clear", clear());
                    sa.sendAnswer(clear);
                    break;
                case "history":
                    CommandToObjectServer hist = new CommandToObjectServer("history", historyR);
                    sa.sendAnswer(hist);
                    break;
                case "execute_script":
                    CommandToObjectServer script = new CommandToObjectServer("execute_script", executeScript().toString());
                    sa.sendAnswer(script);
                    break;
                case "exit":
                    System.out.println("--------------------------------------\nКлиент завершил работу программы. Коллекция успешно сохранена.");
                    Commands.save();
                    socketChannel.close();
                    break;
            }


    }
}
