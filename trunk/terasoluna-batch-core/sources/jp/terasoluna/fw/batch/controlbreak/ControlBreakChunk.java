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

package jp.terasoluna.fw.batch.controlbreak;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.ArrayUtils;

/**
 * <p>コントロールブレイク用チャンククラス。</p>
 * <p>チャンクコントロールブレイクキー毎のチャンクを生成し、キューに追加する。
 * このクラスでは設定されたキーを解析し、チャンクコントロールブレイクが
 * 発生しているかどうかを判定する。</p>
 * <p>判断した結果をチャンクやコントロールブレイクRowデータに持たせ、
 * <code>ControlBreakWorker</code>、
 * <code>ControlBreakBLogicExecutor</code>内でそれぞれブレイク処理を起動させる。
 * 
 * 
 */
public class ControlBreakChunk extends Chunk {

    /**
     * チャンクの終端用オブジェクト。
     */
    public static final Object END_MARK = new Object();
    
    
    /**
     * チャンクのブレイクマーク用オブジェクト。
     */
    public static final Object CHUNK_BREAK_MARK = new Object();
    
    /**
     * コントロールブレイクキーリスト。（小）
     */
    private final List<ControlBreakDefItem> controlBreakDefItemList;

    /**
     * チャンクコントロールブレイクキーリスト。（中）
     */
    private final ControlBreakDefItem chunkControlBreakDefItem;

    /**
     * トランスコントロールブレイクキーリスト。（大）
     */
    private final List<ControlBreakDefItem> transChunkControlBreakDefItemList;
    
    /**
     * 処理中のデータで、チャンクコントロールブレイクが発生しているかどうかを
     * 示す。
     */
    private boolean chunkControlBreak = false;
    
    /**
     * チャンクコントロールブレイクキーマップ。
     * 対象オブジェクトに関して、処理中のオブジェクトの属性を保持するマップ。
     * チャンクコントロールブレイクキーを構成するプロパティのみを保持する。
     */
    private Map<String, Object> chunkControlBreakMap = null;

    /**
     * 対象オブジェクトに関して、次のオブジェクトの属性を保持するマップ。
     * チャンクコントロールブレイクキーを構成するプロパティのみを保持する。
     */
    private Map<String, Object> chunkNextData = null;

    /**
     * コントロールブレイクキーマップ。
     * 対象オブジェクトに関して、処理中のオブジェクトの属性を保持するマップ。
     * コントロールブレイクキーを構成するプロパティのみを保持する。
     */
    private Map<String, Object> controlBreakMap = null;

    /**
     * 対象オブジェクトに関して、次のオブジェクトの属性を保持するマップ。
     * コントロールブレイクキーを構成するプロパティのみを保持する。
     */
    private Map<String, Object> nextData = null;

    /**
     * トランスコントロールブレイクキー。
     */
    private List<List<String>> transChunkControlBreakKeyList;
    
    /**
     * 対象オブジェクトに関して、次のオブジェクトの属性を保持するマップ。
     * トランスコントロールブレイクキーを構成するプロパティのみを保持する。
     */
    private LinkedHashMap<String, Object> transNextData;

    /**
     * 最終チャンクフラグ。
     */
    private boolean endChunk = false;
    
    /**
     * Methodのcache
     */
    private FastHashMap methodMap = null;
    
    /**
     * コンストラクタ
     *  
     * @param jobContext ジョブコンテキスト
     * @param controlBreakDef コントロールブレイクの定義情報
     * @param chunkSize チャンクサイズ
     */
    public ControlBreakChunk(JobContext jobContext,
            ControlBreakDef controlBreakDef, int chunkSize) {
        super(chunkSize, jobContext);
        this.controlBreakDefItemList = controlBreakDef
                .getControlBreakDefItemList();
        this.chunkControlBreakDefItem = controlBreakDef
                .getChunkControlBreakDefItem();
        this.transChunkControlBreakDefItemList = controlBreakDef
                .getTransChunkControlBreakDefItemList();
        this.transChunkControlBreakKeyList = new ArrayList<List<String>>(
                transChunkControlBreakDefItemList.size());

        chunkNextData = new LinkedHashMap<String, Object>(
                chunkControlBreakDefItem.getBreakKey().size());
        transNextData = new LinkedHashMap<String, Object>(chunkNextData.size());

    }

