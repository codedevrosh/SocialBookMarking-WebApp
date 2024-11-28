package com.roshan.thrillio.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import com.roshan.thrillio.constants.KidFriendlyStatus;
import com.roshan.thrillio.entities.Bookmark;
import com.roshan.thrillio.entities.User;
import com.roshan.thrillio.managers.BookmarkManager;
import com.roshan.thrillio.managers.UserManager;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns={"/bookmark","/bookmark/save","/bookmark/mybooks"})
public class BookmarkController extends HttpServlet{
	/*private BookmarkController() {}
	private static BookmarkController instance= new BookmarkController();
	public static BookmarkController getInstance() {
		return instance;
	}*/
	public BookmarkController() {
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=null;
		System.out.println("Servlet Path:"+request.getServletPath());
		if(request.getSession().getAttribute("userId")!=null) {
			long userId =(long)request.getSession().getAttribute("userId");
			if(request.getServletPath().contains("save")) {
				dispatcher =request.getRequestDispatcher("/mybooks.jsp");
				String bid=request.getParameter("bid");
				User user=UserManager.getInstance().getUser(userId);
				Bookmark bookmark=BookmarkManager.getInstance().getBook(Long.parseLong(bid));
				BookmarkManager.getInstance().saveUserBookmark(user,bookmark);
				Collection<Bookmark> list=BookmarkManager.getInstance().getBooks(true ,userId);
				request.setAttribute("books", list);
			}else if(request.getServletPath().contains("mybooks")) {
				dispatcher =request.getRequestDispatcher("/mybooks.jsp");
				Collection<Bookmark> list=BookmarkManager.getInstance().getBooks(true ,userId);
				request.setAttribute("books", list);
			}else{
				dispatcher =request.getRequestDispatcher("/browse.jsp");
				Collection<Bookmark> list=BookmarkManager.getInstance().getBooks(false ,userId);
				request.setAttribute("books", list);
			}
		}
			else {
				dispatcher=request.getRequestDispatcher("/login.jsp");
			}
		dispatcher.forward(request, response);
		}
		
	public void saveUserBookmark(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().saveUserBookmark(user,bookmark);
		
	}
	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus,Bookmark bookmark) throws SQLException {
		BookmarkManager.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus,bookmark);
	}
	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user,bookmark);
		
	}
}
