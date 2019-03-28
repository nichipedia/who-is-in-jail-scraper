package com.realgood.scraper.api;

import java.util.List;
import com.realgood.protobuf.InmateData;

/**
 * 
 * @author nichipedia
 *
 */
public interface DataLayer {

	/**
	 * 
	 * @param inmate
	 */
	void commitInmate(final InmateData inmate);
	
	/**
	 * 
	 * @return
	 */
	List<InmateData> getInmate();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<InmateData> getInmate(final String name);
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<InmateData> getInmate(final long startDate, final long endDate);
}
