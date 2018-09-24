package com.curso.springboot.jpa.utils.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRenderer<T> {

	
	private String url;
	private Page<T> page;
	
	private int totalPages;
	private int pageSize;
	private int currentPage;
	
	private List<PageItem> pages;
	public PageRenderer(String url, Page<T> page) {
		
		super();
		this.url = url;
		this.page = page;
		this.pageSize = page.getSize();
		this.totalPages = page.getTotalPages();
		this.currentPage = page.getNumber() + 1;
		this.pages = new ArrayList<PageItem>();
		
		int from = 0;
		int to = 0;
		
		if (totalPages <= pageSize) {
			from = 1;
			to = totalPages;
		} else {
			if (currentPage <= pageSize / 2) {
				from = 1;
				to = pageSize;
			} else if (currentPage >= totalPages - pageSize / 2) {
				from = totalPages - pageSize;
				to = from + pageSize;
			} else {
				from = currentPage - pageSize/2;
				to = from + pageSize/2;
			}
		}
		
		
		for (int ind = 0; ind < to; ind++) {
			pages.add(new PageItem(from + ind, currentPage == from + ind));
		}
	}
	
	public String getUrl() {
		return url;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public List<PageItem> getPages() {
		return pages;
	}
	 
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean hasNext() {
		return page.hasNext();
	}
	
	public boolean hasPrevious() {
		return page.hasPrevious();
	}
}
