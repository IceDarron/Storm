package wordcount_kafka.scheme;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

public class SchemeWordCount implements Scheme {

    @Override
    public List<Object> deserialize(ByteBuffer buffer) {
        String msg = getString(buffer);
        return new Values(msg);
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("msg");
    }

    public static final String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
