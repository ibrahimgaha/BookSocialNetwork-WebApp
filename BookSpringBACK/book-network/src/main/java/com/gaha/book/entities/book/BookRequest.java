package com.gaha.book.entities.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(Integer id,

		@NotEmpty(message = "100") @NotNull(message = "100") String title,

		@NotEmpty(message = "101") @NotNull(message = "101") String authorName,

		@NotEmpty(message = "102") @NotNull(message = "102") String isbn,

		@NotEmpty(message = "103") @NotNull(message = "103") String synopsis,

		Boolean shareable

) {

	public Integer id() {
		return id;
	}

	public String title() {
		return title;
	}

	public String authorName() {
		return authorName;
	}

	public String isbn() {
		return isbn;
	}

	public String synopsis() {
		return synopsis;
	}

	public Boolean shareable() {
		return shareable;
	}

}
