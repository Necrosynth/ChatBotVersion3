package chatbot;

import data.TopicData;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class Conversation {
    //List of Topic objects
    private static ArrayList<TopicData> topicsList = new ArrayList<>();

    //List of Topic Names
    private static List<String> topicNamesList = new ArrayList<>();

    //Dictionary for Topic Names (key), and Topic
    private static Dictionary<String, ArrayList<String>> topicDictionary = new Hashtable<>();

    //Builds a list of Msgs
    private static void buildListOfMessages(Scanner inputData, List<String> topicMessageList){
        String nextString = "Waiting";
        while (inputData.hasNext()) { //Runs loop until data is an empty line
            nextString = inputData.nextLine();

            //If the next piece of data is empty, it's the end of this Topic (or end of file)
            if (nextString.equals("")) {
                break; //Move on to next Topic
            } else {
                topicMessageList.add(nextString); //Data must be a Topic Message, add to Topic Message List
            }
        }
    }

    //Creates a list of Topic objects from a text file to be contained in this class
    public static void buildListOfTopicsAndMsgs(String fileName) throws FileNotFoundException {
        //Get all data from a file
        Scanner inputData = new Scanner(new File(fileName));
        try {
            while (inputData.hasNextLine()) { //Runs loop while there's more data to process
                //Temporary variables to contain data that will be used to create a Topic object
                String topicName = "";
                ArrayList<String> topicMessageList = new ArrayList<>();
                TopicData tempTopic = null;

                //Temporary variable to contain the next piece of data from all data
                topicName = inputData.nextLine();

                //Build the List of Messages for current Topic
                buildListOfMessages(inputData, topicMessageList);

                //Create new Topic object then add it to the Topics List
                tempTopic = new TopicData(topicName, topicMessageList);
                topicsList.add(tempTopic);
            }
        } finally {
            //Close whether or not there's File Error
            inputData.close();
        }
    }

    //Builds our Topic Dictionary, when called
    public static void buildTopicDictionary(){
        for(TopicData topic : topicsList){
            String name = topic.getTopicName();
            ArrayList<String> msgs = topic.getTopicMessages();

            topicDictionary.put(name, msgs);
        }
    }

    //Get the list of Topics
    public static ArrayList<TopicData> getTopicsList() {
        return topicsList;
    }

    //Get the list of Topic Names
    public static List<String> getTopicNamesList() { return topicNamesList; }

    //Get the Dictionary of Topic Names and Topic Messages
    public static Dictionary<String, ArrayList<String>> getTopicDictionary(){ return topicDictionary; }

    /*
    //Bot built for YouTube Livestreams
    public static void newYTBotTopic(String topicName) {

        ArrayList<String> msgs = topicDictionary.get(topicName);

        if (msgs.size() == 0)
            return;

        //If the size of total messages is less than the YouTube Timeout
        else if (msgs.size() < YouTube.getYTChatTimeout()) {
            for (int i = 0; i < msgs.size(); i++) {
                Location.pasteToChat(msgs.get(i), YouTube.getYTFastMsgDelay());
            }
        //If the size of total messages is greater than or equal to the YouTube Timeout between messages
        } else if (msgs.size() >= YouTube.getYTChatTimeout()) {
            //Say messages up to the Youtube Timeout
            for (int i = 0; i < (YouTube.getYTChatTimeout() + 1); i++) {
                Location.pasteToChat(msgs.get(i), YouTube.getYTFastMsgDelay());
            }

            //Then slow down a bit for the Timeout between messages
            for (int i = (YouTube.getYTChatTimeout() + 1); i < msgs.size(); i++) {
                Location.pasteToChat(msgs.get(i), YouTube.getYTSlowMsgDelay());
            }
        }
    }
     */

    /*
    public static void newCBChatTopic(String topicName) throws AWTException {
        //Robot robot = new Robot();
        //robot.delay(5000);
        ArrayList<String> msgs = topicDictionary.get(topicName);

        if (msgs.size() < 3){
            for (String msg : msgs) {
                Location.pasteToChat(msg, CB.getCbFastMsgDelay());
            }
        }
        else if (msgs.size() >= 3) {
            for (String msg : msgs) {
                //Location.pasteToChat(msgs.get(i), CB.getCbSlowMsgDelay());
                Location.pasteToChat(msg, CB.getCbSlowMsgDelay());
            }
        }
    }
     */
}