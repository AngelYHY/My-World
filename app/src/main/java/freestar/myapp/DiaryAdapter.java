package freestar.myapp;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by freestar on 2016/12/14.
 * 日记适配器
 */

public class DiaryAdapter extends BaseQuickAdapter<Diary, BaseViewHolder> {

    public DiaryAdapter(int layoutResId, List<Diary> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Diary item) {
        holder.setText(R.id.tv_title, String.format("%s\n%s", item.getTitle(), item.getTime()))
                .setText(R.id.tv_content, item.getContent());
    }
}
