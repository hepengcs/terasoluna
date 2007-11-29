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

package jp.terasoluna.fw.web.codelist;

/**
 * DBCodeListLoaderTestで使用する。
 * 
 */
public class DBCodeListLoaderImpl01 extends DBCodeListLoader {

    /**
     * loadCodeList()が呼び出されたか？
     */
    private boolean loadCodeListIsCalled = false;
    
    /**
     * loadCodeListの呼び出し判定。
     */
    public boolean isLoadCodeListIsCalled() {
        return loadCodeListIsCalled;
    }
    
    /**
     * 呼び出されたらtrueをloadCodeListIsCalledをtrueにする。
     */
    @Override
    protected void loadCodeList() {
        this.loadCodeListIsCalled = true;
    }
}
