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

package jp.terasoluna.fw.batch.openapi;

/**
 * 返却コードの列挙クラス。
 * 
 *<p><code>ReturnCode</code> では、以下の返却コードを提供する</p>
 *
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>返却コード</b> </td>
 *    <td> <b>概要</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>NORMAL_CONTINUE</code> </td>
 *    <td align="left">
 *    処理を継続する。通常はこの返却コードを返却する。
 *    最後の入力パラメータである場合にはトランザクションをコミットし、ジョブを正
 *    常終了する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>NORMAL_END</code> </td>
 *    <td align="left">
 *      対象データの途中で処理を終了させたい場合に返却する。
 *    トランザクションをコミットし、ジョブを正常終了する。
 *    （分割ジョブである場合には、子ジョブのみ終了する）
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ERROR_CONTINUE</code> </td>
 *    <td align="left">
 *      エラーデータとしてログ出力を行い、処理を継続する。
 *    ビジネスロジックが含まれるトランザクションには影響を与えない。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ERROR_END</code> </td>
 *    <td align="left">
 *      エラーメッセージをログに出力し、ジョブを終了する。
 *    トランザクションはロールバックされる。
 *    （分割ジョブである場合には、子ジョブのみ終了する）
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p>
 */
public enum ReturnCode {

    /**
     * 返却コード。
     */
    NORMAL_CONTINUE, NORMAL_END, ERROR_CONTINUE, ERROR_END

}
