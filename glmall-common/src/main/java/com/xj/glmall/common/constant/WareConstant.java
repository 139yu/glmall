package com.xj.glmall.common.constant;

public class WareConstant {
    public enum PurchaseStatusEnum{
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),BUYING(2,"已领取"),FINISHED(3,"已完成"),HAS_ERROR(4,"有异常");


        PurchaseStatusEnum(int code,String message){
            this.code = code;
            this.message = message;
        }
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public enum PurchaseDetailsStatusEnum{
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),FINISH(3,"已完成"),
        HASERROR(4,"采购失败");


        PurchaseDetailsStatusEnum(int code,String message){
            this.code = code;
            this.message = message;
        }
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
