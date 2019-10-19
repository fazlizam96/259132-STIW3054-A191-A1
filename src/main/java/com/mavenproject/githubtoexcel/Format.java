/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavenproject.githubtoexcel;

/**
 *
 * @author Fazlizam
 */
public class Format {
    private String header;
    private String data;
    private String data2;
    
    public Format(){
        
    }
    
    public Format(String header, String data1, String data2){
        this.header = header;
        this.data = data1;
        this.data2 = data2;
    }
    
    
    
    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
    
    public void setData(String data1) {
        this.data = data1;
    }

    public String getData() {
        return data;
    }
    
    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData2() {
        return data2;
    }
}




