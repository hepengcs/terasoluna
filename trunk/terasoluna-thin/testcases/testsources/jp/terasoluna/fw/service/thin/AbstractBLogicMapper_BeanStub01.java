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

package jp.terasoluna.fw.service.thin;

/**
 * AbstractBLogicMapperTestクラスで利用する。
 *
 */
public class AbstractBLogicMapper_BeanStub01 {

    private String blogicUserName = null;
    private String userName = null;
    private String blogicRequestValue = null;
    private String blogicSessionValue = null;
    private String blogicApplicationValue = null;

    /**
     * blogicApplicationValueを返却する。
     * @return blogicApplicationValue
     */
    public String getBlogicApplicationValue() {
        return blogicApplicationValue;
    }

    /**
     * blogicApplicationValueを設定する。
     * @param blogicApplicationValue blogicApplicationValue
     */
    public void setBlogicApplicationValue(String blogicApplicationValue) {
        this.blogicApplicationValue = blogicApplicationValue;
    }

    /**
     * blogicRequestValueを取得する。
     */
    public String getBlogicRequestValue() {
        return blogicRequestValue;
    }

    /**
     * blogicRequestValueを設定する。
     */
    public void setBlogicRequestValue(String blogicRequestValue) {
        this.blogicRequestValue = blogicRequestValue;
    }

    /**
     * blogicSessionValueを取得する。
     */
    public String getBlogicSessionValue() {
        return blogicSessionValue;
    }

    /**
     * blogicSessionValueを設定する。
     */
    public void setBlogicSessionValue(String blogicSessionValue) {
        this.blogicSessionValue = blogicSessionValue;
    }

    /**
     * blogicUserNameを取得する。
     */
    public String getBlogicUserName() {
        return blogicUserName;
    }

    /**
     * blogicUserNameを設定する。
     */
    public void setBlogicUserName(String blogicUserName) {
        this.blogicUserName = blogicUserName;
    }

    /**
     * userNameを取得する。
     */
    public String getUserName() {
        return userName;
    }

    /**
     * userNameを設定する。
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


}
