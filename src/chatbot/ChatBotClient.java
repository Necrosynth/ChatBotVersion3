package chatbot;

import data.Settings;
import data.TopicData;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
/*
    ToDo:
    Break up method into helper functions
    Expand on custom settings with YT Livestream URL, Discord Location
    Consider an interface for Place for Place Classes


    Kill Open Program after leaving Topic Menu
 */

//Client for ChatBotEngine
public class ChatBotClient {
    //Path to Browser
    private static String browserLocation = "C:\\";

    //URL to The Prime Thanatos YouTube livestream
    private static final String TPT_URL = "https://www.youtube.com/watch?v=-DGit6qc4Lc";

    //Where Discord is located on computer
    private static final String DISCORD_LOCATION = "\"C:\\Users\\johnw\\AppData\\Local\\Discord\\app-1.0.9011\\Discord.exe\"";

    //Location on computer of the custom Settings text file
    private static final String SETTINGS_FILE_NAME = "config\\Settings.txt";

    //Location on computer of the custom Topics data text file
    private static final String TOPICS_FILE_NAME = "config\\Topics.txt";

    //Setup where the browser is located
    public static void setBrowserLocation(String newBrowserLocation){
        browserLocation = newBrowserLocation;
    }

    //Get location of browser
    public static String getBrowserLocation() {
        return browserLocation;
    }

    //REALLY NEEDED???..........................................
    //Creates list of Topic objects from raw text data file
    private static void setUpTopicDataAndMsgs(String fileName) throws FileNotFoundException {
        Conversation.buildListOfTopicsAndMsgs(fileName);
    }

    private static void getListOfTopicNames(List<String> tempTopicNames){
        List<TopicData> tempTopics = Conversation.getTopicsList();
        for (TopicData tempTopic : tempTopics){
            String topicName = tempTopic.getTopicName();
            tempTopicNames.add(topicName);
        }
    }

    //Get list of Topic Names and prints them out for user
    private static void printOutTopicNames(List<String> tempTopicNames){
        System.out.print("Names of Topics: [" + tempTopicNames.get(0) + ", ");
        for (int i = 1; i < tempTopicNames.size(); i++) {
            if (i < (tempTopicNames.size() - 1))
                System.out.print(tempTopicNames.get(i) + ", ");
            else
                System.out.print(tempTopicNames.get(i));
        }
        System.out.println("]");
    }

    //Chooses a Topic and sends a message
    private static void chooseATopicSendMsg(String placeToGo) throws IOException, AWTException {
        List<String> tempTopicNames = Conversation.getTopicNamesList();
        String userTopic = "Waiting";
        do {
            printOutTopicNames(tempTopicNames);

            //Get input
            System.out.print("Your Name? (Press Enter to go back a Menu): ");
            Scanner console = new Scanner(System.in);
            userTopic = console.nextLine();

            //If the chosen Topic was a valid name
            if (tempTopicNames.contains(userTopic)) {
                if(!Location.isProgramOpen()) {
                    //Do these things if placeToGo is "Youtube"
                    if(placeToGo.equals("Youtube")) {
                        Location.openURL1AndClick(TPT_URL, YouTube.getYTXToClick(), YouTube.getYTYToClick(),
                                YouTube.getYtFastClickDelay(), YouTube.getYtSlowClickDelay());
                    }

                    //Do these things if placeToGo is "CB"
                    else if (placeToGo.equals("CB")){
                        Location.switchOpenProgram();
                        Location.clickTextBox(CB.getCbXToClick(), CB.getCbYToClick(),
                                CB.getCbClickDelay(), CB.getCbClickDelay());
                        CB.newCBChatTopic(userTopic);
                    //Do these things if placeToGo is "Discord"
                    } else if (placeToGo.equals("Discord")){
                        int x1 = Discord.getDirectMsgsX();
                        int y1 = Discord.getDirectMsgsY();
                        int x2 = Discord.getTopDirectMsgX();
                        int y2 = Discord.getTopDirectMsgY();
                        int x3 = Discord.getDirectMsgChatX();
                        int y3 = Discord.getDirectMsgChatY();
                        int fastDelay = Discord.getFastClickDelay();

                        Location.openProgramAndClick(DISCORD_LOCATION, x1, y1, x2, y2, x3, y3, fastDelay, fastDelay);
                        Discord.newDiscordBotTopic(userTopic);
                    }

                    Location.setProgramIsOpen(true);
                } else {
                    Location.switchOpenProgram();
                    if(placeToGo.equals("Youtube")) {
                        YouTube.clickYTChatBox();
                        YouTube.newYTBotTopic(userTopic);
                    } else if (placeToGo.equals("Discord")){
                        Discord.newDiscordBotTopic(userTopic);
                    }
                }

                Location.switchOpenProgram();
                System.out.println();

            } else if (!userTopic.equals("")){
                System.out.println();
                System.out.println("Invalid Command for Topic Name...");
                System.out.println();

            } else {
                System.out.println();
                Location.setProgramIsOpen(false);
                /////////////////KILL PROGRAM TOO
            }

        } while (!userTopic.equals(""));
    }

    //Start main
    public static void main(String[] args) throws IOException, AWTException {
        //Prepare Custom User Settings
        Settings.loadCustomSettings(SETTINGS_FILE_NAME);
        //Prepare Topic Data
        System.out.println("Welcome to the Chat Bot.");
        setUpTopicDataAndMsgs(TOPICS_FILE_NAME);
        Conversation.buildTopicDictionary();

        //Get the list of Topic Names
        getListOfTopicNames(Conversation.getTopicNamesList());

        Scanner console = new Scanner(System.in);

        //Place to Chat Menu
        String placeToChat = "Waiting";
        do {
            System.out.println("Where do you want to chat?");
            System.out.println("Places to chat: [Youtube, Discord]");
            System.out.print("Choose a Place (Enter to Exit): ");
            placeToChat = console.nextLine();
            System.out.println();

            switch (placeToChat) {
                case "":
                    break;

                case "Youtube":
                    //Topic to Talk About Menu
                    chooseATopicSendMsg("Youtube");
                    break;

                case "CB":
                    chooseATopicSendMsg("CB");
                    break;

                case "Discord":
                    chooseATopicSendMsg("Discord");
                    break;

                default:
                    System.out.println("Invalid Command for Place to Chat");
                    System.out.println();
                    break;
            }

        } while (!placeToChat.equals(""));
    }
}