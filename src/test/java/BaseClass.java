import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.BeforeTest;

public class BaseClass {

	@BeforeTest
     public Properties LoadProperties() {
		
		try {
			InputStream inStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();
			prop.load(inStream);
			return prop;
		}
		catch(Exception e) {
			System.out.println("File not found");
			return null;
		}
		
	
		
	}
}
