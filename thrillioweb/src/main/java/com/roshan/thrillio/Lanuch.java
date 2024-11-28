package com.roshan.thrillio;

import java.sql.SQLException;
import java.util.List;

import com.roshan.thrillio.entities.Bookmark;
import com.roshan.thrillio.entities.User;
import com.roshan.thrillio.managers.BookmarkManager;
import com.roshan.thrillio.managers.UserManager;

public class Lanuch {
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;
	
	private static void loadData() {
		System.out.println("1. Loading Data....");
		DataStore.loadData();
		
		users=UserManager.getInstance().getUsers();
		bookmarks=BookmarkManager.getInstance().getBookmarks();
		
		//System.out.println("Printing data...");
		//printuserdata();
		//printbookmarkData();	
	}
	private static void printbookmarkData() {
		for(List<Bookmark> bookmarklist: bookmarks) {
			for(Bookmark bookmark :bookmarklist) {
				System.out.println(bookmark);
			}
		}
		
	}

	private static void printuserdata() {
		for(User user:users) {
			System.out.println(user);
		}
		
	}
	private static void start() throws SQLException {
		//System.out.println("\n2. Bookmarking ...");
		for(User user:users) {
			View.browse(user, bookmarks);
		}
		
	}
	public static void main(String[] args) throws SQLException {
		loadData();
		start();
	}
	
}
