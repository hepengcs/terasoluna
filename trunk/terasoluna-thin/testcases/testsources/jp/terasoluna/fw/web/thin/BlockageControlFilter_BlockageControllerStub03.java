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

import javax.servlet.ServletRequest;
/**
 * BlockageControlFilterTestクラスで利用する。
 * 
 */
public class BlockageControlFilter_BlockageControllerStub03 implements
        BlockageController {

    public void blockade(String path) {

    }

    public void blockade(String path, ServletRequest req) {
    }

    public boolean isBlockaded(String path) {
        return false;
    }

    public boolean isBlockaded(String path, ServletRequest req) {
        return false;
    }

    public boolean isCheckRequired(ServletRequest req) {
        return true;
    }

    public void open(String path) {

    }

    public void open(String path, ServletRequest req) {

    }

}
