/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package org.cellang.core.commons.transfer;

import java.util.List;


/**
 * @author wu
 * 
 */
public interface CometManager {

	public String getName();

	public void addListener(CometListener ln);

	public List<Comet> getCometList();
	
	public Comet getComet(String id);

	public Comet getComet(String id, boolean b);
	
}
