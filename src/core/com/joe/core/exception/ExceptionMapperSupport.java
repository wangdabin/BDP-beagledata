package com.joe.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.joe.core.annotation.InitResource;
import com.joe.core.annotation.RestResource;
import com.joe.core.annotation.SecurityResource;
import com.joe.core.i18n.ExceptionI18nMessage;
import com.joe.core.i18n.I18nMessage;
import com.joe.core.vo.ErrorMessage;
import com.sun.jersey.api.NotFoundException;

/**
 * 统一异常处理器
 */
@SecurityResource(name = ExceptionMapperSupport.NAME)
@InitResource(name = ExceptionMapperSupport.NAME)
@RestResource(name = ExceptionMapperSupport.NAME)
@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

	private static final Logger LOG = Logger.getLogger(ExceptionMapperSupport.class);
	public static final String NAME = "exception";
	
	@Context
	private HttpServletRequest request;
	
	@Context
	private HttpHeaders headers;
	
	private I18nMessage i18nMsg;
	
	public ExceptionMapperSupport(){
		i18nMsg = new ExceptionI18nMessage();
	}

	/**
	 * 异常处理
	 * 
	 * @param exception
	 * @return 异常处理后的Response对象
	 */
	public Response toResponse(Exception exception) {
		String message = ExceptionCode.INTERNAL_SERVER_ERROR;
		Status statusCode = Status.INTERNAL_SERVER_ERROR;
		String acceptHeader = request.getHeader("accept");
		MediaType mediaType = null;
		if (MediaType.APPLICATION_XML.equals(acceptHeader)) {
			mediaType = MediaType.APPLICATION_XML_TYPE;
		} else if (MediaType.APPLICATION_JSON.equals(acceptHeader)) {
			mediaType = MediaType.APPLICATION_JSON_TYPE;
		} else {
			mediaType = headers.getMediaType();
			if (mediaType == null) {
				mediaType = MediaType.APPLICATION_XML_TYPE;
			}
		}
		if (exception instanceof BaseException) {
			BaseException baseException = (BaseException) exception;
			String code = baseException.getCode();
			Object[] args = baseException.getValues();
			message = i18nMsg.format(code, args);
		} else if (exception instanceof NotFoundException) {
			message = i18nMsg.getMessage(ExceptionCode.REQUEST_NOT_FOUND);
			statusCode = Status.NOT_FOUND;
		} else if (exception instanceof HttpClientErrorException) {
			HttpClientErrorException e = (HttpClientErrorException) exception;
			message = e.getStatusCode().getReasonPhrase();
			statusCode = Status.fromStatusCode(e.getStatusCode().value());
		}else{
			message = i18nMsg.getMessage(ExceptionCode.INTERNAL_SERVER_ERROR);
			statusCode = Status.INTERNAL_SERVER_ERROR;
		}
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(statusCode.getStatusCode());
		errorMessage.setMessage(message);
		LOG.error(message, exception);
		return Response.ok(errorMessage).type(mediaType).status(statusCode).build();
	}
}
