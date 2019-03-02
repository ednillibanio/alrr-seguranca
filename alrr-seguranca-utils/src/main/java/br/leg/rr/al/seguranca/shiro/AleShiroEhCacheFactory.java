/**
 *
 */
package br.leg.rr.al.seguranca.shiro;

import net.sf.ehcache.CacheManager;
import org.apache.shiro.util.Factory;

/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
public class AleShiroEhCacheFactory implements Factory<CacheManager> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.util.Factory#getInstance()
	 */
	@Override
	public CacheManager getInstance() {
		return CacheManager.create();
	}

}
