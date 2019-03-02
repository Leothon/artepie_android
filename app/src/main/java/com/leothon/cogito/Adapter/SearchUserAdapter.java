package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

public class SearchUserAdapter extends BaseAdapter {

    public SearchUserAdapter(Context context, ArrayList<User> users){

        super(context, R.layout.followfans_item,users);

    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {

        User user = (User)bean;

        holder.setText(R.id.faf_name,user.getUser_name());
        int role = CommonUtils.isVIP(user.getUser_role());
        if (role != 2){
            if (role == 0){
                holder.setAuthorVisible(R.id.faf_auth,0,1);
            }else if (role == 1){
                holder.setAuthorVisible(R.id.faf_auth,1,1);
            }else {
                holder.setAuthorVisible(R.id.faf_auth,2,0);
                holder.setText(R.id.faf_content,user.getUser_signal());
            }
            holder.setText(R.id.faf_content,"认证：" + user.getUser_role().substring(1));
        }else {
            holder.setAuthorVisible(R.id.faf_auth,2,0);
            holder.setText(R.id.faf_content,user.getUser_signal());
        }
        holder.setImageUrls(R.id.faf_icon,user.getUser_icon());
    }
}
