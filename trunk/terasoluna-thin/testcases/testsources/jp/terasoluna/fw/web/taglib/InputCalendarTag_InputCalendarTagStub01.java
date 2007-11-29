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

package jp.terasoluna.fw.web.taglib;

import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;

/**
 * InputCalendarTagTest用スタブクラス
 * 
 *
 */
@SuppressWarnings({"serial","serial"})
public class InputCalendarTag_InputCalendarTagStub01 extends InputCalendarTag {

    @Override
    protected void defineJavaScript(ResourceBundle calendarBundle) throws JspException {
        this.defineJavaScriptCalled = true;
        this.defineJavaScriptCalendarBundle = calendarBundle;
    }

    @Override
    protected void defineButton(ResourceBundle calendarBundle) throws JspException {
        this.defineButtonCalled = true;
        this.defineButtonCalendarBundle = calendarBundle;
    }

    /*
     * 呼び出し確認用変数
     */
    private boolean defineJavaScriptCalled = false;
    private boolean defineButtonCalled = false;
    private ResourceBundle defineJavaScriptCalendarBundle = null;
    private ResourceBundle defineButtonCalendarBundle = null;

    public boolean isDefineJavaScriptCalled() {
        return defineJavaScriptCalled;
    }

    public boolean isDefineButtonCalled() {
        return defineButtonCalled;
    }

    public ResourceBundle getDefineJavaScriptCalendarBundle() {
        return defineJavaScriptCalendarBundle;
    }

    public ResourceBundle getDefineButtonCalendarBundle() {
        return defineButtonCalendarBundle;
    }
}
