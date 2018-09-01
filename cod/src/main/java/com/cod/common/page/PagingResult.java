package com.cod.common.page;

import com.cod.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 * @param <T>
 */
public class PagingResult<T extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = -2454369717915031521L;
	
	/**
	 * 当前页码
	 */
	private int page;
	
	/**
	 * 每页记录数
	 */
	private int pageSize;
	
	/**
	 * 总记录数
	 */
	private int total;

	private int maxpage;//一共有多少页

	private String htmlst;//

	private String url;//url
	
	/**
	 * 每页数据记录
	 */
	private List<T> rows;

	public PagingResult() {
	}

	public PagingResult(int total, List<T> rows, int page, int pageSize,String url) {
		this.total = total;
		this.rows = rows;
		this.page = page;
		this.pageSize = pageSize;
		this.maxpage=this.countmaxpage();//一共有多少页
		this.htmlst=this.formathtml(url);

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

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}


	public int countmaxpage(){
		if(total%pageSize==0){
		   return maxpage=total/pageSize;
		}
		else{
			return maxpage=total/pageSize+1;
		}
	}



	//动态生成分页代码
    public String formathtml(String url){
		StringBuffer starthtml=new StringBuffer();
		starthtml.append("<div> <ul class=\"pagination\">");
		//如果为第一页则禁用左边按钮
		if(this.page==1|| this.total==0){
			starthtml.append("<li class=\"disabled\"><a href=\"#\"><i class=\"icon-double-angle-left\"></i></a></li>");
		}
		else{
			starthtml.append("<li><a href='");
			starthtml.append(url+"?page="+String.valueOf(page-1)+"&size="+String.valueOf(pageSize));
			starthtml.append("'><i class=\"icon-double-angle-left\"></i></a></li>");
		}

		//最大页数小于等于3页
		if(this.maxpage>1){

            if(maxpage<=2){
				for(int i=1;i<=maxpage;i++){
					if(i==this.page){
						starthtml.append("<li class=\"active\"><a href='");
						starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
						starthtml.append("'>");
						starthtml.append(i);
						starthtml.append("</a></li>");
					}
					else{
						starthtml.append("<li><a href='");
						starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
						starthtml.append("'>");
						starthtml.append(i);
						starthtml.append("</a></li>");
					}
				}
			}

			if(maxpage>=3){

            	if(page<3&&maxpage>=5){
					for(int i=1;i<=5;i++){
						if(i==this.page){
							starthtml.append("<li class=\"active\"><a href='");
							starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
							starthtml.append("'>");
							starthtml.append(i);
							starthtml.append("</a></li>");
						}
						else{
							starthtml.append("<li><a href='");
							starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
							starthtml.append("'>");
							starthtml.append(i);
							starthtml.append("</a></li>");
						}
					}
				}

				if(page+2<=maxpage&&page>=3){
					for(int i=page-2;i<=page+2;i++){
						if(i==this.page){
							starthtml.append("<li class=\"active\"><a href='");
							starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
							starthtml.append("'>");
							starthtml.append(i);
							starthtml.append("</a></li>");
						}
						else{
							starthtml.append("<li><a href='");
							starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
							starthtml.append("'>");
							starthtml.append(i);
							starthtml.append("</a></li>");
						}
					}
				}

				if(page+2>maxpage&&maxpage>=5){
					for(int i=maxpage-4;i<=maxpage;i++){
						if(i==this.page){
							starthtml.append("<li class=\"active\"><a href='");
							starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
							starthtml.append("'>");
							starthtml.append(i);
							starthtml.append("</a></li>");
						}
						else{
							starthtml.append("<li><a href='");
							starthtml.append(url+"?page="+String.valueOf(i)+"&size="+String.valueOf(pageSize));
							starthtml.append("'>");
							starthtml.append(i);
							starthtml.append("</a></li>");
						}
					}
				}
			}

		}

		//如果为最后一页则禁用右边按钮
		if(this.page==maxpage|| this.total==0){
			starthtml.append("<li class=\"disabled\"><a href=\"#\"><i class=\"icon-double-angle-right\"></i></a></li>");

		}
		else{
			starthtml.append("<li><a href='");
			starthtml.append(url+"?page="+String.valueOf(page+1)+"&size="+String.valueOf(pageSize));
			starthtml.append("'><i class=\"icon-double-angle-right\"></i></a></li>");

		}
        starthtml.append("</ul></div>");
		return starthtml.toString();
	}

	public String getHtmlst() {
		return htmlst;
	}

	public void setHtmlst(String htmlst) {
		this.htmlst = htmlst;
	}

	public int getMaxpage() {
		return maxpage;
	}

	public void setMaxpage(int maxpage) {
		this.maxpage = maxpage;
	}
}
