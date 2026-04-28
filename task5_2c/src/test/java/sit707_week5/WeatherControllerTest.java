package sit707_week5;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WeatherControllerTest {
	private static WeatherController wController;
    private static double[] temperatures;

    @BeforeClass
    public static void setUp() {
        System.out.println("+++ Start +++");        
        wController = WeatherController.getInstance();

        int nHours = wController.getTotalHours();
        temperatures = new double[nHours];        
        for (int i = 0; i < nHours; i++) {
            temperatures[i] = wController.getTemperatureForHour(i + 1);
        }
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("+++ ShutDown +++");        
        wController.close();
    }
    
	@Test
	public void testStudentIdentity() {
		String studentId = "s224877838";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Levin Joseph Poovakulath";
		Assert.assertNotNull("Student name is null", studentName);
	}

//	@Test
//	public void testTemperatureMin() {
//		System.out.println("+++ testTemperatureMin +++");
//		
//		// Initialise controller
//		WeatherController wController = WeatherController.getInstance();
//		
//		// Retrieve all the hours temperatures recorded as for today
//		int nHours = wController.getTotalHours();
//		double minTemperature = 1000;
//		for (int i=0; i<nHours; i++) {
//			// Hour range: 1 to nHours
//			double temperatureVal = wController.getTemperatureForHour(i+1); 
//			if (minTemperature > temperatureVal) {
//				minTemperature = temperatureVal;
//			}
//		}
//		
//		// Should be equal to the min value that is cached in the controller.
//		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
//		
//		// Shutdown controller
//		//wController.close();		
//	}
	
//	@Test
//	public void testTemperatureMax() {
//		System.out.println("+++ testTemperatureMax +++");
//		
//		// Initialise controller
//		WeatherController wController = WeatherController.getInstance();
//		
//		// Retrieve all the hours temperatures recorded as for today
//		int nHours = wController.getTotalHours();
//		double maxTemperature = -1;
//		for (int i=0; i<nHours; i++) {
//			// Hour range: 1 to nHours
//			double temperatureVal = wController.getTemperatureForHour(i+1); 
//			if (maxTemperature < temperatureVal) {
//				maxTemperature = temperatureVal;
//			}
//		}
//		
//		// Should be equal to the min value that is cached in the controller.
//		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
//		
//		// Shutdown controller
//		//wController.close();
//	}

//	@Test
//	public void testTemperatureAverage() {
//		System.out.println("+++ testTemperatureAverage +++");
//		
//		// Initialise controller
//		WeatherController wController = WeatherController.getInstance();
//		
//		// Retrieve all the hours temperatures recorded as for today
//		int nHours = wController.getTotalHours();
//		double sumTemp = 0;
//		for (int i=0; i<nHours; i++) {
//			// Hour range: 1 to nHours
//			double temperatureVal = wController.getTemperatureForHour(i+1); 
//			sumTemp += temperatureVal;
//		}
//		double averageTemp = sumTemp / nHours;
//		
//		// Should be equal to the min value that is cached in the controller.
//		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
//		
//		// Shutdown controller
//		//wController.close();
//	}
	
	@Test
	public void testTemperaturePersist() {
		System.out.println("+++ testTemperaturePersist +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		DateTimeFormatter tf = DateTimeFormatter.ofPattern("H:m:s");
		String persistTime = wController.persistTemperature(10, 19.5);
		LocalTime now = LocalTime.now();
		System.out.println("Persist time: " + persistTime + ", now: " + now.format(tf));
		
		LocalTime persistedAt;
		try {
			persistedAt = LocalTime.parse(persistTime, tf);
		} catch (DateTimeParseException e) {
			Assert.fail("Returned persist time is not parseable: " + persistTime);
			return;
		}
		
		int persistedSeconds = persistedAt.toSecondOfDay();
		int nowSeconds = now.toSecondOfDay();
		int diff = Math.abs(nowSeconds - persistedSeconds);
		int minDiffAcrossMidnight = Math.min(diff, 24 * 60 * 60 - diff);
		Assert.assertTrue("Persisted time should be close to current time", minDiffAcrossMidnight <= 5);
		
		wController.close();
	}
}
