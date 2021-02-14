package com.example.c3a_game;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private List<Player> listPlayer;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext, List<Player> listData){
        this.context = aContext;
        this.listPlayer = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listPlayer.size();
    }

    @Override
    public Object getItem(int position) {
        return listPlayer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_player_layout, parent, false);
            holder = new ViewHolder();
            holder.avaView = (ImageView) convertView.findViewById(R.id.ava_imageView);
            holder.playerNameView = (EditText) convertView.findViewById(R.id.nameTextView);
            holder.scoreView = (TextView) convertView.findViewById(R.id.scoreTextView);
            holder.hostBtn = (ImageButton) convertView.findViewById(R.id.hostBtn);
            holder.scoreRadioGroup = (RadioGroup) convertView.findViewById(R.id.scoreRadio);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Player player = this.listPlayer.get(position);
        holder.playerNameView.setText(player.getName());
        holder.scoreView.setText(String.valueOf(player.getScore()));

        holder.hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i < listPlayer.size(); i++){
                    if (i==position) continue;

                    Player other_player = listPlayer.get(i);
                    if (other_player.getStatus() == 1){
                        other_player.setStatus(0);
                        other_player.holder.scoreRadioGroup.setVisibility(View.VISIBLE);
                    }
                }
                if (holder.scoreRadioGroup.getVisibility() == View.VISIBLE){
                    holder.scoreRadioGroup.setVisibility(View.INVISIBLE);
                    holder.scoreRadioGroup.clearCheck();
                    player.setStatus(1);
                } else{
                    holder.scoreRadioGroup.setVisibility(View.VISIBLE);
                    player.setStatus(0);
                }
            }
        });

        holder.playerNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                player.setName(s.toString());
            }
        });

        player.holder = holder;

        player.holder.scoreRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.add:
//                        player.updateScore(1);
                        player.setStatus(2);
//                        holder.scoreView.setText(String.valueOf(player.getScore()));
                        break;
                    case R.id.minus:
//                        player.updateScore(-1);
                        player.setStatus(3);
//                        holder.scoreView.setText(String.valueOf(player.getScore()));
                        break;
                    case R.id.zero:
                        player.setStatus(4);
                        break;
                }
            }
        });

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView avaView;
        EditText playerNameView;
        TextView scoreView;
        ImageButton hostBtn;
        RadioGroup scoreRadioGroup;
    }
}
