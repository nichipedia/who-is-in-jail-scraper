package com.realgood.scraper.api;

import java.util.List;
import com.realgood.protobuf.InmateData;
import com.realgood.protobuf.InmateData.Inmate;

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
	List<Inmate> getInmates();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Inmate> getInmate(final String name);
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Inmate> getInmate(final long startDate, final long endDate);
}
