package com.wordcounttool.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordcounttool.service.WordCountToolService;

@RestController
public class WordCountToolController {

	@Autowired
	private WordCountToolService service;

	@GetMapping("/wordcount/{word}")
	public String wordCountMap(@PathVariable("word") String sentence) {
		return service.wordCountMapService(sentence).toString();
	}

	@SuppressWarnings("resource")
	@PostMapping("/wordcount/upload")
    public ResponseEntity<String> uploadTextFile(@RequestParam("file") MultipartFile file) {
        // Check if the file is not empty
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a valid text file", HttpStatus.BAD_REQUEST);
        }

        // Check if the file is a text file (you can add more checks)
        if (!file.getContentType().equals("text/plain")) {
            return new ResponseEntity<>("Only text files are allowed", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        try {
            // Read the content of the file
            String content = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Return the content of the file
            return new ResponseEntity<>(service.wordCountMapService(content).toString(), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Error reading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
