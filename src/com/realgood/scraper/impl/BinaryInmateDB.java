package com.realgood.scraper.impl;

import java.util.List;
import com.realgood.protobuf.InmateData;
import com.realgood.scraper.api.DataLayer;

/**
 * @author nichipedia
 *
 */
public class BinaryInmateDB implements DataLayer {

	/* (non-Javadoc)
	 * @see com.realgood.scraper.api.DataLayer#commitInmate(com.realgood.protobuf.InmateData)
	 */
	@Override
	public void commitInmate(InmateData inmate) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.realgood.scraper.api.DataLayer#getInmate()
	 */
	@Override
	public List<InmateData> getInmate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.realgood.scraper.api.DataLayer#getInmate(java.lang.String)
	 */
	@Override
	public List<InmateData> getInmate(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.realgood.scraper.api.DataLayer#getInmate(long, long)
	 */
	@Override
	public List<InmateData> getInmate(long startDate, long endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
