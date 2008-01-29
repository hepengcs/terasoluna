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
 * 作業を実行できるクラスが実装するインタフェース。
 * 
 * <p>このインタフェースは、フレームワークの中核となるインタフェースであり、
 * <code>JobManager</code>、<code>JobWorker</code> といったフレームワークの
 * 主要なクラスで実装される。</p>
 * 
 * <p><code>Workable</code> インタフェースの特定の実装クラスで処理される"作業"は、
 * その実装クラスにおいて必要に応じて分割される。分割された"作業"は、
 * このインタフェースを実装した別のクラスのインスタンス群によって分担されること
 * がある。このように <code>Workable</code> インタフェースは、ジョブの実行に必要
 * な"作業"を階層的に分割する際に、コンポジットな構造を作るために使用される。</p>
 * 
 * <p><code>Workable</code> インタフェースの実装は、ジョブと１：１に対応するもの
 * ではない。<code>Workable</code> インタフェースの実装が構成する作業の階層のう
 * ちのひとつの層が、ジョブと対応する。したがって <code>Workable</code> インタフ
 * ェースの実装では、ジョブの一部のみを実行していることがあるが、複数の <code>
 * Workable</code>インタフェースの実装でジョブ全体の状態（ジョブステータス）を共
 * 有するため、パラメータに <code>JobStatus</code> が渡される。</p>
 *
 * @param <T> 作業単位パラメータのクラス
 */
public interface Workable<T extends WorkUnit> {

    /**
     * 作業を行う。
     *
     * @param workUnit 作業単位
     * @param jobStatus ジョブステータス
     */
    void work(T workUnit, JobStatus jobStatus);
}
