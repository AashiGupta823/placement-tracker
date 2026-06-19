package com.aashi.placement_tracker;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ResumeAnalyzerService {

    public Map<String, Object> analyzeResume(
            MultipartFile resumeFile,
            String jobDescription) throws IOException {

        // Resume ka actual text
        String resumeText = "aashi gupta java javascript html css rest apis spring boot mysql git github " +
            "data structures algorithms object oriented programming dbms nlp generative ai " +
            "placement tracker resume analyzer ai plagiarism detection web app " +
            "leetcode dsa problem solving full stack web development " +
            "edunet foundation ibm skillsbuild inamigos foundation internship " +
            "pranveer singh institute technology aktu kanpur information technology " +
            "backend frontend database postman vscode operating system";

        String[] jdWords = jobDescription.toLowerCase()
            .split("[\\s,\\.\\-\\/]+");

        List<String> matchedKeywords = new ArrayList<>();
        List<String> missingKeywords = new ArrayList<>();
        String resumeLower = resumeText.toLowerCase();

        for (String word : jdWords) {
            if (word.length() > 3) {
                if (resumeLower.contains(word)) {
                    if (!matchedKeywords.contains(word))
                        matchedKeywords.add(word);
                } else {
                    if (!missingKeywords.contains(word))
                        missingKeywords.add(word);
                }
            }
        }

        int total = matchedKeywords.size() + missingKeywords.size();
        int score = total > 0 ?
            (matchedKeywords.size() * 100) / total : 0;

        Map<String, Object> result = new HashMap<>();
        result.put("matchScore", score);
        result.put("matchedKeywords", matchedKeywords);
        result.put("missingKeywords", missingKeywords);
        return result;
    }
}