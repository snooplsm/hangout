package com.hangout.service;

import android.content.ContentValues;
import android.database.Cursor;

import com.hangout.api.Photo;
import com.hangout.dao.Dao;

public class PhotoDao extends Dao {

	private static final String SELECT_PHOTO = "select id, caption, highres_link, thumb_link, photo_link, created, updated, album_id, member_id";
	//		"create table photo(id integer primary key, highres_link varchar(255), thumb_link varchar(255), photo_link varchar(255), caption varchar(255), album_id integer, CONSTRAINT fk_album_id FOREIGN KEY (album_id) REFERENCES photo_album(id))",
	
	private MemberDao memberDao;
	
	private Parser<Photo> photoParser = new Parser<Photo>() {

		@Override
		public ContentValues args(Photo t) {
			ContentValues c = new ContentValues();
			c.put("id", t.getId());
			c.put("caption", t.getCaption());
			c.put("created", t.getCreated());
			c.put("highres_link",t.getHighresLink());
			c.put("member_id", t.getMember().getId());			
			c.put("album_id", t.getPhotoAlbum().getId());
			c.put("photo_link",t.getPhotoLink());
			c.put("thumb_link", t.getThumbLink());
			c.put("updated", t.getUpdated());			
			return c;
		}

		@Override
		public Photo parse(Cursor c) {
			Photo p = new Photo();
			p.setId(c.getLong(0));
			p.setCaption(c.getString(1));
			p.setHighresLink(c.getString(2));
			p.setThumbLink(c.getString(3));
			p.setPhotoLink(c.getString(4));
			p.setCreated(c.getLong(5));
			p.setUpdated(c.getLong(6));
			//p.setMember(memberDao.get(c.getLong(4)));
			//p.setPhotoAlbum(photoAlbum)			
			return p;
		}
		
	};
	
}

