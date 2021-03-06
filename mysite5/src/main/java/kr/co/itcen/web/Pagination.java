package kr.co.itcen.web;

public class Pagination {

	/** 한 페이지당 게시글 수 **/
	private int pageSize = 5;

	/** 한 블럭(range)당 페이지 수 **/
	private int rangeSize = 5;

	/** 현재 페이지 **/
	private int curPage = 1;

	/** 현재 블럭(range) **/
	private int curRange = 1;

	/** 총 게시글 수 **/
	private int listCnt;

	/** 총 페이지 수 **/
	private int pageCnt;

	/** 총 블럭(range) 수 **/
	private int rangeCnt;

	/** 시작 페이지 **/
	private int startPage = 1;

	/** 끝 페이지 **/
	private int endPage = 1;

	/** 시작 index **/
	private int startIndex = 0;
	
	/** 마지막 index **/
	private int lastIndex = 0;

	/** 이전 페이지 **/
	private int prevPage;

	/** 다음 페이지 **/
	private int nextPage;
	
	/** 다음 블럭 **/
	private int nextRange;
	
	/** 이전 블럭 **/
	private int prevRange;
	
	
	public int getNextRange() {
		return nextRange;
	}

	public void setNextRange(int nextRange) {
		this.nextRange = nextRange;
	}

	public int getPrevRange() {
		return prevRange;
	}

	public void setPrevRange(int prevRange) {
		this.prevRange = prevRange;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRangeSize() {
		return rangeSize;
	}

	public void setRangeSize(int rangeSize) {
		this.rangeSize = rangeSize;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getListCnt() {
		return listCnt;
	}

	public void setListCnt(int listCnt) {
		this.listCnt = listCnt;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getCurRange() {
		return curRange;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public int getRangeCnt() {
		return rangeCnt;
	}

	public int getStartIndex() {
		return startIndex;
	}
	
	public int getLastIndex() {
		return lastIndex;
	}

	public Pagination(int listCnt, int curPage){

		/**
		 * 페이징 처리 순서
		 * 1. 총 페이지수
		 * 2. 총 블럭(range)수
		 * 3. range setting
		 */

		// 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.
		/** 현재페이지 **/
		setCurPage(curPage);
		/** 총 게시물 수 **/
		setListCnt(listCnt);

		/** 1. 총 페이지 수 **/
		setPageCnt(listCnt);
		/** 2. 총 블럭(range)수 **/
		setRangeCnt(pageCnt);
		/** 3. 블럭(range) setting **/
		rangeSetting(curPage);

		/** DB 질의를 위한 startIndex 설정 **/
		setStartIndex(curPage);
		
		setLastIndex(curPage);
		
		setNextRange();
		setPrevRange();
	}

	public void setPageCnt(int listCnt) {
		this.pageCnt = (int) Math.ceil(listCnt*1.0/pageSize);
	}
	public void setRangeCnt(int pageCnt) {
		this.rangeCnt = (int) Math.ceil(pageCnt*1.0/rangeSize);
	}
	public void rangeSetting(int curPage){

		setCurRange(curPage);        
		this.startPage = (curRange - 1) * rangeSize + 1;
		this.endPage = startPage + rangeSize - 1;

		if(endPage > pageCnt){
			this.endPage = pageCnt;
		}

		this.prevPage = curPage - 1;
		this.nextPage = curPage + 1;
	}
	public void setCurRange(int curPage) {
		this.curRange = (int)((curPage-1)/rangeSize) + 1;
	}
	public void setStartIndex(int curPage) {
		this.startIndex = ((curPage-1) * pageSize);
	}
	public void setLastIndex(int curPage) {
		this.lastIndex = this.startIndex+this.pageSize;
	}
	
	public void setNextRange() {
		this.nextRange = this.curRange*this.pageSize+1;
	}
	
	public void setPrevRange() {
		this.prevRange = (this.curRange-1)*this.pageSize;
	}

	@Override
	public String toString() {
		return "Pagination [pageSize=" + pageSize + ", rangeSize=" + rangeSize + ", curPage=" + curPage + ", curRange="
				+ curRange + ", listCnt=" + listCnt + ", pageCnt=" + pageCnt + ", rangeCnt=" + rangeCnt + ", startPage="
				+ startPage + ", endPage=" + endPage + ", startIndex=" + startIndex + ", lastIndex=" + lastIndex
				+ ", prevPage=" + prevPage + ", nextPage=" + nextPage + "]";
	}
	
	
}

