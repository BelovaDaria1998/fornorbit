package com.belova.fornorbit.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "emotionword", type = "emotionword")
public class EmotionWord {
    @Id
    Long id;
    String word;
    String emotion;
    float coefficient;

    public EmotionWord() {
    }

    public EmotionWord(String[] line) {
        this.word = line[0];
        this.emotion = line[1];
        this.coefficient = Float.parseFloat(line[2]);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public float getCoefficient() {
        return  coefficient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
