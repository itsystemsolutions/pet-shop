package com.thesis.petshop.services.adopt_form.answer;

import org.springframework.stereotype.Service;

@Service
public class FormAnswerService {

    public FormAnswerService() {
    }

    public int analyzeAnswerScore(FormAnswer formAnswer) {
        int correctAnswerCount = 0;

        if (formAnswer.getAnswer1().equalsIgnoreCase("MYSELF")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer2().equalsIgnoreCase("NO")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer3().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer4().equalsIgnoreCase("PARENTS")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer5().equalsIgnoreCase("NO")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer6().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer7().equalsIgnoreCase("MYSELF")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer8().equalsIgnoreCase("FAMILY")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer9().equalsIgnoreCase("8HRS") ||
                formAnswer.getAnswer9().equalsIgnoreCase("9HRS")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer10().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer11().equalsIgnoreCase("PROPER_TRAINING") ||
                formAnswer.getAnswer11().equalsIgnoreCase("HOUSE_TOUR")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer12().equalsIgnoreCase("HOUSE")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer13().equalsIgnoreCase("NO")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer14().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer15().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer16().equalsIgnoreCase("NOT_A_BIG_DEAL")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer17().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer18().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer19().equalsIgnoreCase("YES")) {
            correctAnswerCount++;
        }
        if (formAnswer.getAnswer20().equalsIgnoreCase("HIRE_PROFESSIONAL") ||
                formAnswer.getAnswer20().equalsIgnoreCase("WATCH_YOUTUBE")) {
            correctAnswerCount++;
        }

        return correctAnswerCount;
    }

}
