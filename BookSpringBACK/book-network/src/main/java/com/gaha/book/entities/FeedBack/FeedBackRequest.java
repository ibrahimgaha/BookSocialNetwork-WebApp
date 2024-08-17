package com.gaha.book.entities.FeedBack;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FeedBackRequest(
		@Positive(message = "200") @Min(value = 0, message = "201") @Max(value = 5, message = "202") Double note,

		@NotNull(message = "203") @NotEmpty(message = "203") @NotBlank(message = "203") String comment,

		@NotNull(message = "204") Integer bookId) {

	// Custom getter methods (optional)
	public Double getNote() {
		return note;
	}

	public String getComment() {
		return comment;
	}

	public Integer getBookId() {
		return bookId;
	}

	// Custom "setter-like" methods (not typical for records)
	public FeedBackRequest withNote(Double newNote) {
		return new FeedBackRequest(newNote, this.comment, this.bookId);
	}

	public FeedBackRequest withComment(String newComment) {
		return new FeedBackRequest(this.note, newComment, this.bookId);
	}

	public FeedBackRequest withBookId(Integer newBookId) {
		return new FeedBackRequest(this.note, this.comment, newBookId);
	}
}
