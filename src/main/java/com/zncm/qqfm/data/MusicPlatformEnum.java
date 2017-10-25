package com.zncm.qqfm.data;


public enum MusicPlatformEnum {
        DEFAULT(0, "未知"), LOCAL(1, "内置"), KAOLAFM(2, "考拉"), XIMALAYA(3, "喜马拉雅"), QINGTING(4, "蜻蜓");
        private String strName;
        private int value;

        private MusicPlatformEnum(int value, String strName) {
            this.value = value;
            this.strName = strName;
        }

        public static MusicPlatformEnum valueOf(int value) {
            for (MusicPlatformEnum typeEnum : MusicPlatformEnum.values()) {
                if (typeEnum.value == value) {
                    return typeEnum;
                }
            }
            return LOCAL;
        }

        public String strName() {
            return strName;
        }

        public int value() {
            return value;
        }

    }