    /**
     * コンストラクタ 
     * 
     * @param jobContext JobContext
     * @param controlBreakDef コントロールブレイク定義
     * @param chunkSize チャンクサイズ
     * @param nextData 前チャンクのコントロールブレイクキーの構成情報
     * @param chunkNextData 前チャンクのチャンクコントロールブレイクキーの構成
     * 情報
     */
    public ControlBreakChunk(JobContext jobContext,
            ControlBreakDef controlBreakDef, int chunkSize,
            Map<String, Object> nextData, Map<String, Object> chunkNextData) {
        super(chunkSize, jobContext);
        this.controlBreakDefItemList = controlBreakDef
                .getControlBreakDefItemList();
        this.chunkControlBreakDefItem = controlBreakDef
                .getChunkControlBreakDefItem();
        this.transChunkControlBreakDefItemList = controlBreakDef
                .getTransChunkControlBreakDefItemList();
        this.transChunkControlBreakKeyList = new ArrayList<List<String>>(
                transChunkControlBreakDefItemList.size());

        this.nextData = nextData;
        controlBreakMap = nextData;

        chunkControlBreakMap = chunkNextData;
        this.chunkNextData = new LinkedHashMap<String, Object>(
                chunkControlBreakDefItem.getBreakKey().size());
        transNextData = new LinkedHashMap<String, Object>(chunkNextData.size());

    }
    
    /**
     * 次データを設定する。
     * 
     * @param next 次データ
     * @return コントロールブレイク用Rowオブジェクトクラス
     */
    public ControlBreakRowObject setNext(Object next) {
        controlBreakMap = nextData;

        transChunkControlBreakKeyList.clear();

        // チャンクコントロールブレイクキーマップの初期化
        chunkNextData.clear();

        List<List<String>> controlBreakKeyList = new ArrayList<List<String>>(
                controlBreakDefItemList.size());

        // トランスチャンクコントロールブレイクのブレイクキチェック用
        transNextData.clear();
        
        //チャンクコントロールブレイクの発生を判断し、結果をチャンクにに追加する。
        if (chunkControlBreakDefItem.getBreakKey().size() > 0) {
            for (String propertyName : chunkControlBreakDefItem.getBreakKey()) {
                Object value = getBreakKeyValue(next, propertyName);
                chunkNextData.put(propertyName, value);
                
                // チャンクコントロールブレイクキーを構成するプロパティ名が
                // どれかひとつでも値が変わっている場合には、
                // チャンクコントロールブレイクキーが発生している。
                if (chunkControlBreakMap != null && value != null
                        && !value.equals(
                                chunkControlBreakMap.get(propertyName))) {
                    chunkControlBreak = true;
                    //トランスチャンクコントロールのブレイク候補
                    transNextData.put(propertyName, value);
                }
            }
        }
        // チャンクコントロールブレイクよりも上位のコントロールブレイクが
        // 発生しているか確認する。
        if (chunkControlBreak) {
            checkTransChunkControlBreak(transNextData.keySet());
        }
        
        if (chunkControlBreakMap == null) {
            chunkControlBreakMap = chunkNextData;
            chunkNextData = new LinkedHashMap<String, Object>(
                    chunkControlBreakDefItem.getBreakKey().size());
        }
        
        // コントロールブレイクの発生を判断し、結果をRowデータに追加する。
        if (controlBreakDefItemList.size() > 0) {
            nextData = new LinkedHashMap<String, Object>(
                    controlBreakDefItemList.size());

            for (ControlBreakDefItem controlBreakDefItem
                    : controlBreakDefItemList) {
                boolean controlBreak = false;
                for (String propertyName : controlBreakDefItem.getBreakKey()) {
                    Object value = getBreakKeyValue(next, propertyName);
                    nextData.put(propertyName, value);
                    // チャンクコントロールブレイクキーを構成するプロパティ名が
                    // どれかひとつでも値が変わっている場合には、
                    // チャンクコントロールブレイクキーが発生している。
                    if (!controlBreak && controlBreakMap != null
                            && value != null
                            && !value.equals(controlBreakMap.get(propertyName)))
                    {
                        controlBreak = true;
                    }
                }
                if (controlBreak) {
                    controlBreakKeyList.add(controlBreakDefItem.getBreakKey());
                }
            }
        }

        return new ControlBreakRowObject(next, controlBreakKeyList,
                controlBreakMap);
    }
    
