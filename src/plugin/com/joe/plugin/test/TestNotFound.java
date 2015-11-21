package com.joe.plugin.test;

public class TestNotFound extends TestException {

  private static final long serialVersionUID=23993993939L;
  private String url;
  private String contentType;

  public TestNotFound(String message){
    super(message);    
  }
  
  public TestNotFound(String url, String contentType) {
    this(url, contentType,
         "parser not found for contentType="+contentType+" url="+url);
  }

  public TestNotFound(String url, String contentType, String message) {
    super(message);
    this.url = url;
    this.contentType = contentType;
  }

  public String getUrl() { return url; }
  public String getContentType() { return contentType; }
}