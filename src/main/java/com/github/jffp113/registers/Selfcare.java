package com.github.jffp113.registers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;

public class Selfcare {

    private static final String URL = "https://192.168.0.253:6443";

    private final WebDriver driver;

    public Selfcare( WebDriver driver) {
        this.driver = driver;
    }


    public void registe(InputStream inputStream) {
        Yaml yaml = new Yaml(new Constructor());
        SelfcareYaml configs = yaml.loadAs(inputStream,SelfcareYaml.class);
        procedure(configs);
    }

    private void procedure(SelfcareYaml cfg) {
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        //driver.wait();


        Credential cred = cfg.getCredentials();
        login(cred.getUsername(), cred.getPassword());

        navigateToTimesheet();

        for(Entry e : cfg.getEntries()) {
            //TODO use library to wait
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            registerDay(e);
        }
    }


    private void login(String username, String password) {
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);

        WebElement button = driver.findElement(By.xpath("//button[text()='Entrar']"));
        button.click();
        //usernameField.sendKeys(PASSWORD);
    }

    private void navigateToTimesheet() {
        WebElement coButton = driver.findElement(By.xpath("//span[text()='Coworker Area']"));
        coButton.click();//Timesheet
        WebElement timesheetButton = driver.findElement(By.xpath("//span[text()='Timesheet']"));
        timesheetButton.click();
    }
    //month-container col-xs-3

    private void registerDay(Entry entry) {
        Date date = entry.getDate();
        openDay(date.getMonth(), date.getDay());

        //TODO use library to wait
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        chooseOption("select_client", entry.getClient());
        chooseOption("select_cpackage", entry.getCreditPackage());
        chooseOption("select_projetos", entry.getProject());
        chooseOption("select_ServiceLevels", entry.getServiceLevel());
        chooseOption("select_options", entry.getOptions());

        WebElement hoursElm = driver.findElement(By.name("hours_spent"));
        hoursElm.sendKeys(entry.getHoursSpent());

        WebElement taskElm = driver.findElement(By.name("task"));

        for(String task : entry.getTasks()){
            taskElm.sendKeys("-> " + task);
            taskElm.sendKeys("\n");
        }
        WebElement saveElm = driver.findElement(By.id("save"));
        saveElm.click();
    }

    private void chooseOption(String selectorName, String optionName) {
        WebElement selectElm = driver.findElement(By.name(selectorName));
        Select s = new Select(selectElm);
        s.selectByVisibleText(optionName);
    }

    private void openDay(int month, int day) {
        List<WebElement> monthElm = driver.findElements(By.className("month"));
        WebElement selectedMonth = monthElm.get(month - 1);
        WebElement selectedDay = selectedMonth.findElement(By.xpath(String.format(".//div[text()='%d']", day)));
        selectedDay.click();
    }
}
