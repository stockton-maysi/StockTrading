/**
 * A StockTrader who trades based on the relative strength index of the stock.
 * 
 * @author Ian Mays
 */
public class RSIStockTrader extends StockTrader {
	/**
	 * Constructs a new RSIStockTrader.
	 * @param traderName The name of the trader, for the purposes of outputting data
	 * to the "out" folder
	 * @param startingBalance The trader's starting account balance
	 * @param stockChoice The stock the trader will be trading
	 */
	public RSIStockTrader(String traderName, int startingBalance, Stock stockChoice) {
		super(traderName, startingBalance, stockChoice);
	}
	
	/**
	 * Buys and sells stock so that the percentage of the trader's net worth composed
	 * of stock corresponds to the current RSI.
	 */
	public int tradeEvaluator() {
		Stock stock = getStock();
		
		if (stock.getDay() < 15) {
			return 0;
		}
		
		int netWorth = getNetWorth();
		int shares = getShares();
		int shareValue = stock.getShareValue();
		double rsi = stock.getRSI(14)/100;
		
		int desiredShares = (int) Math.round(rsi * netWorth / shareValue);
		
		return desiredShares - shares;
	}
}