import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents stock data as an ArrayList of TradingDay objects. Also contains fields
 * for metadata representing the current state of the stock, and methods for
 * retrieving the moving average and RSI of the stock.
 * 
 * @author Ian Mays
 */
public class Stock {
	private String name;
	private ArrayList<TradingDay> days;
	private int day;
	private String date;
	private int shareValue;
	
	/**
	 * Constructs a new Stock based on CSV data from Yahoo Finance's export tool.
	 * @param csv The name of the CSV file, without the file extension
	 * @throws FileNotFoundException if the input file does not exist
	 */
	public Stock(String csv) throws FileNotFoundException {
		name = csv;
		days = new ArrayList<>();
		day = -1;
		shareValue = 0;
		
		Scanner in = new Scanner(new File(csv + ".csv"));
		in.nextLine();
		
		while (in.hasNextLine()) {
			String[] row = in.nextLine().split(",");
			String date = row[0];
			int open = (int) Math.round(Double.parseDouble(row[1])*100);
			int high = (int) Math.round(Double.parseDouble(row[2])*100);
			int low = (int) Math.round(Double.parseDouble(row[3])*100);
			int close = (int) Math.round(Double.parseDouble(row[4])*100);
			int adjClose = (int) Math.round(Double.parseDouble(row[5])*100);
			int volume = Integer.parseInt(row[6]);
			
			days.add(new TradingDay(date, open, high, low, close, adjClose, volume));
		}
		
		in.close();
	}
	
	/**
	 * Resets the stock to avoid having to construct a new Stock object for each
	 * trader.
	 */
	public void reset() {
		day = -1;
		shareValue = 0;
	}
	
	/**
	 * Gets the name of the stock.
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the day the stock data currently goes up to.
	 * @return The day, as an integer
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Gets the number of trading days covered by the stock data.
	 * @return The number of trading days
	 */
	public int getNumDays() {
		return days.size();
	}
	
	/**
	 * Gets the current date for the stock.
	 * @return The date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Gets the current value per share of the stock.
	 * @return The share value.
	 */
	public int getShareValue() {
		return shareValue;
	}
	
	/**
	 * Moves the stock to the next day, and updates the date and share value
	 * accordingly.
	 */
	public void nextDay() {
		day++;
		TradingDay currentDay = days.get(day);
		date = currentDay.getDate();
		shareValue = currentDay.getOpen();
	}
	
	/**
	 * Calculates the moving average within a certain window for the stock price.
	 * @param n The number of trading days to consider
	 * @return The average of the previous n closing prices for the stock
	 * @throws ArithmeticException if n is zero
	 * @throws ArrayIndexOutOfBoundsException if the moving average window falls
	 * outside the included data range
	 */
	public double getMovingAverage(int n) {
		int sumOfPrices = 0;
		
		for (int i = day-n; i < day; i++) {
			sumOfPrices += days.get(i).getClose();
		}
		
		return ((double) sumOfPrices) / n;
	}
	
	/**
	 * Calculates the RSI within a certain window for the stock price.<p>
	 * This works by dividing the closing prices in the window into increases and
	 * decreases, and finding the average magnitude of the price change in each
	 * category. The higher the ratio between the average of the increases and the
	 * average of the decreases, the higher the RSI. Meanwhile, if there are no
	 * decreases or no increases, the RSI is defined to be 100 or 0 respectively, so
	 * as to avoid dividing by zero.
	 * @param n The number of trading days to consider
	 * @return A number between 0 and 100 representing the relative strength of the
	 * stock
	 * @throws ArrayIndexOutOfBoundsException if the RSI window falls outside the
	 * included data range
	 */
	public double getRSI(int n) {
		ArrayList<Integer> ups = new ArrayList<>();
		ArrayList<Integer> downs = new ArrayList<>();
		
		for (int i = day-n; i < day; i++) {
			int diff = days.get(i).getClose() - days.get(i-1).getClose();
			
			if (diff >= 0) {
				ups.add(diff);
			} else {
				downs.add(-diff);
			}
		}
		
		if (ups.size() == 0) {
			return 0;
		}
		
		if (downs.size() == 0) {
			return 100;
		}
		
		double upsSum = 0;
		double downsSum = 0;
		
		for (int i = 0; i < ups.size(); i++) {
			upsSum += ups.get(i);
		}
		
		for (int i = 0; i < downs.size(); i++) {
			downsSum += downs.get(i);
		}
		
		double upsAvg = ((double) upsSum) / ups.size();
		double downsAvg = ((double) downsSum) / downs.size();
		
		return 100 - 100 / (1 + upsAvg/downsAvg);
	}
}