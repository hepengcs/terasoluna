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

package jp.terasoluna.fw.web.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.fw.web.codelist.CodeBean;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;

import com.mockrunner.mock.web.MockPageContext;
import com.mockrunner.mock.web.MockServletConfig;

/**
 * {@link jp.terasoluna.fw.web.taglib.DefineCodeListTag}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * defineCodeList タグの実装クラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 */
@SuppressWarnings("unused")
public class DefineCodeListTagTest extends SpringTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefineCodeListTagTest.class);
    }

	@Override
	protected void doOnSetUp() throws Exception {
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[]{
           "jp/terasoluna/fw/web/taglib/DefineCodeListTagTest.xml"};
	}


    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (異常系)<br>
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) id:""<br>
     *         (状態) out.print:正常実行<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspTagException<br>
     *                    ラップした例外：null<br>
     *                    メッセージ：id is required.<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    メッセージ：id is required.<br>
     *         
     * <br>
     * インスタンス変数idが空文字の場合、
     * JspTagExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag01() throws Exception {
		// 前処理
    	DefineCodeListTag tag
		    = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);
		
		// idが""場合
		UTUtil.setPrivateField(tag,"id","");
		
		try {
			// テスト実行
			int result = tag.doStartTag();
			// 判定
			fail();
		} catch (JspTagException ex) {
			// 判定
			// メッセージチェック
			assertEquals("id is required.", ex.getMessage());
			// ラップした例外チェック
			assertNull(ex.getCause());
			// ログチェック
			assertTrue(LogUTUtil.checkError("id is required."));
		}
    }

    /**
     * testDoStartTag02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader02<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans=null<br>
     *         (状態) out.print:正常実行<br>
     *         
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) pageContext:pageスコープに<br>
     *                    "testLoader02"=空のArrayListが設定されること<br>
     *         (状態変化) ログ:ログレベル：warn<br>
     *                    メッセージ：
     *             "Codebean is null. CodeListLoader(bean id:testLoader02)"<br>
     *         
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansがnullを返却する場合、
     * pageスコープに指定されたidをキーとして空のArrayListが
     * 設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag02() throws Exception {
		// 前処理
    	DefineCodeListTag tag
		  = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);
        
        // getCodeBeans=null
		UTUtil.setPrivateField(tag,"id","testLoader02");

		// テスト実行
		int result = tag.doStartTag();
		
		// 判定
		// 戻り値
		assertEquals(Tag.EVAL_BODY_INCLUDE,result);
		// ログチェック
		assertTrue(LogUTUtil.checkWarn(
                "Codebean is null. CodeListLoader(bean id:testLoader02)"));
		// pageスコープに"testLoader02"に空のArrayListが設定されること
		ArrayList codeArrayList
            = (ArrayList)pageContext.getAttribute("testLoader02");
		assertEquals(0,codeArrayList.size());	
    }

    /**
     * testDoStartTag03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader03<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={}（空の配列）<br>
     *         (状態) out.print:正常実行<br>
     *         
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) pageContext:pageスコープに<br>
     *                    "testLoader03"=空のCodeBean配列が設定されること<br>
     *         
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansが空の配列を
     * 返却する場合、pageスコープに指定されたidをキーとして空の
     * CodeBean配列が設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag03() throws Exception {
		// 前処理
    	DefineCodeListTag tag
		    = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);
		// getCodeBeans={}（空の配列）
		UTUtil.setPrivateField(tag, "id", "testLoader03");

		// テスト実行
		int result = tag.doStartTag();
		
		// 判定
		// 戻り値
		assertEquals(Tag.EVAL_BODY_INCLUDE, result);
		// pageスコープに"testLoader"=空のCodeBean配列が設定されること
		CodeBean[] codeBeans
            = (CodeBean[])pageContext.getAttribute("testLoader03");
		assertEquals(0,codeBeans.length);	
    }

    /**
     * testDoStartTag04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader04<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id","name")<br>
     *                }<br>
     *         (状態) out.print:正常実行<br>
     *         
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) pageContext:pageスコープに<br>
     *                    "testLoader04"=CodeBean配列<br>
     *                    {CodeBean("id", "name")}（1件）<br>
     *                    が設定されること<br>
     *         
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansが要素数1の配列を
     * 返却する場合、pageスコープに指定されたidを
     * キーとして取得したCodeBean配列が設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag04() throws Exception {
		// 前処理
    	DefineCodeListTag tag
            = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);
        
        // getCodeBeans={CodeBean("id","name") }
		UTUtil.setPrivateField(tag,"id","testLoader04");

		// テスト実行
		int result = tag.doStartTag();
		
		// 判定
		// 戻り値
		assertEquals(Tag.EVAL_BODY_INCLUDE, result);

        //pageスコープに"testLoader"=CodeBean配列が設定されることを確認する
		CodeBean[] codeBeans
            = (CodeBean[]) pageContext	.getAttribute("testLoader04");
		assertEquals(1, codeBeans.length);
		CodeBean codeBean = codeBeans[0];
		assertEquals("id", codeBean.getId());
		assertEquals("name", codeBean.getName());
		
    }

    /**
     * testDoStartTag05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader05<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id1","name1"),<br>
     *                CodeBean("id2","name2"),<br>
     *                CodeBean("id3","name3"),<br>
     *                }<br>
     *         (状態) out.print:正常実行<br>
     *         
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) pageContext:pageスコープに<br>
     *                    "testLoader05"=CodeBean配列<br>
     *                    {<br>
     *                    CodeBean("id1","name1"),<br>
     *                    CodeBean("id2","name2"),<br>
     *                    CodeBean("id3","name3"),<br>
     *                    }（3件）<br>
     *                    が設定されること<br>
     *         
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansが複数の要素の配列を
     * 返却する場合、pageスコープに指定されたidをキーとして
     * 取得したCodeBean配列が設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag05() throws Exception {
		// 前処理
    	DefineCodeListTag tag
            = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        //getCodeBeans={
        //  CodeBean("id1","name1"),
        //  CodeBean("id2","name2"),
        //  CodeBean("id3","name3"),
        //}
		UTUtil.setPrivateField(tag,"id","testLoader05");

		// テスト実行
		int result = tag.doStartTag();

		// 判定
		// 戻り値
		assertEquals(Tag.EVAL_BODY_INCLUDE, result);
		
        //pageスコープに"testLoader"=CodeBean配列が設定されることを確認する
		CodeBean[] codeBeans
            = (CodeBean[])pageContext.getAttribute("testLoader05");
		assertEquals(3,codeBeans.length);
		for (int i = 0; i < codeBeans.length; i++) {
			CodeBean codeBean = codeBeans[i];
			assertEquals("id" + (i + 1), codeBean.getId());
			assertEquals("name" + (i + 1), codeBean.getName());
		}
    }

    /**
     * testDoStartTag06()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) id:testLoader06<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                CodeListLoaderを実装しないクラスのインスタンス。<br>
     *         (状態) out.print:正常実行<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspTagException<br>
     *                    ラップした例外：ClassCastException<br>
     *                    メッセージ：
     *                bean id:testLoader06 is not instance of CodeListLoader<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    メッセージ：
     *                bean id:testLoader06 is not instance of CodeListLoader<br>
     *         
     * <br>
     * DIコンテナから取得したインスタンスがCodeListLoaderを実装したクラスの
     * インスタンスではない場合、JspTagExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag06() throws Exception {
		// 前処理
    	DefineCodeListTag tag
		  = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);

        MockPageContext pageContext =
        	(MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

		// CodeListLoaderを実装しないクラスのインスタンス。
		UTUtil.setPrivateField(tag, "id", "testLoader06");
		try {
			// テスト実行
			int result = tag.doStartTag();
			fail();
		} catch (JspTagException ex) {
			// 判定
			// ラップした例外チェック
			assertEquals(ClassCastException.class.getName(),
					ex.getRootCause().getClass().getName());
			// メッセージキーチェック
			assertEquals(
					"bean id:testLoader06 is not instance of CodeListLoader.",
					ex.getMessage());
			// ログチェック
			assertTrue(LogUTUtil.checkError(
                    "bean id:testLoader06 is not instance of CodeListLoader."));
		}
    }

    /**
     * testDoEndTag01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_PAGE<br>
     *         
     * <br>
     * TagSupport.EVAL_PAGEが返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoEndTag01() throws Exception {
		// 前処理
    	DefineCodeListTag tag
		  = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);

		// テスト実施
        int result = tag.doEndTag();

        // テスト結果確認
		assertEquals(Tag.EVAL_PAGE, result);
    }

    /**
     * testRelease01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:"id"<br>
     *         
     * <br>
     * 期待値：(状態変化) id:null<br>
     *         
     * <br>
     * idがnullになることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRelease01() throws Exception {
		// 前処理
    	DefineCodeListTag tag
		  = (DefineCodeListTag) TagUTUtil.create(DefineCodeListTag.class);
        UTUtil.setPrivateField(tag, "id", "id");

		// テスト実施
        tag.release();

        // テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "id"));
    }
}
