package com.yunwenlong.model;

import com.yunwenlong.utils.StringUtil;

/**
 * ��ҳModel��
 * 
 * @author
 *
 */
public class PageBean {

	private int page = 1; // 第几页
	private int pageSize = 10; // 每页记录数
	private int start;
	private int maxPage;
	private long total;

	public PageBean(String page, String pageSize,long total) {
		super();
		this.page = Integer.parseInt((StringUtil.isEmpty(page) ? "1" : page));
		this.pageSize = Integer.parseInt((StringUtil.isEmpty(pageSize) ? "10": pageSize));
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (page - 1) * pageSize;
	}

	public int getMaxPage() {
		return (int) (Math.floor(total / pageSize) + 1);
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

}
