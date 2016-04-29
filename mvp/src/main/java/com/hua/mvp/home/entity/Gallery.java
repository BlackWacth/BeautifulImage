package com.hua.mvp.home.entity;

import java.util.List;

/**
 * Created by ZHONG WEI  HUA on 2016/4/1.
 */
public class Gallery {

    /**
     * status : true
     * total : 696
     * tngou : [{"count":5092,"fcount":0,"galleryclass":3,"id":710,"img":"/ext/160321/e57d5816cb72d7486aa6dbf19a7d0c6c.jpg","rcount":0,"size":16,"time":1458561029000,"title":"很诱人的美女翘臀诱惑那超波肉丝腿真长"},{"count":1975,"fcount":0,"galleryclass":3,"id":709,"img":"/ext/160321/0443e950c8e75792ef033472c9071f44.jpg","rcount":0,"size":10,"time":1458560993000,"title":"性感腿模诱人黑丝腿魔鬼身材真美"},{"count":2522,"fcount":0,"galleryclass":3,"id":708,"img":"/ext/160321/6623aa17fe10b1401321fca757b8275c.jpg","rcount":0,"size":10,"time":1458560892000,"title":"超短的旗袍还不是最吸引目光"},{"count":868,"fcount":0,"galleryclass":4,"id":707,"img":"/ext/160321/0918e9ea3a70e9b122e5b891a3ce6764.jpg","rcount":0,"size":13,"time":1458560803000,"title":"如此迷人心魂的美女那细长美腿"},{"count":1162,"fcount":0,"galleryclass":1,"id":706,"img":"/ext/160321/11b7f4fd41e0a2549d3fe5f1c30e8608.jpg","rcount":0,"size":10,"time":1458560719000,"title":"极品美女那性感美腿娇身"},{"count":1350,"fcount":0,"galleryclass":1,"id":705,"img":"/ext/160321/d663c9e93d3d6aa5323db3817af4fabc.jpg","rcount":0,"size":11,"time":1458560657000,"title":"美女腿模高挑身材，那吊带黑丝腿超性感"},{"count":882,"fcount":0,"galleryclass":3,"id":704,"img":"/ext/160318/7c51e5e48c705e737cb90cc0e47f134c.jpg","rcount":0,"size":60,"time":1458306701000,"title":"气质逼人的美女性感长腿好美"},{"count":2134,"fcount":0,"galleryclass":1,"id":703,"img":"/ext/160318/3115aed4b35439691acb30475502043c.jpg","rcount":0,"size":10,"time":1458306237000,"title":"修长的丝袜美腿"},{"count":616,"fcount":0,"galleryclass":4,"id":702,"img":"/ext/160318/961040f6a28e0b5531f443acd8debc15.jpg","rcount":0,"size":10,"time":1458305868000,"title":"诱惑人心的超短裙美女苗条身材性感长腿"},{"count":509,"fcount":0,"galleryclass":3,"id":701,"img":"/ext/160318/555718425bff0a8d14fa1bd43dd555cc.jpg","rcount":0,"size":12,"time":1458305823000,"title":"漂亮女神那极品黑丝长腿太美了"}]
     */

    private boolean status;
    private int total;
    private List<Tngou> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Tngou> getTngou() {
        return tngou;
    }

    public void setTngou(List<Tngou> tngou) {
        this.tngou = tngou;
    }

    public static class Tngou {

        /**
         * count : 5092
         * fcount : 0
         * galleryclass : 3
         * id : 710
         * img : /ext/160321/e57d5816cb72d7486aa6dbf19a7d0c6c.jpg
         * rcount : 0
         * size : 16
         * time : 1458561029000
         * title : 很诱人的美女翘臀诱惑那超波肉丝腿真长
         */

        /**访问数  */
        private int count;

        /**收藏数 */
        private int fcount;

        /**图片分类 */
        private int galleryclass;

        /**图库ID编码  图片WEB访问地址，可以通过 http://www.tngou.net/tnfs/show/+【id】 的方式拼接。
         * 如： http://www.tngou.net/tnfs/show/18  支持网站和手机跨平台浏览*/
        private int id;

        /**图库封面 */
        private String img;

        /**回复数 */
        private int rcount;

        /**图片张数 */
        private int size;

        /**发布时间 */
        private long time;

        /**标题 */
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getGalleryclass() {
            return galleryclass;
        }

        public void setGalleryclass(int galleryclass) {
            this.galleryclass = galleryclass;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Tngou{" +
                    "\ncount=" + count +
                    ", \nfcount=" + fcount +
                    ", \ngalleryclass=" + galleryclass +
                    ", \nid=" + id +
                    ", \nimg='" + img + '\'' +
                    ", \nrcount=" + rcount +
                    ", \nsize=" + size +
                    ", \ntime=" + time +
                    ", \ntitle='" + title + '\'' +
                    "\n}";
        }
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "\nstatus=" + status +
                ", \ntotal=" + total +
                ", \ntngou=" + tngou +
                "\n}";
    }
}
