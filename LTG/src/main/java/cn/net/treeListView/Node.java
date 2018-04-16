package cn.net.treeListView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 * @author zhy
 *
 */
public class Node implements Serializable
{

	private int id;
	private int isLastForPid=-1;//是否是当前节点的最后一个子元素
	private String Option_id="-1";
	/**
	 * 根节点pId为0
	 */
	private int pId = 0;

	private String name;

	/**
	 * 当前的级别
	 */
	private int level;

	/**
	 * 是否展开
	 */
	private boolean isExpand = false;

	private int icon;

	/**
	 * 下一级的子Node
	 */
	private List<Node> children = new ArrayList<Node>();

	/**
	 * 父Node
	 */
	private Node parent;

	private int progress;
	private int progress2;
	private int progress3;
	private int number;
	private String times;
	private String type;
	public Node()
	{
	}

	public Node(int id, int pId, String name,int progress,int progress2,int progress3,int number,int isLastForPid,String Option_id,String times,String type)
	{
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.progress = progress;
		this.number = number;
		this.isLastForPid = isLastForPid;
		this.Option_id = Option_id;
		this.progress2=progress2;
		this.progress3=progress3;
		this.times = times;
		this.type = type;
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

	public int getProgress2() {
		return progress2;
	}

	public void setProgress2(int progress2) {
		this.progress2 = progress2;
	}

	public int getProgress3() {
		return progress3;
	}

	public void setProgress3(int progress3) {
		this.progress3 = progress3;
	}

	public String getOption_id() {
		return Option_id;
	}

	public void setOption_id(String option_id) {
		Option_id = option_id;
	}

	public int getIsLastForPid() {
		return isLastForPid;
	}

	public void setIsLastForPid(int isLastForPid) {
		this.isLastForPid = isLastForPid;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getpId()
	{
		return pId;
	}

	public void setpId(int pId)
	{
		this.pId = pId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public boolean isExpand()
	{
		return isExpand;
	}

	public List<Node> getChildren()
	{
		return children;
	}

	public void setChildren(List<Node> children)
	{
		this.children = children;
	}

	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	/**
	 * 是否为跟节点
	 *
	 * @return
	 */
	public boolean isRoot()
	{
		return parent == null;
	}

	/**
	 * 判断父节点是否展开
	 *
	 * @return
	 */
	public boolean isParentExpand()
	{
		if (parent == null)
			return false;
		return parent.isExpand();
	}

	/**
	 * 是否是叶子界点
	 *
	 * @return
	 */
	public boolean isLeaf()
	{
		return children.size() == 0;
	}

	/**
	 * 获取level
	 */
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	/**
	 * 设置展开
	 *
	 * @param isExpand
	 */
	public void setExpand(boolean isExpand)
	{
		this.isExpand = isExpand;
		if (!isExpand)
		{

			for (Node node : children)
			{
				node.setExpand(isExpand);
			}
		}
	}

}
