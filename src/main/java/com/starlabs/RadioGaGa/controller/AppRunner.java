package com.starlabs.RadioGaGa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import com.starlabs.RadioGaGa.domain.User;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;
    private boolean x = false;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    public void setBool(boolean x) {
        this.x = x;
//        try {
//            this.run();
//        } catch(Exception ex) {
//
//        }
    }

    public boolean getBool() {
        return this.x;
    }


    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        while(true) {
            Thread.sleep(100);
            if(x) {

                // Kick of multiple, asynchronous lookups
                CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
                CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
                CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

                // Wait until they are all done
                CompletableFuture.allOf(page1,page2,page3).join();

                // Print results, including elapsed time
                logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
                logger.info("--> " + page1.get());
                logger.info("--> " + page2.get());
                logger.info("--> " + page3.get());

                x = false;
            }
        }





    }

}