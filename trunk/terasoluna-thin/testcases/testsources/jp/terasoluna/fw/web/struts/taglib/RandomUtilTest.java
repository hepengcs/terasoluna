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

import java.util.List;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * RandomUtilTest ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class RandomUtilTest extends TestCase {

    /**
     * Constructor for RandomUtilTest.
     * @param arg0
     */
    public RandomUtilTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGenerateRandomID01。<br>
     * <br>
     * (正常系)<br>
     * 観点：C<br>
     * <br>
     * 入力値<br>
     * なし<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=ランダムID<br>
     * <br>
     * 10000回実行し、同じIDが存在するか否か確認をするテストケース
     */
    public void testGenerateRandomID01() {

        //テスト設定
        List<String> list = new ArrayList<String>();
        list.add(RandomUtil.generateRandomID());

        //テスト実行&テスト結果確認
        for(int i = 0; i < 10000; i++){
            String rand = RandomUtil.generateRandomID();
            if(list.contains(rand)){
                fail();
            }
            list.add(rand);
        }

        list.clear();
        list = null;

    } /* testGenerateRandomID End */

} /* RandomUtilTest Class End */
