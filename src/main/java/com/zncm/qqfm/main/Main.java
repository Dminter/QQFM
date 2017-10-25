package com.zncm.qqfm.main;

import com.alibaba.fastjson.JSON;
import com.zncm.qqfm.data.*;
import com.zncm.qqfm.data.model.ProgramModel;
import com.zncm.qqfm.data.model.SongModel;
import com.zncm.qqfm.data.renderer.ProgramRenderer;
import com.zncm.qqfm.data.renderer.SongRenderer;
import com.zncm.qqfm.utils.DbHelper;
import com.zncm.qqfm.utils.Xutils;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main implements Runnable {
    MyAudioPlayer myAudioPlayer;
    TimeThread timeThread;
    ProgramModel programModel;
    JList<ProgramModel> progromList;
    List<Program> programs = new ArrayList<>();
    private int pageIndex = 1;
    private int playIndex = -1;
    private int pagesize = -1;
    String url = "";
    SongModel songModel;
    JList<SongModel> songList;
    List<Song> songs = new ArrayList<>();
    private OkHttpClient client;
    JPanel jPanelRight;
    JLabel jLabelTitle;

    public Set<String> setReq;
    public ArrayList<Song> playList;
    private ArrayList<MusicPlatform> platforms = new ArrayList<>();

    JFrame jFrame;

    Container container;
    JPopupMenu popupMenu;

    public Main() {
        jFrame = new JFrame();
        setReq = new HashSet<>();
        playList = new ArrayList<>();
        init();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        JPanel jPanelLeft = new JPanel();
//        jPanelLeft.setPreferredSize(new Dimension(640, 300));
//        jPanelLeft.setBackground(Color.GREEN);
        jPanelRight = new JPanel();
//        jPanelRight.setBackground(Color.RED);
        jPanel.add(jPanelLeft);
        jPanel.add(jPanelRight);


//        programs.add(new Program("你真的知道吗", "http://api.kaolafm.com/api/v4/audios/list?sorttype=1&pagesize=30&pagenum=1&id=1100000000207"));
//        programs.add(new Program("快讯不联播", "http://api.kaolafm.com/api/v4/audios/list?sorttype=1&pagesize=30&pagenum=1&id=1100000479052"));

        programModel = new ProgramModel(programs);
        initProgram();

        progromList = new JList(programModel);
        progromList.setCellRenderer(new ProgramRenderer());
        progromList.setVisibleRowCount(30);
        progromList.setFixedCellWidth(200);
        progromList.setFixedCellHeight(20);
        progromList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
//                if (e.getValueIsAdjusting()) {
                // 鼠标点击在点的时刻
                Program program = (Program) programs.get(progromList.getSelectedIndex());
                if (program != null && Xutils.isNotEmptyOrNull(program.getUrl())) {
                    getListByUrl(program.getUrl());
                }
//                }
            }
        });


        popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("删除");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteItem) {
                    Xutils.debug("删除");
                    Program program = programs.get(progromList.getSelectedIndex());
                    DbHelper.delete(program);
                    initProgram();
                }
            }
        });
        popupMenu.add(deleteItem);
//        popupMenu.addPopupMenuListener(new PopupMenuListener() {
//            @Override
//            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//                Xutils.debug("popupMenuWillBecomeVisible");
//            }
//
//            @Override
//            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//                Xutils.debug("popupMenuWillBecomeInvisible");
//            }
//
//            @Override
//            public void popupMenuCanceled(PopupMenuEvent e) {
//                Xutils.debug("popupMenuCanceled");
//            }
//        });
        progromList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                progromList.setSelectedIndex(progromList.locationToIndex(e.getPoint()));

                showPop(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPop(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        jPanelLeft.add(new JScrollPane(progromList));


        songModel = new SongModel(songs);
        songList = new JList(songModel);
        songList.setCellRenderer(new SongRenderer());
        songList.setVisibleRowCount(30);
        songList.setFixedCellWidth(800);
        songList.setFixedCellHeight(20);
        songList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    // 鼠标点击在点的时刻
                    if (Xutils.listNotNull(songs)) {
                        playIndex = songList.getSelectedIndex();
                        Song song = (Song) songs.get(playIndex);
                        if (song != null) {
                            playSong(song, true);
                            return;
                        }
                    }
                }
            }
        });
        jPanelRight.add(new JScrollPane(songList));


        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();


