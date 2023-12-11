import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Calculates the 14-day RSI over time for Amazon stock, and outputs it to rsi.csv.
 * Used for Excel graphing purposes.
 * 
 * @author Ian Mays
 */
public class TestStockRSI {
	public static void main(String[] args) throws FileNotFoundException {
		Stock amzn = new Stock("AMZN.csv");
		PrintWriter out = new PrintWriter(new File("rsi.csv"));
		
		int n = 14;
		
		for (int i = 0; i < n*2; i++) {
			amzn.nextDay();
		}
		
		for (int i = n*2; i < amzn.getNumDays(); i++) {
			out.println(amzn.getRSI(n));
			amzn.nextDay();
		}
		
		out.close();
	}
}