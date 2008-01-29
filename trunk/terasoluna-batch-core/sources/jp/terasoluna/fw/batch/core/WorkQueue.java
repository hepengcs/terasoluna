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

package jp.terasoluna.fw.batch.core;


/**
 * 作業キューインタフェース。
 * 
 * <p>作業キューには、チャンク（ビジネスロジックの入力データの集まり）、あるいは
 * ジョブを分割するための分割キーなどが作業単位としてキューイングされる。</p>
 * 
 * <p>作業キューは、<code>JobManger</code> によってファクトリを通して作成され、
 * <code>Collector</code> によって取得されて入力を、その<code>JobManger</code> 
 * 配下の<code>Workable</code> オブジェクト（<code>JobWorker</code> など）に渡す
 * 役割を持つ。</p>
 * 
 * <p>作業キューにエンキューするのは、単一の <code>Collector</code> であるが、
 * 作業キューからデキューするのは、複数の <code>Workable</code> オブジェクトと
 * なる可能性がある。</p>
 * 
 */
public interface WorkQueue {

    /**
     * 作業キューから作業単位を取得する。
     *
     * @return キューから取得した作業単位
     */
    WorkUnit take();

    /**
     * 作業キューを終了する。
     *
     */
    void close();

    /**
     * 作業キューに作業単位を追加する。
     *
     * @param workUnit 追加する作業単位
     */
    void put(WorkUnit workUnit);

    /**
     * この作業キューの作業単位を処理するワーカの終了を待ち合わせる。
     */
    void waitForAllWorkers();
}
