package com.konasl.tokengeneration;

import com.konasl.tokengeneration.util.DateUtil;
import com.konasl.tokengeneration.util.TranTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
public class GenerateToken {
    private static String secretKey = null;
    private static String projectId = null;
    private static final String CURRENT_TIME_PATTERN = "yyyyMMddHHmmss";

    public void generateTranToken() throws Exception {

        readFromInputStream();

        LocalDateTime localDateTime = DateUtil.getCurrentDateInUTC();
        String currentTime = DateUtil.getStringFromLocalDateTime(localDateTime, CURRENT_TIME_PATTERN);

        String randomString = generateRandomString();
        String correlationId = currentTime + "-" + randomString;

        String message = (projectId + "-" + currentTime + "-" + correlationId.trim());
        String tranToken = TranTokenUtil.generateToken(currentTime, secretKey, message);

        log.info("projectId: {}", projectId);
        log.info("secretKey: {}", secretKey);
        log.info("Message: {}", message);
        log.info("randomString: {}", randomString);
        log.info("transTime: {}", currentTime);
        log.info("correlationId: {}", correlationId);
        log.info("tranToken: {}", tranToken);
        String printInfo = "transTime: " + currentTime + "\n" +
                "CorrelationId: " + correlationId + "\n" +
                "TranToken: " + tranToken;
        String fileName = "output.txt";

        try {
            writeToFile(fileName, printInfo);
            log.info("Generated token has been written to {}", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    private static void readFromInputStream()
            throws IOException {
        InputStream inputStream = new FileInputStream("./input.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitString = line.split("=");
                if (splitString[0].contains("secretKey")) {
                    secretKey = splitString[1].trim();
                }
                if (splitString[0].contains("projectId")) {
                    projectId = splitString[1].trim();
                }
            }
        }
    }

    private static String generateRandomString() {
        int length = 7;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length / 2];

        secureRandom.nextBytes(randomBytes);

        BigInteger bigInt = new BigInteger(1, randomBytes);

        StringBuilder hexString = new StringBuilder(bigInt.toString(7));
        while (hexString.length() < length) {
            hexString.insert(0, '0');
        }

        return hexString.substring(0, length);
    }
}
