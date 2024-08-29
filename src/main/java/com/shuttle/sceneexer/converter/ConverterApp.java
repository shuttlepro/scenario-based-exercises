package com.shuttle.sceneexer.converter;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: Shuttle
 * @description: 转换器测试应用
 */
@Slf4j
public class ConverterApp {
    public static void main(String[] args) {
        Converter<UserDto, User> userConverter = new UserConverter();

//        UserDto userDto = new UserDto(1001L, "Smith");
//        User user = userConverter.convertFromDto(userDto);
//        log.info("Convert user: {}", user);

        User user = new User(1001L, "Smith", "Smith@gmail.com");
        UserDto userDto = userConverter.convertFromEntity(user);
        log.info("Convert userDto: {}", userDto);

//        List<User> users = List.of(
//                new User(1001L, "Smith", "Smith@gmail.com"),
//                new User(1002L, "John", "John@gmail.com"),
//                new User(1003L, "Mike", "Mike@gmail.com")
//        );
//        log.info("Domain entities:");
//        users.stream().map(User::toString).forEach(log::info);
//
//        log.info("DTO entities converted from domain:");
//        List<UserDto> dtoEntities = userConverter.createFromEntities(users);
//        dtoEntities.stream().map(UserDto::toString).forEach(log::info);

        List<UserDto> userDtos = List.of(
                new UserDto(1001L, "Smith"),
                new UserDto(1002L, "John"),
                new UserDto(1003L, "Mike")
        );
        log.info("DTO entities:");
        userDtos.stream().map(UserDto::toString).forEach(log::info);

        log.info("Domain entities converted from DTO:");
        List<User> users = userConverter.createFromDtos(userDtos);
        users.stream().map(User::toString).forEach(log::info);
    }
}
