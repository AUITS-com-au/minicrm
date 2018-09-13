package com.sh.crm.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class QuickPasswordGenerator {


    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.print("Please enter the password:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            System.out.println(String.format("%s", passwordEncoder.encode(scanner.nextLine())));
        }
    }

}
