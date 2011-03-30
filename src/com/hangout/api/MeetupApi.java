package com.hangout.api;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;

public abstract class MeetupApi {

	private ObjectMapper mapper;

	private static final String SELF = "https://api.meetup.com/members.json/?relation=self";
	
	private static final String GROUPS = "https://api.meetup.com/groups/?member_id=%s";

	private static final String EVENTS = "https://api.meetup.com/2/events?member_id=%s&text_format=plain";
	
	private static final String CHECKINS = "https://api.meetup.com/2/checkins?event_id=%s";
	
	private static final String PHOTOS = "https://api.meetup.com/2/photos?event_id=%s";
	
	private HttpClient client;
	
	private int rateLimitRemaining = 200;
	
	private long rateLimitReset = System.currentTimeMillis()/1000+3600;
	
	public MeetupApi() {
		this(null);
	}

	public MeetupApi(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
		if (this.mapper == null) {
			this.mapper = newMapper();
		}
		final HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		final ClientConnectionManager cm = new ThreadSafeClientConnManager(
				params, schemeRegistry);

		client = new DefaultHttpClient(cm, params);
	}

	public Member getSelf() throws IOException, TokenExpiredException {
		return decode(execute(sign(get(SELF))),Members.class).getMembers().get(0);
	}
	
	public Groups getGroups(long memberId) throws IOException, TokenExpiredException {
		return decode(execute(sign(get(GROUPS,memberId))),Groups.class);
	}
	
	public Checkins getCheckins(String eventId) throws IOException, TokenExpiredException {
		return decode(execute(sign(get(CHECKINS,eventId))), Checkins.class);
	}
	
	public Photos getPhotos(String eventId) throws IOException, TokenExpiredException {
		return decode(execute(sign(get(PHOTOS,eventId))), Photos.class);
	}
	
	public Events getUpcommingEvents(long memberId) throws IOException, TokenExpiredException {
		return decode(execute(sign(get(EVENTS,memberId))),Events.class);
	}
	
	private <T> T first(List<T> data) {
		if(data.isEmpty()) {
			return null;
		}
		return data.get(0);
	}
	
	private HttpResponse execute(HttpRequestBase base) throws ClientProtocolException, IOException {
		return client.execute(base);
	}
	
	protected abstract <T extends HttpRequestBase> T sign(T method);

	private <T> T decode(HttpResponse response, Class<T> clazz) throws TokenExpiredException {
		if(response.getStatusLine().getStatusCode()==401) {
			throw new TokenExpiredException();
		}
		if(response.containsHeader("X-RateLimit-Remaining")) {
			rateLimitRemaining = Integer.parseInt(response.getFirstHeader("X-RateLimit-Remaining").getValue());			
		}
		if(response.containsHeader("X-RateLimit-Reset")) {
			rateLimitReset = Long.parseLong(response.getFirstHeader("X-RateLimit-Reset").getValue());
		}
		try {						
			T t = mapper.readValue(response.getEntity().getContent(),clazz);
			return t;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
	}

	private HttpGet get(String uri, Object...props) {
		HttpGet get = new HttpGet(String.format(uri,props));
		return get;
	}

	private ObjectMapper newMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getDeserializationConfig().disable(Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.getDeserializationConfig().disable(Feature.FAIL_ON_NULL_FOR_PRIMITIVES);
		return mapper;
	}

}
