/**
 * A single trader operating on a single stock, given an initial balance. Its default
 * behavior is to buy as many shares as possible and hold onto them for the entire
 * time period covered.
 * 
 * @author Ian Mays
 */
public class StockTrader {
	private String name;
	private int balance;
	private Stock stock;
	private int shares;
	
	/**
	 * Constructs a new StockTrader.
	 * @param traderName The name of the trader, for the purposes of outputting data
	 * to the "out" folder
	 * @param startingBalance The trader's starting account balance
	 * @param stockChoice The stock the trader will be trading
	 */
	public StockTrader(String traderName, int startingBalance, Stock stockChoice) {
		name = traderName;
		balance = startingBalance;
		stock = stockChoice;
		shares = 0;
	}
	
	/**
	 * Gets the trader's name.
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the trader's current balance.
	 * @return The balance
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * Gets the Stock the trader is trading.
	 * @return The stock
	 */
	public Stock getStock() {
		return stock;
	}
	
	/**
	 * Gets the number of shares the trader currently owns.
	 * @return The shares
	 */
	public int getShares() {
		return shares;
	}
	
	/**
	 * Gets the trader's current net worth, as a sum of their balance plus the product
	 * of their share count and the share value.
	 * @return The net worth
	 */
	public int getNetWorth() {
		return balance + shares*stock.getShareValue();
	}
	
	/**
	 * Buys a certain number of shares. If the trader cannot afford to buy them all,
	 * it will instead purchase as many as it can afford.
	 * @param n The maximum number of shares to buy
	 */
	public void buy(int n) {
		if (n == 0) {
			return; 
		}
		
		if (balance >= n*stock.getShareValue()) {
			balance -= n*stock.getShareValue();
			shares += n;
		} else {
			buy(balance/stock.getShareValue());
		}
	}
	
	/**
	 * Sells a certain number of shares. If the trader does not have enough to sell,
	 * it will instead sell all shares it currently owns.
	 * @param n The maximum number of shares to sell
	 */
	public void sell(int n) {
		if (n == 0) {
			return; 
		}
		
		if (shares >= n) {
			balance += n*stock.getShareValue();
			shares -= n;
		} else {
			sell(shares);
		}
	}
	
	/**
	 * Determines whether to buy, sell, or do nothing on each trading day.<p>
	 * By default, the trader will simply spend all of their money on shares and hold
	 * until the end. This behavior is overwritten in StockTrader's subclasses.
	 * @return A positive number representing shares to buy, or a negative number
	 * representing shares to sell, or zero to do nothing
	 */
	public int tradeEvaluator() {
		return balance/stock.getShareValue();
	}
}