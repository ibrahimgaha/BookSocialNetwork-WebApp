package com.gaha.book.entities.FeedBack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackResponse {

	private Double note;
	private String comment;
	private boolean ownFeedBack;

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isOwnFeedBack() {
		return ownFeedBack;
	}

	public void setOwnFeedBack(boolean ownFeedBack) {
		this.ownFeedBack = ownFeedBack;
	}

}
