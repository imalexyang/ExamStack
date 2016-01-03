package com.examstack.common.util;

public class PagingUtil {

	/**
	 * 返回anchor类型的分页
	 * @param currentPageNo
	 * @param maxPageNo
	 * @param parameters
	 * @param url
	 * @return
	 */
	public static String getPagelink(int currentPageNo, int maxPageNo, String parameters, String url) {

		currentPageNo = currentPageNo > maxPageNo ? maxPageNo : currentPageNo;
		int begainNo = currentPageNo - 5 > 0 ? currentPageNo - 5 : 1;
		int endNo = begainNo + 9 > maxPageNo ? maxPageNo : begainNo + 9;
		StringBuffer bf = new StringBuffer();

		if (maxPageNo > 1) {
			bf.append(currentPageNo > 1 ? ("<li><a href = \"" + url + "?page=" + (currentPageNo - 1 > 1 ? currentPageNo - 1 : 1) + parameters + "\">上一页</a></li>") : "<li class=\"disabled\"><a>上一页</a></li>");
			for (int i = begainNo; i <= endNo; i++) {

				if (i == currentPageNo) {

					bf.append("<li class=\"active\"><a href = \"" + url + "?page=" + i + parameters + "\" >" + i + "</a></li>");
				} else
					bf.append("<li><a href = \"" + url + "?page=" + i + parameters + "\" >" + i + "</a></li>");
			}
			bf.append(currentPageNo < maxPageNo ? ("<li><a href = \"" + url + "?page=" + (currentPageNo + 1 > maxPageNo ? maxPageNo : currentPageNo + 1) + parameters + "\">下一页</a></li>") : "<li class=\"disabled\"><a>下一页</a></li>");
			return bf.toString();
		}
		return "";

	}
	
	
	/**
	 * 返回button类型的分页
	 * @param currentPageNo
	 * @param maxPageNo
	 * @return
	 */
	public static String getPageBtnlink(int currentPageNo, int maxPageNo) {

		currentPageNo = currentPageNo > maxPageNo ? maxPageNo : currentPageNo;
		int begainNo = currentPageNo - 5 > 0 ? currentPageNo - 5 : 1;
		int endNo = begainNo + 9 > maxPageNo ? maxPageNo : begainNo + 9;
		StringBuffer bf = new StringBuffer();

		if (maxPageNo > 1) {
			bf.append(currentPageNo > 1 ? ("<li><a data-id = \"" + (currentPageNo - 1 > 1 ? currentPageNo - 1 : 1) + "\" >上一页</a></li>") : "<li class=\"disabled\"><a>上一页</a></li>");
			for (int i = begainNo; i <= endNo; i++) {

				if (i == currentPageNo) {

					bf.append("<li class=\"active\"><a data-id = \"" + i + "\">" + i + "</a></li>");
				} else
					bf.append("<li><a data-id = \"" + i + "\" >" + i
							+ "</a></li>");
			}
			bf.append(currentPageNo < maxPageNo ? ("<li><a data-id = \"" + (currentPageNo + 1 > maxPageNo ? maxPageNo : currentPageNo + 1) + "\" >下一页</a></li>") : "<li class=\"disabled\"><a>下一页</a></li>");
			return bf.toString();
		}
		return "";

	}
	
	public static void main(String[] args){
		System.out.println(PagingUtil.getPageBtnlink(5,100));
	}

}