    /**
     * 最終データのコントロールブレイク情報の再設定
     * @param last 最終データ
     * @return コントロールブレイク用Rowオブジェクトクラス
     */
    public ControlBreakRowObject setLastData(Object last) {
        transChunkControlBreakKeyList.clear();

        // チャンクコントロールブレイクキーマップの初期化
        chunkNextData = new LinkedHashMap<String, Object>(
                chunkControlBreakDefItem.getBreakKey().size());
        nextData = new LinkedHashMap<String, Object>(controlBreakDefItemList
                .size());
        chunkControlBreak = true;

        List<List<String>> controlBreakKeyList = new ArrayList<List<String>>(
                controlBreakDefItemList.size());

        // チャンクコントロールブレイクの発生を判断し、結果をチャンクにに追加する。
        for (String propertyName : chunkControlBreakDefItem.getBreakKey()) {
            Object value = getBreakKeyValue(last, propertyName);
            chunkNextData.put(propertyName, value);
        }
        checkTransChunkControlBreak(chunkNextData.keySet());

        // コントロールブレイクの発生を判断し、結果をRowデータに追加する。
        if (controlBreakDefItemList.size() > 0) {
            for (ControlBreakDefItem controlBreakDefItem
                    : controlBreakDefItemList) {
                for (String propertyName : controlBreakDefItem.getBreakKey()) {
                    Object value = getBreakKeyValue(last, propertyName);
                    nextData.put(propertyName, value);
                    controlBreakKeyList.add(controlBreakDefItem.getBreakKey());
                }
            }
        }
        return new ControlBreakRowObject(ControlBreakChunk.END_MARK,
                controlBreakKeyList, nextData);
    }
    
    
    /**
     * 指定されたプロパティの値の切り替わりによって、チャンクコントロールブレイ
     * クよりも上位のコントロールブレイクが発生しているか確認する。
     *  
     * @param propertyNameSet 値の切り替わりが発生したプロパティ名
     */
    private void checkTransChunkControlBreak(Set<String> propertyNameSet) {
        if (transChunkControlBreakDefItemList == null) {
            return;
        }
        // チャンクで登録されたブレイクキーを逆順にしてからチェックを行う
        String[] propertyNames =
            propertyNameSet.toArray(new String[propertyNameSet.size()]);
        ArrayUtils.reverse(propertyNames);
        for (ControlBreakDefItem transChunkControlBreakDefItem
                : transChunkControlBreakDefItemList) {
            for (String propertyName : propertyNames) {
                if (transChunkControlBreakDefItem.getBreakKey().contains(
                        propertyName)) {
                    transChunkControlBreakKeyList
                            .add(transChunkControlBreakDefItem.getBreakKey());
                    break;
                }
            }
        }
    }
    
    /**
     * チャンクコントロールブレイクのブレイクキーの値を取得する。
     * 
     * @return チャンクコントロールブレイクのブレイクキーの値
     */
    public Map<String, Object> getChunkControlBreakMap() {
        return chunkControlBreakMap;
    }

    /**
     * トランスコントロールブレイクキーのリストを取得する。
     * 
     * @return トランスコントロールブレイクキーのリスト
     */
    public List<List<String>> getTransChunkControlBreakKeyList() {
        return transChunkControlBreakKeyList;
    }

    
    /**
     * チャンクコントロールブレイクが発生しているかどうかを判定する。
     * 
     * @return チャンクコントロールブレイクが発生している場合には、
     * <code>true</code>
     */
    public boolean isChunkControlBreak() {
        return chunkControlBreak;
    }

    /**
     * 最終チャンクであるかを評価する。
     * 
     * @return 最終チェックのの場合、<code>true</code>
     */
    public boolean isEndChunk() {
        return endChunk;
    }

    /**
     * 最終チャンクフラグを設定する。
     * 
     * @param endChunk 最終チャンクフラグ
     */
    public void setEndChunk(boolean endChunk) {
        this.endChunk = endChunk;
    }

    
    /**
     * コントロールブレイクのブレイクキーの値を取得する。
     * @return controlBreakMap コントロールブレイクのブレイクキーの値
     */
    public Map<String, Object> getNextData() {
        return nextData;
    }

    /**
     * チャンクコントロールブレイクのブレイクキーの値を取得する。
     * @return chunkNextData チャンクコントロールブレイクのブレイクキーの値
     */
    public Map<String, Object> getChunkNextData() {
        return chunkNextData;
    }
    
    /**
     * ブレイクキーの値を取得する
     * @param object RowObject 
     * @param breakKey ブレイクキー
     * @return ブレイクキーの値
     */
    private Object getBreakKeyValue(Object object, String breakKey) {
        // 初期処理
        if (methodMap == null) {
            try {
                // BreakKeySet
                HashSet<String> keySet = new HashSet<String>();
                for (String propertyName : chunkControlBreakDefItem
                        .getBreakKey()) {
                    keySet.add(propertyName);
                }
                for (ControlBreakDefItem controlBreakDefItem
                        : controlBreakDefItemList) {
                    for (String propertyName : controlBreakDefItem
                            .getBreakKey()) {
                        if (!keySet.contains(propertyName)) {
                            keySet.add(propertyName);
                        }
                    }
                }
                // Method Map
                methodMap = new FastHashMap();
                BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
                PropertyDescriptor[] descriptors = beanInfo
                        .getPropertyDescriptors();
                for (PropertyDescriptor descriptor : descriptors) {
                    if (keySet.contains(descriptor.getName())) {
                        methodMap.put(descriptor.getName(), descriptor
                                .getReadMethod());
                    }
                }
            } catch (IntrospectionException e) {
                throw new JobException(e);
            }
        }

        try {
            Method method = (Method) methodMap.get(breakKey);
            return method.invoke(object, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new JobException(e);
        } catch (InvocationTargetException e) {
            throw new JobException(e);
        }
    }

    /**
     * @return methodMap を戻します。
     */
    public FastHashMap getMethodMap() {
        return methodMap;
    }

    /**
     * @param methodMap 設定する methodMap。
     */
    public void setMethodMap(FastHashMap methodMap) {
        this.methodMap = methodMap;
    }
}
