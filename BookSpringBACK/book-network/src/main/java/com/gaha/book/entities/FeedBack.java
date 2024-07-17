package com.gaha.book.entities;

import com.gaha.book.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class FeedBack extends BaseEntity {

	private double note; // 1 to 5 stars

	private String comment;
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

}
