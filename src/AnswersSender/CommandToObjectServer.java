package AnswersSender;

import java.io.Serializable;
public class CommandToObjectServer implements Serializable {
    private String command;
    String answer;
    public CommandToObjectServer(String command, String answer){
        this.command = command;
        this.answer = answer;
    }
    public String getCommand(){
        return command;
    }
    public String getAnswer(){
        return answer;
    }
}
