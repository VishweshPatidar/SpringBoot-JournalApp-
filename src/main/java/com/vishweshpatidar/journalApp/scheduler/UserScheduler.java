package com.vishweshpatidar.journalApp.scheduler;

import com.vishweshpatidar.journalApp.cache.AppCache;
import com.vishweshpatidar.journalApp.entity.JournalEntry;
import com.vishweshpatidar.journalApp.entity.User;
import com.vishweshpatidar.journalApp.repository.UserRepositoryImpl;
import com.vishweshpatidar.journalApp.service.EmailService;
import com.vishweshpatidar.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSAMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join("", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
