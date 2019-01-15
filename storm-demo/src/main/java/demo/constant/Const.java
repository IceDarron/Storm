package demo.constant;

public interface Const {

    /***
     * storm tick相关配置常量
     *
     * @author rongxn
     *
     */
    interface Tick_Time {
        int TICK_TIME_10 = 10;
        int TICK_TIME_30 = 30;
        int TICK_TIME_60 = 60;
    }

    /***
     * storm 积攒数据相关配置常量
     *
     * @author rongxn
     *
     */
    interface Accumulate_Limit {
        int ACCUMULATE_LIMIT__10 = 10;
        int ACCUMULATE_LIMIT__100 = 100;
        int ACCUMULATE_LIMIT__1000 = 1000;
    }


}
