package com.belova.fornorbit.repositories;

import com.belova.fornorbit.models.EmotionWord;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.ArrayList;

public interface DictionaryRepository extends ElasticsearchRepository<EmotionWord, Long> {
        EmotionWord findEmotionWordByCoefficient(float coefficient);
        ArrayList<EmotionWord> findEmotionWordByWord(String word);
}
