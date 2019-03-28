package com.realgood.scraper.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.realgood.protobuf.InmateData;
import com.realgood.scraper.api.JailScrapper;
import com.realgood.scraper.test.ScraperTest;

/**
 * 
 * @author nichipedia
 *
 */
public class StTammanyScraper implements JailScrapper {

	private static final Logger logger = Logger.getLogger(ScraperTest.class);
	private final String date;
	
	public StTammanyScraper(final Date date) {
		this.date = date.toString();
	}
	
	private URL createInmateProfileURL(final String bookingNumber) throws MalformedURLException {
		final String template = "https://www.stpso.com/cron/inmate_pull.php?page=single&booking_number=%s";
		final String urlString = String.format(template, bookingNumber);
		return new URL(urlString);
	}

	public List<InmateData> getCurrentInmates() throws IOException {
		final URL url = new URL("https://www.stpso.com/cron/inmate_pull.php?page=current&page_num=31");
		final Document page = Jsoup.parse(url, 20000);
		final Iterator<Element> inmates = page.select("div[class=inmate row]").iterator();
		while (inmates.hasNext()) {
			final Element inmate = inmates.next();
			for (Element mer:inmate.select("a")) {
				logger.info(mer.hasText());
			}
		}
		return null;
	}
}
