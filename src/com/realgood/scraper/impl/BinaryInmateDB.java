package com.realgood.scraper.impl;

import java.util.List;
import com.realgood.protobuf.InmateData;
import com.realgood.protobuf.InmateData.Inmate;
import com.realgood.scraper.api.DataLayer;

/**
 * @author nichipedia
 *
 */
public class BinaryInmateDB implements DataLayer {

	@Override
	public void commitInmate(InmateData inmate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Inmate> getInmates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inmate> getInmate(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inmate> getInmate(long startDate, long endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}