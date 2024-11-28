package com.roshan.thrillio.managers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.roshan.thrillio.constants.BookGenre;
import com.roshan.thrillio.constants.KidFriendlyStatus;
import com.roshan.thrillio.constants.MovieGenre;
import com.roshan.thrillio.dao.BookmarkDao;
import com.roshan.thrillio.entities.Book;
import com.roshan.thrillio.entities.Bookmark;
import com.roshan.thrillio.entities.Movie;
import com.roshan.thrillio.entities.User;
import com.roshan.thrillio.entities.UserBookmark;
import com.roshan.thrillio.entities.Weblink;
import com.roshan.thrillio.util.HttpConnect;
import com.roshan.thrillio.util.IOUtil;

public class BookmarkManager {
	private static BookmarkManager instance =new BookmarkManager();
	private static BookmarkDao dao=new BookmarkDao();
	private BookmarkManager() {}
	public static BookmarkManager getInstance() {
		return instance;
	}
	
	public Movie createMovie(long id,String title,String profileUrl,int releaseYear,String[] cast,String[] directors,MovieGenre genre,double imbdRating) {
		Movie movie=new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imbdRating);
		
		return movie;
	}
	public Book createBook(long id,String title,String imageUrl,int publicationYear,String publisher,String[] authors,BookGenre genre,double amazonRating) {
		Book book=new Book();
		book.setId(id);
		book.setTitle(title);
		book.setImageUrl(imageUrl);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);
		 
		return book;
	}
	public Weblink createWeblink(long id,String title,String url,String host) {
		Weblink weblink=new Weblink();
		weblink.setId(id);
		weblink.setTitle(title);
		weblink.setUrl(url);
		weblink.setHost(host);
		
		return weblink;
	}
	public List<List<Bookmark>> getBookmarks(){
		return dao.getBookmarks();
	}
	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark=new UserBookmark();
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);
		
		if (bookmark instanceof Weblink) {
			try {				
				String url = ((Weblink)bookmark).getUrl();
				if (!url.endsWith(".pdf")) {
					String webpage = HttpConnect.download(((Weblink)bookmark).getUrl());
					if (webpage != null) {
						IOUtil.write(webpage, bookmark.getId());
					}
				}				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		dao.saveUserBookmark(userBookmark);
		
	}
	
	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) throws SQLException {
		bookmark.setKidFriendlyStatus(kidFriendlyStatus);
		bookmark.setKidFriendlyMarkedBy(user);
		
		dao.updateKidFriendlyStatus(bookmark);
		
		System.out.println("Kid-friendly status: " + kidFriendlyStatus + ",Marked By: "+user.getEmail()+ ", "+ bookmark);
	}
	public void share(User user, Bookmark bookmark) {
		bookmark.setSharedBy(user);
		
		System.out.println("Data to be shared: ");
		if(bookmark instanceof Book) {
			System.out.println(((Book)bookmark).getItemData());	
		}else if(bookmark instanceof Weblink){
			System.out.println(((Weblink)bookmark).getItemData());
		}
		
		dao.sharedInfo(bookmark);
	}
	public Collection<Bookmark> getBooks(boolean isBookmarked, long id) {
		return dao.getBooks(isBookmarked,id);
		
	}
	public Bookmark getBook(long bid) {
		return dao.getBook(bid);
		
	}
}
