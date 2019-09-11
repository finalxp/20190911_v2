package cn.xs.log.bancom;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "KafkaAppender", category = "Core", elementType = "appender", printObject = true)
public class KafkaAppender extends AbstractAppender {
	private String topic;
	private String services;
	private Producer<String, byte[]> kafkaProducer;

	/* 构造函数 */
	public KafkaAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, String topic, String services) {
		super(name, filter, layout, ignoreExceptions);
		this.topic = topic;
		this.services = services;
		kafkaProducer = createKafkaProducer();
	}

	@Override
	public void append(LogEvent event) {
		final byte[] bytes = getLayout().toByteArray(event);
		System.out.println("push message [" + new String(bytes).replace("\r\n", "") + "] to kafka.");
		ProducerRecord<String, byte[]> message = new ProducerRecord<String, byte[]>(topic, bytes);
		try {
			kafkaProducer.send(message).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/* 接收配置文件中的参数 */
	@PluginFactory
	public static KafkaAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("topic") String topic, @PluginAttribute("services") String services,
			@PluginElement("Filter") final Filter filter, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
		if (name == null) {
			LOGGER.error("no name defined in conf.");
			return null;
		}
		if (topic == null) {
			LOGGER.error("no topic defined in conf.");
			return null;
		}
		if (services == null) {
			LOGGER.error("no services defined in conf.");
			return null;
		}
		if (layout == null) {
			layout = PatternLayout.createDefaultLayout();
		}

		return new KafkaAppender(name, filter, layout, ignoreExceptions, topic, services);
	}

	public synchronized Producer<String, byte[]> createKafkaProducer() {
		if (kafkaProducer == null) {
			kafkaProducer = new KafkaProducer<String, byte[]>(assembleProperties());
		}

		return kafkaProducer;
	}

	public Properties assembleProperties() {
		Properties props = new Properties();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, services);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);// a batch size of zero will disable batching entirely
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);// send message without delay
		props.put(ProducerConfig.ACKS_CONFIG, "all");// 对应partition的leader写到本地后即返回成功。极端情况下，可能导致失败
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

		return props;
	}

}