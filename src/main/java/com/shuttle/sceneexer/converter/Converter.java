package com.shuttle.sceneexer.converter;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: Shuttle
 * @description: 对象转换器
 */
@RequiredArgsConstructor
public class Converter<T, U> {

    /**
     * <T> DTO 对象
     * <U> Entity 对象
     */
    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    /**
     * 将 Entity 对象转换为 DTO 对象
     *
     * @param entity 待转换的 Entity 对象
     * @return 转换后生成的 DTO 对象
     */
    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }

    /**
     * 将 DTO 对象转换为 Entity 对象
     *
     * @param dto 待转换的 dto 对象
     * @return 转换后生成的 Entity 对象
     */
    public final U convertFromDto(final T dto) {
        return fromDto.apply(dto);
    }

    /**
     * 将 Entity 对象集合转换为 DTO 对象集合
     *
     * @param entities 待转换的 Entity 对象集合
     * @return 转换后生成的 DTO 对象集合
     */
    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }

    /**
     * 将 DTO 对象集合转换为 Entity 对象集合
     *
     * @param dtos 待转换的 dto 对象集合
     * @return 转换后生成的 Entity 对象集合
     */
    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

}
