/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ext.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="CustomDefaultLandingPageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class CustomDefaultLandingPageAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response) {
		_log.info("***********Inside CustomDefaultLandingPageAction****************");
		String path = PropsValues.DEFAULT_LANDING_PAGE_PATH;

		if (_log.isInfoEnabled()) {
			_log.info(
				PropsKeys.DEFAULT_LANDING_PAGE_PATH + StringPool.EQUAL + path);
		}
			
		HttpSession session = request.getSession();

		if (Validator.isNotNull(path)) {
			LastPath lastPath = new LastPath(
				StringPool.BLANK, path, new HashMap<String, String[]>());


			session.setAttribute(WebKeys.LAST_PATH, lastPath);
		}

		// The commented code shows how you can programmaticaly set the user's
		// landing page. You can modify this class to utilize a custom algorithm
		// for forwarding a user to his landing page. See the references to this
		// class in portal.properties.

		Map<String, String[]> params = new HashMap<String, String[]>();

		params.put("p_l_id", new String[] {"1806"});

		LastPath lastPath = new LastPath("/c", "/portal/layout", params);
		_log.info("************lastPath="+ lastPath +"*************");
		session.setAttribute(WebKeys.LAST_PATH, lastPath);
	}

	private static Log _log =
		 LogFactoryUtil.getLog(CustomDefaultLandingPageAction.class);

}
