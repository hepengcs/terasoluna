/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.thin;

import java.util.Date;

/**
 * ServerBlockageControlFilterTestクラスで利用する。
 * 
 */
public class ServerBlockageControlFilter_ServerBlockageControllerStub02
    implements ServerBlockageController{
    
    public void blockade(){}
    
    public boolean isBlockaded(){
        return true;
    }
    
    public boolean isBlockaded(String pathInfo){
        return true;
    }
    
    public  boolean isPreBlockaded(){
        return false;
    }
    
    public void open(){
    }
    
    public void preBlockade(){
    }
    
    public void preBlockade(Date time){
    }

}

