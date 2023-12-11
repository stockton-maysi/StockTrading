import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tests a default StockTrader (a.k.a. "holder"), an RSIStockTrader, and a 5-day and
 * 20-day MAStockTrader against each other on Apple, Amazon, Google, and Microsoft
 * stock and outputs the results in CSV format to the "out" folder.
 * 
 * @author Ian Mays
 */
public class TestStockTrader {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] stockNames = {"AAPL", "AMZN", "GOOGL", "MSFT"};
		
		for (String stockName : stockNames) {
			Stock stock = new Stock(stockName);
			
			StockTrader[] stockTraders = {
				new StockTrader("Holder", 1000000, stock),
				new RSIStockTrader("RSI Trader", 1000000, stock),
				new MAStockTrader("5-day MA Trader", 1000000, stock, 5),
				new MAStockTrader("20-day MA Trader", 1000000, stock, 20),
			};
			
			for (StockTrader stockTrader : stockTraders) {
				File f = new File("out/" + stockName + " " + stockTrader.getName() + ".csv");
				f.createNewFile();
				PrintWriter out = new PrintWriter(f);
				
				stock.reset();
				
				out.println("Date,Balance,Shares,Share Price,Value of Shares");
				
				for (int i = 0; i < stock.getNumDays(); i++) {
					stock.nextDay();
					int tradeEvaluation = stockTrader.tradeEvaluator();
					
					if (tradeEvaluation > 0) {
						stockTrader.buy(tradeEvaluation);
					} else if (tradeEvaluation < 0) {
						stockTrader.sell(-tradeEvaluation);
					}
					
					out.println(stock.getDate() + "," + ((double) stockTrader.getBalance())/100 + "," + stockTrader.getShares() + "," + ((double) stock.getShareValue())/100 + "," + ((double) stockTrader.getShares()*stock.getShareValue())/100);
				}
				
				out.close();
			}
		}
	}
}