package com.shuttle.sceneexer.converter;

/**
 * @author: Shuttle
 * @description: 用户对象转换器
 */
public class UserConverter extends Converter<UserDto, User> {

    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static UserDto convertToDto(User user) {
        return new UserDto(user.userId(), user.userName());
    }

    private static User convertToEntity(UserDto dto) {
        return new User(dto.userId(), dto.userName(), null);
    }

}
