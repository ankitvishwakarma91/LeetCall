package com.example.leetcall.service;

import com.example.leetcall.dto.LeetcodeSubmission;

import java.util.List;

public interface LeetcodeService {

    public List<LeetcodeSubmission> getRecentSubmission(String leetcodeUsername);

    public boolean hasSubmittedToday(String leetcodeUsername , String timeZone);
}
