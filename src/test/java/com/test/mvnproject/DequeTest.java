package com.test.mvnproject;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DequeTest {
	
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
  public void verifyNavBar() { 
	  
	  boolean result = false;
	  try {
	  WebDriverWait wait = new WebDriverWait(driver, 100);
	  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("main-nav"))));
	  
	  result = true;
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.getStackTrace());
	  }
	  Assert.assertTrue(result);
  }
  
  @Test
  public void verifyRadioButtonsinAdventure() { 
	   
	  int actual_radio_cnt =  driver.findElements(By.xpath("//h3[text()='Let the Adventure Begin!']/following-sibling::form//input[@type='radio']")).size();
	  
	  
	  Assert.assertEquals(actual_radio_cnt,5,"The actual count "+actual_radio_cnt+"is not matching with expected count 5");
  }
  
  
  @Test
  public void verifyAddTraveller() { 
	   
	  WebElement addtravel =  driver.findElement(By.className("add-traveler"));
	  addtravel.click();
	  int actual_cnt = driver.findElements(By.xpath("//div[@class='passenger added-passenger']")).size();
	  Assert.assertEquals(actual_cnt,1,"The Passenager is not added");
	  
	  
	  
  }
  
  
  @Test
  public void verifyVideoNav() throws InterruptedException { 
	   
	  int cnt = driver.findElements(By.xpath("//div[@class='vid-nav']/a")).size(); 
	  
	  for(int i=0;i<cnt;i++)
	  {
		  //get the current header
		  String prev_header =  driver.findElement(By.id("video-text")).getText();
		  
		  //clcik on next video link
		  driver.findElement(By.xpath("//div[@class='vid-arrows nextvid']/i")).click();
		  
		  // get latest header
		  String current_header =  driver.findElement(By.id("video-text")).getText();
		  
		  System.out.println("Current Video Header "+current_header);
		  
		  Thread.sleep(3000);
		  
		  // compare previous and current header are not same
		  Assert.assertNotEquals(current_header, prev_header, "The navigation did not happen for the "+i+1+" navigation");
		  
	  }
	  
	  
  }


  
  @AfterTest
  public void tearDown()
  {
	  driver.close();
	  driver.quit(); 
  }
  
  
  
  
}
