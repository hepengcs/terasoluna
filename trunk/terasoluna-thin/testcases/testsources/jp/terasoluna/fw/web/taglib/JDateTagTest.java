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

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.TagUTUtil;

/**
 * JDateTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class JDateTagTest extends PropertyTestCase {

    // テスト対象
    JDateTag tag = null;

    /**
     * Constructor for JDateTagTest.
     * @param arg0
     */
    public JDateTagTest(String arg0) {
        super(arg0);
        tag = (JDateTag) TagUTUtil.create(JDateTag.class);
    }
    
    /*
     * @see PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        /*
         * プロパティファイルの変わりとなるMapの作成。
         */
        Map<String, String> m = new HashMap<String, String>();
        m.put("wareki.gengo.0.name", "平成");
        m.put("wareki.gengo.0.roman", "H");
        m.put("wareki.gengo.0.startDate", "1989/01/08");
        m.put("wareki.gengo.1.name", "昭和");
        m.put("wareki.gengo.1.roman", "S");
        m.put("wareki.gengo.1.startDate", "1926/12/25");
        m.put("wareki.gengo.2.name", "大正");
        m.put("wareki.gengo.2.roman", "T");
        m.put("wareki.gengo.2.startDate", "1912/07/30");
        m.put("wareki.gengo.3.name", "明治");
        m.put("wareki.gengo.3.roman", "M");
        m.put("wareki.gengo.3.startDate", "1868/09/04");
        // プロパティとしてMapを追加。
        addPropertyAll(m);
    }

    /*
     * @see PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {}

    /**
     * testDoFormat01。<br>
     * <br>
     * (正常系)<br>
     * 観点：E<br>
     * <br>
     * 入力値<br>
     * date="2004-11-24 10:31:00.000000000"<br>
     * pattern="Gyy.MM.dd"<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="H16.11.24"<br>
     * <br>
     * 引数dateおよび、patternがNullではない場合のテストケース<br>
     */
    public void testDoFormat01() throws Exception {

        // テスト設定
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        Date date = new Date(time.getTime());
        tag.pattern = "Gyy.MM.dd";

        // テスト実行
        String result = tag.doFormat(date);

        // テスト結果確認
        assertEquals("H16.11.24", result);

    } /* testDoFormat01 End */

} /* JDateTagTest Class End */
