package com.roshan.thrillio;

import java.sql.SQLException;
import java.util.List;

import com.roshan.thrillio.constants.KidFriendlyStatus;
import com.roshan.thrillio.constants.UserType;
import com.roshan.thrillio.controllers.BookmarkController;
import com.roshan.thrillio.entities.Bookmark;
import com.roshan.thrillio.entities.User;
import com.roshan.thrillio.partner.Shareable;

public class View {
	public static void browse(User user,List<List<Bookmark>> bookmarks) throws SQLException {
		System.out.println("\n"+user.getEmail()+ " is browsing items" );
		int bookmarkCount=0;
		for (List<Bookmark> bookmarklist:bookmarks) {
			for(Bookmark bookmark : bookmarklist) {
				//Bookmarking!!
				//if(bookmarkCount<DataStore.USER_BOOKMARK_LIMIT) {
					boolean isBookmarked=getBookmarkDecision(bookmark);
					if(isBookmarked) {
						bookmarkCount++;
						
						BookmarkController.getInstance().saveUserBookmark(user, bookmark);
						
						System.out.println("New Item Bookmarked --"+bookmark);
					}
				//}
				
				
				if(user.getUserType().equals(UserType.EDITOR) 
						|| user.getUserType().equals(UserType.CHEIF_EDITOR)) {
					//Mark as kid-friendly
					if(bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						KidFriendlyStatus kidFriendlyStatus= getKidFriendlyStatusDecision(bookmark);
						if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus,bookmark);
						}
					}
					//Sharing !!
					if(bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED)
							&& bookmark instanceof Shareable) {
						boolean isShared=getShareDecision();
						if(isShared) {
							BookmarkController.getInstance().share(user,bookmark);						}
					}
				   
				}
				
			}
		}
		
	}
	//TODO: Below methods simulate user input. After IO,we take input via console.
	private static boolean getShareDecision() {
		return Math.random() <0.5 ? true: false;
		
	}
	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
		double randomVal = Math.random();
	     
	    return randomVal < 0.4 ? KidFriendlyStatus.APPROVED :
	        (randomVal >= 0.4 && randomVal < 0.8) ? KidFriendlyStatus.REJECTED :
	            KidFriendlyStatus.UNKNOWN;
	}
	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() <0.5 ? true: false;
 		
	}
	/*public static void bookmark(User user,Bookmark[][] bookmarks) {
		System.out.println("\n"+user.getEmail()+ " is bookmarking" );
		for(int i=0;i< DataStore.USER_BOOKMARK_LIMIT;i++) {
			int typeOffset=(int)(Math.random()*DataStore.BOOKMARK_TYPES_COUNT);
			int bookmarkOffset=(int)(Math.random()*DataStore.BOOKMARK_COUNT_PER_TYPE);
		
			Bookmark bookmark=bookmarks[typeOffset][bookmarkOffset];
			BookmarkController.getInstance().saveUserBookmark(user,bookmark);		
			System.out.println(bookmark);
			}
	}*/

	
}
