package cn.net.dingwei.sup;


import android.util.Log;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 压缩解压缩包类
 *
 * @author rockyzheng
 *
 */
public class ZipUtil {
	/**
	 * 压缩数据
	 *
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] Compress(byte[] object) throws IOException {

		byte[] data = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ZOutputStream zOut = new ZOutputStream(out,JZlib.Z_BEST_COMPRESSION);
			DataOutputStream objOut = new DataOutputStream(zOut);
			objOut.write(object);
			objOut.flush();
			zOut.close();
			data = out.toByteArray();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	/**
	 * 解压被压缩的数据
	 *
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] UnCompress(byte[] object) {
		byte[]	tem_data = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(object);
			ZInputStream zIn = new ZInputStream(in);
			DataInputStream objIn = new DataInputStream(zIn);
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len1 = -1;
			while ((len1 = objIn.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len1);
			}
			tem_data=outSteam.toByteArray();

			outSteam.close();
			objIn.close();
			zIn.close();
			in.close();
            tem_data = genNewByte(tem_data);
			Log.e("mylog", "解压的数据="+new String(tem_data));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tem_data;
	}

    private static byte[] genNewByte(byte[] object) {
        byte[] result = null;
        String tempStr = new String(object);
        try {
            JSONObject jsonObject = new JSONObject(tempStr);
            String vc = jsonObject.getString("vc");
			Log.i("123", "接口VC：：: "+vc);
//			e2d5d7427a93bbc62ab11acbaff79824 -命题库
//			a57d18eda26f4a1f58692a9dd530d977 -医护360
//			a880010768735403c5873773d1680f78 -西藏公考
			if("a57d18eda26f4a1f58692a9dd530d977".equals(vc)) {
                jsonObject.remove("vc");
                result = jsonObject.toString().getBytes();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

	public static String UnZipString(byte[] responseBody) {
		Log.i("123", "开始解压：：：：：：: "+responseBody.length);
		byte[] MyresponseBody2 = new byte[responseBody.length];
		System.arraycopy(responseBody, 0, MyresponseBody2, 0,
				responseBody.length);
		MyresponseBody2[0] = responseBody[responseBody.length - 2];
		MyresponseBody2[MyresponseBody2.length - 2] = responseBody[0];
		byte[] unCompressed_temp = UnCompress(MyresponseBody2);
        String result = "";
        if(unCompressed_temp != null) {
            result = new String(unCompressed_temp);
        }
		return result;
	}
}