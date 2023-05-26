package org.toasthub.hive.stats;

import org.toasthub.hive.utils.Request;
import org.toasthub.hive.utils.Response;

public interface GeneralStatsSvc {

	public void systemStats(Request request, Response response);
	public void prefStats(Request request, Response response);
	public void piStats(Request request, Response response);
}
