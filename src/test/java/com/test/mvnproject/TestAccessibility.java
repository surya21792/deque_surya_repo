package com.test.mvnproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class TestAccessibility {
	
	
	 private static WebDriver driver = null;
	 private static String url = "https://dequeuniversity.com/demo/mars";
  @BeforeTest
  public void setUp()
  {
	  System.setProperty("webdriver.chrome.driver", "chromedriver");
	  driver = new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  driver.get(url);
	 
  }
  
  
  @Test
  
  public void verifyAccessibility() throws JsonIOException, JsonSyntaxException, FileNotFoundException
  {
	  AxeBuilder builder = new AxeBuilder();
	  Results results = builder.analyze(driver);
	  List<Rule> violations = results.getViolations();
	  String reportFile = "report";
	  if (violations.size() == 0)
	     {
	         Assert.assertTrue(true, "No violations found");
	     }
	     else
	     {
	         JsonParser jsonParser = new JsonParser();
	         Gson gson = new GsonBuilder().setPrettyPrinting().create();
	         AxeReporter.writeResultsToJsonFile(reportFile, results);
	         JsonElement jsonElement = jsonParser.parse(new FileReader(reportFile + ".json"));
	         String prettyJson = gson.toJson(jsonElement);
	         AxeReporter.writeResultsToTextFile(reportFile, prettyJson);
	         
	         Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");
	     }
  }
  
  @AfterTest
  public void tearDown()
  {
	  driver.close();
	  driver.quit(); 
  }
}
