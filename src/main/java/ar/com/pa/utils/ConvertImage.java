package ar.com.pa.utils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class ConvertImage {

	public static String encodeToBase64() throws IOException {
		String filePath = "C:\\Users\\GUSTAVO\\Desktop\\BDS\\sinConvertir.jpg";
		byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		System.out.println(encodedString);
		return encodedString;
    }
	
	public static void decodeBase64(String encodedString) throws IOException {
		 String outPutFileName = "C:\\Users\\GUSTAVO\\Desktop\\BDS\\convertida.jpg";
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		FileUtils.writeByteArrayToFile(new File(outPutFileName), decodedBytes);

    }
}
