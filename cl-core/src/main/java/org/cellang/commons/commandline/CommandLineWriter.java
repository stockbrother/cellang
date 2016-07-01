/**
 * Dec 19, 2013
 */
package org.cellang.commons.commandline;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface CommandLineWriter {
	
	public CommandLineWriter writeLine();	
	
	public CommandLineWriter write(String str);
	
	public CommandLineWriter writeLine(String line);
	
}
