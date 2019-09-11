package xs.main;

import javax.print.attribute.standard.MediaName;

import com.validsoft.core.node.api.ProxyServer;

public class demo {
	public static void main(String[] args) {
		com.validsoft.core.node.api.ProxyServer.main(args);
		ProxyServer proxyServer = new ProxyServer();
		proxyServer.init();
		String prop = proxyServer.getProp("aaa", "sdfs");
		System.out.println(prop);
	}

}
