package com.galen.pom.galen;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Avinash on 16/Dec/2018.
 */
public class GalenExampleTest
{
    private WebDriver driver;

    @Before
    public void setUp()
    {
    	//Set up the chrome properties
    	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
        //Create a Chrome Driver
        driver = new ChromeDriver();
        //Set the implicit time at browser level
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Go to swtestacademy.com
        driver.get("http://www.swtestacademy.com/");
      //Set the browser size for desktop
        //driver.manage().window().setSize(new Dimension(1200, 800));
        driver.manage().window().maximize();
        
    }

    @Test
    public void homePageLayoutTest() throws IOException
    {
        //Create a layoutReport object
        //checkLayout function checks the layout and returns a LayoutReport object
        LayoutReport layoutReport = Galen.checkLayout(driver, "specs/homepage.gspec", Arrays.asList("desktop"));

        //Create a tests list
        List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

        //Create a GalenTestInfo object
        GalenTestInfo test = GalenTestInfo.fromString("homepage layout");

        //Get layoutReport and assign to test object
        test.getReport().layout(layoutReport, "check homepage layout");

        //Add test object to the tests list
        tests.add(test);

        //Create a htmlReportBuilder object
        HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

        //Create a report under /target folder based on tests list
        htmlReportBuilder.build(tests, "target");

        //If layoutReport has errors Assert Fail
        if (layoutReport.errors() > 0)
        {
            Assert.fail("Layout test failed");
        }
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

}