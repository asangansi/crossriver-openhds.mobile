package org.openhds.mirth;

public class Response {

	private int statusCode;
	private String response;

	public Response(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setBody(String string) {
		this.response = string;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResponse() {
		return response;
	}
}
