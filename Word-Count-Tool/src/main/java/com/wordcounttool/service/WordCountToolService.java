package com.wordcounttool.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class WordCountToolService {

	public Map<String, Integer> wordCountMapService(String sentence) {

		String[] sentenceSplit = sentence.split(" ");

		Map<String, Integer> wordCountMap = new HashMap<>();

		for (String word : sentenceSplit) {
			if (wordCountMap.containsKey(word)) {
				wordCountMap.put(word, wordCountMap.get(word) + 1);
			} else {
				wordCountMap.put(word, 1);
			}
		}
		return wordCountMap;
	}
}
