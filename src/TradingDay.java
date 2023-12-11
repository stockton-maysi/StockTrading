/**
 * A single day of trading for a given stock, including data for opening, closing, high
 * and low prices, as well as trading volume, though many of these attributes are not
 * taken into account by the algorithms showcased here.
 * 
 * @author Ian Mays
 */
public class TradingDay {
	private String date;
	private int open;
	private int high;
	private int low;
	private int close;
	private int adjClose;
	private int volume;
	
	/**
	 * Constructs a new TradingDay.
	 * @param d The date, as a String
	 * @param o The opening price
	 * @param h The high price
	 * @param l The low price
	 * @param c The closing price
	 * @param a The adjusted closing price
	 * @param v The trading volume
	 */
	public TradingDay(String d, int o, int h, int l, int c, int a, int v) {
		date = d;
		open = o;
		high = h;
		low = l;
		close = c;
		adjClose = a;
		volume = v;
	}
	
	/**
	 * Gets the date of the trading day.
	 * @return The date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Gets the opening price for the trading day.
	 * @return The opening price
	 */
	public int getOpen() {
		return open;
	}
	
	/**
	 * Gets the highest price for the trading day.
	 * @return The highest price
	 */
	public int getHigh() {
		return high;
	}
	
	/**
	 * Gets the lowest price for the trading day.
	 * @return The lowest price
	 */
	public int getLow() {
		return low;
	}
	
	/**
	 * Gets the closing price for the trading day.
	 * @return The closing price
	 */
	public int getClose() {
		return close;
	}
	
	/**
	 * Gets the adjusted closing price for the trading day.
	 * @return The adjusted closing price
	 */
	public int getAdjClose() {
		return adjClose;
	}
	
	/**
	 * Gets the trading volume for the trading day.
	 * @return The trading volume
	 */
	public int getVolume() {
		return volume;
	}
}