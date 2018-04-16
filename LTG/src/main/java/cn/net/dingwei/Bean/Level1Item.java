package cn.net.dingwei.Bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.net.dingwei.adpater.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Level1Item extends AbstractExpandableItem<ExamGroupBean.ContentBean> implements MultiItemEntity {
    public ExamGroupBean.ContentBean contentBean;

    public Level1Item(ExamGroupBean.ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}