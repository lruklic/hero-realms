package runner;

import static spark.Spark.*;

/**
 * Starting point of web application.   
 * 
 * @author lruklic
 *
 */
  
public class Runner {

	public static void main(String[] args) {
		
		staticFileLocation("/public");
		
		redirect.get("", "/");
		redirect.get("/", "/index.html");
		
	}
	
}
