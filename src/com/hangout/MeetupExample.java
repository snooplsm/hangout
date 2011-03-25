package com.hangout;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;


public class MeetupExample
{
  private static final String PROTECTED_RESOURCE_URL = "https://api.meetup.com/members?relation=self";
  
  public static void main(String[] args)
  {
	if(args.length < 2) throw new IllegalArgumentException("usage: MeetupExample {key} {secret}");
	OAuthProvider provider = new CommonsHttpOAuthProvider(
            "https://api.meetup.com/oauth/request/", "https://api.meetup.com/oauth/access/",
            "http://www.meetup.com/authorize/");

    // fetches a request token from the service provider and builds
    // a url based on AUTHORIZE_WEBSITE_URL and CALLBACK_URL to
    // which your app must now send the user
	
	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(args[0],
            args[1]);
    try {
		String url = provider.retrieveRequestToken(consumer, "obp");
		System.out.println(url);
	} catch (OAuthMessageSignerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (OAuthNotAuthorizedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (OAuthExpectationFailedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (OAuthCommunicationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

}