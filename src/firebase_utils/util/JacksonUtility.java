package firebase_utils.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import firebase_utils.error.JacksonUtilityException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JacksonUtility {
	protected static final Logger LOGGER = Logger.getRootLogger();

	public JacksonUtility() {
	}

	public static String GET_JSON_STRING_FROM_MAP(Map<String, Object> dataMap) throws JacksonUtilityException {
		if (dataMap != null && !dataMap.isEmpty()) {
			StringWriter writer = new StringWriter();

			try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(writer, dataMap);
			} catch (Throwable var4) {
				String msg = dataMap.toString();
				LOGGER.warn(msg);
				throw new JacksonUtilityException(msg);
			}

			return writer.toString();
		} else {
			LOGGER.info("cannot convert data from map into json when map is null/empty");
			return new String();
		}
	}

	public static Map<String, Object> GET_JSON_STRING_AS_MAP(String jsonResponse) throws JacksonUtilityException, IOException {
		if (jsonResponse != null && !jsonResponse.trim().isEmpty()) {
			jsonResponse = jsonResponse.trim();
			Object result = null;

			ObjectMapper mapper = new ObjectMapper();
			Object o = mapper.readValue(jsonResponse, Object.class);
			if (o instanceof Map) {
				result = (Map) o;
			}

			if (result == null) {
				result = new LinkedHashMap();
			}

			return (Map)result;
		} else {
			LOGGER.warn(jsonResponse);
			return new HashMap();
		}
	}
}






