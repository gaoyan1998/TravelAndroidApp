package com.ikiler.travel.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ikiler.travel.Model.Song;
import com.ikiler.travel.R;
import com.ikiler.travel.util.MusicUtils;

import java.util.List;

public class MusicAdapter extends BaseAdapter {
	private Context context;
	private List<Song> list;
	private int position_flag = 0;
	// 定义四种颜色，蓝色，紫色，绿色，红色
	private int myBlue = Color.argb(0xff, 0x00, 0xBF, 0xFF);
	private int myPurple = Color.argb(0xff, 0xFF, 0x00, 0xFF);
	private int myGreeen = Color.argb(0xff, 0x00, 0xFF, 0x00);
	private int myRed = Color.argb(0xff, 0xFF, 0x00, 0x00);

	private String Theme;

	public MusicAdapter(Context mainActivity, List<Song> list) {
		this.context = mainActivity;
		this.list = list;
	}

	public void setFlag(int flag) {
		this.position_flag = flag;
	}

	public void setTheme(String Theme) {
		this.Theme = Theme;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			// 引入布局
			view = View.inflate(context, R.layout.list_item, null);
			// 实例化对象
			holder.song = (TextView) view.findViewById(R.id.item_mymusic_song);
			holder.singer = (TextView) view
					.findViewById(R.id.item_mymusic_singer);
			holder.duration = (TextView) view
					.findViewById(R.id.item_mymusic_duration);
			holder.position = (TextView) view
					.findViewById(R.id.item_mymusic_postion);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// 给控件赋值
		String string_song = list.get(i).getSong();
		if (string_song.length() >= 5
				&& string_song.substring(string_song.length() - 4,
						string_song.length()).equals(".mp3")) {
			holder.song.setText(string_song.substring(0,
					string_song.length() - 4).trim());
		} else {
			holder.song.setText(string_song.trim());
		}
		if (i == position_flag) {
			holder.song.setTextColor(myBlue);
			holder.singer.setTextColor(myBlue);
			holder.duration.setTextColor(myBlue);
			holder.position.setText("");
			holder.position.setBackgroundResource(R.mipmap.play_small);
		} else {
			holder.song.setTextColor(Color.BLACK);
			holder.singer.setTextColor(Color.BLACK);
			holder.duration.setTextColor(Color.BLACK);
			holder.position.setText(i + 1 + "");
			holder.position.setBackground(null);
		}

		holder.singer.setText(list.get(i).getSinger().toString().trim());
		// 时间需要转换一下
		int duration = list.get(i).getDuration();
		String time = MusicUtils.formatTime(duration);
		holder.duration.setText(time);

		return view;
	}

	class ViewHolder {
		TextView song;// 歌曲名
		TextView singer;// 歌手
		TextView duration;// 时长
		TextView position;// 序号
	}

}
