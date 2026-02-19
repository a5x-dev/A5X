package br.com.a5x;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import quickfix.Application;
import quickfix.FieldNotFound;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.UnsupportedMessageType;
import quickfix.field.Password;
import quickfix.field.Username;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.MessageCracker;

public class FixClient extends MessageCracker implements Application {

    private static final Logger log = LoggerFactory.getLogger(FixClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    
    private final SessionSettings settings;
    private Integer partyId;

    public FixClient(SessionSettings settings) {
        this.settings = settings;
    }

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("Session created: {}", sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("Logon successful: {}", sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("Logout: {}", sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        if (message instanceof quickfix.fix44.Logon) {
            log.info("Sending Logon message...");
            try {
                String username = getSettingOrEnv(sessionID, "Username", "FIX_USERNAME");
                String password = getSettingOrEnv(sessionID, "Password", "FIX_PASSWORD");
                
                if (settings != null && settings.isSetting(sessionID, "PartyId")) {
                    partyId = settings.getInt(sessionID, "PartyId");
                }
                
                if (username != null && !username.isBlank()) {
                    message.setString(Username.FIELD, username);
                }
                if (password != null && !password.isBlank()) {
                    message.setString(Password.FIELD, password);
                }
            } catch (Exception e) {
                log.error("Error setting logon credentials", e);
            }
        } else if (message instanceof quickfix.fix44.Heartbeat) {
            log.debug("Sending Heartbeat: {}", message);
        } else {
            log.debug("Sending admin message: {}", message);
        }
    }
    
    private String getSettingOrEnv(SessionID sessionID, String settingKey, String envKey) {
        try {
            if (settings != null && settings.isSetting(sessionID, settingKey)) {
                String value = settings.getString(sessionID, settingKey);
                if (value != null && !value.isBlank()) return value;
            }
        } catch (Exception ignored) {}
        return System.getenv(envKey);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        if (message instanceof quickfix.fix44.Heartbeat) {
            log.debug("Heartbeat received: {}", message);
        } else {
            log.info("Admin message received: {}", message);
        }
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        log.info("Sending message: {} to {}", message, sessionId);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectTagValue, UnsupportedMessageType {
        log.info("App received: {}", message);
        crack(message, sessionId);
    }

    public void onMessage(ExecutionReport report, SessionID sessionId) throws FieldNotFound {
        log.info("ExecutionReport: {} | Status: {}", report.getClOrdID().getValue(), report.getOrdStatus().getValue());
    }

    public void sendNewMessage(SessionID sessionId, String resourcePath, String msgName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.error("Resource not found: {}", resourcePath);
                return;
            }
            
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Message message = new Message();
            includeA5XFields(message, jsonString, msgName);
            log.info("Message will Send {}", message);
            Session.lookupSession(sessionId).send(message);
            log.info("Message Sent {}", msgName);
        } catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage());
        }
    }

    private void includeA5XFields(Message message, String originalJsonString, String msgName) {
        message.getHeader().setString(35, "U8");
        message.setString(5003, msgName);
        message.setInt(448, partyId);
        
        String jsonPayload = normalizeJson(originalJsonString);
        byte[] jsonBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);
        
        message.setInt(5001, jsonBytes.length);
        message.setString(5002, jsonPayload);
        message.setString(11, UUID.randomUUID().toString());
        message.setUtcTimeStamp(60, LocalDateTime.now(ZoneId.of("UTC")));
    }
    
    private String normalizeJson(String json) {
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(json);
            return OBJECT_MAPPER.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            log.error("Error normalizing JSON: {}", e.getMessage());
            return json;
        }
    }
}
