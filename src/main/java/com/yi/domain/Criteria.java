package com.yi.domain;

public class Criteria {
	private int page;// 페이지번호
	private int perPageNum; // 페이지당 보일 게시글 갯수

	public Criteria() {
		page = 1;
		perPageNum = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}

	@Override
	public String toString() {
		return String.format("Criteria [page=%s, perPageNum=%s]", page, perPageNum);
	}
	
	
	
}
