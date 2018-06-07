package com.company;



import com.google.gson.JsonObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        //generateGradeReport();
        //checkNotification();
        webCrawler();
    }


    public static List<String> links=new ArrayList<>();
    static WebDriver webDriver;
    public static void webCrawler() throws NoSuchElementException{
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        webDriver=new ChromeDriver();
        webDriver.navigate().to("http://www.bbc.com/news/world/africa");
        List <WebElement> groups=webDriver.findElements(By.className("distinct-component-group "));
        for (WebElement i:groups)
        searchForLinks(i);
        System.out.println(groups.get(0).getAttribute("class"));


        writeToJson();

        for (String i : links){
            System.out.println(i);
        }
        webDriver.navigate().to("localhost:3000");


    }
    public static void searchForLinks(WebElement element){


        List<WebElement> atags =  element.findElements(By.tagName("a"));
        List<String> weblinks=new ArrayList<>();
        for (WebElement weblink:atags){
            String url = weblink.getAttribute("href");
            if (!weblinks.contains(url)&&!links.contains(url)
                    && !weblink.getText().equals("https://www.bbc.com/news/world/africa")) weblinks.add(url);
        }
        if (links!=null && atags!=null)links.addAll(weblinks);
        else if (links==null && atags!=null)links=weblinks;


        for (WebElement webElement:element.findElements(By.tagName("div")) )
        searchForLinks(webElement);


    }
    public static void writeToJson() {
        JsonObject json=new JsonObject();
        JsonObject titleJson=new JsonObject();
        int z=0;
        for (String url :links){
            System.out.println("In link :"+url);
            webDriver.navigate().to(url);
            if (true) {
                System.out.println("in world africa");
                try {
                    if (webDriver.findElement(By.className("story-body")) != null) {
                        WebElement body = webDriver.findElement(By.className("story-body"));
                        WebElement title = webDriver.findElement(By.className("story-body__h1"));
                        String text = null;
                        for (WebElement i : webDriver.findElement(By.className("story-body__inner")).findElements(By.tagName("p"))) {
                            if (text != null) text += "\r" + i.getText();
                            else text = i.getText();
                        }
                        


                        json.addProperty(title.getText(), text);
                        titleJson.addProperty(title.getText(), z);
                        System.out.println(title.getText());
                        z++;
                    }
                }
                catch (Exception e){
                }
            }


        }
        FileWriter file;
        try {

            file = new FileWriter("C:\\Users\\Haile\\Documents\\myApp\\file1.json");
            file.write(json.toString());
            file.close();


        }
        catch (IOException io){
            io.printStackTrace();
        }

        try {

            file = new FileWriter("C:\\Users\\Haile\\Documents\\myApp\\file2.json");
            file.write(titleJson.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully Copied JSON Object to File...");


        System.out.println(titleJson);
        System.out.println(json);

    }
}

