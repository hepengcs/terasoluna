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

/**
 * <p>
 *  ランダムID生成クラス。
 * </p>
 * 
 * <p>
 *  {@link LinkTag} や {@link FormTag} 等で、アクション <code>URL</code>
 *  にキャッシュ避け用のランダム <code>ID</code> を追加時に使用される。
 * </p>
 * 
 * 
 * @see FormTag
 * @see LinkTag
 * 
 */
public class RandomUtil {

    /**
     * ランダムIDをURLに追加するときのパラメータ名。
     */
    public static final String RANDOM_ID_KEY = "r";

    /**
     * 乱数ジェネレータ。
     */
    private static final java.util.Random RANDOM = new java.util.Random();

    /**
     * <p>ランダムID文字列を生成する。</p>
     *
     * @return ランダムID
     */
    public static String generateRandomID() {
        long r = RANDOM.nextLong();
        long t = System.currentTimeMillis();
        return String.valueOf(Math.abs(r + t));
    }

}
