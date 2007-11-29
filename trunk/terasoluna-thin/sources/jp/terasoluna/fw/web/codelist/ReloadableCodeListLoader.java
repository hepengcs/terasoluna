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
 * 再読み込みを行うコードリスト機能のインターフェース。
 * <p/>
 * コードリストの再読み込みを行う。
 *
 */
public interface ReloadableCodeListLoader extends CodeListLoader {

    /**
     * コードリストの再読み込みを行う。
     * <p/>
     * コードリストは原則としてアプリケーション中で一意となる情報である。
     * コードリストの内容が編集されても影響がないように実装する必要がある。
     */
    void reload();

}
