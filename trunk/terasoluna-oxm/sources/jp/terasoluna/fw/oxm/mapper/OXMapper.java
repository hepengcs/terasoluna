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

package jp.terasoluna.fw.oxm.mapper;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import org.w3c.dom.Document;

/**
 * オブジェクト-XML変換を行うためのインタフェース。
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public interface OXMapper {
    /**
     * DOMツリーからオブジェクトに変換するメソッド。
     * @param doc DOMツリー
     * @param out 変換対象のオブジェクト
     */
    void unmarshal(Document doc, Object out);
    
    /**
     * ストリームからXMLデータを取り出し、オブジェクトに変換する。
     * @param reader XMLデータ。文字セットが指定されていない場合、
     * VMのデフォルト文字セットが使用される。
     * @param out XMLから変換されたオブジェクト。
     */
    void unmarshal(Reader reader, Object out);
    
    /**
     * ストリームからXMLデータを取り出し、オブジェクトに変換する。
     * @param is XMLデータ。
     * @param argCharset 文字セット。Nullまたは空文字の場合、UTF-8が指定される。
     * @param out XMLから変換されたオブジェクト。
     */
    void unmarshal(InputStream is, String argCharset, Object out);

    /**
     * オブジェクトからXMLに変換を行い、ストリームに出力するメソッド。
     * @param in 変換対象のオブジェクト
     * @param writer ライター
     */
    void marshal(Object in, Writer writer);
}
