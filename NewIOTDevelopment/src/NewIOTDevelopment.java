import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mraa.Aio;
import mraa.Platform;
import mraa.mraa;

public class NewIOTDevelopment {

	static java.sql.Date dt;

	static {
		try {
			System.loadLibrary("mraajava");
		} catch (UnsatisfiedLinkError e) {
			System.err
					.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n"
							+ e);
			System.exit(1);
		}
	}

	public static void main(String[] args) {

		// check that we are running on Galileo or Edison
		Platform platform = mraa.getPlatformType();
		if (platform != Platform.INTEL_GALILEO_GEN1
				&& platform != Platform.INTEL_GALILEO_GEN2
				&& platform != Platform.INTEL_EDISON_FAB_C) {
			System.err.println("Unsupported platform, exiting");
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Some exception while looking for the driver.");
			e.printStackTrace();
			return;
		}

		Connection connection = null;

		try {
			// please correct the url path according to your created sql.
			connection = DriverManager.getConnection(
					"jdbc:mysql://192.168.1.2:3306/lab", "root", "password");

		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		}

		// create an analog input object from MRAA using pin A0
		Aio pin = new Aio(0);
		// Gpio pin2 = new Gpio(13);

		// loop forever printing the input value every second
		int counter = 0;

		while (true) {
			if (counter == 10) {
				counter = 0;
				break;
			}
			int value = pin.read();
			if (value > 0 && value < 10) {
				System.out.println(String.format("Pin value: %d", value));
				try {
					dt = new java.sql.Date(0);
					// the mysql insert statement
					String query = " insert into sensor (dateandtime,valuess)"
							+ " values (?, ?)";
					PreparedStatement preparedStmt = connection
							.prepareStatement(query);
					preparedStmt.setDate(1, dt);
					preparedStmt.setInt(2, value);

					// execute the preparedstatement
					preparedStmt.execute();
					counter++;
				} catch (Exception e) {
					System.out.println("Duplicate entries are not allowed.");
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Sleep interrupted: " + e.toString());
			}
		}
	}
}
