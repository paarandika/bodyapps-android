package fossasia.valentina.bodyapp.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Sync {
	public static String POST(String url, String json) {

		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			// json =
			// "{\"m_id\" : \"4287c1b8-5d8f-4114-8302-266fa5daa38\", \"m_unit\": \"cm\", \"mid_neck_girth\" : \"10\", \"bust__girth\" :\"10\", \"waist_girth\" : \"10\", \"hip_girth\" : \"10\", \"across_back_shoulder_width\" : \"10\", \"shoulder_drop\" : \"10\", \"shoulder_slope_degrees\" :\"10\", \"arm_length\" :\"10\", \"wrist_girth\" : \"10\", \"upper_arm_girth\" : \"10\", \"armscye_girth\" : \"10\", \"height\" : \"10\", \"hip_height\" :\"10\", \"user_id\" : \"53902211dc597a30055594f8\", \"person.name\": \"San\", \"person.emailId\":\"san@hotmail.com\", \"person.dob\": \"12/10/1990\"}";


			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
			System.out.println(result + "result");

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		return result;
	}
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        
        inputStream.close();
        result=result.replaceAll("\"","");
        return result;
        
    }
}
