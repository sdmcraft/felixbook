package com.packtpub.felix.bookshelf.service.impl.mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import com.packtpub.felix.bookshelf.inventory.api.BookInventory;
import com.packtpub.felix.bookshelf.inventory.api.BookNotFoundException;
import com.packtpub.felix.bookshelf.inventory.api.InvalidBookException;
import com.packtpub.felix.bookshelf.inventory.api.MutableBook;
import com.packtpub.felix.bookshelf.inventory.api.BookInventory.SearchCriteria;
import com.packtpub.felix.bookshelf.service.api.BookInventoryNotRegisteredRuntimeException;
import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;
import com.packtpub.felix.bookshelf.service.api.SessionNotValidRuntimeException;

public class BookshelfServiceMockImpl implements BookshelfService {

	private String sessionId;
	private BundleContext context;

	public BookshelfServiceMockImpl(BundleContext context) {
		this.context = context;
	}

	public String login(String username, char[] password)
			throws InvalidCredentialsException {
		if ("admin".equals(username)
				&& Arrays.equals(password, "admin".toCharArray())) {
			this.sessionId = Long.toString(System.currentTimeMillis());
			return this.sessionId;
		}
		throw new InvalidCredentialsException(username);
	}

	public void logout(String sessionId) {
		checkSession(sessionId);
		this.sessionId = null;
	}

	public boolean sessionIsValid(String sessionId) {
		return this.sessionId != null && this.sessionId.equals(sessionId);
	}

	protected void checkSession(String sessionId) {
		if (!sessionIsValid(sessionId)) {
			throw new SessionNotValidRuntimeException(sessionId);
		}
	}

	public Set<String> getGroups(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addBook(String session, String isbn, String title,
			String author, String category, int rating)
			throws BookAlreadyExistsException, InvalidBookException {
		checkSession(sessionId);
		BookInventory bookInventory = lookupBookInventory();
		MutableBook mutableBook = bookInventory.createBook(isbn);
		mutableBook.setAuthor(author);
		mutableBook.setTitle(title);
		mutableBook.setCategory(category);
		mutableBook.setRating(rating);
		bookInventory.storeBook(mutableBook);
	}

	public void modifyBookCategory(String session, String isbn, String category)
			throws BookNotFoundException, InvalidBookException {
		checkSession(sessionId);
		BookInventory bookInventory = lookupBookInventory();
		MutableBook mutableBook = bookInventory.loadBookForEdit(isbn);
		mutableBook.setCategory(category);
	}

	public void modifyBookRating(String session, String isbn, int rating)
			throws BookNotFoundException, InvalidBookException {
		checkSession(sessionId);
		BookInventory bookInventory = lookupBookInventory();
		MutableBook mutableBook = bookInventory.loadBookForEdit(isbn);
		mutableBook.setRating(rating);
	}

	public void removeBook(String session, String isbn)
			throws BookNotFoundException {
		checkSession(sessionId);
		BookInventory bookInventory = lookupBookInventory();
		bookInventory.removeBook(isbn);
	}

	public Book getBook(String sessionId, String isbn)
			throws BookNotFoundException {
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		return inventory.loadBook(isbn);
	}

	private BookInventory lookupBookInventory() {
		String name = BookInventory.class.getName();
		ServiceReference ref = this.context.getServiceReference(name);
		if (ref == null) {
			throw new BookInventoryNotRegisteredRuntimeException(name);
		}
		return (BookInventory) this.context.getService(ref);
	}

	public Set<String> searchBooksByCategory(String session, String categoryLike) {
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		Map<SearchCriteria, String> criteria = new HashMap<BookInventory.SearchCriteria, String>();
		criteria.put(SearchCriteria.CATEGORY_LIKE, categoryLike);
		return inventory.searchBooks(criteria);
	}

	public Set<String> searchBooksByAuthor(String session, String authorLike) {
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		Map<SearchCriteria, String> criteria = new HashMap<BookInventory.SearchCriteria, String>();
		criteria.put(SearchCriteria.AUTHOR_LIKE, authorLike);
		return inventory.searchBooks(criteria);
	}

	public Set<String> searchBooksByTitle(String session, String titleLike) {
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		Map<SearchCriteria, String> criteria = new HashMap<BookInventory.SearchCriteria, String>();
		criteria.put(SearchCriteria.TITLE_LIKE, titleLike);
		return inventory.searchBooks(criteria);
	}

	public Set<String> searchBooksByRating(String session, int ratingLower,
			int ratingUpper) {
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		Map<SearchCriteria, String> criteria = new HashMap<BookInventory.SearchCriteria, String>();
		criteria.put(SearchCriteria.RATING_GT, Integer.toString(ratingLower));
		criteria.put(SearchCriteria.RATING_LT, Integer.toString(ratingUpper));
		return inventory.searchBooks(criteria);
	}

}
