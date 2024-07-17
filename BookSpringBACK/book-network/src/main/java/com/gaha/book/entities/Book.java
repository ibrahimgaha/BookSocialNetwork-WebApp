package com.gaha.book.entities;

import java.util.List;

import com.gaha.book.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "book")
	private List<BookTransactionHistory> histories;

}
