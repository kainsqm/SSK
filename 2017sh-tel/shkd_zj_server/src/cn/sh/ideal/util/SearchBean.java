package cn.sh.ideal.util;

public class SearchBean {
	private long startoffset = 0;

	// 每页显示的记录数
	private long rowsPerPage = 10;

	// 当前页面
	private long currentPage = 1;

	// 起始点
	private long start;

	// 终止点
	private long end;

	private long myStart = -1;

	private long myEnd = -1;

	//
	private long totalRecords = -1;

	private long totalPages;

	public long getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(long rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getEnd() {
		if (myEnd >= 0) {
			end = myEnd;
		} else {
			end = currentPage * rowsPerPage;
			if (totalRecords >= 0 && end > totalRecords) {
				end = totalRecords;
			}
		}
		return end;
	}

	public long getStart() {
		if (myStart >= 0) {
			start = myStart;
		} else {
			start = (currentPage - 1) * rowsPerPage + startoffset;
		}
		return start;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public void setMyStart(long myStart) {
		this.myStart = myStart;
	}

	public void setMyEnd(long myEnd) {
		this.myEnd = myEnd;
	}

}
