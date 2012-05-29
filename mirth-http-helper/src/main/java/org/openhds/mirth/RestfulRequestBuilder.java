package org.openhds.mirth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Builder class for constructing HTTP requests
 */
public class RestfulRequestBuilder {

	private URL url;
	private String basicUser;
	private String basicPass;
	private Map<String, String> params = new HashMap<String, String>();

	public RestfulRequestBuilder(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public RestfulRequestBuilder setBasicUser(String user) {
		this.basicUser = user;
		return this;
	}

	public RestfulRequestBuilder setBasicPass(String pass) {
		this.basicPass = pass;
		return this;
	}

	public RestfulRequestBuilder withParam(String paramName, String paramValue) {
		params.put(paramName, paramValue);
		return this;
	}

	public Response buildGet() throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			
			if (basicUser != null && basicPass != null) {
				UsernamePasswordCredentials cred = new UsernamePasswordCredentials(basicUser, basicPass);
				AuthScope auth = new AuthScope(url.getHost(), url.getPort());
				client.getCredentialsProvider().setCredentials(auth, cred);
			}
			
			HttpGet getRequest = new HttpGet(url.toString());
			
			HttpResponse httpResponse = client.execute(getRequest);
			Response response = new Response(httpResponse.getStatusLine().getStatusCode());
			HttpEntity entity = httpResponse.getEntity();
			response.setBody(EntityUtils.toString(entity));
			EntityUtils.consume(entity);
			
			return response;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}
