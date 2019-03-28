package com.realgood.scraper.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.realgood.protobuf.InmateData;
import com.realgood.protobuf.InmateData.Inmate;
import com.realgood.protobuf.InmateData.Inmate.Gender;

/**
 * @author nichipedia
 *
 */
public class ScraperTest {
	
	private static final Logger logger = Logger.getLogger(ScraperTest.class);
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		try {
			final URL url = new URL("https://www.stpso.com/cron/inmate_pull.php?page=current&page_num=31");
			final String jail = "St. Tammany Parish Jail";
			final DateFormat formatter = new SimpleDateFormat("MM/dd/YY");	
			final Date time = new Date();
			final String today = formatter.format(time);
			final Document page = Jsoup.parse(url, 20000);
			final List<String> inmates = page.select("div[class=inmate row]")
			.stream()
			.filter(inmate -> {
				final String bookingDate = inmate.select("td").get(1).text();
				return bookingDate.equals(today);
			})
			.map(inmate -> {
				final String fullName = inmate.select("h3").stream().findFirst().get().text();
				final String partialImg = inmate.select("img").stream().findFirst().get().attr("src");
				final String bookingDate = inmate.select("td").get(1).text();
				final String bail = inmate.select("b").first().text();
				final String description = inmate.select("td").get(3).text();
				
				final String inmateNumber = inmate.select("td").get(0).text();
				String age = "NOT FOUND";
				Gender gender = Gender.UNKNOWN;
				String releasable = "NOT FOUND";
				String mug_shot = "";
				String race = "NOT FOUND";
			
				
				try {
					final URL profileURL = new URL(getURL(inmateNumber));
					final Document profile = Jsoup.parse(profileURL, 20000);
					List<Element> rows = profile.select("td");
					age = rows.get(1).text();
					String sex = rows.get(2).text();
					if (sex.equals("M")) {
						gender = Gender.MALE;
					} else if (sex.equals("F")) {
						gender = Gender.FEMALE;
					}
					race = rows.get(3).text();
					releasable = rows.get(5).text();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e);
				}
				
				
				
				Inmate data = Inmate.newBuilder().setName(fullName)
						.setBookingDescription(description)
						.setJail(jail)
						.setDate(today)
						.build();
				return inmateNumber;
			})
			.collect(Collectors.toList());
			
			inmates.stream().forEach(System.out::println);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public static String getMugShotURL(final String inmateId) {
		return String.format("view-source:https://www.stpso.com/inmate_roster_gallery/%s.jpg", inmateId);
	}
	
	public static String getURL(final String inmateId) {
		return String.format("https://www.stpso.com//cron/inmate_pull.php?page=single&booking_number=%s", inmateId);
	}
	

}