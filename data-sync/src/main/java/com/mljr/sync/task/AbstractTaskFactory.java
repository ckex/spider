/**
 * 
 */
package com.mljr.sync.task;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午1:11:52
 *
 */
public abstract class AbstractTaskFactory {

	public abstract MobileTask createMobileTask();

	public abstract GPSTask createGPSTask();

}
