/**
 * Jun 25, 2012
 */
package org.cellang.clwt.commons.client.mvc.widget;

/**
 * @author wuzhen
 * 
 */
public interface StringEditorI extends EditorI<String> {

	public static final String PK_TEXAREA = "isTextArea";
	
	public static final String PK_LENGTH_LIMIT = "lengthLimit";
	@Deprecated //create password editor.
	public static final String PK_ISPASSWORD = "isPassword";
}
