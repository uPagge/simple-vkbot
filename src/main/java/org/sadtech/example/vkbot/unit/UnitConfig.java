package org.sadtech.example.vkbot.unit;

import org.sadtech.social.bot.domain.Clarification;
import org.sadtech.social.bot.domain.unit.AnswerCheck;
import org.sadtech.social.bot.domain.unit.AnswerText;
import org.sadtech.social.bot.domain.unit.AnswerValidity;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.vk.bot.sdk.utils.VkApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnitConfig {

    @Bean
    public AnswerText defaultUnit() {
        return AnswerText.of("Не знаю что вам на это ответить");
    }

    @Bean
    public AnswerText hello(
            AnswerValidity map
    ) {
        return AnswerText.builder()
                .boxAnswer(
                        BoxAnswer.builder()
                                .message("Привет, %firstname%! Чем могу помочь")
                                .stickerId(21)
                                .build()
                )
                .nextUnit(map)
                .build();
    }

    @Bean
    public AnswerValidity map(
            VkApi vkApi,
            AnswerText noCityText,
            AnswerCheck checkCity
    ) {
        return AnswerValidity.builder()
                .clarificationQuestion(
                        message -> vkApi.getUserCity(message.getPersonId().intValue())
                                .map(
                                        s -> Clarification.builder()
                                                .question(BoxAnswer.of("Ваш город " + s + "?"))
                                                .value(s)
                                                .build()
                                ).get()
                )
                .unitNo(noCityText)
                .unitYes(checkCity)
                .unitNull(noCityText)
                .keyWord("добраться")
                .keyWord("доехать")
                .build();
    }

    @Bean
    public AnswerText noCityText(
            AnswerCheck checkCity
    ) {
        return AnswerText.builder()
                .boxAnswer(BoxAnswer.of("Уточните ваш город"))
                .nextUnit(checkCity)
                .build();
    }

    @Bean
    public AnswerCheck checkCity() {
        return AnswerCheck.builder()
                .check(message -> message.getText().equalsIgnoreCase("москва"))
                .unitTrue(AnswerText.of("Добраться можно на троллейбусе"))
                .unitFalse(AnswerText.of("Сожалею, но в вашем городе мы не работаем"))
                .build();
    }


}
