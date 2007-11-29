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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.jsp.JspException;

/**
 * PageLinksTagのテスト用スタブクラス。
 * 
 */
@SuppressWarnings("serial")
public class PageLinksTag_PageLinksTagStub01 extends PageLinksTag {

    @Override
    protected void defineHtml(int row, int startIndex, int totalCount) throws JspException {
        defineHtmlCalled = true;
        defineHtmlRow = row;
        defineHtmlStartIndex = startIndex;
        defineHtmlTotalCount = totalCount;
    }
    @Override
    protected void addPrevSubmit(StringBuilder sb, int row, int startIndex, int totalCount) {
        addPrevSubmitCalled = true;
        addPrevSubmitSb = new StringBuilder(sb);
        addPrevSubmitRow = row;
        addPrevSubmitStartIndex = startIndex;
        addPrevSubmitTotalCount = totalCount;
        sb.append("a");
    }
    @Override
    protected void addDirectSubmit(StringBuilder sb, int row, int startIndex, int totalCount) {
        addDirectSubmitCalled = true;
        addDirectSubmitSb = new StringBuilder(sb);
        addDirectSubmitRow = row;
        addDirectSubmitStartIndex = startIndex;
        addDirectSubmitTotalCount = totalCount;
        sb.append("b");
    }
    @Override
    protected void addNextSubmit(StringBuilder sb, int row, int startIndex, int totalCount) {
        addNextSubmitCalled = true;
        addNextSubmitSb = new StringBuilder(sb);
        addNextSubmitRow = row;
        addNextSubmitStartIndex = startIndex;
        addNextSubmitTotalCount = totalCount;
        sb.append("c");
    }
    @Override
    protected void addPrevLink(StringBuilder sb, int row, int startIndex, int totalCount) {
        addPrevLinkCalled = true;
        addPrevLinkSb = new StringBuilder(sb);
        addPrevLinkRow = row;
        addPrevLinkStartIndex = startIndex;
        addPrevLinkTotalCount = totalCount;
        sb.append("a");
    }
    @Override
    protected void addDirectLink(StringBuilder sb, int row, int startIndex, int totalCount) {
        addDirectLinkCalled = true;
        addDirectLinkSb = new StringBuilder(sb);
        addDirectLinkRow = row;
        addDirectLinkStartIndex = startIndex;
        addDirectLinkTotalCount = totalCount;
        sb.append("b");
    }
    @Override
    protected void addNextLink(StringBuilder sb, int row, int startIndex, int totalCount) {
        addNextLinkCalled = true;
        addNextLinkSb = new StringBuilder(sb);
        addNextLinkRow = row;
        addNextLinkStartIndex = startIndex;
        addNextLinkTotalCount = totalCount;
        sb.append("c");
    }

    private boolean defineHtmlCalled = false;
    private boolean addPrevSubmitCalled = false;
    private boolean addDirectSubmitCalled = false;
    private boolean addNextSubmitCalled = false;
    private boolean addPrevLinkCalled = false;
    private boolean addDirectLinkCalled = false;
    private boolean addNextLinkCalled = false;

    private int defineHtmlRow = -1;
    private int defineHtmlStartIndex = -1;
    private int defineHtmlTotalCount = -1;
    private StringBuilder addPrevSubmitSb = null;
    private int addPrevSubmitRow = -1;
    private int addPrevSubmitStartIndex = -1;
    private int addPrevSubmitTotalCount = -1;
    private StringBuilder addDirectSubmitSb = null;
    private int addDirectSubmitRow = -1;
    private int addDirectSubmitStartIndex = -1;
    private int addDirectSubmitTotalCount = -1;
    private StringBuilder addNextSubmitSb = null;
    private int addNextSubmitRow = -1;
    private int addNextSubmitStartIndex = -1;
    private int addNextSubmitTotalCount = -1;
    private StringBuilder addPrevLinkSb = null;
    private int addPrevLinkRow = -1;
    private int addPrevLinkStartIndex = -1;
    private int addPrevLinkTotalCount = -1;
    private StringBuilder addDirectLinkSb = null;
    private int addDirectLinkRow = -1;
    private int addDirectLinkStartIndex = -1;
    private int addDirectLinkTotalCount = -1;
    private StringBuilder addNextLinkSb = null;
    private int addNextLinkRow = -1;
    private int addNextLinkStartIndex = -1;
    private int addNextLinkTotalCount = -1;

    public boolean isDefineHtmlCalled() {
        return defineHtmlCalled;
    }
    public boolean isAddPrevSubmitCalled() {
        return addPrevSubmitCalled;
    }
    public boolean isAddDirectSubmitCalled() {
        return addDirectSubmitCalled;
    }
    public boolean isAddNextSubmitCalled() {
        return addNextSubmitCalled;
    }
    public boolean isAddPrevLinkCalled() {
        return addPrevLinkCalled;
    }
    public boolean isAddDirectLinkCalled() {
        return addDirectLinkCalled;
    }
    public boolean isAddNextLinkCalled() {
        return addNextLinkCalled;
    }
    public int getAddDirectLinkRow() {
        return addDirectLinkRow;
    }
    public StringBuilder getAddDirectLinkSb() {
        return addDirectLinkSb;
    }
    public int getAddDirectLinkStartIndex() {
        return addDirectLinkStartIndex;
    }
    public int getAddDirectLinkTotalCount() {
        return addDirectLinkTotalCount;
    }
    public int getAddDirectSubmitRow() {
        return addDirectSubmitRow;
    }
    public StringBuilder getAddDirectSubmitSb() {
        return addDirectSubmitSb;
    }
    public int getAddDirectSubmitStartIndex() {
        return addDirectSubmitStartIndex;
    }
    public int getAddDirectSubmitTotalCount() {
        return addDirectSubmitTotalCount;
    }
    public int getAddNextLinkRow() {
        return addNextLinkRow;
    }
    public StringBuilder getAddNextLinkSb() {
        return addNextLinkSb;
    }
    public int getAddNextLinkStartIndex() {
        return addNextLinkStartIndex;
    }
    public int getAddNextLinkTotalCount() {
        return addNextLinkTotalCount;
    }
    public int getAddNextSubmitRow() {
        return addNextSubmitRow;
    }
    public StringBuilder getAddNextSubmitSb() {
        return addNextSubmitSb;
    }
    public int getAddNextSubmitStartIndex() {
        return addNextSubmitStartIndex;
    }
    public int getAddNextSubmitTotalCount() {
        return addNextSubmitTotalCount;
    }
    public int getAddPrevLinkRow() {
        return addPrevLinkRow;
    }
    public StringBuilder getAddPrevLinkSb() {
        return addPrevLinkSb;
    }
    public int getAddPrevLinkStartIndex() {
        return addPrevLinkStartIndex;
    }
    public int getAddPrevLinkTotalCount() {
        return addPrevLinkTotalCount;
    }
    public int getAddPrevSubmitRow() {
        return addPrevSubmitRow;
    }
    public StringBuilder getAddPrevSubmitSb() {
        return addPrevSubmitSb;
    }
    public int getAddPrevSubmitStartIndex() {
        return addPrevSubmitStartIndex;
    }
    public int getAddPrevSubmitTotalCount() {
        return addPrevSubmitTotalCount;
    }
    public int getDefineHtmlRow() {
        return defineHtmlRow;
    }
    public int getDefineHtmlStartIndex() {
        return defineHtmlStartIndex;
    }
    public int getDefineHtmlTotalCount() {
        return defineHtmlTotalCount;
    }
}
