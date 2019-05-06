package com.untech.mcrcb.web.common;

/**
 * TODO:  静态常量
 * @author : Mr.lx-root
 * @Date: 2019/3/28 0028
 */
public class Constants {

    /** TODO: ---用户角色
     * @Author Mr.lx
     * @Date 2019/3/28 0028
     **/
    public static final class USER_ROLE {
        //卫生院
        public static final String ROLE_WSY = "ROLE_WSY";
        //医养中心
        public static final String ROLE_YLY = "ROLE_YLY";
        //卫生院--全部都能查看的角色
        public static final String ROLE_WJW = "ROLE_WJW";

        public static final String ROLE_WSY_CHECK = "ROLE_CHECK";
        public static final String ROLE_WSY_RECHECK = "ROLE_RECHECK";
        //医养中心初审角色，与卫生院初审角色分开
        public static final String ROLE_JLY_CHECK = "ROLE_JLY_CHECK";
        //医养中心复审角色，与卫生院复审分开
        public static final String ROLE_JLY_RECHECK = "ROLE_JLY_RECHECK";
    }
    public static final class USER {
        public static final String USER_ID = "userId";
        public static final String ROLE_CODE = "roleCode";
    }



    /** TODO: --- 账号体系
     * @Author Mr.lx
     * @Date 2019/3/28 0028
     **/
    public static final class ACC_CODE {
        //默认
        public static final int DEFAULT = 0;
        //卫生院
        public static final int WSY = 1;
        public static final String WSY_STR = "1";
        //医养中心
        public static final int JLY = 2;
        public static final String JLY_STR = "2";
        //卫计委
        public static final int WJW = 9;
    }
    /** TODO:---支出用途
     * @Author Mr.lx
     * @Date 2019/4/3 0003
     **/
    public static final class OUT_USE {
        public static final String MEDICAL = "医疗";
        public static final String DRUG = "药品";
    }
    /** TODO: --- 收入或支出
     * @Author Mr.lx
     * @Date 2019/4/8 0008
     * @return
     **/
    public static final class IN_OUT {
        //收入
        public static final String IN = "1";
        //支出
        public static final String OUT = "2";
        //因新增医养中心体系，王瑞不同意在中台进行改动，所以，使用蹩脚方案，添加一个下拉框的选项说明是敬老院
        public static final String YLY= "4";
    }


    public static final class INSTITUTION_TYPE {
        public static final int WSY= 3400;
        public static final int JLY= 3500;
    }

}
