package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.service.PostService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostForm postForm = (PostForm) target;

            List<String> tags = Arrays.stream(postForm.getTags().split("\\s+"))
                    .filter(str -> !    str.isEmpty())
                    .collect(Collectors.toList());
            boolean isCorrect = tags.stream().allMatch(tag -> tag.matches("[a-z]+") && tag.length() < 30);
            if (!isCorrect) {
                errors.rejectValue("tags", "tags.invalid-tags", "invalid tags");
            }
        }
    }
}
