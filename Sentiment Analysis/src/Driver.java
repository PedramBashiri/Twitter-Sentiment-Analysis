import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import twitter4j.TwitterException;

public class Driver {

	public static void main(String[] args) throws IOException {
		int Count0 = 0, Count1 = 0, Count2 = 0, Count3 = 0, Count4 = 0, Count5 = 0;
		// String topic = "Android Lollipop";
		// ArrayList<String> tweets = TweetManager.getTweets(topic);
		ArrayList<String> tweets = new ArrayList<String>();

		Writer writer = null;
		FileWriter outputFile = new FileWriter("piedata.csv");

		BufferedReader br = new BufferedReader(new FileReader(
				"ResultsLollipop.txt"));
		try {
			String line = br.readLine();

			while (line != null) {
				if (line.contains("http://") || line.contains("https://")
						|| line.matches("[0-9]+"))
					;
				else
					tweets.add(line);
				line = br.readLine();
			}
		} finally {
			br.close();
		}

		NLP.init();
		for (String tweet : tweets) {
			switch (NLP.findSentiment(tweet)) {
			case 0:
				Count0++;
				break;
			case 1:
				Count1++;
				break;
			case 2:
				Count2++;
				break;
			case 3:
				Count3++;
				break;
			case 4:
				Count4++;
				break;
			default:
				System.out.println("A TWEET WITHOUT A SCORE");

			}
		}
		try {
			writer = new BufferedWriter(outputFile);
			StringBuilder string = new StringBuilder();
			string.append("score" + "," + "population" + "\n");
			string.append("0" + "," + Count0 + "\n");
			string.append("1" + "," + Count1 + "\n");
			string.append("2" + "," + Count2 + "\n");
			string.append("3" + "," + Count3 + "\n");
			string.append("4" + "," + Count4 + "\n");

			writer.write(string.toString());
		}

		catch (IOException te) {
			te.printStackTrace();
			System.out.println("Something is wrong with writing part "
					+ te.getMessage());
			System.exit(-1);
		}

		finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}

		}
	}
}
