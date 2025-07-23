package com.kaba4cow.imgxiv.common.condition;

import java.util.Map;
import java.util.Objects;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnPropertyEnabledCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnPropertyEnabled.class.getName());
		if (Objects.isNull(attributes))
			return false;

		String prefix = (String) attributes.get("prefix");
		boolean matchIfMissing = (Boolean) attributes.get("matchIfMissing");

		String key = prefix.concat(".enabled");
		String value = context.getEnvironment().getProperty(key);

		return Objects.isNull(value)//
				? matchIfMissing//
				: value.equalsIgnoreCase("true");
	}

}
