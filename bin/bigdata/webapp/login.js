jQuery(document).ready(function ($) {
	$('#loginform').submit(function (event) {
		event.preventDefault();
		var data = 'j_username=' + $('#username').val() + '&j_password=' + $('#password').val();
		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/spring-security-rest/j_spring_security_check'

		}).done(function(data, textStatus, jqXHR) {
			var preLoginInfo = JSON.parse($.cookie('dashboard.pre.login.request'));
			alert(preLoginInfo.url);
			window.location = '/';
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert('Booh! Wrong credentials, try again!'+errorThrown);
		});
	});
});