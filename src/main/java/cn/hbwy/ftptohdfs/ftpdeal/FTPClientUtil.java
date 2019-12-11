package cn.hbwy.ftptohdfs.ftpdeal;

import org.apache.commons.net.ftp.FTPClient;

import java.util.concurrent.ArrayBlockingQueue;


public class FTPClientUtil extends FTPClient {
    private ArrayBlockingQueue arrayBlockingQueue;
    public FTPClientUtil(ArrayBlockingQueue arrayBlockingQueue){
        this.arrayBlockingQueue=arrayBlockingQueue;
    }

    public void close(){
        arrayBlockingQueue.offer(this);
    }

}
