package cn.sh.ideal.util;

import java.util.List;



public class SearchResultBean  {
	private static final long serialVersionUID = 1063399809412355849L;
	private long maxRowCount;
	private long maxPage;
	private List data;
	private SearchBean searchBean;

	public long getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(long maxRowCount) {
		this.maxRowCount = maxRowCount;
		searchBean.setTotalRecords(maxRowCount);
		countMaxPage();
	}

	private void countMaxPage() {
		if (searchBean != null) {
			if (maxRowCount % searchBean.getRowsPerPage() == 0) {
				maxPage = maxRowCount / searchBean.getRowsPerPage();
			} else {
				maxPage = maxRowCount / searchBean.getRowsPerPage() + 1;
			}
			searchBean.setTotalPages(maxPage);
		}
	}

	public long getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(long maxPage) {
		this.maxPage = maxPage;
	}



	public List getData() {
		return data;
	}

	
	public void setData(List data) {
		this.data = data;
	}

	public SearchBean getSearcherBean() {
		return searchBean;
	}

	public void setSearchBean(SearchBean searcherBean) {
		this.searchBean = searcherBean;
	}

}
