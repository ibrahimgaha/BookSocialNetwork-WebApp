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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
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

	@Transient
	public double getRate() {
		if (feedBacks == null || feedBacks.isEmpty()) {
			return 0.0;
		}
		var rate = this.feedBacks.stream().mapToDouble(FeedBack::getNote).average().orElse(0.0);
		double roundedRate = Math.round(rate * 10.0) / 10.0;

		return roundedRate;
	}

	@OneToMany(mappedBy = "book")
	private List<BookTransactionHistory> histories;

}
