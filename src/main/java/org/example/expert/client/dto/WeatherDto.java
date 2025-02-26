package org.example.expert.client.dto;

import lombok.Getter;

@Getter
public record WeatherDto(String date, String weather) {

}