//               http://api.kaolafm.com/api/v4/audios/list?sorttype=1&pagesize=30&pagenum=1&id=1100000000207
//        for (int i = 0; i <5 ; i++) {
//            JButton jButton = new JButton();
//            jButton.setText("muisc:"+i);
//            jButton.setBackground(Color.YELLOW);
//            jPanelLeft.add(jButton);
//        }


        JPanel jPanelBottomLeft = new JPanel();
        JPanel jPanelBottomRight = new JPanel();

        JButton jButtonPre = new JButton("<");
        JButton jButtonNext = new JButton(">");
        JButton jButtonStop = new JButton("||");

        jButtonPre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playNext(false);
            }
        });

        jButtonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playNext(true);
            }
        });

        jButtonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myAudioPlayer.getPlayer().close();
            }
        });


        jPanelBottomLeft.add(jButtonPre);
        jPanelBottomLeft.add(jButtonStop);
        jPanelBottomLeft.add(jButtonNext);


        jLabelTitle = new JLabel();

        jPanelRight.add(jLabelTitle);

        jPanel.add(jPanelBottomLeft);
        jPanel.add(jPanelBottomRight);


        container.add(jPanel);


    }

    private void showPop(MouseEvent e) {
        if (e.isPopupTrigger() && progromList.getSelectedIndex() != -1) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void initProgram() {
        programs = (List<Program>) DbHelper.query(Program.class);
        programModel.setPrograms(programs);
        if (progromList != null) {
            progromList.updateUI();
        }
    }

    private void init() {

        timeThread = new TimeThread();
        timeThread.start();

        container = jFrame.getContentPane();


        JMenu jMenuProgram = new JMenu();
        jMenuProgram.setText("节目");

        JMenuItem addItem = new JMenuItem("添加节目");
        JMenuItem pasteItem = new JMenuItem("粘贴节目");
        pasteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = Xutils.getSysClipboardText();
                if (Xutils.isEmptyOrNull(url)) {
                    return;
                }
                urlHandle(url);
            }
        });
        jMenuProgram.add(addItem);
        jMenuProgram.add(pasteItem);


        JMenu jMenuPlatform = new JMenu();
        jMenuPlatform.setText("平台");

//        JRadioButtonMenuItem kaolaItem  = new JRadioButtonMenuItem("考拉");
//        JRadioButtonMenuItem qingtingItem  = new JRadioButtonMenuItem("蜻蜓");
//        JRadioButtonMenuItem ximalayaItem  = new JRadioButtonMenuItem("喜马拉雅");


        platforms.add(new MusicPlatform(MusicPlatformEnum.LOCAL.value()));
        platforms.add(new MusicPlatform(MusicPlatformEnum.KAOLAFM.value()));
        platforms.add(new MusicPlatform(MusicPlatformEnum.XIMALAYA.value()));
        platforms.add(new MusicPlatform(MusicPlatformEnum.QINGTING.value()));

        ButtonGroup buttonGroup = new ButtonGroup();

        for (MusicPlatform platform : platforms
                ) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(MusicPlatformEnum.valueOf(platform.getType()).strName());
            buttonGroup.add(item);
            jMenuPlatform.add(item);
        }


        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenuProgram);
        jMenuBar.add(jMenuPlatform);

        jFrame.setJMenuBar(jMenuBar);


        jFrame.setBackground(Color.WHITE);
        jFrame.setTitle("奇奇FM");
        jFrame.setLocation(730, 470);
        jFrame.setSize(new Dimension(1100, 800));
        jFrame.setResizable(true);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        pack();


