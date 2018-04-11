package org.it.learn.netty.common.model;

/**
 * Created by lwz on 2017/06/21 17:01.
 */
public class Response {

    private short  module;

    private short  cmd;

    private int    stateCode;

    private byte[] data;

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDataLength() {
        if (data == null) {
            return 0;
        }

        return data.length;
    }
}