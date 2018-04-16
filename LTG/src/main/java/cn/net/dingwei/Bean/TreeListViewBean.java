package cn.net.dingwei.Bean;

import java.io.Serializable;


import cn.net.treeListView.TreeNodeId;
import cn.net.treeListView.TreeNodeIsLastItem;
import cn.net.treeListView.TreeNodeLabel;
import cn.net.treeListView.TreeNodeNumber;
import cn.net.treeListView.TreeNodePid;
import cn.net.treeListView.TreeNodeProgress;
import cn.net.treeListView.TreeNodeProgress2;
import cn.net.treeListView.TreeNodeProgress3;
import cn.net.treeListView.TreeOption_id;
import cn.net.treeListView.TreeTimes;
import cn.net.treeListView.TreeType;

/**
 * 多级展开实体类
 * @author Administrator
 *
 */
public class TreeListViewBean implements Serializable{
	@TreeNodeId
	private int id;
	@TreeNodePid
	private int pId;
	@TreeNodeLabel
	private String name;
	@TreeNodeProgress
	private int Progress;
	@TreeNodeProgress2
	private int Progress2;
	@TreeNodeProgress3
	private int Progress3;
	@TreeNodeNumber
	private int Number;
	@TreeNodeIsLastItem
	private int isLastForPid; //1 是最后一个 0 不是
	@TreeOption_id
	private String Option_id;
	@TreeTimes
	private String times;
	@TreeType
	private String type;
	public TreeListViewBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * @param id
	 * @param pId 父ID
	 * @param name 名字
	 * @param Progress 进度1
	 * @param Progress2 进度2
	 * @param Progress3 进度3
	 * @param Number   数量 总数
	 * @param isLastForPid	0 不是最后一个  1 是最后一个
     * @param Option_id		id
     */
	public TreeListViewBean(int id, int pId, String name,int Progress,int Progress2,int Progress3,int Number,int isLastForPid,String Option_id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.Progress = Progress;
		this.Number = Number;
		this.isLastForPid  =isLastForPid;
		this.Option_id = Option_id;
		this.Progress2 = Progress2;
		this.Progress3 = Progress3;
	}
	public TreeListViewBean(int id, int pId, String name,int Progress,int Progress2,int Progress3,int Number,int isLastForPid,String Option_id,String times,String type) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.Progress = Progress;
		this.Number = Number;
		this.isLastForPid  =isLastForPid;
		this.Option_id = Option_id;
		this.Progress2 = Progress2;
		this.Progress3 = Progress3;
		this.times = times;
		this.type = type;
	}

	public int getProgress3() {
		return Progress3;
	}

	public void setProgress3(int progress3) {
		Progress3 = progress3;
	}

	public int getIsLastForPid() {
		return isLastForPid;
	}

	public void setIsLastForPid(int isLastForPid) {
		this.isLastForPid = isLastForPid;
	}

	public String getOption_id() {
		return Option_id;
	}

	public void setOption_id(String option_id) {
		Option_id = option_id;
	}

	public int getProgress2() {
		return Progress2;
	}

	public void setProgress2(int progress2) {
		Progress2 = progress2;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProgress() {
		return Progress;
	}
	public void setProgress(int progress) {
		Progress = progress;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}


}
