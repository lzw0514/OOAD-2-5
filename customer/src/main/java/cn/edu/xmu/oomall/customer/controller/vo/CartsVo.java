package cn.edu.xmu.oomall.customer.controller.vo;

    private int errno;    //错误码
    private String errmsg;  //错误信息
    private Object data;   //响应数据

    public CartsVo(int errno, String errmsg, Object data) {
        this.errno = errno;
        this.errmsg = errmsg;
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
