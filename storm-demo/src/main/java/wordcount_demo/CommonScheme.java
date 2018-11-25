package wordcount_demo;


import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.nio.ByteBuffer;
import java.util.List;

public class CommonScheme implements Scheme {

    public List<Object> deserialize(byte[] ser) {
        String strMSG = null;
        Values values = new Values();
        try {
            strMSG = new String(ser, "UTF-8");
            values.add(strMSG);
        } catch (Exception e) {
            values.add("DATA-ERROR");
        }
        return values;
    }

    public List<Object> deserialize(ByteBuffer byteBuffer) {
        return deserialize(byteBuffer);
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("key","message");
    }


}
