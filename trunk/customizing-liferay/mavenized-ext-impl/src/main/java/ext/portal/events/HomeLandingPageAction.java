/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * 
 * Taken from http://www.liferay.com/community/forums/-/message_boards/message/2111793
 */
package com.ext.portal.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

public class HomeLandingPageAction extends Action {
	public void run(HttpServletRequest request, HttpServletResponse response) {
		_log.error("Starting HomeLandingPageAction");
		java.lang.Long userId = (java.lang.Long) request
				.getAttribute(WebKeys.USER_ID);
		if (request.getSession() != null) {
			userId = (java.lang.Long) request.getSession().getAttribute(
					WebKeys.USER_ID);
		}
		String path = null;
		if (userId != null) {
			_log.error("user id:  " + userId.toString());
			List<UserGroup> userGroups = null;
			// 10106 appears to be the default company id
			long company = 0;
			try {
				userGroups = UserGroupLocalServiceUtil.getUserUserGroups(userId
						.longValue());
				_log.error("user group count:  " + userGroups.size());
			} catch (Exception e) {
				// error getting user groups
				_log.error("*** USER GROUPS ERROR *** " + e.getMessage());
			}
			try {
				_log.error("user id:  " + userId.longValue());
				User user = UserLocalServiceUtil
						.getUserById(userId.longValue());
				if (user != null) {
					_log.error("user -- " + user.getFullName());
					company = user.getCompanyId();
					_log.error("company-- " + company);
				} else {
					_log.error("user is null");
				}
			} catch (PortalException e) {
				_log.error("*** USER PORTAL EXCEPTION *** " + e.getMessage());
				e.printStackTrace();
			} catch (SystemException e) {
				_log.error("*** USER SYSTEM EXCEPTION *** " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				_log.error("*** USER EXCEPTION *** " + e.getMessage());
				e.printStackTrace();
			}
			if (userGroups.size() > 0) {
				Group group = null;
				List<Group> groups = null;
				try {
					groups = GroupLocalServiceUtil.search(PortalUtil
							.getCompanyId(request), null, null, null,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				} catch (SystemException e) {
					_log.error("*** GROUPS SYSTEM EXCEPTION *** "
							+ e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					_log.error("*** GROUPS EXCEPTION *** " + e.getMessage());
					e.printStackTrace();
				}
				if (groups.size() > 0) {
					_log.error("groups count-- " + groups.size());
					Iterator<UserGroup> itu = userGroups.iterator();
					_log.error("User Group iterator instantiated");
					while (itu.hasNext()) {
						_log.error("Inside User Group Loop");
						UserGroup tempUG = itu.next();
						_log.error("user group-- " + tempUG.getName() + "/"
								+ tempUG.getUserGroupId());
						Iterator<Group> itg = groups.iterator();
						while (itg.hasNext()) {
							_log.error("Inside Group Loop");
							try {
								Group tempG = itg.next();
								_log.error("group-- " + tempG.getGroupId()
										+ "/" + tempG.getName());
								if (UserGroupLocalServiceUtil
										.hasGroupUserGroup(tempG.getGroupId(),
												tempUG.getUserGroupId())) {
									_log.error("Group has user group!");
									int pageCount = tempG.getPrivateLayoutsPageCount() + tempG.getPublicLayoutsPageCount();
									_log.error("page count-- " + pageCount);
									if (pageCount != 0) {
										group = tempG;
										_log.error("We have a group-- "
												+ group.getName() + "/"
												+ group.getGroupId());
										break;
									}
								}
							} catch (SystemException e) {
								_log
										.error("*** FIND GROUP SYSTEM EXCEPTION *** "
												+ e.getMessage());
								e.printStackTrace();
							} catch (Exception e) {
								_log.error("*** FIND GROUP EXCEPTION *** "
										+ e.getMessage());
								e.printStackTrace();
							}
						}
						if (group != null) {
							break;
						} else {
							_log
									.error("*** NO COMMUNITIES FOUND FOR USER GROUP *** ");
						}
					}
				} else {
					_log.error("*** NO COMMUNITIES FOUND IN SYSTEM *** ");
				}
				try {
					// if we have user groups, try to find all the communities
					// that user is a part of
					// based on the user group list
					if (group != null) {
						_log.error("group-- " + group.getGroupId() + "/"
								+ group.getName());
						// if we have communities, pick the first from the list
						// and get the url
						path = ((GroupImpl) group).getFriendlyURL();
						_log.error("path-- " + path);
					} else {
						// no communities = can't do much
						_log.error("*** NO COMMUNITIES ***");
					}
				} catch (Exception e) {
					// error getting communities
					_log.error("*** COMMUNITIES ERROR *** " + e.getMessage());
				}
			} else {
				// no user group = can't do much
				_log.error("*** NO USER GROUPS ***");
			}
		} else {
			// no user = can't do much
			_log.error("*** NULL USER_ID ***");
		}
		if (_log.isInfoEnabled()) {
			_log.info("HomeLandingPageAction="
					+ (path != null ? path : "<null>"));
		}
		if (Validator.isNotNull(path)) {
			// if we don't have a null path, go there
			//LastPath lastPath = new LastPath("/web", path,
			LastPath lastPath = new LastPath("/group", path,
					new HashMap<String, String[]>());
			HttpSession session = request.getSession();
			session.setAttribute(WebKeys.LAST_PATH, lastPath);
		}
	}

	private static Log _log = LogFactory.getLog(HomeLandingPageAction.class);
}
