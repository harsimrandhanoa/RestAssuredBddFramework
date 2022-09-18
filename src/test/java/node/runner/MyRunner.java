package node.runner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features= {"src/test/resources/"},
		//features= {"@rerun/failed_scenarios.txt"},
		glue = {"node.teststeps"},
        plugin= {"html:target/site/cucumber-reports.html","rerun:rerun/failed_scenarios.txt"},
        tags = "@Login or @Product or @Order",
        monochrome = false
	  )

public class MyRunner  extends AbstractTestNGCucumberTests {
	
//	@Test
//	public void testa(){
//		System.out.println("Abc");
//	}
		
}