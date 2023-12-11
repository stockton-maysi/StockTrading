/**
 * A StockTrader who trades based on moving averages.
 * 
 * @author Ian Mays
 */
public class MAStockTrader extends StockTrader {
	private int days;
	
	/**
	 * Constructs a new MAStockTrader.
	 * @param traderName The name of the trader, for the purposes of outputting data
	 * to the "out" folder
	 * @param startingBalance The trader's starting account balance
	 * @param stockChoice The stock the trader will be trading
	 * @param n The number of trading days to consider
	 */
	public MAStockTrader(String traderName, int startingBalance, Stock stockChoice, int n) {
		super(traderName, startingBalance, stockChoice);
		days = n;
	}
	
	/**
	 * If the current stock price is below the moving average, buys 10% of its current
	 * balance worth of shares. If the current price is above the moving average, sells
	 * 10% of its currently owned stock. All values are rounded to the nearest integer
	 * number of shares.
	 */
	public int tradeEvaluator() {
		Stock stock = getStock();
		int currentDay = stock.getDay();
		
		if (currentDay < days) {
			return 0;
		}
		
		int balance = getBalance();
		int shares = getShares();
		double movingAverage = stock.getMovingAverage(days);
		int currentSharePrice = stock.getShareValue();
		
		if (currentSharePrice < movingAverage) {
			return (int) Math.round(0.1 * ((double) balance)/currentSharePrice);
		} else if (currentSharePrice > movingAverage) {
			return (int) Math.round(-0.1 * shares);
		}
		
		return 0;
	}
}