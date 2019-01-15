package demo.manager.properties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;

public class Path {

	public static final InputStream getTopoResource(String s) {
		// URL url=Path.class.getClassLoader().getResource(s);
		// String path = URLDecoder.decode(url.getFile(), "utf-8");
		// return path;
		InputStream stream = Path.class.getClassLoader().getResourceAsStream(s);
		return stream;
	}

	public static final String getResource() throws FileNotFoundException, IOException {
		URL url = Path.class.getClassLoader().getResource("");
		String path = URLDecoder.decode(url.getFile(), "utf-8");
		return path;

	}

	// 仅main包内可用，test内不可用,topo不可用
	public static void main(String[] args) throws IOException {
		// BufferedReader reader=new BufferedReader(new
		// FileReader(Path.getResource()+"/ep_xfk/config_SN/storm-supervisor-config/business.xml"));
		// String reade=null;
		// int i=0;
		// while((reade=reader.readLine())!=null){
		// System.out.println("line:"+i+++" "+reade);
		// }
		// reader.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Path.getTopoResource("ep_xfk/config_SN/storm-supervisor-config/business.xml"), "utf-8"));
		String reade = null;
		int i = 0;
		while ((reade = reader.readLine()) != null) {
			System.out.println("line:" + i++ + "	" + reade);
		}
		reader.close();
	}
}
