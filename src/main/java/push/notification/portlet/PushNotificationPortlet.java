package push.notification.portlet;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.*;

import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.push.notifications.service.PushNotificationsDeviceLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gurramve
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=push-notification Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PushNotificationPortlet extends MVCPortlet {

	public void sendPushNotification(ActionRequest actionRequest, ActionResponse actionResponse) {

		String userName = actionRequest.getParameter("userName");
		String message = actionRequest.getParameter("message");

		long companyId=0;
		long[] userIds = new long[1];

		try {
			//send push notification

			companyId = PortalUtil.getCompany(actionRequest).getCompanyId();
			User user = _userLocalService.getUserByScreenName(companyId,userName);

			JSONObject jsonMessage = JSONFactoryUtil.createJSONObject();
			jsonMessage.put("body", message);

			userIds[0]=user.getUserId();

			List<Long> as = new ArrayList<>();


			_pushNotificationDeviceLocalService.sendPushNotification(userIds,jsonMessage);

		}catch (Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		List<User> userList = _userLocalService.getUsers(-1, -1);
		renderRequest.setAttribute("userList",userList);
		super.doView(renderRequest, renderResponse);

	}



	private UserLocalService _userLocalService;

	@Reference(unbind = "-")
	protected void serUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;

	}


	private PushNotificationsDeviceLocalService _pushNotificationDeviceLocalService;

	@Reference(unbind = "=")
	protected void setPushNotificationDeviceService(PushNotificationsDeviceLocalService pushNotificationDeviceLocalService) {
		_pushNotificationDeviceLocalService = pushNotificationDeviceLocalService;
	}


}