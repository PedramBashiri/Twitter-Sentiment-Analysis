import twitter4j.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GetTweets {
	/**
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Twitter twitter = new TwitterFactory().getInstance();
		long maxId = Long.parseLong("588110091238432768");

		Writer writer = null;
		FileWriter outputFile = new FileWriter("ResultsLollipop.txt", true);
		Writer writer2 = null;
		FileWriter outputFile2 = new FileWriter("TweetIds.txt", true);

		List<Long> tweetIds = new ArrayList<Long>();

		BufferedReader br = new BufferedReader(new FileReader("TweetIds.txt"));
		try {

			String line = br.readLine();

			while (line != null) {
				tweetIds.add(Long.parseLong(line));
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		try {
			writer = new BufferedWriter(outputFile);
			writer2 = new BufferedWriter(outputFile2);
			try {
				Query query = new Query("android5");
				query.setLang("en");
				//query.setMaxId(maxId);
				List<Status> tweets;
				QueryResult result;
				do {
					result = twitter.search(query);
					tweets = result.getTweets();
					for (Status tweet : tweets) {
						// writer.write("@" + tweet.getUser().getScreenName()
						// + "-" + tweet.getText() + " -- " + tweet.getId() +
						// "---" + ++localId + "\n");

						if (tweetIds.contains(tweet.getId()))
							;
						else {
							writer2.write(tweet.getId() + "\n");
							writer.write(tweet.getText() + "\n");
						}

					}
				} while ((query = result.nextQuery()) != null);

				//System.out.println(tweets.get(tweets.size() - 1).getId());
				System.exit(0);
			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Failed to search tweets: "
						+ te.getMessage());
				System.exit(-1);
			}
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}
}