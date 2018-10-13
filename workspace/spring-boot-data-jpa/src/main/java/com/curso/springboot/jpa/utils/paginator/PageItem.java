package com.curso.springboot.jpa.utils.paginator;

public class PageItem {

	private int numPage;
	private boolean currentPage;
	public PageItem(int numPage, boolean currentPage) {
		super();
		this.numPage = numPage;
		this.currentPage = currentPage;
	}
	public int getNumPage() {
		return numPage;
	}
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	public boolean isCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(boolean currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
