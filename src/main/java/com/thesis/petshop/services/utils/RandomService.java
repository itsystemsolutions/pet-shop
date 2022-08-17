package com.thesis.petshop.services.utils;

/*
 * Created: renzb
 * Date: 8/17/2022
 */

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class RandomService {

    private static Random random = null;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static Random random(int bound) {
        return new Random(bound);
    }

    public String randomCode() {
        return String.format("%04d", random.nextInt(10000));
    }

    public String generatePetCode() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }

}
