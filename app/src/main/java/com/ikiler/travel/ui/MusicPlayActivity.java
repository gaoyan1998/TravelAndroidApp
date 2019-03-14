package com.ikiler.travel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ikiler.travel.Adapter.MusicAdapter;
import com.ikiler.travel.Model.Song;
import com.ikiler.travel.R;
import com.ikiler.travel.ui.CustomView.GradientTextView;
import com.ikiler.travel.ui.CustomView.SystemBarTintManager;
import com.ikiler.travel.util.MusicUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlayActivity extends AppCompatActivity {

    //    private SharedPreferences sharedPreferences;
    private ListView listview;
    private List<Song> list;
    private MusicAdapter adapter;
    private MediaPlayer mplayer = new MediaPlayer();
    private GradientTextView text_main;
    private SeekBar seekBar;
    private TextView textView1, textView2;
    private ImageView imageView_play, imageView_next, imageView_front,
            imageview, imageview_playstyle;
    private int screen_width;
    private Random random = new Random();
    // 用于判断当前的播放顺序，0->单曲循环,1->顺序播放,2->随机播放
    private int play_style = 0;
    // 判断seekbar是否正在滑动
    private boolean ischanging = false;
    private Thread thread;
    // 当前音乐播放位置,从0开始
    private int currentposition;
    // 屏幕显示的最大listview条数
    private int max_item;
    // 修改顶部状态栏颜色使用
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Theme_blue);
        setContentView(R.layout.activity_music_play);
//        // 顶部状态栏颜色设置
        mTintManager = new SystemBarTintManager(MusicPlayActivity.this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.blue);
        // 获得屏幕宽度并保存在screen_width中
        init_screen_width();
        // 顶部视图控件的绑定
        initTopView();
        // 顶部和 底部操作栏按钮点击事件
        setClick();
        // listview的绑定,数据加载,以及相关事件的监听
        setListView();
        // 设置mediaplayer监听器
        setMediaPlayerListener();

    }

    @Override
    protected void onDestroy() {

        if (mplayer.isPlaying()) {
            mplayer.stop();
        }
        mplayer.release();
        super.onDestroy();
    }

    // 给屏幕宽度赋值
    private void init_screen_width() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screen_width = size.x;
    }

    private void initTopView() {
        text_main = (GradientTextView) this.findViewById(R.id.text_main);
        imageview_playstyle = (ImageView) this.findViewById(R.id.play_style);
    }

    private void setClick() {
        imageview_playstyle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                play_style++;
                if (play_style > 2) {
                    play_style = 0;
                }
                switch (play_style) {
                    case 0:
                        imageview_playstyle.setImageResource(R.mipmap.cicle);
                        Toast.makeText(MusicPlayActivity.this, "单曲循环",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        imageview_playstyle.setImageResource(R.mipmap.ordered);
                        Toast.makeText(MusicPlayActivity.this, "顺序播放",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        imageview_playstyle.setImageResource(R.mipmap.unordered);
                        Toast.makeText(MusicPlayActivity.this, "随机播放",
                                Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });


        View layout_playbar = (View) findViewById(R.id.layout_playbar);
        imageview = (ImageView) layout_playbar.findViewById(R.id.imageview);
        imageView_play = (ImageView) layout_playbar
                .findViewById(R.id.imageview_play);
        imageView_next = (ImageView) layout_playbar
                .findViewById(R.id.imageview_next);
        imageView_front = (ImageView) layout_playbar
                .findViewById(R.id.imageview_front);
        textView1 = (TextView) layout_playbar.findViewById(R.id.name);
        textView2 = (TextView) layout_playbar.findViewById(R.id.singer);
        seekBar = (SeekBar) layout_playbar.findViewById(R.id.seekbar);
        imageView_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change_play_image(R.mipmap.play, R.mipmap.pause);
                if (mplayer.isPlaying()) {
                    mplayer.pause();
                    imageview.clearAnimation();
                } else {
                    mplayer.start();
                    // thread = new Thread(new SeekBarThread());
                    // thread.start();

                }
            }
        });

        imageView_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (play_style == 2) {
                    random_nextMusic();
                    auto_change_listview();
                } else {
                    nextMusic();
                    auto_change_listview();
                }
            }
        });

        imageView_front.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (play_style == 2) {
                    random_nextMusic();
                    auto_change_listview();
                } else {
                    frontMusic();
                    auto_change_listview();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                ischanging = false;
                mplayer.seekTo(seekBar.getProgress());
                thread = new Thread(new SeekBarThread());
                thread.start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                ischanging = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                // 可以用来写拖动的时候实时显示时间
            }
        });

    }
    private void setListView() {
        listview = (ListView) this.findViewById(R.id.listveiw);

        list = new ArrayList<Song>();
        list = MusicUtils.getMusicData(MusicPlayActivity.this);
        adapter = new MusicAdapter(MusicPlayActivity.this, list);
        // 标记正在播放的音乐条目为主题色
        adapter.setFlag(currentposition);
        adapter.notifyDataSetChanged();

        listview.setAdapter(adapter);

        // 判断当前歌曲是否在屏幕可见范围内，若是则显示定位图标，否则隐藏
        check_location();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                currentposition = position;
                musicplay(currentposition);
                text_main.setText(cut_song_name(list.get(currentposition).getSong()));
                adapter.setFlag(currentposition);
                adapter.notifyDataSetChanged();
            }
        });

