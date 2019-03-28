package com.realgood.scraper.api;

import java.io.IOException;
import java.util.Collection;
import com.realgood.protobuf.InmateData.Inmate;

/**
 * @author nichipedia
 *
 */
public interface JailScrapper {
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	Collection<Inmate> getCurrentInmates() throws IOException;
}