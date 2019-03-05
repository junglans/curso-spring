package com.curso.springboot.api.dao;

import java.io.Serializable;

public class QueryRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1639505795530704185L;
	private FilterBy[] filters;
	private SortBy[] sorts;

	public QueryRequest() {
	}

	public FilterBy[] getFilters() {
		return filters;
	}

	public void setFilters(FilterBy[] filters) {
		this.filters = filters;
	}

	public SortBy[] getSorts() {
		return sorts;
	}

	public void setSorts(SortBy[] sorts) {
		this.sorts = sorts;
	}

}
