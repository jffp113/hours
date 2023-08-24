package com.github.jffp113;

import com.github.jffp113.registers.Selfcare;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);

        WebDriver driver = new ChromeDriver(options);

        Selfcare selfcare = new Selfcare(driver);
        selfcare.registe(new FileInputStream("./logbook.yaml"));
    }

}