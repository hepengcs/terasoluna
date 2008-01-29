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

package jp.terasoluna.fw.oxm.mapper.castor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorCreateMarshallerIOException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMappingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMarshalException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorUnsupportedEncodingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorValidationException;
import jp.terasoluna.fw.oxm.serialize.XMLSerializerEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.ClassDescriptorResolver;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLClassDescriptorResolver;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;

/**
 * Castorを利用したオブジェクト-XML変換クラス。
 * 
 * <p>
 * オブジェクト-XMLの変換を明示的に指定する場合は、Castorマッピング定義ファイルが必要となる。
 * 一つのCastorマッピング定義ファイルでオブジェクト→XML、XML→オブジェクト 相互の変換を行うことができる。
 * Castorマッピング定義ファイルは変換を行うオブジェクトのクラスと 同じパッケージ、同じ名前、拡張子”.xml”で配置すること。
 * </p>
 * 
 * <p>
 * Castorマッピング定義ファイルを省略した場合は、Castorのデフォルト変換ルールが適用される。
 * </p>
 * 
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>デフォルト変換を使用する場合のオプション</legend> デフォルト変換を使用する場合は、CastorのXMLネーミングオプションを
 * 下記のように mixed と指定しておくこと。<br>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>castor.properties</legend> org.exolab.castor.xml.naming=mixed
 * </fieldset> <br>
 * このオプションの設定有無によって、下記のようにMarshall時の出力XMLが変化する。<br>
 * デフォルトのままだと、Unmarshall時に問題があるため、このオプションを設定する。<br>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>【org.exolab.castor.xml.naming=lower (デフォルト)】</legend>
 * <code>UserBean → &lt;user-bean&gt;</code><br>
 * </fieldset> <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;"> <legend>【org.exolab.castor.xml.naming=mixed】</legend>
 * <code>UserBean → &lt;userBean&gt;</code> </fieldset> </fieldset>
 * 
 * <p>
 * 例）XML変換対象のオブジェクトが「sample.SampleBean」クラスの場合、
 * Castorマッピング定義ファイルはクラスパス上の「sample/SampleBean.xml」 ファイルとなる。
 * </p>
 * 
 * <p>
 * Castorマッピング定義ファイルの記述方法は、 Castorの仕様に準じている。 詳細はTERASOUNAのドキュメントを参照すること。
 * </p>
 * 
 * <p>
 * 本クラスでは、一度Castorマッピング定義ファイルを読み込むと 内部でキャッシュされる。 キャッシュを利用しない場合、本クラスの
 * {@link #cache}属性をfalseにすること。
 * </p>
 * 
 * <p>
 * <strong>使用例</strong>
 * </p>
 * 
 * <p>
 * 【変換対象のオブジェクト】 <code><pre>
 *       public class SampleDto {
 *           private int userid;
 *           private String username;
 *           private Item[] item;
 *           ・・・
 *           （getter、setter）
 *       }
 *       public class Item {
 *           private int id;
 *           private String name;
 *           private int price;
 *           ・・・
 *           （getter、setter）
 *       }
 * </pre></code>
 * </p>
 * 
 * <p>
 * 【変換対象のXMLデータ】 <code><pre>
 *       &lt;sample-dto&gt;
 *        &lt;user-id&gt;15&lt;/user-id&gt;
 *        &lt;user-name&gt;user1&lt;/user-name&gt;
 *        &lt;item&gt;
 *          &lt;id&gt;100&lt;/id&gt;
 *          &lt;name&gt;item1&lt;/name&gt;
 *          &lt;price&gt;1000&lt;/price&gt;
 *        &lt;/item&gt;
 *        &lt;item&gt;
 *          &lt;id&gt;101&lt;/id&gt;
 *          &lt;name&gt;item2&lt;/name&gt;
 *          &lt;price&gt;2000&lt;/price&gt;
 *        &lt;/item&gt;
 *       &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * 【変換対象のオブジェクト】 <code><pre>
 *       SampleDto[0].userid[0] = 15
 *       SampleDto[0].name[0] = user1
 *       SampleDto[0].Item[0].id[0] = 100
 *       SampleDto[0].Item[0].name[0] = &quot;item1&quot;
 *       SampleDto[0].Item[0].price[0] = 1000
 *       SampleDto[0].Item[1].id[0] = 101
 *       SampleDto[0].Item[1].name[0] = &quot;item2&quot;
 *       SampleDto[0].Item[1].price[0] = 200
 * </pre></code>
 * </p>
 * 
 * <p>
 * 【Castorマッピング定義ファイル】 <code><pre>
 *       &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *       &lt;!DOCTYPE mapping PUBLIC &quot;-//EXOLAB/Castor Object Mapping DTD Version 1.0//EN&quot;
 *                                       &quot;http://castor.exolab.org/mapping.dtd&quot;&gt;
 *       &lt;mapping&gt;
 *         &lt;class name=&quot;sample.dto.SampleDto&quot;&gt;
 *           &lt;map-to xml=&quot;sample-dto&quot;/&gt;
 *           &lt;field name=&quot;userid&quot; type=&quot;int&quot;&gt;
 *             &lt;bind-xml name=&quot;user-id&quot; node=&quot;element&quot;/&gt;
 *           &lt;/field&gt;
 *            &lt;field name=&quot;username&quot; type=&quot;string&quot;&gt;
 *             &lt;bind-xml name=&quot;user-name&quot; node=&quot;element&quot;/&gt;
 *           &lt;/field&gt;
 *           &lt;field name=&quot;item&quot; type=&quot;sample.dto.Item&quot; collection=&quot;array&quot;&gt;
 *             &lt;bind-xml name=&quot;Item&quot; node=&quot;element&quot;/&gt;
 *          &lt;/field&gt;
 *         &lt;/class&gt;
 *         &lt;class name=&quot;sample.dto.Item&quot;&gt;
 *           &lt;field name=&quot;id&quot; type=&quot;int&quot;&gt;
 *           &lt;/field&gt;
 *           &lt;field name=&quot;name&quot; type=&quot;string&quot;&gt;
 *           &lt;/field&gt;
 *           &lt;field name=&quot;price&quot; type=&quot;int&quot;&gt;
 *           &lt;/field&gt;
 *         &lt;/class&gt;
 *       &lt;/mapping&gt; 
 * </pre></code>
 * </p>
 * 
 * <p>
 * 【実装コード（XML→オブジェクト）】
 * 
 * <pre><code>
 * CastorOXMapper oxmapper = new CastorOXMapperImpl();
 * SampleDto bean = new SampleDto(); // このオブジェクトにXMLデータが格納される
 * Reader reader = new FileReader(&quot;C:/sample/sampleDto.xml&quot;); // 変換するXMLデータ
 * 
 * // XML→オブジェクト変換
 * oxmapper.unmarshal(reader, bean);
 * </code></pre>
 * 
 * </p>
 * 
 * <p>
 * 【実装コード（オブジェクト→XML）】
 * 
 * <pre><code>
 * CastorOXMapper oxmapper = new CastorOXMapperImpl();
 * Writer writer = new OutputStreamWriter(System.out);
 * 
 * // オブジェクト→XML
 * oxmapper.marshal(bean, writer);
 * </code></pre>
 * 
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public class CastorOXMapperImpl implements OXMapper {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(CastorOXMapperImpl.class);

    /**
     * デフォルトの文字セット。
     */
    protected static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 文字セット。
     */
    private String charset = DEFAULT_CHARSET;

    /**
     * Castorマッピング定義ファイルのキャッシュ設定。デフォルトはtrue。
     */
    private boolean cache = true;

    /**
     * Marshallerオプション suppressXSITypeにfalseを設定するとマッピング定義無設定時に
     * 生成するドキュメントに「xmlns:xsi」及び「xsi:type」を付与する
     */
    private boolean suppressXSIType = true;

    /**
     * Marshallerオプション preserveWhitespaceにtrueを設定すると、
     * 生成するドキュメントのルートノードに「xml:space="preserve"」を付与する。<br>
     * デフォルト値はtrueである。 全電文に対して有効になるため、使用には注意が必要。
     */
    private boolean preserveWhitespaceAtMarshal = true;

    /**
     * Unmarshallerオプション whitespacePreserveにtrueを設定すると、 トップレベルの空白を保持するようになる。<br>
     * デフォルト値はfalseである。 全電文に対して有効になるため、使用には注意が必要。
     */
    private boolean preserveWhitespaceAtUnmarshal = false;

    /**
     * Marshallerオプション indentingにtrueを設定すると、生成されるxmlがインデントされる。<br>
     * <b>※注意：preserveWhitespaceAtMarshal が <u>false</u> の時のみ有効<b><br>
     */
    private boolean indenting = true;
    
    /**
     * パッケージのセパレータ。
     */
    public static final String NESTED_PACKAGE_SEPARATOR = ".";

    /**
     * フォルダのセパレータ。
     */
    public static final String NESTED_FOLDER_SEPARATOR = "/";

    /**
     * Castorマッピング定義ファイルのサフィックス。
     */
    public static final String CASTOR_MAPPINGFILE_SUFFIX = ".xml";

    /**
     * Castorマッピング定義ファイルをキャッシュするMap。
     */
    private Map<Class, Mapping> mappingFileCache = new HashMap<Class, Mapping>();

    /**
     * Marshallerで共有するXMLClassDescriptorResolver。
     */
    private XMLClassDescriptorResolver sharedResolverForMarshaller = null;

    /**
     * Marshallerに設定済みのCastorマッピングのClassの集合。
     */
    private Set<Class> hasSetMappingSetForMarshaller = new HashSet<Class>();

    /**
     * DOMツリーをオブジェクトに変換する。
     * 
     * @param doc
     *            DOMツリー。
     * @param out
     *            XMLから変換されたオブジェクト。
     */
    public void unmarshal(Document doc, Object out) {

        if (doc == null) {
            log.error("DOM tree is null.");
            throw new IllegalArgumentException("DOM tree is null.");
        }

        Unmarshaller unmarshaller = createUnmarshaller(out);

        // XML→Object
        try {
            unmarshaller.unmarshal(doc);
        } catch (MarshalException e) {
            // XMLデータのマッピングに失敗
            log.error("Castor unmarshal failure.", e);
            throw new CastorMarshalException(e);
        } catch (ValidationException e) {
            // XMLデータの検証に失敗
            log.error("Castor validation error.", e);
            throw new CastorValidationException(e);
        }
    }

    /**
     * ストリームからXMLデータを取り出し、オブジェクトに変換する。
     * 
     * @param reader
     *            XMLデータ。文字セットが指定されていない場合、 VMのデフォルト文字セットが使用される。
     * @param out
     *            XMLから変換されたオブジェクト。
     */
    public void unmarshal(Reader reader, Object out) {

        if (reader == null) {
            log.error("Reader is null.");
            throw new IllegalArgumentException("Reader is null.");
        }

        Unmarshaller unmarshaller = createUnmarshaller(out);

        // XML→Object
        try {
            unmarshaller.unmarshal(reader);
        } catch (MarshalException e) {
            // XMLデータのマッピングに失敗
            log.error("Castor unmarshal failure.", e);
            throw new CastorMarshalException(e);
        } catch (ValidationException e) {
            // XMLデータの検証に失敗
            log.error("Castor validation error.", e);
            throw new CastorValidationException(e);
        }
    }

    /**
     * ストリームからXMLデータを取り出し、オブジェクトに変換する。
     * 
     * <p>
     * 引数argCharsetがnullまたは空文字の場合、 InputStreamReaderの文字セットとして属性{@link #charset}の値が使用される。<br>
     * デフォルト設定では、属性{@link #charset}の値は"UTF-8"である。
     * </p>
     * 
     * @param is
     *            XMLデータ。
     * @param argCharset
     *            文字セット。
     * @param out
     *            XMLから変換されたオブジェクト。
     */
    public void unmarshal(InputStream is, String argCharset, Object out) {

        if (is == null) {
            log.error("InputStream is null.");
            throw new IllegalArgumentException("InputStream is null.");
        }

        String charset = argCharset;

        // 文字セットが指定されていない場合は、UTF-8を使用する
        if (charset == null || charset.length() < 1) {
            if (log.isDebugEnabled()) {
                log.debug("Character encoding is not found. " + DEFAULT_CHARSET
                        + " is used.");
            }
            charset = getCharset();
        }

        InputStreamReader isr = null;

        // XML→Object
        try {
            isr = new InputStreamReader(is, charset);
            unmarshal(isr, out);
        } catch (UnsupportedEncodingException e) {
            // サポートしていないエンコーディング
            log.error("Character encoding error.", e);
            throw new CastorUnsupportedEncodingException(e);
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                log.error("Failed to close input stream.", e);
            }
        }
    }

    /**
     * アンマーシャラーを生成する。 Castorマッピング定義の設定を行う。
     * 
     * @param out
     *            出力対象のオブジェクト
     * @return アンマーシャラー
     */
    protected Unmarshaller createUnmarshaller(Object out) {

        if (out == null) {
            log.error("Unmarshal object is null.");
            throw new IllegalArgumentException("Unmarshal object is null.");
        }

        Unmarshaller unmarshaller = new Unmarshaller(out);

        Class mappingClass = out.getClass();

        // Castorマッピング定義
        Mapping mapping = getCastorMapping(mappingClass);

        if (mapping != null) {
            try {
                unmarshaller.setMapping(mapping);
            } catch (MappingException e) {
                // Castorマッピング定義ファイルに問題がある
                log.error("Castor mapping file is invalid. "
                        + "- [root-classpath]/"
                        + getMappingFilePath(mappingClass), e);
                throw new CastorMappingException(e);
            }

            // Castorマッピング定義ファイルをキャッシュする
            if (cache && !mappingFileCache.containsKey(mappingClass)) {
                mappingFileCache.put(mappingClass, mapping);
            }
        }

        // Castorでバリデーション（形式チェック）は行わない
        unmarshaller.setValidation(false);

        // Unmarshallerオプション whitespacePreserve
        unmarshaller.setWhitespacePreserve(preserveWhitespaceAtUnmarshal);

        return unmarshaller;
    }

    /**
     * オブジェクトをXMLに変換し、ストリームに書き込む。
     * 
     * @param in
     *            XMLに変換するオブジェクト
     * @param writer
     *            変換したXMLを書き込むライター
     */
    public void marshal(Object in, Writer writer) {
        Marshaller marshaller = createMarshaller(in, writer);

        // Object→XML
        try {
            marshaller.marshal(in);
        } catch (MarshalException e) {
            // XMLデータのマッピングに失敗
            log.error("Castor marshal failure.", e);
            throw new CastorMarshalException(e);
        } catch (ValidationException e) {
            // XMLデータの検証に失敗
            log.error("Castor validation error.", e);
            throw new CastorValidationException(e);
        }
    }

    /**
     * マーシャラーを生成する。 Castorマッピング定義を設定する。
     * 
     * @param in
     *            XML変換対象のオブジェクト
     * @param writer
     *            出力用ライター
     * @return マーシャラー
     */
    @SuppressWarnings("deprecation")
    protected Marshaller createMarshaller(Object in, Writer writer) {

        if (in == null) {
            log.error("Marshall object is null.");
            throw new IllegalArgumentException("Marshall object is null.");
        }

        if (writer == null) {
            log.error("Writer is null.");
            throw new IllegalArgumentException("Writer is null.");
        }

        Marshaller marshaller = null;

        // マーシャラーの生成
        try {
            XMLSerializerEx serializer = new XMLSerializerEx(writer,
                    new OutputFormat(Method.XML, charset, indenting));
            serializer.setPreserveWhitespace(preserveWhitespaceAtMarshal);

            marshaller = new Marshaller((ContentHandler) serializer);
            if (sharedResolverForMarshaller == null) {
                ClassDescriptorResolver resolver = marshaller.getResolver();
                if (resolver instanceof XMLClassDescriptorResolver) {
                    sharedResolverForMarshaller = (XMLClassDescriptorResolver) resolver;
                }
            } else {
                marshaller.setResolver(sharedResolverForMarshaller);
            }
        } catch (IOException e) {
            log.error("Marshaling io error.", e);
            throw new CastorCreateMarshallerIOException(e);
        }

        Class mappingClass = in.getClass();

        if (!hasSetMappingSetForMarshaller.contains(mappingClass)) {
            // Castorマッピング定義
            Mapping mapping = getCastorMapping(mappingClass);

            if (mapping != null) {
                try {
                    marshaller.setMapping(mapping);
                    hasSetMappingSetForMarshaller.add(mappingClass);
                } catch (MappingException e) {
                    // Castorマッピング定義ファイルに問題がある
                    log.error("Castor mapping file is invalid. "
                            + "- [root-classpath]/"
                            + getMappingFilePath(mappingClass), e);
                    throw new CastorMappingException(e);
                }

                // Castorマッピング定義ファイルをキャッシュする
                if (cache && !mappingFileCache.containsKey(mappingClass)) {
                    mappingFileCache.put(mappingClass, mapping);
                }
            }
        }

        marshaller.setValidation(false);

        // Marshallerオプション suppressXSIType
        marshaller.setSuppressXSIType(suppressXSIType);

        return marshaller;
    }

    /**
     * Castorマッピング定義ファイルを取得する。 {@link #cache}属性がtrueの場合、読み込んだCastorマッピング定義ファイルを
     * キャッシュする。falseの場合、キャッシュは行わない。
     * 
     * @param mappingClass
     *            マッピング対象のクラス
     * @return Castorマッピング定義ファイル
     */
    protected Mapping getCastorMapping(Class mappingClass) {
        Mapping mapping = null;

        // キャッシュがあれば返す
        if (cache) {
            mapping = mappingFileCache.get(mappingClass);
            if (mapping != null) {
                return mapping;
            }
        }

        // Castorマッピング定義ファイルを取得する
        mapping = new Mapping();
        URL mappingURL = getUrl(mappingClass);

        if (mappingURL == null) {
            // Castorマッピング定義ファイルがない場合はnullを返却し、Castorのデフォルトルールを適用する
            return null;
        }

        // マッピングインスタンスにCastorマッピング定義ファイルをロードする
        mapping.loadMapping(new InputSource(mappingURL.toExternalForm()));

        return mapping;
    }

    /**
     * URLを取得する。
     * 
     * @param mappingClass
     *            マッピング対象のクラス
     * @return リソースのURLインスタンス
     */
    protected URL getUrl(Class mappingClass) {
        return Thread.currentThread().getContextClassLoader().getResource(
                getMappingFilePath(mappingClass));
    }

    /**
     * Castorマッピングファイルのパスを取得する。
     * 
     * @param mappingClass
     *            マッピングするクラス。
     * @return Castorマッピングファイルのパス
     */
    protected String getMappingFilePath(Class mappingClass) {
        StringBuilder buf = new StringBuilder();
        buf.append(mappingClass.getName().replace(NESTED_PACKAGE_SEPARATOR,
                NESTED_FOLDER_SEPARATOR));
        buf.append(CASTOR_MAPPINGFILE_SUFFIX);
        return buf.toString();
    }

    /**
     * cacheを取得する。
     * 
     * @return cache属性
     */
    public boolean isCache() {
        return cache;
    }

    /**
     * cacheを設定する。
     * 
     * @param cache
     *            cache属性に設定する値
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * 文字セットを取得する。
     * 
     * @return 文字セット。
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 文字セットを設定する。
     * 
     * @param charset
     *            文字セット。
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * Marshallerオプション suppressXSIType<br>
     * <br>
     * <b>Castorマッピング無設定化時にスキーマバリデータを適用する場合にtrueを設定する。</b><br>
     * <br>
     * falseを設定するとネストしたBeanをMarshallする時に、
     * 生成するXMLドキュメントに「xmlns:xsi」及び「xsi:type」を付与する。<br>
     * このネームスペースが付与されると、Castorマッピング無設定化時に スキーマバリデータを使用した場合、正しくUnmarshallできない。<br>
     * そのため、スキーマバリデータを使用する場合には、このオプションをtrueに 設定する。<br>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>false</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam&gt;
     *         &lt;reserveDetailList 
     *         xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; 
     *         xsi:type=&quot;java:sample.ReserveDetail&quot;&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>true（デフォルト）</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam&gt;
     *         &lt;reserveDetailList&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br>
     * 
     * @param suppressXSIType
     *            設定する suppressXSIType
     */
    public void setSuppressXSIType(boolean suppressXSIType) {
        this.suppressXSIType = suppressXSIType;
    }

    /**
     * Unmarshallerオプション whitespacePreserveを設定する。
     * <p>
     * trueを設定すると、全ての要素について空白を保持 したままアンマーシャルが行われる。<br>
     * falseの場合でも、「xml:space="preserve"」が指定された 要素については、空白を保持したままアンマーシャルされる。
     * 
     * 全電文に対して有効になるため、使用には注意が必要である。 デフォルト値は<code>false</code>。
     * 
     * @param preserveWhitespaceAtUnmarshal
     *            設定する whitespacePreserve
     */
    public void setPreserveWhitespaceAtUnmarshal(
            boolean preserveWhitespaceAtUnmarshal) {
        this.preserveWhitespaceAtUnmarshal = preserveWhitespaceAtUnmarshal;
    }

    /**
     * Marshallerオプション preserveWhitespaceを設定する。
     * <p>
     * trueを設定すると生成するドキュメントのルートノードに 「xml:space="preserve"」を付与する。<br>
     * 全電文に対して有効になるため、使用には注意が必要である。 デフォルト値は<code>true</code>。
     * 
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>false</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam&gt;
     *         &lt;reserveDetailList&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>true（デフォルト）</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam xml:space=&quot;preserve&quot;&gt;
     *         &lt;reserveDetailList&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br> *
     * 
     * @param preserveWhitespaceAtMarshal
     *            設定するpreserveWhitespace
     */
    public void setPreserveWhitespaceAtMarshal(
            boolean preserveWhitespaceAtMarshal) {
        this.preserveWhitespaceAtMarshal = preserveWhitespaceAtMarshal;
    }

    /**
     * Marshallerオプション indentingを設定する<br>
     * <br>
     * trueを設定すると、生成されるxmlがインデントされる。<br>
     * <br>
     * <b>※注意：preserveWhitespaceAtMarshal が <u>false</u> の時のみ有効<b><br>
     * 
     * @param indenting 設定する indenting
     */
    public void setIndenting(boolean indenting) {
        this.indenting = indenting;
    }

}
