package com.sample.jsp.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class UpdatePasswordJSPPortlet extends JSPPortlet {
    private static final String CMD = "cmd";

    @Override
    public void doDispatch(
            RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
        HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);

        String cmd = originalRequest.getParameter(CMD);
        if(Validator.isNull(cmd)) {
            include(editJSP, renderRequest, renderResponse);
        } else {
            if(SessionErrors.isEmpty(request)) {
                include(viewJSP, renderRequest, renderResponse);
            }else{
                include(editJSP, renderRequest, renderResponse);
            }
        }
    }

    
    @Override
    public void processAction(ActionRequest actionRequest,
        ActionResponse actionResponse) throws IOException, PortletException {
        
        String cmd = ParamUtil.getString(actionRequest, CMD);

        if (Validator.isNull(cmd)) {
                //actionResponse.setRenderParameter("error", "cmd is null");
                return;
        }
        
        HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
        SessionErrors.clear(request);
        try {
                updatePassword(actionRequest, actionResponse);
                return;
        }
        catch (Exception e) {
                if (e instanceof UserPasswordException) {
                        SessionErrors.add(request, e.getClass().getName(), e);
                }
                else if (e instanceof NoSuchUserException ||
                                 e instanceof PrincipalException) {
                        SessionErrors.add(request, e.getClass().getName());
                }
                else {
                        PortalUtil.sendError(e, actionRequest, actionResponse);
                }
        }
    }
    
    private void updatePassword(ActionRequest request, ActionResponse response)
            throws Exception {

        PortletSession session = request.getPortletSession();

        long userId = PortalUtil.getUserId(request);
        String password1 = ParamUtil.getString(request, "password1");
        String password2 = ParamUtil.getString(request, "password2");
        boolean passwordReset = false;

        UserServiceUtil.updatePassword(userId, password1, password2,
                passwordReset);

        session.setAttribute("USER_PASSWORD", password1);
    }

    private static final Log log = LogFactory.getLog(UpdatePasswordJSPPortlet.class);
}
