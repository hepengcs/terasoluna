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

package jp.terasoluna.fw.oxm.xsd.xerces;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.exception.NamespaceNotFoundException;
import jp.terasoluna.fw.oxm.xsd.exception.NamespaceNotUniqueException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserIOException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserSAXException;
import jp.terasoluna.fw.oxm.xsd.exception.PropertyIOException;
import jp.terasoluna.fw.oxm.xsd.exception.SchemaFileNotFoundException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * XMLデータの形式チェックを行うSchemaValidator実装クラス。
 * <p>
 * 形式チェックにはスキーマ定義ファイルを使用する。 本クラスはシングルトンとして使用すること。
 * </p>
 * <p>
 * スキーマ定義ファイルは変換を行うオブジェクトのクラスと 同じパッケージ、 同じ名前、拡張子”.xsd”で配置すること。 <br>
 * 例）XML変換対象のオブジェクトが「sample.SampleBean」クラスの場合、
 * スキーマ定義ファイルはクラスパス上の「sample/SampleBean.xsd」 ファイルとなる。
 * </p>
 * <p>
 * XMLのパースにはDOMパーサが必要となる。 TERASOLUNAでは詳細なエラー情報を取得するために、 拡張した{@link jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx}、
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx}を利用する。
 * </p>
 * 
 * <hr>
 * <h4>名前空間の設定</h4>
 * <p>
 * 本クラスは、スキーマ定義に名前空間を使用することができる。 名前空間を使用する場合、{@link #namespace}属性にtrueを設定する必要がある。
 * デフォルトはfalseで、名前空間を使用しない設定になっている。 名前空間を使用する場合、スキーマ定義ファイルのキャッシュを行うことができる。
 * 名前空間を使用するためには、以下の設定が必要である。
 * <ul>
 * <li>プロパティファイル(namespaces.properties)に、 XML変換対象のクラス名と使用する名前空間の対応付けを記述する。
 * （詳細は下記の設定例を参照）</li>
 * <li>プロパティファイル(namespaces.properties)をクラスパス上に置く。</li>
 * <li>本クラスのインスタンスを生成（new）する。</li>
 * <li>インスタンスの{@link #namespace}属性にtrueを設定する。</li>
 * <li>スキーマ定義のキャッシュ設定をする。デフォルトはキャッシュが有効である。 キャッシュを無効にする場合、インスタンスの{@link #cache}属性をfalseに設定する。</li>
 * <li>インスタンスの{@link #initNamespaceProperties()}メソッドを呼び出して、 プロパティファイルを読み込む。</li>
 * </ul>
 * 
 * <br>
 * 【名前空間のプロパティファイル（nemaspace.properties）の設定例】<br>
 * 例）XML変換対象のオブジェクトが「sample.SampleBean」クラス、名前空間が
 * 「http://xxx.co.jp/sample/samplebean」の場合、 プロパティファイルに下記の設定を行う。<br>
 * <br>
 * jp.terasoluna.sample2.dto.SumParam.Namespace =
 * http://xxx.co.jp/sample/samplebean
 * </p>
 * 
 * <hr>
 * <p>
 * <strong>名前空間を使用しない場合のスキーマファイル設定例</strong>
 * </p>
 * 
 * <p>
 * 【形式チェック対象のXMLデータ】 <code><pre>
 *   &lt;sample-dto&gt;
 *     &lt;user-id&gt;15&lt;/user-id&gt;
 *     &lt;user-name&gt;user1&lt;/user-name&gt;
 *     &lt;item&gt;
 *       &lt;id&gt;100&lt;/id&gt;
 *       &lt;name&gt;item1&lt;/name&gt;
 *       &lt;price&gt;1000&lt;/price&gt;
 *     &lt;/item&gt;
 *     &lt;item&gt;
 *       &lt;id&gt;101&lt;/id&gt;
 *       &lt;name&gt;item2&lt;/name&gt;
 *       &lt;price&gt;2000&lt;/price&gt;
 *     &lt;/item&gt;
 *   &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * 【スキーマ定義ファイルの設定例】<br>
 * <code><pre>
 *   &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *   &lt;xs:schema xmlns:xs=&quot;http://www.w3.org/2001/XMLSchema&quot;&gt;
 *     &lt;xs:element name=&quot;sample-dto&quot; type=&quot;sample-dto-type&quot;/&gt;
 *     &lt;xs:complexType name=&quot;sample-dto-type&quot;&gt;
 *       &lt;xs:sequence&gt;
 *         &lt;xs:element name=&quot;user-id&quot; type=&quot;xs:int&quot; /&gt;
 *         &lt;xs:element name=&quot;user-name&quot; type=&quot;xs:string&quot; /&gt;
 *         &lt;xs:element name=&quot;item&quot; type=&quot;item-type&quot; minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot; /&gt;
 *       &lt;/xs:sequence&gt;
 *     &lt;/xs:complexType&gt;
 *     &lt;xs:complexType name=&quot;item-type&quot;&gt;
 *       &lt;xs:sequence&gt;
 *          &lt;xs:element name=&quot;id&quot; type=&quot;xs:int&quot; /&gt;
 *         &lt;xs:element name=&quot;name&quot; type=&quot;xs:string&quot; /&gt;
 *         &lt;xs:element name=&quot;price&quot; type=&quot;xs:int&quot; /&gt;
 *       &lt;/xs:sequence&gt;
 *     &lt;/xs:complexType&gt;
 *   &lt;/xs:schema&gt;
 * </pre></code>
 * </p>
 * 
 * <hr>
 * <p>
 * <strong>名前空間を使用する場合のスキーマファイル設定例</strong>
 * </p>
 * 
 * 【形式チェック対象のXMLデータサンプル】
 * 
 * &lt;sample-dto xmlns=&quot;http://xxx.co.jp/sample/samplebean&quot;&gt;
 * &lt;user-id&gt;15&lt;/user-id&gt; &lt;user-name&gt;user1&lt;/user-name&gt;
 * &lt;item&gt; &lt;id&gt;100&lt;/id&gt; &lt;name&gt;item1&lt;/name&gt;
 * &lt;price&gt;1000&lt;/price&gt; &lt;/item&gt; &lt;item&gt;
 * &lt;id&gt;101&lt;/id&gt; &lt;name&gt;item2&lt;/name&gt;
 * &lt;price&gt;2000&lt;/price&gt; &lt;/item&gt; &lt;/sample-dto&gt;
 * 
 * 【スキーマ定義ファイルの設定サンプル】
 * 
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;xs:schema xmlns:xs=&quot;http://www.w3.org/2001/XMLSchema&quot;
 * xmlns:tns=&quot;http://xxx.co.jp/sample/samplebean&quot;
 * targetNamespace=&quot;http://xxx.co.jp/sample/samplebean&quot;
 * elementFormDefault=&quot;qualified&quot;&gt; &lt;xs:element
 * name=&quot;sample-dto&quot; type=&quot;tns:sample-dto-type&quot;/&gt;
 * &lt;xs:complexType name=&quot;sample-dto-type&quot;&gt; &lt;xs:sequence&gt;
 * &lt;xs:element name=&quot;user-id&quot; type=&quot;xs:int&quot;/&gt;
 * &lt;xs:element name=&quot;user-name&quot; type=&quot;xs:string&quot;/&gt;
 * &lt;xs:element name=&quot;item&quot; type=&quot;tns:item-type&quot;
 * minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot;/&gt;
 * &lt;/xs:sequence&gt; &lt;/xs:complexType&gt; &lt;xs:complexType
 * name=&quot;item-type&quot;&gt; &lt;xs:sequence&gt; &lt;xs:element
 * name=&quot;id&quot; type=&quot;xs:int&quot;/&gt; &lt;xs:element
 * name=&quot;name&quot; type=&quot;xs:string&quot;/&gt; &lt;xs:element
 * name=&quot;price&quot; type=&quot;xs:int&quot;/&gt; &lt;/xs:sequence&gt;
 * &lt;/xs:complexType&gt; &lt;/xs:schema&gt;
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * @see org.apache.xerces.util.XMLGrammarPoolImpl
 * 
 */
public class SchemaValidatorImpl implements SchemaValidator {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(SchemaValidatorImpl.class);

    /**
     * パッケージのセパレータ。
     */
    public static final String NESTED_PACKAGE_SEPARATOR = ".";

    /**
     * フォルダのセパレータ。
     */
    public static final String NESTED_FOLDER_SEPARATOR = "/";

    /**
     * スキーマ定義ファイルのサフィックス。
     */
    public static final String XSD_FILE_SUFFIX = ".xsd";

    /**
     * プロパティファイルからネームスペース名を取得するキーの接尾辞。
     */
    private static final String NAME_SPACE_SUFFIX = ".Namespace";

    /**
     * クラス名と名前空間のマッピングを定義したプロパティファイル名。
     */
    protected String namespacePropertyFileName = "namespaces.properties";

    /**
     * スキーマ定義のキャッシュ使用設定。 ※名前空間を使用する場合のみ、キャッシュが行なわれる。
     */
    protected boolean cache = true;

    /**
     * 名前空間の使用設定。
     */
    protected boolean namespace = false;

    /**
     * 名前空間の文法プール。
     */
    protected XMLGrammarPool grammarPool = new XMLGrammarPoolImpl();

    /**
     * クラス名と名前空間のマッピングを定義したプロパティ。
     */
    protected Properties namespaceProperties = null;

    /**
     * 名前空間のチェック設定。
     */
    protected boolean namespaceCheck = true;

    /**
     * スキーマ定義のキャッシュ使用設定を行う。
     * 
     * @param cache
     *            スキーマ定義ファイルのキャッシュを行う場合、true
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * 名前空間の使用設定を行う。
     * 
     * @param namespace
     *            名前空間を使用する場合、true
     */
    public void setNamespace(boolean namespace) {
        this.namespace = namespace;
    }

    /**
     * 名前空間のチェック設定を行う。
     * 
     * @param namespaceCheck
     *            名前空間をチェックする場合、true
     */
    public void setNamespaceCheck(boolean namespaceCheck) {
        this.namespaceCheck = namespaceCheck;
    }

    /**
     * クラス名と名前空間のマッピングを定義したプロパティを設定する
     * 
     * @param namespaceProperties
     *            クラス名と名前空間のマッピングを定義したプロパティ
     */
    public void setNamespaceProperties(Properties namespaceProperties) {
        this.namespaceProperties = namespaceProperties;
    }

    /**
     * クラス名と名前空間のマッピングを定義したプロパティファイル名を設定する。
     * 
     * @param namespacePropertyFileName
     *            クラス名と名前空間のマッピングを定義したプロパティファイル名
     */
    public void setNamespacePropertyFileName(String namespacePropertyFileName) {
        this.namespacePropertyFileName = namespacePropertyFileName;
    }

    /**
     * 初期化時に名前空間をプロパティに設定する。
     */
    public void initNamespaceProperties() {
        loadNamespaceProperties();

        if (this.namespaceCheck) {
            checkNamespaceProperties();
        }
    }

    /**
     * 名前空間が定義されたプロパティファイルを読み込み、属性にセットする。
     */
    protected void loadNamespaceProperties() {
        // propertyNameがnullまたは空文字の場合、以後の処理を行わない。
        if (namespacePropertyFileName == null
                || "".equals(namespacePropertyFileName)) {
            return;
        }

        // カレントスレッドのコンテキストクラスローダを使用すると
        // WEB-INF/classesのプロパティファイルを読むことができない場合がある。
        // だがJNLPでリソースを取得するには、メインスレッドのコンテキスト
        // クラスローダを利用しなければならないため両方を併用する。
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(namespacePropertyFileName);
        if (is == null) {
            is = this.getClass().getResourceAsStream(
                    "/" + namespacePropertyFileName);
            if (is == null) {
                log.warn("Can not find property-file ["
                        + namespacePropertyFileName + "]");
                return;
            }
        }

        this.namespaceProperties = new Properties();

        try {
            this.namespaceProperties.load(is);
        } catch (IOException e) {
            log.error("Can not read property-file ["
                    + namespacePropertyFileName);
            throw new PropertyIOException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("Failed to close inputStream.", e);
            }
        }
    }

    /**
     * 名前空間が重複していないかのチェックを行う。
     */
    protected void checkNamespaceProperties() {
        StringBuilder logStr = new StringBuilder();
        // namespacePropertiesがnullまたは空の場合、以後の処理を行わない。
        if (namespaceProperties == null || namespaceProperties.isEmpty()) {
            return;
        }

        List<String> namespacePropertiesList = new ArrayList<String>();
        for (Object namespaceKey : namespaceProperties.keySet()) {
            String namespaceValue = namespaceProperties
                    .getProperty((String) namespaceKey);
            // 名前空間が重複している場合、エラーログの出力を行い、例外をスローする。
            if (namespacePropertiesList.contains(namespaceValue)) {
                logStr.setLength(0);
                logStr.append("Namespace name [");
                logStr.append(namespaceValue);
                logStr.append("] is not unique. ");
                logStr.append("Namespace must be unique. ");
                logStr.append("(key = [");
                logStr.append(namespaceKey);
                logStr.append("])");
                log.error(logStr.toString());
                throw new NamespaceNotUniqueException();
            }
            namespacePropertiesList.add(namespaceValue);
        }
    }

    /**
     * XMLデータの形式チェックを行うメソッド。
     * <p>
     * XMLデータをDOMツリーに変換する際に、XMLスキーマによる 形式チェック（妥当性検証）を実行する。<br>
     * 形式チェックには、スキーマ定義ファイルを用いる。
     * </p>
     * <p>
     * 正常に形式チェックが終了した場合、DOMツリーを返却する。<br>
     * ユーザの入力が原因と考えられるデータ型のエラーが発生した場合、引数のerrorMessagesにエラー情報を格納して、nullを返却する。<br>
     * それ以外のエラーに関しては、OXMappingExceptionのサブクラスにラップしてスローする。
     * </p>
     * 
     * @param in
     *            XMLデータ
     * @param object
     *            変換対象のオブジェクト
     * @param errorMessages
     *            エラーメッセージが格納されるインスタンス
     * @return DOMツリー
     */
    public Document validate(InputStream in, Object object,
            ErrorMessages errorMessages) {
        if (in == null) {
            log.error("InputStream is null.");
            throw new IllegalArgumentException("InputStream is null.");
        }
        if (errorMessages == null) {
            log.error("ErrorMessages is null.");
            throw new IllegalArgumentException("ErrorMessages is null.");
        }

        // DOMパーサの作成
        DOMParser parser = null;
        try {
            parser = createDomParser(object);
            setCommonParserProperty(parser, errorMessages);
            setCommonParserFeature(parser);
        } catch (SAXNotRecognizedException e) {
            // 認識できないキーが設定された場合にスローされる例外
            log.error("Schema property error.", e);
            throw new ParserNotSupportedException(e);
        } catch (SAXNotSupportedException e) {
            // サポートされていない値が設定された場合にスローされる例外
            log.error("Schema property error.", e);
            throw new ParserNotSupportedException(e);
        }

        // パース
        try {
            parser.parse(new InputSource(in));
        } catch (SAXException e) {
            log.error("Schema parse error.", e);
            throw new ParserSAXException(e);
        } catch (IOException e) {
            log.error("Schema io error.", e);
            throw new ParserIOException(e);
        }

        if (errorMessages.hasErrorMessage()) {
            return null;
        }
        return parser.getDocument();
    }

    /**
     * DOMパーサを生成する。
     * 
     * @param object
     *            パース対象のオブジェクト
     * @return DOMパーサ
     * @throws SAXNotSupportedException
     *             認識できないキーが設定された場合にスローされる例外
     * @throws SAXNotRecognizedException
     *             サポートされていない値が設定された場合にスローされる例外
     */
    protected DOMParser createDomParser(Object object)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        DOMParser parser = new DOMParser(createXmlParserConfiguration());

        // スキーマ定義ファイルのURL
        URL schemaURL = getUrl(object);
        if (schemaURL == null) {
            log.error("Schema file is not found. Set schema file in "
                    + "[root-classpath]/" + getSchemaFilePath(object));
            throw new SchemaFileNotFoundException();
        }

        // スキーマ定義ファイルのロケーション設定
        if (namespace) {
            StringBuilder key = new StringBuilder();
            key.append(Constants.XERCES_PROPERTY_PREFIX);
            key.append(Constants.SCHEMA_LOCATION);

            StringBuilder location = new StringBuilder();
            location.append(getNamespaceName(object));
            location.append(" ");
            location.append(schemaURL.toExternalForm());

            parser.setProperty(key.toString(), location.toString());
        } else {
            parser.setProperty(Constants.XERCES_PROPERTY_PREFIX
                    + Constants.SCHEMA_NONS_LOCATION, schemaURL
                    .toExternalForm());
        }
        return parser;
    }

    /**
     * XMLParserConfigurationを生成する。 名前空間とキャッシュの使用を有効にしている場合、
     * スキーマファイルのキャッシュを利用する。
     * 
     * @return XMLParserConfiguration XMLパーサの設定を保持するオブジェクト
     */
    protected XMLParserConfiguration createXmlParserConfiguration() {
        if (namespace && cache) {
            // 名前空間の文法プールを設定することでキャッシュを有効にする
            return new XML11ConfigurationEx(grammarPool);
        }
        return new XML11ConfigurationEx();
    }

    /**
     * パーサ共通のPropertyを設定する。
     * 
     * @param parser
     *            DOMパーサ
     * @param errorMessages
     *            エラー
     * @throws SAXNotRecognizedException
     *             認識できないキーが設定された場合にスローされる例外
     * @throws SAXNotSupportedException
     *             サポートされていない値が設定された場合にスローされる例外
     */
    protected void setCommonParserProperty(DOMParser parser,
            ErrorMessages errorMessages) throws SAXNotRecognizedException,
            SAXNotSupportedException {
        // パース時に発生したエラーを処理するインスタンス
        parser.setProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY, new XMLErrorReporterEx(
                errorMessages));
    }

    /**
     * パーサ共通のFeatureを設定する
     * 
     * @param parser
     *            DOMパーサ
     * @throws SAXNotRecognizedException
     *             認識できないキーが設定された場合にスローされる例外
     * @throws SAXNotSupportedException
     *             サポートされていない値が設定された場合にスローされる例外
     */
    protected void setCommonParserFeature(DOMParser parser)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        // すべての妥当性検査エラーを通知する
        parser.setFeature(Constants.SAX_FEATURE_PREFIX
                + Constants.VALIDATION_FEATURE, true);

        // 形式チェックにスキーマ定義ファイルを使用する設定
        parser.setFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE, true);
    }

    /**
     * URLを取得する。
     * <p>
     * 引数のオブジェクトと同一のクラスパスから、定義ファイルを取得する
     * </p>
     * 
     * @param object
     *            オブジェクト
     * @return リソースのURLインスタンス
     */
    protected URL getUrl(Object object) {
        return Thread.currentThread().getContextClassLoader().getResource(
                getSchemaFilePath(object));
    }

    /**
     * スキーマファイルのパスを取得する。
     * 
     * @param object
     *            スキーマチェック対象のオブジェクト
     * @return スキーマファイルのパス
     */
    protected String getSchemaFilePath(Object object) {
        if (object == null) {
            log.error("Argument is null.");
            throw new IllegalArgumentException("Argument is null.");
        }

        StringBuilder retStr = new StringBuilder();
        retStr.append(object.getClass().getName().replace(
                NESTED_PACKAGE_SEPARATOR, NESTED_FOLDER_SEPARATOR));
        retStr.append(XSD_FILE_SUFFIX);
        return retStr.toString();
    }

    /**
     * プロパティファイルから名前空間を取得する。 名前空間を使用しない場合、nullを返す。 必要な名前空間が設定されていない場合、実行時例外を投げる。
     * 
     * @param object
     *            オブジェクト
     * @return リソースのURLインスタンス
     */
    protected String getNamespaceName(Object object) {
        if (object == null) {
            log.error("Argument is null.");
            throw new IllegalArgumentException("Argument is null.");
        }

        // 名前空間を使わない
        if (!namespace) {
            return null;
        }

        // 名前空間を定義するファイルがない
        if (this.namespaceProperties == null) {
            String message = "Namespace property is not set. " + "Put "
                    + namespacePropertyFileName + " file on your classpath, "
                    + "and call SchemaValidatorImpl initNamespaceProperties() "
                    + "after instanciate.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // 名前空間を取得する
        StringBuilder namespaceKey = new StringBuilder(object.getClass()
                .getName());
        namespaceKey.append(NAME_SPACE_SUFFIX);
        String namespaceName = namespaceProperties.getProperty(namespaceKey
                .toString());

        if (namespaceName == null) {
            log.error("Schema namespace is not found. Set namespace key - "
                    + namespaceKey.toString() + " in "
                    + namespacePropertyFileName + " file.");
            throw new NamespaceNotFoundException();
        }
        return namespaceName;
    }
}
