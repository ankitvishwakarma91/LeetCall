package com.example.leetcall.service.impl;

import com.example.leetcall.dto.LeetcodeSubmission;
import com.example.leetcall.service.LeetcodeService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeetcodeServiceImpl implements LeetcodeService {


    private final WebClient webClient;

    public LeetcodeServiceImpl() {
        String BASE_URL = "https://leetcode.com";
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Referer", BASE_URL)
                .build();
    }

    @Override
    public List<LeetcodeSubmission> getRecentSubmission(String leetcodeUsername) {
        String query = """
                {
                     "query": "query recentSubmissions($username: String!) { recentSubmissionList(username: $username, limit: 20) { title statusDisplay timestamp lang } }",
                      "variables": {
                        "username": "%s"
                      }
                }
                """.formatted(leetcodeUsername);

        JsonNode response = webClient.post()
                .uri("/graphql")
                .bodyValue(query)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode list = response
                .path("data")
                .path("recentSubmissionList");

        List<LeetcodeSubmission> submissions = new ArrayList<>();

        if (list.isArray()) {
            for (JsonNode node : list) {
                LeetcodeSubmission sub = new LeetcodeSubmission();
                sub.setTitle(node.path("title").asString());
                sub.setStatusDisplay(node.path("statusDisplay").asString());
                sub.setTimestamp(node.path("timestamp").asString());
                sub.setLang(node.path("lang").asString());
                submissions.add(sub);
            }
        }

        return submissions;
    }

    @Override
    public boolean hasSubmittedToday(String leetcodeUsername, String timeZone) {

        List<LeetcodeSubmission> submissions = getRecentSubmission(leetcodeUsername);

        ZoneId userTimeZone = ZoneId.of(timeZone);
        LocalDate todayInUserTimeZone = LocalDate.now(userTimeZone);

        return submissions.stream()
                .filter(s -> s.getStatusDisplay().equalsIgnoreCase("Accepted"))
                .anyMatch(s -> {
                    long epoch = Long.parseLong(s.getTimestamp());
                    LocalDate submissionDate = Instant.ofEpochSecond(epoch)
                            .atZone(userTimeZone)
                            .toLocalDate();
                    return submissionDate.equals(todayInUserTimeZone);
                });
    }
}
