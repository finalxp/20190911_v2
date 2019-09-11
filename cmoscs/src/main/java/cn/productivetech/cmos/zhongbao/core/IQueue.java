package cn.productivetech.cmos.zhongbao.core;

import java.util.List;

/**
 * 消息队列实现方式，生产者产生消息，消费者进行消费
 * @author Administrator
 * @created 2019-04-19
 * @param <T>
 */
public interface IQueue<T> {

	/**
	 * 相当于生产者,将消息T全部添加到消息队列中
	 * @param date
	 */
	public void put(List<T> data);
	
	/**
	 * 相当于消费者，按照先进先出的阻塞方式读取队列中的消息
	 */
	public void pop();

	public int size();


}
