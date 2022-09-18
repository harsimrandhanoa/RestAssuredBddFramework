package node.reports;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

	static ExtentReports reports;

	public static ExtentReports getInstance() {

		if (reports == null) {// first test

			reports = new ExtentReports();
			// init the report folder
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String reportsFolder = dateFormat.format(date).replaceAll(":", "_");


			String reportFolderPath = System.getProperty("user.dir") + "//reports//" + reportsFolder;
			File f = new File(reportFolderPath);
			f.mkdirs();// create dynamic report folder name + screenshots

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
			sparkReporter.config().setReportName("Cucumber BDD Rest Framework");
			sparkReporter.config().setDocumentTitle("Cucumber BDD Rest Framework Reports");
			/*
			 * sparkReporter.config().setTheme(Theme.STANDARD);
			 * sparkReporter.config().setEncoding("utf-8");
			 */

			reports.attachReporter(sparkReporter);
		}

		return reports;

	}
}