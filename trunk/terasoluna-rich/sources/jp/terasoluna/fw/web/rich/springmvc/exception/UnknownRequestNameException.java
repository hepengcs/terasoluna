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

package jp.terasoluna.fw.web.rich.springmvc.exception;

/**
 * BeanNameUrlHandlerMappingExでリクエスト名に対応するリクエストコントローラが
 * 存在しなかった場合に、UnkownRequestNameControllerからスローされるクラス。
 * 
 * 詳細は、UnkownRequestNameControllerを参照のこと。
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.UnkownRequestNameController
 * 
 */
public class UnknownRequestNameException extends Exception {
    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = -7482776140798712735L;
}
