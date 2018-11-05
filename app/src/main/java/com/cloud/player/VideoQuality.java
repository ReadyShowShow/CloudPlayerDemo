package com.cloud.player;

public enum VideoQuality {
    VIDEO_HD2(0, "mp4hd2", "超清"),
    VIDEO_HD(1, "mp4hd", "高清"),
    VIDEO_STANDARD(2, "flvhd", "标清"),
    VIDEO_AUTO(3, "auto", "自动"),
    VIDEO_HD3(4, "mp4hd3", "1080P"),
    VIDEO_LOW(5, "3gphd", "省流");

    public final int id;
    public final String engName;
    public final String chiName;

    VideoQuality(int id, String engName, String chiName) {
        this.id = id;
        this.engName = engName;
        this.chiName = chiName;
    }

    public int toInt() {
        return id;
    }

    public static VideoQuality fromInt(int i) {
        switch (i) {
            case 0:
                return VIDEO_HD2;
            case 1:
                return VIDEO_HD;
            case 2:
                return VIDEO_STANDARD;
            case 3:
                return VIDEO_AUTO;
            case 4:
                return VIDEO_HD3;
            case 5:
                return VIDEO_LOW;
            default:
                return VIDEO_STANDARD;
        }
    }

    public static VideoQuality fromString(String desc) {
        switch (desc) {
            case "3gphd":
                return VIDEO_LOW;
            case "flvhd":
                return VIDEO_STANDARD;
            case "mp4hd":
                return VIDEO_HD;
            case "mp4hd2":
                return VIDEO_HD2;
            case "mp4hd3":
                return VIDEO_HD3;
            default:
                return VIDEO_STANDARD;
        }
    }
}
