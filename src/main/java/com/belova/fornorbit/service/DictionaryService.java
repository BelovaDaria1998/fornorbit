package com.belova.fornorbit.service;

import com.belova.fornorbit.models.EmotionWord;
import com.belova.fornorbit.repositories.DictionaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DictionaryService {
    private DictionaryRepository dictionaryRepository;
    private ConvertMessage convertMessage;

    @Autowired
    public void setDictionaryRepository(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }
    public long count() {
        return dictionaryRepository.count();
    }

    public Long loading() {
        dictionaryRepository.deleteAll();
        File file = new File(getClass().getClassLoader().getResource("emo_dict.csv").getPath());
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader((fileReader));
            String line;
            long count = dictionaryRepository.count();
            dictionaryRepository.deleteAll();
            Long i = count;
            List<EmotionWord> emotionWordList = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split(";");
                if (i > 0L) {
                    lines[0] = lines[0].replaceAll("ё", "е");
                    lines[0] = lines[0].replaceAll("Ё", "Е");
                    EmotionWord emotionWord = new EmotionWord(lines);
                    emotionWord.setId(i);
                    emotionWordList.add(emotionWord);
                }
                i++;
            }
            dictionaryRepository.saveAll(emotionWordList);
            count = dictionaryRepository.count();
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public float rating() {
        float rating = 0;
        ArrayList<String> p = convertMessage.findPhrases();
        ArrayList<EmotionWord> findedWords;
        for (String str: p) {
            findedWords = dictionaryRepository.findEmotionWordByWord(str);
            if(findedWords.size() == 0) {
                continue;
            }
            else {
                for(int i = 0; i < findedWords.size(); i ++) {
                    if(str.equals(findedWords.get(i).getWord())) {
                        rating += findedWords.get(i).getCoefficient();
                        continue;
                    }
                    else {
                        String str1 = str.toLowerCase();
                        if(str1.equals(findedWords.get(i).getWord())) {
                            rating += findedWords.get(i).getCoefficient();
                            continue;
                        }
                    }
                    if(findedWords.get(i).getWord().indexOf(" ") == -1 && findedWords.get(i).getWord().length() > str.length()) {
                        rating += findedWords.get(i).getCoefficient();
                    }
                }
            }
        }
        return rating;
    }

    public void createConvertMessage(String message) {
        convertMessage = new ConvertMessage(message);
    }

    public void clear() {
        dictionaryRepository.deleteAll();
    }
    public Iterable<EmotionWord> findAll() {
        return dictionaryRepository.findAll();
    }
}

