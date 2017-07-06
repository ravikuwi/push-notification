<%@ include file="init.jsp" %>

<portlet:actionURL var="sendPushNoficationURL" name="sendPushNotification">

</portlet:actionURL>

<p>
	<aui:form action="<%=sendPushNoficationURL.toString() %>" name="sendPushNofitication" method="post">
		<aui:select name="userName" id="userNameId">
			<aui:option label="test first" value="testfirst"/>
			<aui:option label="test second" value="testsecond"/>
			<aui:option label="test third" value="testthird"/>
		</aui:select>
		<aui:input type="textarea" name="message" id="messageId" cols="30" rows="5"></aui:input>
		<aui:button name="Send" type="submit" value="Send"></aui:button>

	</aui:form>
</p>