//        myDialog_bestlove = new MyDialog(MainActivity.this,
//                R.style.dialogTheme, R.layout.setting_best_lovesong);
//        final Window window2 = myDialog_bestlove.getWindow();
//        window2.setGravity(Gravity.CENTER);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                if (currentposition >= firstVisibleItem
                        && currentposition <= firstVisibleItem
                        + visibleItemCount) {
                } else {
                }
                max_item = visibleItemCount;
            }
        });
    }

    private void musicplay(int position) {

        textView1.setText(cut_song_name(list.get(position).getSong()).trim());
        textView2.setText(list.get(position).getSinger().trim());
        text_main.setText(cut_song_name(list.get(currentposition).getSong()));
        seekBar.setMax(list.get(position).getDuration());
        imageView_play.setImageResource(R.mipmap.pause);

        try {
            mplayer.reset();
            mplayer.setDataSource(list.get(position).getPath());
            mplayer.prepare();
            mplayer.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
        thread = new Thread(new SeekBarThread());
        thread.start();
    }

    private void setMediaPlayerListener() {
        // 监听mediaplayer播放完毕时调用
        mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                switch (play_style) {
                    case 0:
                        musicplay(currentposition);
                        break;
                    case 1:
                        // 这里会引发初次进入时直接点击播放按钮时，播放的是下一首音乐的问题
                        nextMusic();
                        break;
                    case 2:
                        random_nextMusic();
                        break;
                    default:

                        break;
                }
            }
        });
        // 设置发生错误时调用
        mplayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                mp.reset();
                // Toast.makeText(MainActivity.this, "未发现音乐", 1500).show();
                return false;
            }
        });
    }

    // 自定义的线程,用于下方seekbar的刷新
    class SeekBarThread implements Runnable {

        @Override
        public void run() {
            while (!ischanging && mplayer.isPlaying()) {
                // 将SeekBar位置设置到当前播放位置
                seekBar.setProgress(mplayer.getCurrentPosition());

                try {
                    // 每500毫秒更新一次位置
                    Thread.sleep(500);
                    // 播放进度

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 下一曲
    private void nextMusic() {
        currentposition++;
        if (currentposition > list.size() - 1) {
            currentposition = 0;
        }
        musicplay(currentposition);
        adapter.setFlag(currentposition);
        adapter.notifyDataSetChanged();
    }

    // 上一曲
    private void frontMusic() {
        currentposition--;
        if (currentposition < 0) {
            currentposition = list.size() - 1;
        }
        musicplay(currentposition);
        adapter.setFlag(currentposition);
        adapter.notifyDataSetChanged();
    }

    // 随机播放下一曲
    private void random_nextMusic() {
        currentposition = currentposition + random.nextInt(list.size() - 1);
        currentposition %= list.size();
        musicplay(currentposition);
        adapter.setFlag(currentposition);
        adapter.notifyDataSetChanged();
    }

    // 切掉音乐名字最后的.mp3
    private String cut_song_name(String name) {
        if (name.length() >= 5
                && name.substring(name.length() - 4, name.length()).equals(
                ".mp3")) {
            return name.substring(0, name.length() - 4);
        }
        return name;
    }

    // 定位图标的显示与隐藏
    private void check_location() {
        if (currentposition >= listview.getFirstVisiblePosition()
                && currentposition <= listview.getLastVisiblePosition()) {
        } else {
        }
    }

    // 点击下一曲上一曲时自动滚动列表
    private void auto_change_listview() {
        if (currentposition <= listview.getFirstVisiblePosition()) {
            listview.setSelection(currentposition);
        }
        if (currentposition >= listview.getLastVisiblePosition()) {
            // listview.smoothScrollToPosition(currentposition);
            listview.setSelection(currentposition - max_item + 2);
        }
    }

    // 该方法用于判断点击播放使用那张图片替换
    private void change_play_image(int resID_play, int resID_pause) {
        if (imageView_play
                .getDrawable()
                .getCurrent()
                .getConstantState()
                .equals(getResources().getDrawable(resID_play)
                        .getConstantState())) {
            imageView_play.setImageResource(resID_pause);
        } else {
            imageView_play.setImageResource(resID_play);
        }
    }
}
