package com.belova.fornorbit.service;

import java.util.ArrayList;
import java.util.List;

public class ConvertMessage {
    String[] messages;
    public ConvertMessage(String message) {
        this.messages = message.split("[\\.|\\?|\\!]");
        deletePunctuation();
    }
    public void deletePunctuation() {
        for(int i = 0; i < messages.length; i ++) {
            messages[i] = messages[i].replaceAll("ё", "е");
            messages[i] = messages[i].replaceAll("Ё", "Е");
            messages[i] = messages[i].replaceAll("[^a-zA-Zа-яА-Я]", " ").replaceAll(" {2,}", " ").trim();
        }
    }
    public ArrayList<String> findPhrases() {
        ArrayList<String> phrases = new ArrayList<String>();
        for(int k = 0; k < messages.length; k ++) {
            String[] words = messages[k].split(" ");
            String phrase;
            for (int i = 0; i < words.length; i++) {
                if(words[i].length() == 1 || words[i].length() == 2) {
                    continue;
                }
                phrase = words[i];
                phrases.add(words[i]);
                phrase += " ";
                for (int j = i + 1; j < i + 3 && j < words.length; j++) {
                    phrase += words[j];
                    phrases.add(phrase);
                    phrase += " ";
                }
            }
        }
        return phrases;
    }

    //private void defi
    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }
}
