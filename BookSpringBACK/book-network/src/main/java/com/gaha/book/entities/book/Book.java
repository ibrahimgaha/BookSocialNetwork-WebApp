package com.gaha.book.entities.book;

import java.util.List;

import com.gaha.book.common.BaseEntity;
import com.gaha.book.entities.User;
import com.gaha.book.entities.FeedBack.FeedBack;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Book extends BaseEntity {

	private String title;

	private String authorName;

	private String isbn;

	private String synopsis;

	private String bookCover;

	private boolean archived;

	private boolean shareable;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	@OneToMany(mappedBy = "book")
	private List<FeedBack> feedBacks;

	@OneToMany(mappedBy = "book")
	private List<BookTransactionHistory> histories;

	@Transient
	public double getRate() {
		if (feedBacks == null || feedBacks.isEmpty()) {
			return 0.0;
		}
		var rate = this.feedBacks.stream().mapToDouble(FeedBack::getNote).average().orElse(0.0);
		double roundedRate = Math.round(rate * 10.0) / 10.0;

		return roundedRate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getBookCover() {
		return bookCover;
	}

	public void setBookCover(String bookCover) {
		this.bookCover = bookCover;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public boolean isShareable() {
		return shareable;
	}

	public void setShareable(boolean shareable) {
		this.shareable = shareable;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<FeedBack> getFeedBacks() {
		return feedBacks;
	}

	public void setFeedBacks(List<FeedBack> feedBacks) {
		this.feedBacks = feedBacks;
	}

	public List<BookTransactionHistory> getHistories() {
		return histories;
	}

	public void setHistories(List<BookTransactionHistory> histories) {
		this.histories = histories;
	}

}
