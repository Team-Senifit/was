package com.senifit.was.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.senifit.was.entity.selections.BaseSelectionEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SelectionEnumJacksonConfig {

    /**
     * Serializer that writes selection enums as their global code string.
     */
    public static class SelectionEnumSerializer extends StdSerializer<BaseSelectionEnum> {
        public SelectionEnumSerializer() {
            super(BaseSelectionEnum.class);
        }

        @Override
        public void serialize(BaseSelectionEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
                return;
            }
            gen.writeString(value.getCode());
        }
    }

    /**
     * Deserializer that reads a code string and resolves to an enum constant of the target enum type.
     */
    public static class SelectionEnumDeserializer<E extends Enum<E> & BaseSelectionEnum> extends StdDeserializer<E> {
        private final Class<E> enumType;

        public SelectionEnumDeserializer(Class<E> enumType) {
            super(enumType);
            this.enumType = enumType;
        }

        @Override
        public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String code = node.isTextual() ? node.asText() : null;
            if (code == null) {
                return null;
            }
            return BaseSelectionEnum.fromCode(enumType, code);
        }
    }

    @Bean
    public com.fasterxml.jackson.databind.Module selectionEnumModule() {
        SimpleModule module = new SimpleModule("SelectionEnumModule") {
            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addSerializers(new Serializers.Base() {
                    @Override
                    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
                        Class<?> raw = type.getRawClass();
                        if (raw.isEnum() && BaseSelectionEnum.class.isAssignableFrom(raw)) {
                            return new SelectionEnumSerializer();
                        }
                        return null;
                    }
                });
                context.addDeserializers(new Deserializers.Base() {
                    @Override
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) {
                        if (type.isEnum() && BaseSelectionEnum.class.isAssignableFrom(type)) {
                            return new SelectionEnumDeserializer(type);
                        }
                        return null;
                    }
                });
            }
        };
        return module;
    }
}


