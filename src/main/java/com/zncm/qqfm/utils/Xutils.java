package com.zncm.qqfm.utils;



import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jiaomx on 2017/7/20.
 */

public class Xutils {


    public static String getDateHM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dt = sdf.format(time);
        return dt;
    }

    public static String getDateHMS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dt = sdf.format(time);
        return dt;
    }
    public static void debug(Object object) {
         System.out.println( String.valueOf(object));
    }


    public static boolean isEmptyOrNull(String string) {

        return string == null || string.trim().length() == 0 || string.equals("null");


    }

    public static boolean isNotEmptyOrNull(String string) {
        return !isEmptyOrNull(string);

    }


    public static <T> boolean listNotNull(List<T> t) {
        if (t != null && t.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 100秒-》01:40
     */
    public static String secToTime(int time) {
        int hour = time / 3600;
        int minute = time / 60 % 60;
        int second = time % 60;
        StringBuffer sbInfo = new StringBuffer();
        if (hour > 0) {
            if (hour < 10) {
                sbInfo.append("0");
            }
            sbInfo.append(hour).append(":");
        }
        if (minute < 10) {
            sbInfo.append("0");
        }
        sbInfo.append(minute).append(":");
        if (second < 10) {
            sbInfo.append("0");
        }
        sbInfo.append(second);
        return sbInfo.toString();
    }


    /**
     * 从剪切板获得文字。
     */
    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    /**
     * 将字符串复制到剪切板。
     */
    public static void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

    /**
     * 从剪切板获得图片。
     */
    public static Image getImageFromClipboard() throws Exception {
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable cc = sysc.getContents(null);
        if (cc == null)
            return null;
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        return null;
    }

    /**
     * 复制图片到剪切板。
     */
    public static void setClipboardImage(final Image image) {
        Transferable trans = new Transferable() {
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.imageFlavor };
            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            public Object getTransferData(DataFlavor flavor)
                    throws UnsupportedFlavorException, IOException {
                if (isDataFlavorSupported(flavor))
                    return image;
                throw new UnsupportedFlavorException(flavor);
            }

        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans,
                null);
    }
}
