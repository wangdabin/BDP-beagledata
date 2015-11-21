jQuery(document).ready(function($) {
	$.ajax({
		type: 'GET',
		url: '/ws/v1/users/1'

	}).done(function (data, textStatus, jqXHR) {
		$('#helloweenMessage').html(data.childNodes);

	}).fail(function (jqXHR, textStatus, errorThrown) {
		if (jqXHR.status === 401) { // HTTP Status 401: Unauthorized
			var preLoginInfo = JSON.stringify({method: 'GET', url: '/'});
			$.cookie('restsecurity.pre.login.request', preLoginInfo);
			window.location = '/login.html';

		} else {
			alert('Houston, we have a problem...');
		}
	});
});