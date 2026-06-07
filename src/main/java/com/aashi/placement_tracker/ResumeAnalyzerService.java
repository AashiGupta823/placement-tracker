package com.aashi.placement_tracker;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ResumeAnalyzerService {

    public Map<String, Object> analyzeResume(
            MultipartFile resumeFile,
            String jobDescription) throws IOException {

        // Extract text from PDF
        PDDocument document = PDDocument.load(
            resumeFile.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String resumeText = stripper.getText(document)
            .toLowerCase();
        document.close();

        // Extract keywords from JD
        String[] jdWords = jobDescription.toLowerCase()
            .split("[\\s,\\.]+");

        List<String> matchedKeywords = new ArrayList<>();
        List<String> missingKeywords = new ArrayList<>();

        Set<String> resumeWords = new HashSet<>(
            Arrays.asList(resumeText.split("[\\s,\\.()]+")));

        for (String word : jdWords) {
            if (word.length() > 3) {
                if (resumeWords.contains(word)) {
                    if (!matchedKeywords.contains(word))
                        matchedKeywords.add(word);
                } else {
                    if (!missingKeywords.contains(word))
                        missingKeywords.add(word);
                }
            }
        }

        int total = matchedKeywords.size() +
            missingKeywords.size();
        int score = total > 0 ?
            (matchedKeywords.size() * 100) / total : 0;

        Map<String, Object> result = new HashMap<>();
        result.put("matchScore", score);
        result.put("matchedKeywords", matchedKeywords);
        result.put("missingKeywords", missingKeywords);

        return result;
    }
}