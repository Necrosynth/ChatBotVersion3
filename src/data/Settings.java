package data;

import chatbot.ChatBotClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Settings {

    //Handles loading the Custom Settings into the program
    public static void loadCustomSettings(String fileName) throws FileNotFoundException {
        //Get all data from the Settings file
        Scanner inputData = new Scanner(new File(fileName));
        try {
            while (inputData.hasNextLine()) { //Runs loop while there's more data to process
                while(inputData.hasNext()){
                    String nextToken = inputData.next();
                    switch (nextToken){
                        case "BROWSER_LOCATION:": //Start picking out pieces of data from the Settings
                            String browserLocation = inputData.nextLine();
                            ChatBotClient.setBrowserLocation(browserLocation);
                    }
                }
            }
        } finally {
            //Close whether or not there's File Error
            inputData.close();
        }
    }
}