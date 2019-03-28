package com.realgood.scraper.impl;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.realgood.protobuf.InmateData.Inmate;
import com.realgood.protobuf.InmateData.Inmate.Builder;
import com.realgood.protobuf.InmateData.Inmate.Gender;
import com.realgood.scraper.api.JailScrapper;

/**
 * 
 * @author nichipedia
 *
 */
public class StTammanyScraper implements JailScrapper {

	private static final Logger logger = Logger.getLogger(StTammanyScraper.class);
	private final Date time;
	
	public StTammanyScraper(final Date date) {
		this.time = date;
	}
	
	public StTammanyScraper() {
		this.time = new Date();
	}
	
	public Collection<Inmate> getCurrentInmates() throws IOException {
		Set<Inmate> temp = new HashSet<Inmate>();
		final String jail = "St. Tammany Parish Jail";
		final DateFormat formatter = new SimpleDateFormat("MM/dd/YY");	
		final String today = formatter.format(this.time);
		for (int i = 31; i > 27; i--) {
			final URL url = new URL(getPagiURL(i));
			final Document page = Jsoup.parse(url, 20000);
			final Set<Inmate> inmates = page.select("div[class=inmate row]")
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
				final Builder builder = Inmate.newBuilder();
				try {
					final URL profileURL = new URL(getProfileURL(inmateNumber));
					final Document profile = Jsoup.parse(profileURL, 20000);
					final List<Element> rows = profile.select("td");
					final String age = rows.get(1).text();
					if (age.length() != 0) {
						builder.setAge(age);
					} else {
						builder.setAge("NOT FOUND");
					}
					String sex = rows.get(2).text();
					if (sex.equals("M")) {
						builder.setGender(Gender.MALE);
					} else if (sex.equals("F")) {
						builder.setGender(Gender.FEMALE);
					} else {
						builder.setGender(Gender.UNKNOWN);
					}
					final String race = rows.get(3).text();
					if (race.length() != 0) {
						builder.setRace(race);
					} else {
						builder.setRace("NOT FOUND");
					}
					final String releasable = rows.get(5).text();
					if (releasable.length() != 0) {
						builder.setReleaseable(releasable);
					} else {
						builder.setReleaseable("NOT FOUND");
					}
					} catch (IOException e) {
						logger.error(e);
					}
					final String mugShotURL = getMugShotURL(inmateNumber);
					final Inmate data = builder
							.setName(fullName)
							.setBookingDescription(description)
							.setJail(jail)
							.setDate(today)
							.setBail(bail)
							.setMugShot(mugShotURL)
							.build();
					return data;
				})
			.collect(Collectors.toSet());
			temp.addAll(inmates);
		}
		return temp;
	}
	
	private static String getMugShotURL(final String inmateId) {
		return String.format("https://www.stpso.com/inmate_roster_gallery/%s.jpg", inmateId);
	}
	
	private static String getPagiURL(final int pageNumber) {
		return String.format("https://www.stpso.com//cron/inmate_pull.php?page=current&page_num=%d", pageNumber);
	}
	
	private static String getProfileURL(final String inmateId) {
		return String.format("https://www.stpso.com//cron/inmate_pull.php?page=single&booking_number=%s", inmateId);
	}
	
}