//        playMusic("D:/Dev/QQFM/music/什么叫做“花郎台”.mp3");

    }

    private void playMusic(String filePath) {
        try {

            myAudioPlayer = new MyAudioPlayer(new File(filePath));
//            myAudioPlayer.player.close();
            myAudioPlayer.getPlayer().play();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        Xutils.debug("111111111111");
    }


    class TimeThread extends Thread {
        @Override
        public void run() {
//            super.run();
            try {
                while (true) {
                    Thread.sleep(1000);
                    if (myAudioPlayer.getPlayer() != null && myAudioPlayer.getPlayer().isComplete()) {
                        /**
                         *播放下一个
                         */
                        playNext(true);
                    }
//                    Xutils.debug(Xutils.getDateHMS(new Date().getTime()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playNext(boolean isNext) {
        if (Xutils.listNotNull(songs)){
            if (playIndex < songs.size() - 1) {
                if (isNext) {
                    ++playIndex;
                } else {
                    --playIndex;
                }

            } else {
                playIndex = 0;
            }
            playSong(songs.get(playIndex), true);
        }
    }


    public static void main(String[] args) {
        Main main = new Main();

    }


    public void realPlay(Song song) {
        if (song == null) {
            return;
        }


        if (Xutils.isNotEmptyOrNull(song.getName())) {
            jLabelTitle.setText(song.getName());
        }

        String filePath = song.getFilePath();
        if (Xutils.isNotEmptyOrNull(filePath)) {
            playMusic(filePath);
        }

    }

    public void playSong(final Song song, final boolean isAutoPlay) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        if (song == null) {
            return;
        }
        if (Xutils.isNotEmptyOrNull(song.getFilePath())) {
            realPlay(song);
            return;
        }
        final String downloadUrl = song.getDownurl();
        if (!Xutils.isNotEmptyOrNull(downloadUrl)) {
            return;
        }

        if (!setReq.add(downloadUrl)) {
            return;
        }
        Request request = new Request.Builder().url(downloadUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (isAutoPlay) {

                }
                setReq.remove(downloadUrl);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String fileEnd = "aac";
                if (song.getDownurl().contains(".")) {
                    fileEnd = song.getDownurl().substring(song.getDownurl().lastIndexOf("."), song.getDownurl().length());
                }
                InputStream is = response.body().byteStream();
                String fileRoot = "D:/Dev/QQFM/music/";
                File fold = new File(fileRoot);
                if (!fold.exists()) {
                    fold.mkdir();
                }
                String filePath = fileRoot + song.getName() + fileEnd;
                File file = new File(filePath);
                FileOutputStream fos = new FileOutputStream(file);
                byte buff[] = new byte[1024 * 8];
                int len;
                long current = 0;
                long total = response.body().contentLength();
                while ((len = is.read(buff)) != -1) {
                    fos.write(buff, 0, len);
                    fos.flush();
                    current += len;
                    int progress = (int) (100 * current / total);
                    if (isAutoPlay) {

                    }
                }
                is.close();
                fos.close();
                song.setFilePath(filePath);
                if (Xutils.isNotEmptyOrNull(filePath)) {
                    song.setCateid(System.currentTimeMillis() + "");
                    song.setArtist("");
                }
                if (isAutoPlay) {
                    realPlay(song);
                }

            }
        });
    }

//    @Override
//    public void valueChanged(ListSelectionEvent e) {
////        JOptionPane.showMessageDialog(this, "我喜欢的小说：" + progromList.getSelectedValue(), null, JOptionPane.INFORMATION_MESSAGE);
////
////
////        Program program = (Program) programs.get(progromList.getSelectedIndex());
//
//
//        getListByUrl();
//
//    }

    private void getListByUrl(String url) {

//        url = program.getUrl();
        try {


            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Xutils.debug("str" + str);
                    try {
                        List<Song> pageSongs = new ArrayList<>();
                        if (Xutils.isNotEmptyOrNull(str)) {
                            if (pageIndex == 1) {
                                songs = new ArrayList<>();
                            }
                            if (url.startsWith(Constants.QQSRX)) {
                                ArrayList<Srx> srxes = (ArrayList<Srx>) JSON.parseArray(str, Srx.class);
                                if (Xutils.listNotNull(srxes)) {
                                    for (Srx tmp : srxes
                                            ) {
                                        pageSongs.add(new Song(tmp));
                                    }
                                }
                            } else if (url.startsWith(Constants.QINGTING)) {
                                String ret = JSON.parseObject(str).getString("data");
                                ArrayList<Qingting> qingtings = (ArrayList<Qingting>) JSON.parseArray(ret, Qingting.class);
                                if (Xutils.listNotNull(qingtings)) {
                                    for (Qingting tmp : qingtings
                                            ) {
                                        try {
                                            String mediainfo = tmp.getMediainfo();
                                            String bitrates_url = JSON.parseObject(mediainfo).getString("bitrates_url");
                                            String file_path = JSON.parseObject(JSON.parseArray(bitrates_url).get(0).toString()).getString("file_path");
                                            /**
                                             *http://jsod.qingting.fm/m4a/59775ac97cb8913c4a3fff26_7693914_64.m4a?deviceid=00000000-635e-c23e-2956-571b65a2a43e
                                             */
                                            String url = "http://jsod.qingting.fm/" + file_path + "?deviceid=00000000-635e-c23e-2956-571b65a2a43e";
                                            tmp.setUrl(url);
                                            pageSongs.add(new Song(tmp));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            } else if (url.startsWith(Constants.XIMALAYA)) {
                                String ret = JSON.parseObject(str).getString("data");
                                ret = JSON.parseObject(ret).getString("list");
                                ArrayList<Ximalaya> ximalayas = (ArrayList<Ximalaya>) JSON.parseArray(ret, Ximalaya.class);
                                if (Xutils.listNotNull(ximalayas)) {
                                    for (Ximalaya tmp : ximalayas
                                            ) {
                                        pageSongs.add(new Song(tmp));
                                    }
                                }
                            } else if (url.startsWith(Constants.WEBURL_FMBAIDU)) {
                                String ret = JSON.parseObject(str).getString("list");
                                ArrayList<Fmbaidu> fmbaidus = (ArrayList<Fmbaidu>) JSON.parseArray(ret, Fmbaidu.class);
                                if (Xutils.listNotNull(fmbaidus)) {
                                    for (Fmbaidu tmp : fmbaidus
                                            ) {
                                        pageSongs.add(new Song(tmp));
                                    }
                                }
                            } else if (url.startsWith(Constants.KAOLAFM)) {
                                String ret = JSON.parseObject(str).getString("result");
                                ret = JSON.parseObject(ret).getString("dataList");
                                ArrayList<Kaola> kaolas = (ArrayList<Kaola>) JSON.parseArray(ret, Kaola.class);
                                if (Xutils.listNotNull(kaolas)) {
                                    for (Kaola tmp : kaolas
                                            ) {
                                        pageSongs.add(new Song(tmp));
                                    }
                                }
                            } else {
                                String ret = JSON.parseObject(str).getString("list");
                                pageSongs = (ArrayList<Song>) JSON.parseArray(ret, Song.class);
                                pageSongs.remove(0);
                            }
                            if (pagesize != -1 && Xutils.listNotNull(pageSongs) && pageSongs.size() == pagesize) {
                            } else {
                            }
                            if (Xutils.listNotNull(pageSongs)) {
                                songs.addAll(pageSongs);
                            }


                            songModel.setSongs(songs);

                            playList.clear();
                            playList.addAll(songs);

                            if (songList != null) {
                                songList.updateUI();
                            }
//                            jPanelRight.repaint();
//                            jPanelRight.invalidate();

//                            if (pageIndex == 1) {
//                            } else {
//                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e1) {
            Xutils.debug("链接不合法~");
            e1.printStackTrace();
        }
    }


    private void urlHandle(String url) {
        if (Xutils.isNotEmptyOrNull(url)) {
            /**
             *http://xima.tv/biXlhQ
             * android.os.NetworkOnMainThreadException
             */
            if (url.startsWith(Constants.WEBURL_XIMALAYA_PRE)
                    || url.startsWith(Constants.WEBURL_XIMALAYA)
                    || url.startsWith(Constants.WEBURL_XIMALAYA_WWW)) {
                String title = url;
                BaseProgrom progrom = null;
                try {
                    try {
                        Document doc = Jsoup.connect(url).ignoreContentType(true).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout(5000).get();
                        url = doc.baseUri();
                        title = doc.title();
                        /**
                         * 【曾仕强：中国式管理——管理思维】在线收听_mp3下载_ 喜马拉雅电台
                         */
                        if (Xutils.isNotEmptyOrNull(title)) {
                            if (title.startsWith("【") && title.contains("】")) {
                                title = title.substring(title.indexOf("【") + 1, title.lastIndexOf("】"));
                            }
                        }
                        progrom = new BaseProgrom(title, url);
                        dealInfo(progrom);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (url.startsWith(Constants.WEBURL_KAOLAFM_WWW)) {
                String title = url;
                BaseProgrom progrom = null;
                try {
                    Document doc = Jsoup.connect(url).ignoreContentType(true).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout(5000).get();
                    url = doc.baseUri();
                    title = doc.title();

                    /**
                     *<input id="albumID" type="hidden" value="1100000247593" />
                     脉脉早报|脉脉早报全集
                     */
                    String albumID = doc.getElementById("albumID").toString();
                    albumID = albumID.replaceAll("\\D", "");
                    if (Xutils.isNotEmptyOrNull(albumID)) {
                        url = "http://m.kaolafm.com/share/zj.html?albumId=" + albumID;
                    }
                    if (Xutils.isNotEmptyOrNull(title)) {
                        if (title.startsWith("【") && title.contains("】")) {
                            title = title.substring(title.indexOf("【") + 1, title.lastIndexOf("】"));
                        }
                    }
                    progrom = new BaseProgrom(title, url);
                    dealInfo(progrom);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (url.startsWith(Constants.WEBURL_QINGTING_WWW) || url.startsWith(Constants.WEBURL_QINGTING)) {
                if (url.startsWith(Constants.WEBURL_QINGTING)) {
                    /**
                     *http://m.qingting.fm/vchannels/72014?pagetype=1&targettype=2&timestamp=1505982919&from=5&os=1&deviceid=ffffffff-a04a-62c0-2956-571b65a2a43e&av=6&catid=529&channelid=72014&share=a_qqf
                     */
                    if (url.contains("pagetype")) {
                        url = url.substring(0, url.indexOf("pagetype") - 1);
                    }
                }
                String title = url;
                BaseProgrom progrom = null;
                try {
                    Document doc = Jsoup.connect(url).ignoreContentType(true).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout(5000).get();
                    url = doc.baseUri();
                    title = doc.title();
                    String channel = url.replaceAll("\\D", "");
                    /**
                     *http://api2.qingting.fm/v6/media/channelondemands/108012/programs/7520780/order/0/pagesize/30 -老的方式需要programsID，弃用
                     * http://api2.qingting.fm/v6/media/channelondemands/41504/programs/order/0/curpage/1/pagesize/10 -新的支持翻页
                     */
                    if (Xutils.isNotEmptyOrNull(channel)) {
                        url = "http://api2.qingting.fm/v6/media/channelondemands/" + channel + "/programs/order/0/curpage/1/pagesize/30";
                    }
                    if (Xutils.isNotEmptyOrNull(title)) {
                        if (title.contains("_")) {
                            title = title.substring(0, title.indexOf("_"));
                        }
                    }
                    progrom = new BaseProgrom(title, url);
                    dealInfo(progrom);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                dealInfo(new BaseProgrom(url));
            }
        } else {
            Xutils.debug("剪切板没有数据~");
        }
    }


    private boolean dealInfo(BaseProgrom progrom) {
        int type = MusicPlatformEnum.DEFAULT.value();
        String text = progrom.getUrl();
        String title = progrom.getTitle();
        if (Xutils.isEmptyOrNull(title)) {
            title = new Random().nextInt(1000) + "";
        }
        String url = "";
        if (text.startsWith(Constants.WEBURL_XIMALAYA) || text.startsWith(Constants.WEBURL_XIMALAYA_WWW)) {
            type = MusicPlatformEnum.XIMALAYA.value();
            /**
             *http://m.ximalaya.com/73880812/album/8125936
             * http://www.ximalaya.com/30495264/album/6108936/
             * http://mobile.ximalaya.com/mobile/v1/album/track/ts-1501238497350?albumId=3475911&isAsc=true
             */
            String tmp = "";
            if (text.startsWith(Constants.WEBURL_XIMALAYA)) {
                tmp = text.replace(Constants.WEBURL_XIMALAYA, "");
            } else if (text.startsWith(Constants.WEBURL_XIMALAYA_WWW)) {
                tmp = text.replace(Constants.WEBURL_XIMALAYA_WWW, "");
            }
            String albumInfo[] = tmp.split("/album/");
            if (albumInfo != null && albumInfo.length == 2) {
                if (albumInfo[1].endsWith("/")) {
                    albumInfo[1] = albumInfo[1].substring(0, albumInfo[1].length() - 1);
                }
                url = "http://mobile.ximalaya.com/mobile/v1/album/track/ts-" + albumInfo[0] + "?albumId=" + albumInfo[1] + "&isAsc=true&pageSize=30&&pageId=1";
            }
        } else if (text.startsWith(Constants.QINGTING)) {
            type = MusicPlatformEnum.QINGTING.value();
            url = text;
        } else if (text.startsWith(Constants.WEBURL_FMBAIDU)) {
            /**
             *http://fm.baidu.com/dev/api/?tn=playlist&id=public_tuijian_suibiantingting
             */
            if (text.contains("id=public_")) {
                title = text.substring(text.indexOf("id=public_") + "id=public_".length(), text.length());
            }
            url = text;
        } else if (text.startsWith(Constants.WEBURL_KAOLAFM)) {
            type = MusicPlatformEnum.KAOLAFM.value();

            /**
             * kaolafm
             *http://m.kaolafm.com/share/zj.html?albumId=1100000000207
             */
            String tmp = "";
            if (text.startsWith(Constants.WEBURL_KAOLAFM)) {
                tmp = text.replace(Constants.WEBURL_KAOLAFM, "");
            }
            /**
             *1100000000207
             */
            if (Xutils.isNotEmptyOrNull(tmp)) {
                /**
                 *http://api.kaolafm.com/api/v4/audios/list?sorttype=1&pagesize=30&pagenum=1&id=1100000000207
                 */
                url = "http://api.kaolafm.com/api/v4/audios/list?sorttype=1&pagesize=30&pagenum=1&id=" + tmp;

            }
        }
        realAdd(type, title, url);
        return false;
    }

    private void realAdd(int type, String title, String url) {
        if (Xutils.isEmptyOrNull(title) || Xutils.isEmptyOrNull(url)) {
            Xutils.debug("解析失败~");
        }
        Program mProgram = new Program(title, url);
        mProgram.setType(type);

        try {
            DbHelper.getProgramDao().create(mProgram);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        DbHelper.add(mProgram);
        initProgram();

        Xutils.debug("添加成功~");
    }


}
